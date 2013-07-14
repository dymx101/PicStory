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

import org.apache.log4j.Logger;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * Abstract Queue-based ZooKeeper Synchronizer.
 *
 * <p>This provides a framework for implementing many of the basic blocking concurrency patterns
 * that rely on ZooKeeper's first-in, first-out ordering guarantees. It is designed to provide
 * most of the ordering issues that are present in most ZooKeeper concurrency algorithms.
 *
 * <p>This class should be considered as the ZooKeeper equivalent to
 * {@link java.util.concurrent.locks.AbstractQueuedSynchronizer}, as it provides much of the same
 * patterns. However, state-management does <em>not</em> take much place internally to this
 * api, since state management in the distributed sense is much more algorithm-dependent than
 * in the standard concurrent world.
 *
 * <p>Subclasses should be designed as a non-public internal helper class that implement the
 * synchronization properties of their enclosing class.
 *
 * <p> This class supports either or both <em>exclusive</em> and <em>shared</em> modes. When
 * acquired in exclusive mode, attempted acquires by other parties cannot succeed. Shared mode
 * acquires by multiple parties may (but are not required to) succeed.
 *
 *
 * @author Scott Fines
 *         Date: Apr 22, 2011
 *         Time: 11:10:25 AM
 */
@Beta
public abstract class ZkQueuedSynchronizer extends ZkPrimitive2{
    private static final Logger logger = Logger.getLogger(ZkQueuedSynchronizer.class);


    /**
     * Creates a new ZkPrimitive with the correct node information.
     *
     * @param baseNode         the base node to use
     * @param privileges       the privileges for this node.
     * @param executor         the command executor to use
     */
    protected ZkQueuedSynchronizer(String baseNode, ZkCommandExecutor executor,List<ACL> privileges) {
        super(baseNode, executor,privileges);
    }

    /**
     * Acquires in exclusive mode, ignoring interrupts. Implemented by invoking at least
     * once {@link #tryAcquireDistributed(org.apache.zookeeper.ZooKeeper, String, boolean)}, returning
     * on success. Otherwise the thread lies dormant until it is informed by the ZooKeeper event
     * system that it may try again.
     *
     * <p>Note: This implementation will call the {@code tryAcquireDistributed} from within a
     * {@link ZkCommandExecutor}, and may call the same method repeatedly before the thread sleeps.
     *
     * @return the name of the node which represents the caller's lock
     * @throws KeeperException if an Exception occurs while communicating with ZooKeeper
     */
    public final String acquire() throws KeeperException {
        final String node = commandExecutor.execute(new ZkCommand<String>() {
            @Override
            public String execute(ZooKeeper zk) throws KeeperException, InterruptedException {
                logger.trace("Creating node");
                return createNode(zk);
            }
        });
        commandExecutor.execute(new ZkCommand<Void>() {
            @Override
            public Void execute(ZooKeeper zk) throws KeeperException, InterruptedException {

                while(true){
                    logger.trace("Acquiring local lock");
                    localLock.lock();
                    try{
                        logger.trace("Attempting distributed acquisition");
                        boolean acquired = tryAcquireDistributed(zk,node,true);
                        if(!acquired){
                            logger.trace("Distributed acquisition unsuccessful, waiting for notification");
                            condition.awaitUninterruptibly();
                        }else{
                            logger.trace("Distributed acquisition successful");
                            return null;
                        }
                    }finally{
                        localLock.unlock();
                    }
                }
            }
        });
        return node;
    }

    public final String acquireShared() throws KeeperException{
        final String node = commandExecutor.execute(new ZkCommand<String>() {
            @Override
            public String execute(ZooKeeper zk) throws KeeperException, InterruptedException {
                return createNode(zk);
            }
        });
        commandExecutor.execute(new ZkCommand<Void>() {
            @Override
            public Void execute(ZooKeeper zk) throws KeeperException, InterruptedException {
                while(true){
                    localLock.lock();
                    try{
                        boolean acquired = tryAcquireSharedDistributed(zk,node,true);
                        if(!acquired){
                            condition.awaitUninterruptibly();
                        }else{
                            return null;
                        }
                    }finally{
                        localLock.unlock();
                    }
                }
            }
        });
        return node;
    }

