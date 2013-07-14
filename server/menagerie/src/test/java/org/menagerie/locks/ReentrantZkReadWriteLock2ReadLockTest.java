package org.menagerie.locks;

import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.menagerie.BaseZkSessionManager;
import org.menagerie.ZkSessionManager;
import org.menagerie.ZkUtils;
import org.menagerie.util.TestingThreadFactory;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.menagerie.TestUtils.newZooKeeper;

/**
 * @author Scott Fines
 *         Date: 5/30/11
 *         Time: 6:45 AM
 */
public class ReentrantZkReadWriteLock2ReadLockTest {

    private static final Logger logger = Logger.getLogger(ReentrantZkLock2Test.class);
    private static final String hostString = "localhost:2181";
    private static final String baseLockPath = "/test-readwrite-locks-readlock-tests-2";
    private static final int timeout = 2000;
    private static final ExecutorService testService = Executors.newFixedThreadPool(2, new TestingThreadFactory());

    private static ZooKeeper zk;
    private static ZkSessionManager zkSessionManager;

    @Before
    public void setup() throws Exception {
        zk = newZooKeeper(hostString,timeout);

        //be sure that the lock-place is created
        ZkUtils.recursiveSafeDelete(zk, baseLockPath, -1);
        zk.create(baseLockPath,new byte[]{}, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        zkSessionManager = new BaseZkSessionManager(zk);
    }

    @After
    public void tearDown() throws Exception{
        try{
            List<String> children = zk.getChildren(baseLockPath,false);
            for(String child:children){
                ZkUtils.safeDelete(zk, baseLockPath +"/"+child,-1);
            }
            ZkUtils.safeDelete(zk, baseLockPath,-1);

        }catch(KeeperException ke){
            //suppress because who cares what went wrong after our tests did their thing?
        }finally{
            zk.close();
        }
    }

    @Test(timeout = 1000l)
    public void testReadLockAcquiresThenReleases() throws Exception {
         /*
         Tests that a read lock creates a node in ZooKeeper, and that releasing it
         removes that node from ZooKeeper
         */
        ReadWriteLock rwLock = new ReentrantZkReadWriteLock2(baseLockPath, zkSessionManager);
        Lock readLock = rwLock.readLock();
        readLock.lock();
        try{
            //check the lock path to see if there is a node there
            assertEquals("A Read lock node was not created!",1,ZkUtils.filterByPrefix(zkSessionManager.getZooKeeper().getChildren(baseLockPath,false),"readLock").size());
        }finally{
            readLock.unlock();
        }

        //check that that node was properly deleted
        assertEquals("A Read lock node was not deleted!",0,ZkUtils.filterByPrefix(zkSessionManager.getZooKeeper().getChildren(baseLockPath,false),"readLock").size());
    }

    @Test(timeout = 1000l)
    public void testTwoReadLocksCanAcquireSimultaneously() throws Exception{
        /*
        Tests that two different ReadLock instances can acquire the same lock simultaneously
        */
        final CountDownLatch latch = new CountDownLatch(1);
        ReadWriteLock rwLock = new ReentrantZkReadWriteLock2(baseLockPath, zkSessionManager);
        Lock readLock = rwLock.readLock();
        readLock.lock();
        try{
            Future<Void> future = testService.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    ReadWriteLock rwLock2 = new ReentrantZkReadWriteLock2(baseLockPath,zkSessionManager);
                    Lock readLock2 = rwLock2.readLock();
                    readLock2.lock();
                    try{
                        latch.countDown();
                    }finally{
                        readLock2.unlock();
                    }
                    return null;
                }
            });

            //this will time out if the second lock can't acquire
            latch.await();

