/*
 * Copyright 2010 Scott Fines
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.menagerie.locks;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.menagerie.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Will eventually replace the ReentrantZkLock when I'm sure it's good enough.
 * @author Scott Fines
 *         Date: Apr 25, 2011
 *         Time: 4:12:06 PM
 */
class ReentrantZkLock2 implements Lock {
//    private static final Logger logger = Logger.getLogger(ReentrantZkLock2.class);
    private final Sync sync;
    private static final byte[] emptyBytes = new byte[]{};
    private final ZkCommandExecutor executor;
    private final String machineId;
    private final List<ACL> privileges;
    private final String baseNode;

    private final LockHolder holder = new LockHolder();


    public ReentrantZkLock2(String baseNode, ZkSessionManager zkSessionManager){
        this(baseNode,new ZkCommandExecutor(zkSessionManager),ZooDefs.Ids.OPEN_ACL_UNSAFE);
    }

    public ReentrantZkLock2(String baseNode, ZkSessionManager zkSessionManager,List<ACL> privileges){
        this(baseNode,new ZkCommandExecutor(zkSessionManager),privileges);
    }

    public ReentrantZkLock2(String baseNode,ZkCommandExecutor executor, List<ACL> privileges) {
        this.executor = executor;
        this.privileges = privileges;
        this.baseNode = baseNode;
        try{
            machineId = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        this.sync = new Sync(baseNode,executor,privileges);

        ensureNodeExists();
    }

    @Override
    public void lock() {
        if(holder.increment())return; //we have successful reentrancy
        try{
            String lockNode = sync.acquire();
            holder.setHoldingThread(lockNode);
        } catch (KeeperException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        if(Thread.currentThread().isInterrupted())
            throw new InterruptedException();

        if(holder.increment())return; //already have the lock
        try{
            holder.setHoldingThread(sync.acquireInterruptibly());
        }catch(KeeperException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean tryLock() {
        if(holder.increment()) return true; //already have the lock
        try{
            final String node = sync.tryAcquire();
            if(node!=null)
                holder.setHoldingThread(node);
            return node!=null;
        } catch (KeeperException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean tryLock(long l, TimeUnit timeUnit) throws InterruptedException {
        if(Thread.interrupted())
            throw new InterruptedException();
        if(holder.increment()) return true; //already have the lock
        try {
            final String nodeName = sync.tryAcquireNanos(timeUnit.toNanos(l));
            if(nodeName!=null)
                holder.setHoldingThread(nodeName);
            return nodeName!=null;
        } catch (KeeperException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void unlock() {
        int remaining = holder.decrement();
        if(remaining==0){
            try {
                sync.release();
                holder.clear();
            } catch (KeeperException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException();
    }

    protected String getLockPrefix() {
        return "lock";
    }

    private String getBaseLockPath(){
        return baseNode+"/"+getPartyId();
    }


/*--------------------------------------------------------------------------------------------------------------------*/
    /*private helper methods*/


    private String getPartyId(){
        return getLockPrefix()+'-'+machineId+'-'+Thread.currentThread().getId()+'-';
    }

    private void ensureNodeExists() {
        try {
            executor.execute(new ZkCommand<Void>() {
                @Override
                public Void execute(ZooKeeper zk) throws KeeperException, InterruptedException {
                    ZkUtils.recursiveSafeCreate(zk,baseNode,emptyBytes,privileges, CreateMode.PERSISTENT);
                    return null;
                }
            });
        } catch (KeeperException e) {
            throw new RuntimeException(e);
        }
    }

    private class Sync extends ZkQueuedSynchronizer{
        /**
         * Creates a new ZkPrimitive with the correct node information.
         *
         * @param baseNode         the base node to use
         * @param privileges       the privileges for this node.
         * @param executor
         */
        protected Sync(String baseNode, ZkCommandExecutor executor, List<ACL> privileges) {
            super(baseNode, executor, privileges);
        }

        @Override
        protected boolean tryAcquireDistributed(ZooKeeper zk, String path, boolean watch) throws KeeperException {
            boolean complete=false;
            try{
                complete=tryAcquireDistributedInterruptibly(zk,path,watch);
            }catch(InterruptedException ie){
                //set the thread's interrupt status
                Thread.currentThread().interrupt();
            }
            return complete;
        }

        @Override
        protected boolean tryAcquireDistributedInterruptibly(ZooKeeper zk, String path, boolean watch) throws KeeperException, InterruptedException {
            List<String> locks = ZkUtils.filterByPrefix(zk.getChildren(baseNode,false),getLockPrefix());
            ZkUtils.sortBySequence(locks,'-');
            String myNodeName = path.substring(path.lastIndexOf('/')+1);
            int myPos = locks.indexOf(myNodeName);

            int nextNodePos = myPos-1;
            while(nextNodePos>=0){
                Stat stat;
                if(watch){
                    stat=zk.exists(baseNode+"/"+locks.get(nextNodePos),signalWatcher);
                }else{
                    stat=zk.exists(baseNode+"/"+locks.get(nextNodePos),false);
                }if(stat!=null){
                    //there is a node which already has the lock, so we need to wait for notification that that
                    //node is gone
                    return false;
                }else{
                    nextNodePos--;
                }
            }
            return true;
        }

        @Override
        protected String createNode(ZooKeeper zk) throws KeeperException {
            String completeNode=null;
            try{
                String node = getNode(zk);
                if(node!=null)
                    return node;

                //nobody exists with my party's unique name, so create one and return it
                completeNode = ZkUtils.safeCreate(zk, getBaseLockPath(), emptyBytes, privileges, CreateMode.EPHEMERAL_SEQUENTIAL);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return completeNode;
        }

        private String getNode(ZooKeeper zk) throws InterruptedException, KeeperException {
            List<String> lockChildren = ZkUtils.filterByPrefix(zk.getChildren(baseNode, false), getLockPrefix());
            //see if lockChildren contains an entry with my unique identifier. If it does, return that
            String myLockId = getPartyId();
            for (String lockChild : lockChildren) {
                if (lockChild.startsWith(myLockId)) {
                    return baseNode+'/'+lockChild;
                }
            }
            return null;
        }

        @Override
        protected boolean tryReleaseDistributed(ZooKeeper zk) throws KeeperException{
            try {
                String lockNode = holder.getLockNode();
                return lockNode == null || ZkUtils.safeDelete(zk, lockNode, -1);
            }catch (InterruptedException e) {
                return false;
            }
        }
    }
}