    /**
     * Acquires in exclusive mode Implemented by invoking at least
     * once {@link #tryAcquireDistributed(org.apache.zookeeper.ZooKeeper, String, boolean)}, returning
     * on success. Otherwise the thread lies dormant until it is informed by the ZooKeeper event
     * system that it may try again.
     *
     * <p>This implementation is equivalent to {@link #acquire()} except that it is responsive to
     * Interruption; upon receiving an interruption event, the Interrupted status of the Thread
     * will be cleared and an {@link InterruptedException} will be thrown.
     *
     * <p>Note: This implementation will call the {@code tryAcquireDistributed} from within a
     * {@link ZkCommandExecutor}, and may call the same method repeatedly before the thread sleeps.
     *
     * @return the name of the node which represents the caller's lock
     * @throws KeeperException if an Exception occurs while communicating with ZooKeeper
     * @throws InterruptedException if the current thread is interrupted
     */
    public final String acquireInterruptibly() throws InterruptedException, KeeperException {
        final String node = commandExecutor.executeInterruptibly(new ZkCommand<String>() {
            @Override
            public String execute(ZooKeeper zk) throws KeeperException, InterruptedException {
                return createNode(zk);
            }
        });
        commandExecutor.executeInterruptibly(new ZkCommand<Void>() {
            @Override
            public Void execute(ZooKeeper zk) throws KeeperException, InterruptedException {
                boolean acquired=false;
                while (!acquired) {
                    logger.trace("Attempting to acquire interruptibly");
                    localLock.lock();
                    try {
                        acquired = tryAcquireDistributedInterruptibly(zk,node, true);
                        if (!acquired) {
                            condition.await();
                        }
                    } finally {
                        localLock.unlock();
                    }
                }
                logger.trace("Acquired interruptibly");
                return null;
            }
        });
        return node;
    }

    public final String acquireSharedInterruptibly() throws InterruptedException, KeeperException {
        final String node = commandExecutor.executeInterruptibly(new ZkCommand<String>() {
            @Override
            public String execute(ZooKeeper zk) throws KeeperException, InterruptedException {
                return createNode(zk);
            }
        });
        commandExecutor.executeInterruptibly(new ZkCommand<Void>() {
            @Override
            public Void execute(ZooKeeper zk) throws KeeperException, InterruptedException {
                boolean acquired=false;
                while (!acquired) {
                    logger.trace("Attempting to acquire interruptibly");
                    localLock.lock();
                    try {
                        acquired = tryAcquireSharedDistributedInterruptibly(zk,node, true);
                        if (!acquired) {
                            condition.await();
                        }
                    } finally {
                        localLock.unlock();
                    }
                }
                logger.trace("Acquired interruptibly");
                return null;
            }
        });
        return node;
    }

    /**
     * Attempts to acquire in exclusive mode. This method will query ZooKeeper to see if
     * acquisition is allowed to succeed by calling
     * {@link #tryAcquireDistributed(org.apache.zookeeper.ZooKeeper, String, boolean)}. If that
     * method returns success, then this will return the name of the lock node that was created.
     * Otherwise, null is returned.
     *
     * <p>Note: This implementation will call the
     * {@link #tryAcquireDistributed(org.apache.zookeeper.ZooKeeper, String, boolean)} method from
     * inside a {@link ZkCommandExecutor}, and therefore may make a call to that method several times
     * before returning.
     *
     * @return the name of the acquired node, or null if no acquisition was possible
     * @throws KeeperException if an Exception occurs will communicating with ZooKeeper
     */
    public final String tryAcquire() throws KeeperException{
        final String node = commandExecutor.execute(new ZkCommand<String>() {
            @Override
            public String execute(ZooKeeper zk) throws KeeperException, InterruptedException {
                return createNode(zk);
            }
        });
        boolean acquired = commandExecutor.execute(new ZkCommand<Boolean>() {
            @Override
            public Boolean execute(ZooKeeper zk) throws KeeperException, InterruptedException {
                return tryAcquireDistributed(zk, node, false);
            }
        });
        if(!acquired){
            cleanupAfterFailure(node);
            return null;
        }

        return node;
    }

