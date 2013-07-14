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
import org.menagerie.TestUtils;
import org.menagerie.ZkSessionManager;
import org.menagerie.ZkUtils;
import org.menagerie.util.TestingThreadFactory;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.menagerie.TestUtils.newZooKeeper;

/**
 * @author Scott Fines
 *         Date: 6/2/11
 *         Time: 4:44 PM
 */
public class ReentrantZkReadWriteLock2WriteLockTest {

    private static final Logger logger = Logger.getLogger(ReentrantZkLock2Test.class);
    private static final String hostString = "localhost:2181";
    private static final String baseLockPath = "/test-readwrite-locks-writelock-tests-2";
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
    public void testWriteLockBlocksReadLockSameInstance() throws Exception{
        /*
        Tests that a single write lock allows no read locks to acquire.

        This is the test of the fundamental purpose of the WriteLock--that it represents
        an exclusive lock.

        This is predicated on the assumption that readLocks CAN successfully acquire
        without contention. If this is not true, this method will fail.
        */
        final ReadWriteLock rwLock = new ReentrantZkReadWriteLock2(baseLockPath,zkSessionManager);
        Lock writeLock = rwLock.writeLock();
        writeLock.lock();
        Future<Void> future;
        try{
            future = testService.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    /*
                    If the read lock never acquires, then this method will block until the timeout
                    kills everything
                    */
                    final Lock readLock = rwLock.readLock();
                    readLock.lock();
                    try{
                        return null;
                    }finally{
                        readLock.unlock();
                    }

                }
            });

            assertTrue("Read lock was acquired prematurely",timeout(future,200,TimeUnit.MILLISECONDS));
        }finally{
            writeLock.unlock();
        }

        //make sure that it successfully releases as well
        //if the write lock fails to correctly release, then this future will cause the test to time out
        future.get();
    }

    @Test(timeout = 1000l)
    public void testWriteLockBlocksReadLockDifferentInstance() throws Exception{
        /*
        Tests that a single write lock allows no read locks to acquire. To simulate
        different Processes in the same JVM, this uses a new ReadWriteLock on each thread.

        This is predicated on the assumption that readLocks CAN successfully acquire
        without contention. If this is not true, this method will fail.
        */
        ReadWriteLock rwLock = new ReentrantZkReadWriteLock2(baseLockPath,zkSessionManager);
        Lock writeLock = rwLock.writeLock();
        writeLock.lock();
        Future<Void> future;
        try{
            future = testService.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    /*
                    If the read lock never acquires, then this method will block until the timeout
                    kills everything
                    */
                    ReadWriteLock rwLock = new ReentrantZkReadWriteLock2(baseLockPath,zkSessionManager);
                    final Lock readLock = rwLock.readLock();
                    readLock.lock();
                    try{
                        return null;
                    }finally{
                        readLock.unlock();
                    }

                }
            });

            assertTrue("Read lock was acquired prematurely",timeout(future,200,TimeUnit.MILLISECONDS));
        }finally{
            writeLock.unlock();
        }

        //make sure that it successfully releases as well
        //if the write lock fails to correctly release, then this future will cause the test to time out
        future.get();

    }

    @Test(timeout = 1000l)
    public void testWriteLockBlocksReadLockDifferentZkInstance() throws Exception{
        /*
        Tests that a single write lock allows no read locks to acquire. To simulate
        different machines, this uses a new ReadWriteLock and a new ZooKeeper instance on each thread.

        This is predicated on the assumption that readLocks CAN successfully acquire
        without contention. If this is not true, this method will fail.
        */
        ReadWriteLock rwLock = new ReentrantZkReadWriteLock2(baseLockPath,zkSessionManager);
        Lock writeLock = rwLock.writeLock();
        writeLock.lock();
        Future<Void> future;
        try{
            future = testService.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    /*
                    If the read lock never acquires, then this method will block until the timeout
                    kills everything
                    */
                    ZooKeeper zooKeeper = TestUtils.newZooKeeper(hostString, timeout);
                    try{
                        ZkSessionManager zksm = new BaseZkSessionManager(zooKeeper);
                        ReadWriteLock rwLock = new ReentrantZkReadWriteLock2(baseLockPath,zksm);
                        Lock readLock = rwLock.readLock();
                        readLock.lock();
                        try{
                            return null;
                        }finally{
                            readLock.unlock();
                        }
                    }finally{
                        zooKeeper.close();
                    }

                }
            });

            assertTrue("Read lock was acquired prematurely",timeout(future,200,TimeUnit.MILLISECONDS));
        }finally{
            writeLock.unlock();
        }

        //make sure that it successfully releases as well
        //if the write lock fails to correctly release, then this future will cause the test to time out
        future.get();

    }

    @Test(timeout = 1000l)
    public void testWriteLockInterruptiblyBlocksReadLockSameInstance() throws Exception{
        /*
        Tests that a single write lock allows no read locks to acquire.

        This is predicated on the assumption that readLocks CAN successfully acquire
        without contention. If this is not true, this method will fail.
        */
        final ReadWriteLock rwLock = new ReentrantZkReadWriteLock2(baseLockPath,zkSessionManager);
        Lock writeLock = rwLock.writeLock();
        writeLock.lockInterruptibly();
        Future<Void> future;
        try{
            future = testService.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    /*
                    If the read lock never acquires, then this method will block until the timeout
                    kills everything
                    */
                    final Lock readLock = rwLock.readLock();
                    readLock.lock();
                    try{
                        return null;
                    }finally{
                        readLock.unlock();
                    }

                }
            });

            assertTrue("Read lock was acquired prematurely",timeout(future,200,TimeUnit.MILLISECONDS));
        }finally{
            writeLock.unlock();
        }

        //make sure that it successfully releases as well
        //if the write lock fails to correctly release, then this future will cause the test to time out
        future.get();

    }

    @Test(timeout = 1000l)
    public void testWriteLockInterruptiblyBlocksReadLockDifferentInstance() throws Exception{
        /*
        Tests that a single write lock allows no read locks to acquire. To simulate
        different Processes in the same JVM, this uses a new ReadWriteLock on each thread.

        This is predicated on the assumption that readLocks CAN successfully acquire
        without contention. If this is not true, this method will fail.
        */
        ReadWriteLock rwLock = new ReentrantZkReadWriteLock2(baseLockPath,zkSessionManager);
        Lock writeLock = rwLock.writeLock();
        writeLock.lockInterruptibly();
        Future<Void> future;
        try{
            future = testService.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    /*
                    If the read lock never acquires, then this method will block until the timeout
                    kills everything
                    */
                    ReadWriteLock rwLock = new ReentrantZkReadWriteLock2(baseLockPath,zkSessionManager);
                    final Lock readLock = rwLock.readLock();
                    readLock.lock();
                    try{
                        return null;
                    }finally{
                        readLock.unlock();
                    }

                }
            });

            assertTrue("Read lock was acquired prematurely",timeout(future,200,TimeUnit.MILLISECONDS));
        }finally{
            writeLock.unlock();
        }

        //make sure that it successfully releases as well
        //if the write lock fails to correctly release, then this future will cause the test to time out
        future.get();

    }

    @Test(timeout = 1000l)
    public void testWriteLockInterruptiblyBlocksReadLockDifferentZkInstance() throws Exception{
        /*
        Tests that a single write lock allows no read locks to acquire. To simulate
        different machines, this uses a new ReadWriteLock and a new ZooKeeper instanceon each thread.

        This is predicated on the assumption that readLocks CAN successfully acquire
        without contention. If this is not true, this method will fail.
        */
        ReadWriteLock rwLock = new ReentrantZkReadWriteLock2(baseLockPath,zkSessionManager);
        Lock writeLock = rwLock.writeLock();
        writeLock.lockInterruptibly();
        Future<Void> future;
        try{
            future = testService.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    /*
                    If the read lock never acquires, then this method will block until the timeout
                    kills everything
                    */
                    ZooKeeper zooKeeper = TestUtils.newZooKeeper(hostString, timeout);
                    try{
                        ZkSessionManager zksm = new BaseZkSessionManager(zooKeeper);
                        ReadWriteLock rwLock = new ReentrantZkReadWriteLock2(baseLockPath,zksm);
                        Lock readLock = rwLock.readLock();
                        readLock.lock();
                        try{
                            return null;
                        }finally{
                            readLock.unlock();
                        }
                    }finally{
                        zooKeeper.close();
                    }

                }
            });

            assertTrue("Read lock was acquired prematurely",timeout(future,200,TimeUnit.MILLISECONDS));
        }finally{
            writeLock.unlock();
        }

        //make sure that it successfully releases as well
        //if the write lock fails to correctly release, then this future will cause the test to time out
        future.get();

    }

    @Test(timeout = 1000l)
    public void testTryWriteLockBlocksReadLockSameInstance() throws Exception{
        /*
        Tests that a single write lock allows no read locks to acquire.

        This is predicated on the assumption that readLocks CAN successfully acquire
        without contention. If this is not true, this method will fail.
        */
        final ReadWriteLock rwLock = new ReentrantZkReadWriteLock2(baseLockPath,zkSessionManager);
        Lock writeLock = rwLock.writeLock();
        boolean acquired = writeLock.tryLock();
        Future<Void> future;
        try{
            assertTrue("Write Lock was not successfully acquired!",acquired);
            future = testService.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    /*
                    If the read lock never acquires, then this method will block until the timeout
                    kills everything
                    */
                    ReadWriteLock rwLock2 = new ReentrantZkReadWriteLock2(baseLockPath,zkSessionManager);
                    final Lock readLock = rwLock2.readLock();
                    readLock.lock();
                    try{
                        return null;
                    }finally{
                        readLock.unlock();
                    }

                }
            });

            assertTrue("Read lock was acquired prematurely",timeout(future,200,TimeUnit.MILLISECONDS));
        }finally{
            writeLock.unlock();
        }

        //make sure that it successfully releases as well
        //if the write lock fails to correctly release, then this future will cause the test to time out
        future.get();

    }

    @Test(timeout = 1000l)
    public void testTryWriteLockBlocksReadLockDifferentInstance() throws Exception{
        /*
        Tests that a single write lock allows no read locks to acquire. To simulate
        different Processes in the same JVM, this uses a new ReadWriteLock on each thread.

        This is predicated on the assumption that readLocks CAN successfully acquire
        without contention. If this is not true, this method will fail.
        */
        ReadWriteLock rwLock = new ReentrantZkReadWriteLock2(baseLockPath,zkSessionManager);
        Lock writeLock = rwLock.writeLock();
        boolean acquired = writeLock.tryLock();

        Future<Void> future;
        try{
            assertTrue("Write Lock unsuccessfully acquired",acquired);
            future = testService.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    /*
                    If the read lock never acquires, then this method will block until the timeout
                    kills everything
                    */
                    ZooKeeper newSession = TestUtils.newLocalZooKeeper(timeout);
                    try{
                        ReadWriteLock rwLock = new ReentrantZkReadWriteLock2(baseLockPath,new BaseZkSessionManager(newSession));
                        final Lock readLock = rwLock.readLock();
                        readLock.lock();
                        try{
                            return null;
                        }finally{
                            readLock.unlock();
                        }
                    }finally{
                        newSession.close();
                    }
                }
            });

            assertTrue("Read lock was acquired prematurely",timeout(future,200,TimeUnit.MILLISECONDS));
        }finally{
            writeLock.unlock();
        }

        //make sure that it successfully releases as well
        //if the write lock fails to correctly release, then this future will cause the test to time out
        future.get();

    }

    @Test(timeout = 1000l)
    public void testTryWriteLockTimedBlocksReadLockSameInstance() throws Exception{
        /*
        Tests that a single write lock allows no read locks to acquire. To simulate
        different machines, this uses a new ReadWriteLock and a new ZooKeeper instanceon each thread.

        This is predicated on the assumption that readLocks CAN successfully acquire
        without contention. If this is not true, this method will fail.
        */
        final ReadWriteLock rwLock = new ReentrantZkReadWriteLock2(baseLockPath,zkSessionManager);
        Lock writeLock = rwLock.writeLock();
        boolean acquired = writeLock.tryLock(100,TimeUnit.MILLISECONDS);
        Future<Void> future;
        try{
            assertTrue("Write lock unsuccessfully acquired",acquired);
            future = testService.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    /*
                    If the read lock never acquires, then this method will block until the timeout
                    kills everything
                    */
                    Lock readLock = rwLock.readLock();
                    readLock.lock();
                    try{
                        return null;
                    }finally{
                        readLock.unlock();
                    }

                }
            });

            assertTrue("Read lock was acquired prematurely",timeout(future,200,TimeUnit.MILLISECONDS));
        }finally{
            writeLock.unlock();
        }

        //make sure that it successfully releases as well
        //if the write lock fails to correctly release, then this future will cause the test to time out
        future.get();

    }

    @Test(timeout = 1000l)
    public void testTryWriteLockTimedBlocksReadLockDifferentInstance() throws Exception{
        /*
        Tests that a single write lock allows no read locks to acquire. To simulate
        different machines, this uses a new ReadWriteLock on each thread.

        This is predicated on the assumption that readLocks CAN successfully acquire
        without contention. If this is not true, this method will fail.
        */
        ReadWriteLock rwLock = new ReentrantZkReadWriteLock2(baseLockPath,zkSessionManager);
        Lock writeLock = rwLock.writeLock();
        boolean acquired = writeLock.tryLock(100,TimeUnit.MILLISECONDS);
        Future<Void> future;
        try{
            assertTrue("Write lock unsuccessfully acquired",acquired);
            future = testService.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    /*
                    If the read lock never acquires, then this method will block until the timeout
                    kills everything
                    */
                    ReadWriteLock rwLock2 = new ReentrantZkReadWriteLock2(baseLockPath,zkSessionManager);
                    Lock readLock = rwLock2.readLock();
                    readLock.lock();
                    try{
                        return null;
                    }finally{
                        readLock.unlock();
                    }

                }
            });

            assertTrue("Read lock was acquired prematurely",timeout(future,200,TimeUnit.MILLISECONDS));
        }finally{
            writeLock.unlock();
        }

        //make sure that it successfully releases as well
        //if the write lock fails to correctly release, then this future will cause the test to time out
        future.get();
    }

    @Test(timeout = 1000l)
    public void testTryWriteLockTimedBlocksReadLockDifferentZkInstance() throws Exception{
        /*
        Tests that a single write lock allows no read locks to acquire. To simulate different
        nodes, this uses a new ZooKeeper instance on each thread.

        This is predicated on the assumption that readLocks CAN successfully acquire
        without content. If this is not true, this method will fail.
        */
        ReadWriteLock rwLock = new ReentrantZkReadWriteLock2(baseLockPath,zkSessionManager);
        Lock writeLock = rwLock.writeLock();
        boolean acquired = writeLock.tryLock(100,TimeUnit.MILLISECONDS);
        Future<Void> future;
        try{
            assertTrue("Write lock unsuccessfully acquired",acquired);
            future = testService.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    /*
                    If the read lock never acquires, then this method will block until the timeout
                    kills everything
                    */
                    ZooKeeper newSession = TestUtils.newLocalZooKeeper(timeout);
                    try{
                        ReadWriteLock rwLock2 = new ReentrantZkReadWriteLock2(baseLockPath,new BaseZkSessionManager(newSession));
                        Lock readLock = rwLock2.readLock();
                        readLock.lock();
                        try{
                            return null;
                        }finally{
                            readLock.unlock();
                        }
                    }finally{
                        newSession.close();
                    }

                }
            });

            assertTrue("Read lock was acquired prematurely",timeout(future,200,TimeUnit.MILLISECONDS));
        }finally{
            writeLock.unlock();
        }

        //make sure that it successfully releases as well
        //if the write lock fails to correctly release, then this future will cause the test to time out
        future.get();
    }

    @Test(timeout = 1000l)
    public void testTryWriteLockReentrant() throws Exception{
        /*
        Tests that the same Write lock instance can access the lock reentrantly,
        and that the same number of releases are required as the number of acquisitions.

        This relies on the assumption that a WriteLock is exclusive. If this is not true,
        the test will fail
        */
        final ReadWriteLock rwLock = new ReentrantZkReadWriteLock2(baseLockPath,zkSessionManager);
        Lock writeLock = rwLock.writeLock();
        writeLock.tryLock();
        Future<Void> future;
        try{
             future = testService.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    final Lock readLock = rwLock.readLock();

                    readLock.lock();
                    try {
                        return null;
                    } finally {
                        readLock.unlock();
                    }
                }
            });

            //make sure that the read lock is blocked for long enough
            assertTrue("Read lock was acquired prematurely", timeout(future,200,TimeUnit.MILLISECONDS));

            //lock a second time
            writeLock.tryLock();
            try{
                //make sure that we can't get a Read Lock
                assertTrue("Read lock was acquired prematurely",timeout(future,100,TimeUnit.MILLISECONDS));
            }finally{
                writeLock.unlock();
            }

            //make sure we're still locks
            assertTrue("Read lock was acquired prematurely", timeout(future,100,TimeUnit.MILLISECONDS));
        }finally{
            writeLock.unlock();
        }

        future.get(); //make sure no errors were thrown.
    }

    @Test(timeout = 1000l)
    public void testOnlyOneWriteLockAllowedSameInstance() throws Exception{
        /*
        Tests that only a single Write lock is allowed at a time.

        This uses the same Lock instance over multiple threads to confirm thread-safety
        */
        ReadWriteLock rwLock = new ReentrantZkReadWriteLock2(baseLockPath,zkSessionManager);
        final Lock writeLock = rwLock.writeLock();
        writeLock.lock();
        Future<Void> future;
        try{
            future = testService.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    writeLock.lock();
                    try{
                        return null;
                    }finally{
                        writeLock.unlock();
                    }
                }
            });

            assertTrue("Write lock was acquired by another thread",timeout(future,200,TimeUnit.MILLISECONDS));
        }finally{
            writeLock.unlock();
        }

        //make sure that the second write lock successfully acquired, and that no errors happened
        //if the write lock fails to properly release its lock, then this test will timeout and fail
        future.get();
    }

    @Test(timeout = 1000l)
    public void testOnlyOneWriteLockAllowedDifferentInstance() throws Exception{
        /*
        Tests that only a single Write lock is allowed at a time.

        This uses two different lock instances to help confirm two separate
        processes within the same JVM works correctly.
        */
        ReadWriteLock rwLock = new ReentrantZkReadWriteLock2(baseLockPath,zkSessionManager);
        Lock writeLock = rwLock.writeLock();
        writeLock.lock();
        Future<Void> future;
        try{
            future = testService.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    ReadWriteLock rwLock = new ReentrantZkReadWriteLock2(baseLockPath,zkSessionManager);
                    Lock writeLock = rwLock.writeLock();
                    writeLock.lock();
                    try{
                        return null;
                    }finally{
                        writeLock.unlock();
                    }
                }
            });

            assertTrue("Write lock was acquired by another thread",timeout(future,200,TimeUnit.MILLISECONDS));
        }finally{
            writeLock.unlock();
        }

        //make sure that the second write lock successfully acquired, and that no errors happened
        //if the write lock fails to properly release its lock, then this test will timeout and fail
        future.get();
    }

    @Test(timeout = 1000l)
    public void testOnlyOneWriteLockAllowedDifferentZkInstance() throws Exception{
        /*
        Tests that only a single Write lock is allowed at a time.

        This uses two different lock instances to help confirm two separate
        machines will obey the same lock
        */
        ReadWriteLock rwLock = new ReentrantZkReadWriteLock2(baseLockPath,zkSessionManager);
        Lock writeLock = rwLock.writeLock();
        writeLock.lock();
        Future<Void> future;
        try{
            future = testService.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    ZooKeeper zk = TestUtils.newZooKeeper(hostString,timeout);
                    try{
                        ReadWriteLock rwLock = new ReentrantZkReadWriteLock2(baseLockPath,new BaseZkSessionManager(zk));
                        Lock writeLock = rwLock.writeLock();
                        writeLock.lock();
                        try{
                            return null;
                        }finally{
                            writeLock.unlock();
                        }
                    }finally{
                        zk.close();
                    }
                }
            });

            assertTrue("Write lock was acquired by another thread",timeout(future,200,TimeUnit.MILLISECONDS));
        }finally{
            writeLock.unlock();
        }

        //make sure that the second write lock successfully acquired, and that no errors happened
        //if the write lock fails to properly release its lock, then this test will timeout and fail
        future.get();
    }


    @Test(timeout = 1000l)
    public void testWriteLockCanDowngradeToReadLockIfOnSameThread() throws Exception{
        ReadWriteLock rwLock = new ReentrantZkReadWriteLock2(baseLockPath,zkSessionManager);
        Lock writeLock = rwLock.writeLock();
        writeLock.lock();

        Lock readLock = rwLock.readLock();
        boolean acquired = readLock.tryLock();
        assertTrue("read lock was not acquired!",acquired);

        writeLock.unlock();
        readLock.unlock();
    }

    private boolean timeout(Future<?> future, long timeout, TimeUnit unit) throws ExecutionException, InterruptedException {
        try{
            future.get(timeout,unit);
        }catch(TimeoutException te){
            return true;
        }
        return false;
    }
}
