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

import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.menagerie.ZkCommandExecutor;
import org.menagerie.ZkQueuedSynchronizer;
import org.menagerie.ZkSessionManager;
import org.menagerie.ZkUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * @author Scott Fines
 *         Date: 5/27/11
 *         Time: 2:42 PM
 */
class ReentrantZkReadWriteLock2 implements ReadWriteLock {
    private static final Logger logger = Logger.getLogger(ReentrantZkReadWriteLock2.class);
    private ReadWriteLockHolder lockHolder = new ReadWriteLockHolder();
    private final String machineId;
    private static final byte[] emptyBytes = new byte[]{};
    private final ReadLock readLock;
    private final WriteLock writeLock;

    public ReentrantZkReadWriteLock2(String baseNode, ZkSessionManager sessionManager){
         this(baseNode,new ZkCommandExecutor(sessionManager), ZooDefs.Ids.OPEN_ACL_UNSAFE);
    }

    public ReentrantZkReadWriteLock2(String baseNode, ZkSessionManager sessionManager,List<ACL> privileges){
        this(baseNode,new ZkCommandExecutor(sessionManager), privileges);
    }

    public ReentrantZkReadWriteLock2(String baseNode, ZkCommandExecutor executor, List<ACL> privileges){
        this.readLock = new ReadLock(baseNode,executor,privileges);
        this.writeLock = new WriteLock(baseNode,executor,privileges);

        try{
            this.machineId = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Lock readLock() {
        return readLock;
    }

    @Override
    public Lock writeLock() {
        return writeLock;
    }

    public final class ReadLock implements Lock{
        private final ReadSync sync;

        public ReadLock(String baseNode,ZkCommandExecutor commandExecutor, List<ACL> privileges) {
            sync = new ReadSync(baseNode,commandExecutor,privileges);
        }

        @Override
        public void lock() {
            if(lockHolder.readIncrement())return;
            try{
								logger.trace("readIncrement=false, creating");
                lockHolder.setReadingThread(sync.acquireShared());
            } catch (KeeperException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void lockInterruptibly() throws InterruptedException {
            if(Thread.currentThread().isInterrupted())
                throw new InterruptedException();

            if(lockHolder.readIncrement())return;
            try{
                lockHolder.setReadingThread(sync.acquireSharedInterruptibly());
            } catch (KeeperException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public boolean tryLock() {
            if(lockHolder.readIncrement())return true;
            try{
                String node = sync.tryAcquireShared();
                if(node!=null)
                    lockHolder.setReadingThread(node);
                return node!=null;
            } catch (KeeperException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public boolean tryLock(long l, TimeUnit timeUnit) throws InterruptedException {
            if(Thread.interrupted())
                throw new InterruptedException();
            if(lockHolder.readIncrement()) return true; //already have the lock
            try {
                final String nodeName = sync.tryAcquireSharedNanos(timeUnit.toNanos(l));
                if(nodeName!=null)
                    lockHolder.setReadingThread(nodeName);
                return nodeName!=null;
            } catch (KeeperException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void unlock() {
            logger.trace("Attempting to unlock read lock");
            int remaining = lockHolder.readDecrement();
            logger.trace("read locks remaining: "+ remaining);
            if(remaining==0){
                try{
                    logger.trace("No read locks remaining, attempting to release shared");
                    sync.releaseShared();
                    lockHolder.readClear();
                    logger.trace("shared release successful");
                } catch (KeeperException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        @Override
        public Condition newCondition() {
            throw new UnsupportedOperationException();
        }
    }

    public final class WriteLock implements Lock{
        private final WriteSync sync;

        public WriteLock(String baseNode,ZkCommandExecutor commandExecutor, List<ACL> privileges) {
            sync = new WriteSync(baseNode,commandExecutor,privileges);
        }

        @Override
        public void lock() {
            if(lockHolder.writeIncrement())return;
            try{
                lockHolder.setWritingThread(sync.acquireShared());
            } catch (KeeperException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void lockInterruptibly() throws InterruptedException {
            if(Thread.currentThread().isInterrupted())
                throw new InterruptedException();
            if(lockHolder.writeIncrement())return;
            try{
                lockHolder.setWritingThread(sync.acquireSharedInterruptibly());
            } catch (KeeperException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public boolean tryLock() {
            if(lockHolder.writeIncrement())return true;
            try{
                String node = sync.tryAcquireShared();
                if(node!=null)
                    lockHolder.setWritingThread(node);
                return node!=null;
            } catch (KeeperException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public boolean tryLock(long l, TimeUnit timeUnit) throws InterruptedException {
            if(Thread.interrupted())
                throw new InterruptedException();
            if(lockHolder.writeIncrement()) return true; //already have the lock
            try {
                final String nodeName = sync.tryAcquireSharedNanos(timeUnit.toNanos(l));
                if(nodeName!=null)
                    lockHolder.setWritingThread(nodeName);
                return nodeName!=null;
            } catch (KeeperException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void unlock() {
            int remaining = lockHolder.writeDecrement();
            if(remaining<=0){
                try{
                    sync.releaseShared();
										lockHolder.writeClear();
                    //lockHolder.readClear();
                } catch (KeeperException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        @Override
        public Condition newCondition() {
            throw new UnsupportedOperationException();
        }
    }

    abstract class Sync extends ZkQueuedSynchronizer{

        /**
         * Creates a new ZkPrimitive with the correct node information.
         *
         * @param baseNode   the base node to use
         * @param privileges the privileges for this node.
         * @param executor   the command executor to use
         */
        protected Sync(String baseNode, ZkCommandExecutor executor, List<ACL> privileges) {
            super(baseNode, executor, privileges);
        }

        @Override
        protected final String createNode(ZooKeeper zk) throws KeeperException {
            logger.trace("Attempting to create node");
            String completeNode=null;
            try{
                String node = getNode(zk);
                if(node!=null)
                    return node;

                logger.trace("No previous node exists, creating a new one");
                //nobody exists with my party's unique name, so create one and return it
                completeNode = doCreateNode(zk);
            } catch (InterruptedException e) {
                //restore the interrupt status
                Thread.currentThread().interrupt();
            }
            return completeNode;
        }

        @Override
        protected final boolean tryAcquireSharedDistributed(ZooKeeper zk, String path, boolean watch) throws KeeperException {
            try {
                return tryAcquireSharedDistributedInterruptibly(zk,path,watch);
            } catch (InterruptedException e) {
                //don't swallow the Interruption, just ignored for the purposes of this attempt
                Thread.currentThread().interrupt();
                return false;
            }
        }

        private String getNode(ZooKeeper zk) throws InterruptedException, KeeperException {
            List<String> lockChildren = ZkUtils.filterByPrefix(zk.getChildren(baseNode, false), getLockPrefix());
            //see if lockChildren contains an entry with my unique identifier. If it does, return that
            String myLockId = getPartyId();
            for (String lockChild : lockChildren) {
                if (lockChild.startsWith(myLockId)) {
                    return baseNode+"/"+lockChild;
                }
            }
            return null;
        }

        protected abstract String doCreateNode(ZooKeeper zk) throws KeeperException, InterruptedException;

        protected final String getPartyId(){
            return getLockPrefix()+'-'+machineId+'-'+Thread.currentThread().getId()+'-';
        }

        protected abstract String getLockPrefix();
    }

    private class ReadSync extends Sync{

        /**
         * Creates a new ZkPrimitive with the correct node information.
         *
         * @param baseNode   the base node to use
         * @param privileges the privileges for this node.
         * @param executor   the command executor to use
         */
        protected ReadSync(String baseNode, ZkCommandExecutor executor, List<ACL> privileges) {
            super(baseNode, executor, privileges);
        }

        @Override
        protected String doCreateNode(ZooKeeper zk) throws KeeperException,InterruptedException{
            String writeNode = lockHolder.getWriteNode();
            if(writeNode!=null){
                //we can just create a readnode with the same information
                String nodeName = baseNode+'/'+getPartyId()+'-'+ZkUtils.parseSequenceString(writeNode,'-');
                String readNode;
                try {
                    readNode = ZkUtils.safeCreate(zk,nodeName, emptyNode, privileges, CreateMode.EPHEMERAL);
                } catch (InterruptedException e) {
                    //TODO -sf- is this correct? Make sure to test with high latency connection...

                    //clean up--safely delete the created node
                    ZkUtils.uninterruptibleSafeDelete(zk,nodeName,-1);
                    Thread.currentThread().interrupt();
                    return null;
                }
                //put the state into the lockHolder
                lockHolder.setReadingThread(readNode);
                return readNode;
            }else{
                try {
                    return zk.create(baseNode+'/'+getPartyId()+'-',emptyNode,privileges,CreateMode.EPHEMERAL_SEQUENTIAL);
                } catch (InterruptedException e) {
                    //TODO -sf- is this correct? Make sure to test with high latency connection...
                    Thread.currentThread().interrupt();
                    return null;
                }
            }
        }

        @Override
        protected boolean tryAcquireSharedDistributedInterruptibly(ZooKeeper zk, String path, boolean watch)
                                                                        throws KeeperException,InterruptedException {
            if(lockHolder.getReadNode()!=null)return true; //we already have it
            long mySeqNumber = ZkUtils.parseSequenceNumber(path,'-');

            List<String> aheadWriteLocks = ZkUtils.filterByPrefix(zk.getChildren(baseNode,false),"writeLock");
            //filter out the writeLocks that came around after we created an element
						Iterator<String> aheadIter = aheadWriteLocks.iterator();
            while(aheadIter.hasNext()){
								String writeLock = aheadIter.next();
                if(ZkUtils.parseSequenceNumber(writeLock,'-')>mySeqNumber){
										aheadIter.remove();
                }
            }

            ZkUtils.sortByReverseSequence(aheadWriteLocks, '-');

            while(aheadWriteLocks.size()>0){
                String lastWriteLock = aheadWriteLocks.remove(0);
                Stat stat;
                if(watch){
                    stat = zk.exists(baseNode+'/'+lastWriteLock,signalWatcher);
                }else
                    stat = zk.exists(baseNode+'/'+lastWriteLock,false);
                if(stat!=null)
                    return false;
            }
            return true;
        }

        @Override
        protected boolean tryReleaseSharedDistributed(ZooKeeper zk) throws KeeperException {
            try {
                String readNode = lockHolder.getReadNode();
                logger.trace("Attempting to remove node "+ readNode);
                return ZkUtils.safeDelete(zk, readNode,-1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }

        @Override
        protected String getLockPrefix() {
            return "readLock";
        }
    }

    private class WriteSync extends Sync{

        /**
         * Creates a new ZkPrimitive with the correct node information.
         *
         * @param baseNode   the base node to use
         * @param privileges the privileges for this node.
         * @param executor   the command executor to use
         */
        protected WriteSync(String baseNode, ZkCommandExecutor executor, List<ACL> privileges) {
            super(baseNode, executor, privileges);
        }


        @Override
        protected String doCreateNode(ZooKeeper zk) throws KeeperException, InterruptedException {
            return ZkUtils.safeCreate(zk, baseNode+'/'+getPartyId(), emptyBytes, privileges, CreateMode.EPHEMERAL_SEQUENTIAL);
        }


        @Override
        protected boolean tryAcquireSharedDistributedInterruptibly(ZooKeeper zk, String path, boolean watch)
                                                                        throws KeeperException,InterruptedException {
            logger.trace("Attempting interruptible shared acquisition on node "+path);
            long mySeqNumber = ZkUtils.parseSequenceNumber(path,'-');

            List<String> otherLocks = ZkUtils.filterByPrefix(zk.getChildren(baseNode,false),"writeLock","readLock");
            //filter out the writeLocks that came around after we created an element
            List<String> aheadLocks = new LinkedList<String>();
            for(String writeLock:otherLocks){
                if(ZkUtils.parseSequenceNumber(writeLock,'-')<mySeqNumber){
                    logger.trace("Node "+ writeLock+" was created after I was, removing");
                    aheadLocks.add(writeLock);
                }
            }

            ZkUtils.sortByReverseSequence(aheadLocks, '-');
            logger.trace("lock nodes which may be ahead: "+ aheadLocks);
            while(aheadLocks.size()>0){
                String lastLock = aheadLocks.remove(0);
                Stat stat;
                if(watch){
                    stat = zk.exists(baseNode+'/'+lastLock,signalWatcher);
                }else{
                    stat = zk.exists(baseNode+'/'+lastLock,false);
                }
                if(stat!=null){
                    logger.trace("Node "+ lastLock+" still exists and is lower orderd than I am, cannot acquire");
                    return false;
                }
            }
						logger.trace("Successfully acquired write lock "+path);
            return true;
        }

        @Override
        protected boolean tryReleaseSharedDistributed(ZooKeeper zk) throws KeeperException {
            try {
                return ZkUtils.safeDelete(zk,lockHolder.getWriteNode(),-1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }

        @Override
        protected String getLockPrefix() {
            return "writeLock";
        }
    }


    private static final class ReadWriteLockHolder{
//        private final LockHolder readLockHolder = new LockHolder();
        private final LockHolder writeLockHolder = new LockHolder();
        private volatile String readNode;
        private final AtomicInteger readCount = new AtomicInteger(0);

        public boolean readIncrement(){
            if(readNode!=null){
                int count = readCount.incrementAndGet();
								logger.trace("number of read locks acquired="+count);
                return true;
            }else{
                return false;
            }
        }

        public boolean writeIncrement(){
						//logged by LockHolder
           return writeLockHolder.increment();
        }

        public int readDecrement(){
            int count =readCount.decrementAndGet();
						logger.trace("Decrementing read to value "+ count);
						return count;
        }

        public int writeDecrement(){
            int count= writeLockHolder.decrement();
						logger.trace("Decrementing write to value "+ count);
						return count;
        }

        public void setReadingThread(String lockNode){
            readCount.set(1);
            readNode = lockNode;
            logger.trace("lockNode= "+lockNode+", readCount=1");
        }

        public void setWritingThread(String lockNode){
            writeLockHolder.setHoldingThread(lockNode);
        }

        public String getWriteNode(){
            return writeLockHolder.getLockNode();
        }

        public String getReadNode(){
            return readNode;
//            return readLockHolder.getLockNode();
        }

        public void readClear() {
						logger.trace("Clearing read lock data");
            readCount.set(0);
            readNode=null;
        }

        public void writeClear(){
           writeLockHolder.clear();
        }
    }
}