    public final String tryAcquireShared() throws KeeperException{
        final String node = commandExecutor.execute(new ZkCommand<String>() {
            @Override
            public String execute(ZooKeeper zk) throws KeeperException, InterruptedException {
                return createNode(zk);
            }
        });
        boolean acquired = commandExecutor.execute(new ZkCommand<Boolean>() {
            @Override
            public Boolean execute(ZooKeeper zk) throws KeeperException, InterruptedException {
                return tryAcquireSharedDistributed(zk, node, false);
            }
        });
        if(!acquired){
            cleanupAfterFailure(node);
            return null;
        }

        return node;
    }

    /**
     * Attemps to acquire in exclusive mode, aborting if interrupted, and failing if the
     * given timeout elapses. Implemented by invoking
     * {@link #tryAcquireDistributed(org.apache.zookeeper.ZooKeeper, String, boolean)} at least once,
     * returning on success. Otherwise, the thread lies dormant until either it has been informed by
     * ZooKeeper to try again, it has been interrupted, or the time has elapsed. If the time has not
     * elapsed, and the thread has not been interrupted, then this implementation will try again.
     *
     * <p>Note: The timeout will, be necessity, not be an exact representation of the total time
     * allowed. Because of network and other system effects, it may take longer than the maximum
     * time to elapse before failing, or it may return success after the system clock has registered
     * the timeout to have passed.
     *
     * @param timeoutNanos the maximum number of nanoseconds to wait
     * @return the name of the node representing the exclusive acquisition, or null if acquisition failed
     * @throws KeeperException if an error occurs communicating with ZooKeeper
     * @throws InterruptedException if the thread has been interrupted
     */
    public final String tryAcquireNanos(long timeoutNanos) throws KeeperException,InterruptedException{
        if(Thread.currentThread().isInterrupted())
            throw new InterruptedException();
        long timeout = timeoutNanos;

        long start = System.nanoTime();
        final String node = commandExecutor.executeInterruptibly(new ZkCommand<String>() {
            @Override
            public String execute(ZooKeeper zk) throws KeeperException, InterruptedException {
                if (Thread.currentThread().isInterrupted())
                    throw new InterruptedException();

                return createNode(zk);
            }
        });
        long end = System.nanoTime();
        if(Thread.currentThread().isInterrupted()){
            logger.trace("Thread has been interrupted, aborting attempt");
            cleanupAfterFailure(node);
            throw new InterruptedException();
        }
        timeout-=(end-start);
        if(timeout<0){
            logger.trace("Timeout has happened before acquisition occurred; aborting attempt");
            cleanupAfterFailure(node);
            return null;
        }
        while(timeout>0){
            if(Thread.currentThread().isInterrupted())
                throw new InterruptedException();
            start = System.nanoTime();
            boolean localAcquired = localLock.tryLock(timeout, TimeUnit.NANOSECONDS);
            end = System.nanoTime();
            timeout-=(end-start);
            if(!localAcquired||timeout<0){
                logger.trace("Local lock not acquired within the specified timeout, aborting attempt");
                cleanupAfterFailure(node);
                return null;
            }
            try{
                start = System.nanoTime();
                boolean acquired = commandExecutor.execute(new ZkCommand<Boolean>() {
                    @Override
                    public Boolean execute(ZooKeeper zk) throws KeeperException, InterruptedException {
                        if(Thread.currentThread().isInterrupted())
                            throw new InterruptedException();

                        return tryAcquireDistributed(zk,node,true);
                    }
                });
                end=System.nanoTime();
                timeout-=(end-start);
                if(timeout<0){
                    cleanupAfterFailure(node);
                    return null;
                }else if(!acquired){
                    timeout = condition.awaitNanos(timeout);
                }else{
                    return node;
                }
            }finally{
                localLock.unlock();
            }
        }
        if(timeout<0){
            logger.trace("Timeout occurred before acquisition could occur; Aborting attempt");
            cleanupAfterFailure(node);
            return null;
        }
        logger.trace("Attempt successful!");
        return node;
    }

