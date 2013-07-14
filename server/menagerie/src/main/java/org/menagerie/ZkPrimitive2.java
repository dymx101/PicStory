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
package org.menagerie;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Scott Fines
 *         Date: Apr 22, 2011
 *         Time: 10:51:39 AM
 */
public class ZkPrimitive2 {

    /**
     * Represents an empty znode (no data on the node)
     */
    protected static final byte[] emptyNode = new byte[]{};

    /**
     * The Session Manager to use with this Primitive
     */
    protected final ZkCommandExecutor commandExecutor;

    /**
     * The base node for all behaviors
     */
    protected final String baseNode;

    /**
     * The ACL privileges for this Primitive to use
     */
    protected final List<ACL> privileges;

    /**
     * A local mutex lock for managing inter-thread synchronization
     */
    protected final Lock localLock;

    /**
     * A local mutex condition, associated with {@link #localLock}, for causing threads to wait for watch
     * conditions.
     */
    protected final Condition condition;

    /**
     * Set to {@code true} if the ZooKeeper Session ever expires. Otherwise, should be set to false.
     * <p>
     * Setting this to {@code true} will cause some subclasses to cancel their activities, on the basis that they are
     * no longer able to complete a given task.
     */
    protected volatile boolean broken=false;

    /**
     * A signalling watcher, whose job it is to call {@link java.util.concurrent.locks.Condition#signal()} or
     * {@link java.util.concurrent.locks.Condition#signalAll()} to notify any threads sleeping through the
     * local {@link #condition} instance.
     */
    protected final Watcher signalWatcher;

    /**
     * Creates a new ZkPrimitive with the correct node information.
     *
     * @param baseNode the base node to use
     * @param commandExecutor the session manager to use
     * @param privileges the privileges for this node.
     */
    protected ZkPrimitive2(String baseNode, ZkCommandExecutor commandExecutor, List<ACL> privileges) {
        if(baseNode==null)
            throw new NullPointerException("No base node specified!");
        this.baseNode = baseNode;
        this.commandExecutor = commandExecutor;
        this.privileges = privileges;

        this.localLock = new ReentrantLock(true);
        condition = this.localLock.newCondition();
        signalWatcher = new SignallingWatcher(this);
        ensureNodeExists();
    }

    /**
     * Ensures that the base node exists in ZooKeeper.
     * <p>
     * Note: This method does NOT create elements recursively--if the base node is a sub-node of a
     * node which doesn't exist, a NoNode Exception will be thrown.
     *
     * @throws RuntimeException wrapping a KeeperException if something goes wrong communicating with the ZooKeeper server
     *         RuntimeException wrapping an InterruptedException if something goes wrong communicating with the ZooKeeper
     *                                  Server.
     */
    protected final void ensureNodeExists(){
        try {
            commandExecutor.execute(new ZkCommand<Void>() {
                @Override
                public Void execute(ZooKeeper zk) throws KeeperException, InterruptedException {
                    ZkUtils.safeCreate(zk,baseNode,emptyNode,privileges,CreateMode.PERSISTENT);
                    return null;
                }
            });
        } catch (KeeperException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o.getClass()!=this.getClass()) return false;

        ZkPrimitive that = (ZkPrimitive) o;

        return baseNode.equals(that.baseNode);
    }

    @Override
    public int hashCode() {
        return baseNode.hashCode();
    }

    /**
     * Notifies any/all parties which may be waiting for {@link #signalWatcher} to fire.
     */
    protected void notifyParties(){
        localLock.lock();
        try{
            condition.signalAll();
        }finally{
            localLock.unlock();
        }
    }

    private static class SignallingWatcher implements Watcher {
        private final ZkPrimitive2 primitive;

        private SignallingWatcher(ZkPrimitive2 primitive) {
            this.primitive = primitive;
        }

        @Override
        public void process(WatchedEvent event) {

            primitive.notifyParties();
        }
    }

}