            //makre sure no exceptions are thrown
            future.get();
        }finally{
            readLock.unlock();
        }
    }

    @Test(timeout = 1000l)
    public void testOneReadLockDoesNotAllowDifferentWriteLockAccess() throws Exception{
        /*
        Tests that if a Read lock has the lock, then a different write lock instance can't acquire
        */
        final CountDownLatch latch = new CountDownLatch(1);
        ReadWriteLock rwLock = new ReentrantZkReadWriteLock2(baseLockPath,zkSessionManager);
        Lock readLock = rwLock.readLock();
        logger.debug("Acquiring read lock");
        readLock.lock();
        Future<Void> future;
        try{
            future = testService.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {

                    ReadWriteLock secondLock = new ReentrantZkReadWriteLock2(baseLockPath,zkSessionManager);
                    Lock writeLock = secondLock.writeLock();
                    logger.debug("Attempting to acquire write lock");
                    writeLock.lock();
                    logger.debug("Write lock acquired");
                    try{
                        latch.countDown();
                    }finally{
                        logger.debug("Attempting to unlock write lock");
                        writeLock.unlock();
                        logger.debug("unlock of write lock successful");
                    }
                    return null;
                }
            });

            boolean notAcquired = !latch.await(250, TimeUnit.MILLISECONDS);
            assertTrue("The write lock was improperly acquired!",notAcquired);

        }finally{
            logger.debug("Attempting to unlock read lock");
            readLock.unlock();
            logger.debug("unlock of read lock successful");
        }

        //check that the lock gets acquired correctly
        final boolean acquired = latch.await(500, TimeUnit.SECONDS);
        assertTrue("Write lock was never acquired!",acquired);
        future.get();
    }

    @Test(timeout = 1000l)
    public void testOneReadLockDoesNotAllowSameWriteLockAccess() throws Exception{
        /*
        Tests that if a Read lock has the lock, then the same write lock instance can't acquire
        */
        final CountDownLatch latch = new CountDownLatch(1);
        final ReadWriteLock rwLock = new ReentrantZkReadWriteLock2(baseLockPath,zkSessionManager);
        Lock readLock = rwLock.readLock();
        logger.debug("Acquiring read lock");
        readLock.lock();
        Future<Void> future;
        try{
            future = testService.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {

//                    ReadWriteLock secondLock = new ReentrantZkReadWriteLock2(baseLockPath,zkSessionManager);
                    Lock writeLock = rwLock.writeLock();
                    logger.debug("Attempting to acquire write lock");
                    writeLock.lock();
                    logger.debug("Write lock acquired");
                    try{
                        latch.countDown();
                    }finally{
                        logger.debug("Attempting to unlock write lock");
                        writeLock.unlock();
                        logger.debug("unlock of write lock successful");
                    }
                    return null;
                }
            });

            boolean notAcquired = !latch.await(250, TimeUnit.MILLISECONDS);
            assertTrue("The write lock was improperly acquired!",notAcquired);

        }finally{
            logger.debug("Attempting to unlock read lock");
            readLock.unlock();
            logger.debug("unlock of read lock successful");
        }

        //check that the lock gets acquired correctly
        final boolean acquired = latch.await(500, TimeUnit.SECONDS);
        assertTrue("Write lock was never acquired!",acquired);
        future.get();
    }

    @Test(timeout = 1000l)
    public void testReadLocksReentrant() throws Exception{
       /*
        Tests that lock() is reentrant by allowing two locks to acquire on the same thread.

        Note: This test relies on a WriteLock being unable to acquire while ReadLocks own the lock.
        If this mechanism is broken, then this test will fail
        */
        final ReentrantZkReadWriteLock2 rwLock = new ReentrantZkReadWriteLock2(baseLockPath, zkSessionManager);
        Lock readLock = rwLock.readLock();

        readLock.lock();
        Future<Void> writeFuture = testService.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                Lock writeLock = rwLock.writeLock();
                writeLock.lockInterruptibly();
                try{
                    return null;
                }finally {
                    writeLock.unlock();
                }
            }
        });
        //make sure write lock does not acquire
        boolean timedOut = false;
        try{
            writeFuture.get(100, TimeUnit.MILLISECONDS);
        }catch(TimeoutException te){
            //this is what we hope for, so we can move on
            timedOut=true;
        }
        assertTrue("WriteLock was incorrectly acquired!",timedOut);

        //lock again
        readLock.lock();

        //make sure that we still can't acquire the write lock
        timedOut = false;
        try{
            writeFuture.get(100, TimeUnit.MILLISECONDS);
        }catch(TimeoutException te){
            //this is what we hope for, so we can move on
            timedOut=true;
        }
        assertTrue("WriteLock was incorrectly acquired!",timedOut);

        //now unlock one lock
        readLock.unlock();

        //make sure that we still are safely acquired
         timedOut = false;
        try{
            writeFuture.get(100, TimeUnit.MILLISECONDS);
        }catch(TimeoutException te){
            //this is what we hope for, so we can move on
            timedOut=true;
        }
        assertTrue("WriteLock was incorrectly acquired after releasing only one lock!",timedOut);

        //unlock the last lock
        readLock.unlock();

        //now we should be perfectly okay, so fail if it times out
        writeFuture.get();
    }

    @Test(timeout = 1000l)
    public void testReadLocksInterruptiblyReentrant() throws Exception{
        /*
        Tests that lockInterruptibly is reentrant by allowing two locks to acquire on the same thread.

        Note: This test relies on a WriteLock being unable to acquire while ReadLocks own the lock.
        If this mechanism is broken, then this test will fail
        */
        final ReentrantZkReadWriteLock2 rwLock = new ReentrantZkReadWriteLock2(baseLockPath, zkSessionManager);
        Lock readLock = rwLock.readLock();

        readLock.lockInterruptibly();
        Future<Void> writeFuture = testService.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                Lock writeLock = rwLock.writeLock();
                writeLock.lockInterruptibly();
                try{
                    return null;
                }finally {
                    writeLock.unlock();
                }
            }
        });
        //make sure write lock does not acquire
        boolean timedOut = false;
        try{
            writeFuture.get(100, TimeUnit.MILLISECONDS);
        }catch(TimeoutException te){
            //this is what we hope for, so we can move on
            timedOut=true;
        }
        assertTrue("WriteLock was incorrectly acquired!",timedOut);

        //lock again
        readLock.lockInterruptibly();

        //make sure that we still can't acquire the write lock
        timedOut = false;
        try{
            writeFuture.get(100, TimeUnit.MILLISECONDS);
        }catch(TimeoutException te){
            //this is what we hope for, so we can move on
            timedOut=true;
        }
        assertTrue("WriteLock was incorrectly acquired!",timedOut);

        //now unlock one lock
        readLock.unlock();

        //make sure that we still are safely acquired
         timedOut = false;
        try{
            writeFuture.get(100, TimeUnit.MILLISECONDS);
        }catch(TimeoutException te){
            //this is what we hope for, so we can move on
            timedOut=true;
        }
        assertTrue("WriteLock was incorrectly acquired after releasing only one lock!",timedOut);

        //unlock the last lock
        readLock.unlock();

        //now we should be perfectly okay, so fail if it times out
        writeFuture.get();
    }

    @Test(timeout = 1000l)
    public void testReadLockInterruptible() throws Exception{
        /*
        Tests that a read lock's attempt to Lock can be interrupted in
        mid-acquisition.

        Note: This depends on the ability of a single write lock to block
        the acquisition of a read lock. This method will likely fail if that
        does not work correctly
        */
        final CyclicBarrier barrier = new CyclicBarrier(2);
        final ReadWriteLock rwLock = new ReentrantZkReadWriteLock2(baseLockPath,zkSessionManager);
        final AtomicReference<Thread> otherThread = new AtomicReference<Thread>();
        Future<Boolean> future = testService.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                otherThread.set(Thread.currentThread());
                Lock readLock = rwLock.readLock();
                //wait for other thread to be ready
                barrier.await();
                //now attempt to lock interruptibly
                try{
                    readLock.lockInterruptibly();
                }catch(InterruptedException ie){
                    //we were successfully interrupted
                    return true;
                }
                return false;
            }
        });
        final Lock writeLock = rwLock.writeLock();
        writeLock.lock();
        try{
            barrier.await(); //now we are ready to interrupt the other thread

            //interrupt the other thread
            otherThread.get().interrupt();

            //make sure we get back true
            boolean interrupted = future.get();
            assertTrue("Other thread was not interrupted!",interrupted);
        }finally{
            writeLock.unlock();
        }
    }

    @Test(timeout = 1000l)
    public void testReadLockInterruptibleOnEntry() throws Exception{
        /*
        Tests that a read lock will recognize that it has been interrupted
        on entry of the lockInterruptibly() method
        */

        final CyclicBarrier barrier = new CyclicBarrier(2);
        final ReadWriteLock rwLock = new ReentrantZkReadWriteLock2(baseLockPath,zkSessionManager);
        final AtomicReference<Thread> otherThread = new AtomicReference<Thread>();
        Future<Boolean> future = testService.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                otherThread.set(Thread.currentThread());
                barrier.await();
                //wait until the current thread is interrupted
                while(!Thread.currentThread().isInterrupted()){
                    //spin lock until we know we're interrupted
                }
                //attempt to lock interruptibly--should blow up
                try{
                    rwLock.readLock().lockInterruptibly();
                }catch(InterruptedException ie){
                    return true;
                }
                return false;
            }
        });

        barrier.await();
        //we know the thread has been set
        otherThread.get().interrupt();
        //wait for completion
        final Boolean interrupted = future.get();
        assertTrue("Lock did not respond to interruption!",interrupted);
    }


    @Test(timeout = 1000l)
    public void testTryReadLockPreventsSameWriteLockAccess() throws Exception{
        /*
        Tests that if a Read lock has the lock, then the same write lock instance can't acquire
        */
        final CountDownLatch latch = new CountDownLatch(1);
        final ReadWriteLock rwLock = new ReentrantZkReadWriteLock2(baseLockPath,zkSessionManager);
        Lock readLock = rwLock.readLock();
        logger.debug("Acquiring read lock");
        readLock.tryLock();
        Future<Void> future;
        try{
            future = testService.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {

//                    ReadWriteLock secondLock = new ReentrantZkReadWriteLock2(baseLockPath,zkSessionManager);
                    Lock writeLock = rwLock.writeLock();
                    logger.debug("Attempting to acquire write lock");
                    writeLock.lock();
                    logger.debug("Write lock acquired");
                    try{
                        latch.countDown();
                    }finally{
                        logger.debug("Attempting to unlock write lock");
                        writeLock.unlock();
                        logger.debug("unlock of write lock successful");
                    }
                    return null;
                }
            });

            boolean notAcquired = !latch.await(250, TimeUnit.MILLISECONDS);
            assertTrue("The write lock was improperly acquired!",notAcquired);

        }finally{
            logger.debug("Attempting to unlock read lock");
            readLock.unlock();
            logger.debug("unlock of read lock successful");
        }

        //check that the lock gets acquired correctly
        final boolean acquired = latch.await(500, TimeUnit.SECONDS);
        assertTrue("Write lock was never acquired!",acquired);
        future.get();
    }

    @Test(timeout = 1000l)
    public void testTryReadLockReturnsFalseIfNotAcquirable() throws Exception{
        /*
        Tests that tryLock will return false if the lock cannot be acquired

        Note: This depends on writeLock's ability to correctly stop read locks
        from acquiring. If that mechanism is incorrect, then this test will
        fail
        */
        final ReadWriteLock rwLock = new ReentrantZkReadWriteLock2(baseLockPath,zkSessionManager);
        Lock writeLock = rwLock.writeLock();
        writeLock.lock();
        try{
            final Future<Boolean> future = testService.submit(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return rwLock.readLock().tryLock();
                }
            });

            boolean notAcquired = !future.get();
            assertTrue("Read Lock incorrectly reported successful acquisition!",notAcquired);
        }finally{
            writeLock.unlock();
        }

    }

    @Test(timeout = 1000l)
    public void testTryReadLockReentrant() throws Exception{
        /*
        Tests that tryLock is reentrant by allowing two locks to acquire on the same thread.

        Note: This test relies on a WriteLock being unable to acquire while ReadLocks own the lock.
        If this mechanism is broken, then this test will fail
        */
        final ReentrantZkReadWriteLock2 rwLock = new ReentrantZkReadWriteLock2(baseLockPath, zkSessionManager);
        Lock readLock = rwLock.readLock();

        boolean acquired = readLock.tryLock();
        assertTrue("lock not acquired!",acquired);
        Future<Void> writeFuture = testService.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                Lock writeLock = rwLock.writeLock();
                writeLock.lockInterruptibly();
                try{
                    return null;
                }finally {
                    writeLock.unlock();
                }
            }
        });
        //make sure write lock does not acquire
        boolean timedOut = false;
        try{
            writeFuture.get(100, TimeUnit.MILLISECONDS);
        }catch(TimeoutException te){
            //this is what we hope for, so we can move on
            timedOut=true;
        }
        assertTrue("WriteLock was incorrectly acquired!",timedOut);

        //lock again
        acquired = readLock.tryLock();
        assertTrue("lock not acquired reentrantly!",acquired);

        //make sure that we still can't acquire the write lock
        timedOut = false;
        try{
            writeFuture.get(100, TimeUnit.MILLISECONDS);
        }catch(TimeoutException te){
            //this is what we hope for, so we can move on
            timedOut=true;
        }
        assertTrue("WriteLock was incorrectly acquired!",timedOut);

        //now unlock one lock
        readLock.unlock();

        //make sure that we still are safely acquired
         timedOut = false;
        try{
            writeFuture.get(100, TimeUnit.MILLISECONDS);
        }catch(TimeoutException te){
            //this is what we hope for, so we can move on
            timedOut=true;
        }
        assertTrue("WriteLock was incorrectly acquired after releasing only one lock!",timedOut);

        //unlock the last lock
        readLock.unlock();

        //now we should be perfectly okay, so fail if it times out
        writeFuture.get();
    }

    @Test(timeout = 1000l)
    public void testTryReadLockTimedPreventsSameWriteLockAccess() throws Exception{
        /*
        Tests that if a Read lock acquires the lock through a timed mechanism,
        then the same write lock instance can't acquire
        */
        final CountDownLatch latch = new CountDownLatch(1);
        final ReadWriteLock rwLock = new ReentrantZkReadWriteLock2(baseLockPath,zkSessionManager);
        Lock readLock = rwLock.readLock();
        logger.debug("Acquiring read lock");
        boolean acquired = readLock.tryLock(100, TimeUnit.MILLISECONDS);
        assertTrue("Read lock was not correctly acquired!",acquired);
        Future<Void> future;
        try{
            future = testService.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {

//                    ReadWriteLock secondLock = new ReentrantZkReadWriteLock2(baseLockPath,zkSessionManager);
                    Lock writeLock = rwLock.writeLock();
                    logger.debug("Attempting to acquire write lock");
                    writeLock.lock();
                    logger.debug("Write lock acquired");
                    try{
                        latch.countDown();
                    }finally{
                        logger.debug("Attempting to unlock write lock");
                        writeLock.unlock();
                        logger.debug("unlock of write lock successful");
                    }
                    return null;
                }
            });

            boolean notAcquired = !latch.await(250, TimeUnit.MILLISECONDS);
            assertTrue("The write lock was improperly acquired!",notAcquired);

        }finally{
            logger.debug("Attempting to unlock read lock");
            readLock.unlock();
            logger.debug("unlock of read lock successful");
        }

        //check that the lock gets acquired correctly
        acquired = latch.await(500, TimeUnit.SECONDS);
        assertTrue("Write lock was never acquired!",acquired);
        future.get();
    }

    @Test(timeout = 1000l)
    public void testTryReadLockTimedReturnsFalseIfNotAcquirable() throws Exception{
        /*
        Tests that tryLockTimed will return false if the lock cannot be acquired in the time specified

        Note: This depends on writeLock's ability to correctly stop read locks
        from acquiring. If that mechanism is incorrect, then this test will
        fail
        */
        final ReadWriteLock rwLock = new ReentrantZkReadWriteLock2(baseLockPath,zkSessionManager);
        Lock writeLock = rwLock.writeLock();
        writeLock.lock();
        try{
            final Future<Boolean> future = testService.submit(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return rwLock.readLock().tryLock(200,TimeUnit.MILLISECONDS);
                }
            });

            boolean notAcquired = !future.get();
            assertTrue("Read Lock incorrectly reported successful acquisition!",notAcquired);
        }finally{
            writeLock.unlock();
        }

    }

    @Test(timeout = 1000l)
    public void testTryReadLockTimedReentrant() throws Exception{
        /*
        Tests that tryLock is reentrant by allowing two locks to acquire on the same thread.

        Note: This test relies on a WriteLock being unable to acquire while ReadLocks own the lock.
        If this mechanism is broken, then this test will fail
        */
        final ReentrantZkReadWriteLock2 rwLock = new ReentrantZkReadWriteLock2(baseLockPath, zkSessionManager);
        Lock readLock = rwLock.readLock();

        boolean acquired = readLock.tryLock(100,TimeUnit.MILLISECONDS);
        assertTrue("lock not acquired!",acquired);
        Future<Void> writeFuture = testService.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                Lock writeLock = rwLock.writeLock();
                writeLock.lockInterruptibly();
                try{
                    return null;
                }finally {
                    writeLock.unlock();
                }
            }
        });
        //make sure write lock does not acquire
        boolean timedOut = false;
        try{
            writeFuture.get(100, TimeUnit.MILLISECONDS);
        }catch(TimeoutException te){
            //this is what we hope for, so we can move on
            timedOut=true;
        }
        assertTrue("WriteLock was incorrectly acquired!",timedOut);

        //lock again
        acquired = readLock.tryLock(100,TimeUnit.MILLISECONDS);
        assertTrue("lock not acquired reentrantly!",acquired);

        //make sure that we still can't acquire the write lock
        timedOut = false;
        try{
            writeFuture.get(100, TimeUnit.MILLISECONDS);
        }catch(TimeoutException te){
            //this is what we hope for, so we can move on
            timedOut=true;
        }
        assertTrue("WriteLock was incorrectly acquired!",timedOut);

        //now unlock one lock
        readLock.unlock();

        //make sure that we still are safely acquired
         timedOut = false;
        try{
            writeFuture.get(100, TimeUnit.MILLISECONDS);
        }catch(TimeoutException te){
            //this is what we hope for, so we can move on
            timedOut=true;
        }
        assertTrue("WriteLock was incorrectly acquired after releasing only one lock!",timedOut);

        //unlock the last lock
        readLock.unlock();

        //now we should be perfectly okay, so fail if it times out
        writeFuture.get();
    }

     @Test(timeout = 1000l)
    public void testTryReadLockTimedInterruptible() throws Exception{
        /*
        Tests that a read lock's attempt to Lock can be interrupted in
        mid-acquisition.

        Note: This depends on the ability of a single write lock to block
        the acquisition of a read lock. This method will likely fail if that
        does not work correctly
        */
        final CyclicBarrier barrier = new CyclicBarrier(2);
        final ReadWriteLock rwLock = new ReentrantZkReadWriteLock2(baseLockPath,zkSessionManager);
        final AtomicReference<Thread> otherThread = new AtomicReference<Thread>();
        Future<Boolean> future = testService.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                otherThread.set(Thread.currentThread());
                Lock readLock = rwLock.readLock();
                //wait for other thread to be ready
                barrier.await();
                //now attempt to lock interruptibly
                try{
                    readLock.tryLock(1,TimeUnit.SECONDS);
                }catch(InterruptedException ie){
                    //we were successfully interrupted
                    return true;
                }
                return false;
            }
        });
        final Lock writeLock = rwLock.writeLock();
        writeLock.lock();
        try{
            barrier.await(); //now we are ready to interrupt the other thread

            //interrupt the other thread
            otherThread.get().interrupt();

            //make sure we get back true
            boolean interrupted = future.get();
            assertTrue("Other thread was not interrupted!",interrupted);
        }finally{
            writeLock.unlock();
        }
    }

    @Test(timeout = 1000l)
    public void testTryReadLockTimedInterruptibleOnEntry() throws Exception{
        /*
        Tests that a read lock will recognize that it has been interrupted
        on entry of the lockInterruptibly() method
        */

        final CyclicBarrier barrier = new CyclicBarrier(2);
        final ReadWriteLock rwLock = new ReentrantZkReadWriteLock2(baseLockPath,zkSessionManager);
        final AtomicReference<Thread> otherThread = new AtomicReference<Thread>();
        Future<Boolean> future = testService.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                otherThread.set(Thread.currentThread());
                barrier.await();
                //wait until the current thread is interrupted
                while(!Thread.currentThread().isInterrupted()){
                    //spin lock until we know we're interrupted
                }
                //attempt to lock interruptibly--should blow up
                try{
                    rwLock.readLock().tryLock(1, TimeUnit.SECONDS);
                }catch(InterruptedException ie){
                    return true;
                }
                return false;
            }
        });

        barrier.await();
        //we know the thread has been set
        otherThread.get().interrupt();
        //wait for completion
        final Boolean interrupted = future.get();
        assertTrue("Lock did not respond to interruption!",interrupted);
    }

    @Test(timeout = 1000l)
    public void testTwothreadsHaveAccessViaSameReadLock() throws Exception{
        /*
        Tests that two threads, when using the same read lock, have the same amount of access

        This test will timeout if there is an error
        */
        final CountDownLatch latch = new CountDownLatch(1);
        ReadWriteLock lock = new ReentrantZkReadWriteLock2(baseLockPath,zkSessionManager);
        final Lock readLock = lock.readLock();
        readLock.lock();
        try{
            Future<Void> future = testService.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    readLock.lock();
                    try{
                        latch.countDown();
                    }finally{
                        readLock.unlock();
                    }
                    return null;
                }
            });

            //wait for second thread to access lock
            latch.await();

            //check for errors
            future.get();
        }finally{
            readLock.unlock();
        }
    }
}