    public final String tryAcquireSharedNanos(long timeoutNanos) throws KeeperException,InterruptedException{
        if(Thread.currentThread().isInterrupted())
            throw new InterruptedException();
        long timeout = timeoutNanos;

        long start = System.nanoTime();
        final String node = commandExecutor.executeInterruptibly(new ZkCommand<String>() {
            @Override
            public String execute(ZooKeeper zk) throws KeeperException, InterruptedException {
                if (Thread.currentThread().isInterrupted())
                    throw new InterruptedException();

                return createNode(zk);
            }
        });
        long end = System.nanoTime();
        if(Thread.currentThread().isInterrupted()){
            logger.trace("Thread has been interrupted, aborting attempt");
            cleanupAfterFailure(node);
            throw new InterruptedException();
        }
        timeout-=(end-start);
        if(timeout<0){
            logger.trace("Timeout has happened before acquisition occurred; aborting attempt");
            cleanupAfterFailure(node);
            return null;
        }
        while(timeout>0){
            if(Thread.currentThread().isInterrupted())
                throw new InterruptedException();
            start = System.nanoTime();
            boolean localAcquired = localLock.tryLock(timeout, TimeUnit.NANOSECONDS);
            end = System.nanoTime();
            timeout-=(end-start);
            if(!localAcquired||timeout<0){
                logger.trace("Local lock not acquired within the specified timeout, aborting attempt");
                cleanupAfterFailure(node);
                return null;
            }
            try{
                start = System.nanoTime();
                boolean acquired = commandExecutor.execute(new ZkCommand<Boolean>() {
                    @Override
                    public Boolean execute(ZooKeeper zk) throws KeeperException, InterruptedException {
                        if(Thread.currentThread().isInterrupted())
                            throw new InterruptedException();

                        return tryAcquireSharedDistributed(zk, node, true);
                    }
                });
                end=System.nanoTime();
                timeout-=(end-start);
                if(timeout<0){
                    cleanupAfterFailure(node);
                    return null;
                }else if(!acquired){
                    timeout = condition.awaitNanos(timeout);
                }else{
                    return node;
                }
            }finally{
                localLock.unlock();
            }
        }
        if(timeout<0){
            logger.trace("Timeout occurred before acquisition could occur; Aborting attempt");
            cleanupAfterFailure(node);
            return null;
        }
        logger.trace("Attempt successful!");
        return node;
    }


    /**
     * Releases in exclusive mode. Implemented by calling at least once
     * {@link #tryReleaseDistributed(org.apache.zookeeper.ZooKeeper)}.
     *
     *
     * @throws KeeperException if an error occurs communicating with ZooKeeper
     */
    public final void release() throws KeeperException{
        commandExecutor.execute(new ZkCommand<Void>() {
            @Override
            public Void execute(ZooKeeper zk) throws KeeperException, InterruptedException {
                tryReleaseDistributed(zk);
                return null;
            }
        });
    }

    public final void releaseShared() throws KeeperException{
        commandExecutor.execute(new ZkCommand<Void>() {
            @Override
            public Void execute(ZooKeeper zk) throws KeeperException, InterruptedException {
                tryReleaseSharedDistributed(zk);
                return null;
            }
        });
    }

    /**
     * Creates a new node for usage in the synchronizer. For example, a Lock implementation
     * would create (or find) it's required lock node inside this method.
     *
     * <p>This method is assumed to <em>always</em> be uninterruptible.
     *
     * <p>Note: This method should <em>always</em> be called from inside a
     * {@link ZkCommandExecutor}, to protect against system effects.
     *
     *
     * @param zk the ZooKeeper instance to use
     * @return the name of the node which is created
     * @throws KeeperException if an error occurs talking to ZooKeeper.
     */
    protected String createNode(ZooKeeper zk) throws KeeperException{
        throw new UnsupportedOperationException();
    }

    /**
     * Attempts to acquire exclusively in a distributed manner, ignoring interrupts.
     *
     * <p>The {@code watch} parameter is set to true whenever a ZooKeeper watch should be
     * set on a node of some kind. This flag should <em>never</em> be ignored, as ignoring
     * it may result in deadlocks occurring with other methods in this class.
     *
     * <p>This method is assumed to <em>always</em> be uninterruptible.
     *
     * <p>Note: This method should <em>always</em> be called from inside a
     * {@link ZkCommandExecutor}, to protect against system effects.
     *
     *
     * @param zk the ZooKeeper instance to use
     * @param path the path of interest. It is usually created
     * by {@link #createNode(org.apache.zookeeper.ZooKeeper)}
     * @param watch set to true if a ZooKeeper watch should be set
     * @return true if the distributed acquisition was successful
     * @throws KeeperException if an Error occurs while communicating with ZooKeeper
     */
    protected boolean tryAcquireDistributed(ZooKeeper zk,String path,boolean watch) throws KeeperException {
        throw new UnsupportedOperationException();
    }

    /**
     * Attempts to acquire exclusively in a distributed manner, aborting on interrupt.
     *
     * <p>The {@code watch} parameter is set to true whenever a ZooKeeper watch should be
     * set on a node of some kind. This flag should <em>never</em> be ignored, as ignoring
     * it may result in deadlocks occurring with other methods in this class.
     *
     * <p>Note: This method should <em>always</em> be called from inside a
     * {@link ZkCommandExecutor}, to protect against system effects.
     *
     *
     * @param zk the ZooKeeper instance to use
     * @param path the path of interest. It is usually created
     * by {@link #createNode(org.apache.zookeeper.ZooKeeper)}
     * @param watch set to true if a ZooKeeper watch should be set
     * @return true if the distributed acquisition was successful
     * @throws KeeperException if an Error occurs while communicating with ZooKeeper
     */
    protected boolean tryAcquireDistributedInterruptibly(ZooKeeper zk,String path,boolean watch) throws KeeperException,InterruptedException {
        throw new UnsupportedOperationException();
    }



    protected boolean tryAcquireSharedDistributed(ZooKeeper zk,String path, boolean watch) throws KeeperException {
        throw new UnsupportedOperationException();
    }

    protected boolean tryAcquireSharedDistributedInterruptibly(ZooKeeper zk,String path, boolean watch) throws KeeperException,InterruptedException{
        throw new UnsupportedOperationException();
    }


    protected boolean tryReleaseSharedDistributed(ZooKeeper zk)throws KeeperException{
        throw new UnsupportedOperationException();
    }

    /**
     * Attempts to perform a distributed release in exclusive mode.
     *
     * <p>Note: this method should <em>always</em> be called from inside a
     * {@link ZkCommandExecutor}, to protect against system effects.
     *
     * @param zk the ZooKeeper instance to use
     * @return true if release is successful
     * @throws KeeperException if an Error occurs while communicating with ZooKeeper
     */
    protected boolean tryReleaseDistributed(ZooKeeper zk) throws KeeperException{
        throw new UnsupportedOperationException();
    }

/*----------------------------------------------------------------------------------------------*/
    /*private helper methods*/

    /*Cleans up after failing to acquire in some way*/
    private void cleanupAfterFailure(final String node) throws KeeperException {
        commandExecutor.execute(new ZkCommand<Void>() {
            @Override
            public Void execute(ZooKeeper zk) throws KeeperException, InterruptedException {
                ZkUtils.safeDelete(zk, node, -1);
                return null;
            }
        });
    }

}
