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
package org.menagerie.latches;

import org.apache.zookeeper.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.menagerie.BaseZkSessionManager;
import org.menagerie.ZkCommand;
import org.menagerie.ZkCommandExecutor;
import org.menagerie.ZkUtils;
import org.menagerie.latches.spi.ZkCyclicBarrier;
import org.menagerie.util.TestingThreadFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 *
 * @author Scott Fines
 * @version 1.0
 *          Date: 12-Dec-2010
 *          Time: 09:34:14
 */
@Ignore("Ignoring until rewrite")
public class ZkCyclicBarrierTest {
    private static final String baseBarrierPath = "/test-barriers";
    private static final int timeout = 5000;

    private final ExecutorService executor = Executors.newFixedThreadPool(10, new TestingThreadFactory());
    private DistributedCyclicBarrier cyclicBarrier;
    private ZooKeeper zk;
    private String barrierPath;

    @Before
    public void setup() throws Exception {
        barrierPath = baseBarrierPath+System.currentTimeMillis();
        zk = newZooKeeper();

        //be sure that the lock-place is created
        try{
            ZkCommandExecutor zkCommandExecutor = new ZkCommandExecutor(new BaseZkSessionManager(zk));
            zkCommandExecutor.execute(new ZkCommand<Void>() {
                @Override
                public Void execute(ZooKeeper zk) throws KeeperException, InterruptedException {
                    zk.create(barrierPath,new byte[]{}, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                    return null;
                }
            });
        }catch(KeeperException ke){
            if(ke.code()!= KeeperException.Code.NODEEXISTS)
                throw ke;
        }
    }

    @After
    public void tearDown() throws Exception{
        try{
            List<String> children = zk.getChildren(barrierPath,false);
            for(String child:children){
                ZkUtils.recursiveSafeDelete(zk,barrierPath+"/"+child,-1);
            }
            zk.delete(barrierPath,-1);

        }catch(KeeperException ke){
            if(ke.code()!= KeeperException.Code.NONODE)
                throw ke;
        }finally{
            zk.close();
        }
    }

    @Test(timeout = 5000l)
    public void testBarrierWorks()throws Exception{
        cyclicBarrier = new ZkCyclicBarrier(1, new BaseZkSessionManager(zk), barrierPath);
        cyclicBarrier.await();
        //this method would timeout if await doesn't move forward
    }

    @Test(timeout = 5000l)
    public void testBarrierWorksWithTwoThreads() throws Exception{
        cyclicBarrier = new ZkCyclicBarrier(2, new BaseZkSessionManager(zk), barrierPath);

        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    fail(e.getMessage());
                } catch (BrokenBarrierException e) {
                    fail(e.getMessage());
                }
            }
        });

        cyclicBarrier.await();
    }

    @Test(timeout = 5000l)
    public void testBarrierWorksWithTwoClients() throws Exception{
        cyclicBarrier = new ZkCyclicBarrier(2, new BaseZkSessionManager(zk), barrierPath);

        Future<Void> errorFuture = executor.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                ZooKeeper otherZooKeeper = newZooKeeper();
                try {
                    DistributedCyclicBarrier otherCyclicBarrier = new ZkCyclicBarrier(2, new BaseZkSessionManager(otherZooKeeper), barrierPath);
                    System.out.printf("%s: Waiting on barrier%n",Thread.currentThread().getName());
                    otherCyclicBarrier.await();
                } finally {
                    otherZooKeeper.close();
                }
                System.out.printf("%s; Barrier returned on secondary thread%n",Thread.currentThread().getName());
                return null;
            }
        });
        System.out.printf("%s: Waiting on barrier%n",Thread.currentThread().getName());
        cyclicBarrier.await();
        System.out.printf("%s; Barrier returned on main thread%n",Thread.currentThread().getName());
        //check to make sure any errors haven't happened, by looking for ExecutionExceptions
        errorFuture.get();
    }

    @Test(timeout = 10000l)
    public void testBarrierWorksWithManyClients() throws Exception{
        final int numClients=9;
        cyclicBarrier = new ZkCyclicBarrier(numClients, new BaseZkSessionManager(zk), barrierPath);

        List<Future<Void>> futures = new ArrayList<Future<Void>>(numClients);
        for(int client=0;client<numClients;client++){
            futures.add(executor.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    ZooKeeper otherZooKeeper = newZooKeeper();
                    try {
                        DistributedCyclicBarrier otherCyclicBarrier = new ZkCyclicBarrier(numClients, new BaseZkSessionManager(otherZooKeeper), barrierPath);
//                        System.out.printf("%s: Waiting on barrier%n",Thread.currentThread().getName());
                        otherCyclicBarrier.await();
                    } finally {
                        otherZooKeeper.close();
                    }
//                    System.out.printf("%s; Barrier returned on secondary thread%n",Thread.currentThread().getName());
                    return null;
                }
            }));
        }
//        System.out.printf("%s: Waiting on barrier%n",Thread.currentThread().getName());
        cyclicBarrier.await();
//        System.out.printf("%s; Barrier returned on main thread%n",Thread.currentThread().getName());
        //check to make sure any errors haven't happened, by looking for ExecutionExceptions
        for(Future<Void> future:futures){
            future.get();
        }
    }

    @Test(timeout = 5000l, expected = TimeoutException.class)
    public void testBarrierTimeOutThrowsTimeoutException() throws Exception{
        cyclicBarrier = new ZkCyclicBarrier(2, new BaseZkSessionManager(zk), barrierPath);
//        assertEquals("Cyclic Barrier is not properly constructed!",1,zk.getChildren(baseBarrierPath,false).size());
        cyclicBarrier.await(500, TimeUnit.MILLISECONDS);
    }

    @Test(timeout = 1000l, expected = BrokenBarrierException.class)
    public void testBarrierTimeoutCausesBrokenBarrierOnOtherThread() throws Exception{
        cyclicBarrier = new ZkCyclicBarrier(3, new BaseZkSessionManager(zk), barrierPath);
//        assertEquals("Cyclic Barrier is not properly constructed!",1,zk.getChildren(baseBarrierPath,false).size());

        Future<?> errorFuture = executor.submit(new Runnable() {
            @Override
            public void run() {
                boolean thrown = false;
                try {
                    cyclicBarrier.await(500, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    fail(e.getMessage());
                } catch (BrokenBarrierException e) {
                    fail(e.getMessage());
                } catch (TimeoutException e) {
                    thrown = true;
                }
                assertTrue("TimeoutException was never thrown!", thrown);
            }
        });
        cyclicBarrier.await();
        errorFuture.get();
    }

    @Test(timeout = 1000l, expected = BrokenBarrierException.class)
    public void testBarrierTimeoutCausesBrokenBarrierOnOtherClients() throws Exception{
        cyclicBarrier = new ZkCyclicBarrier(3, new BaseZkSessionManager(zk), barrierPath);

        Future<Void> errorFuture = executor.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                ZooKeeper newZk = newZooKeeper();
                boolean thrown = false;
                try{
                    DistributedCyclicBarrier otherCyclicBarrier = new ZkCyclicBarrier(3, new BaseZkSessionManager(newZk), barrierPath);
                    otherCyclicBarrier.await(500,TimeUnit.MILLISECONDS);
                } catch (TimeoutException e) {
                    thrown = true;
                }finally{
                    newZk.close();
                }
                assertTrue("TimeoutException was never thrown!", thrown);
                return null;
            }
        });
        cyclicBarrier.await();
        //check for Errors
        errorFuture.get();
    }

    @Test(timeout = 1000l)
    public void testResetWorks() throws Exception{
        cyclicBarrier = new ZkCyclicBarrier(1, new BaseZkSessionManager(zk), barrierPath);
        cyclicBarrier.await();

        cyclicBarrier.reset();

    }

    @Test(timeout = 3000l)
    public void testResetOnOtherThreadBreaksBarrier() throws Exception{
        cyclicBarrier = new ZkCyclicBarrier(2, new BaseZkSessionManager(zk), baseBarrierPath);

        Future<Void> errorFuture = executor.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                boolean thrown = false;
                try {
                    cyclicBarrier.await();
                } catch (BrokenBarrierException bbe) {
                    thrown = true;
                }
                assertTrue("BrokenBarrierException was not thrown!", thrown);
                return null;
            }
        });
        //make sure the other thread is waiting
        while(cyclicBarrier.getNumberWaiting()<=0){
            Thread.sleep(100);
        }
        cyclicBarrier.reset();
        //check for bad things happening
        errorFuture.get();
    }

    @Test(timeout = 3000l)
    public void testResetOnOtherClientBreaksBarrier() throws Exception{
        cyclicBarrier = new ZkCyclicBarrier(3, new BaseZkSessionManager(zk), baseBarrierPath);

        Future<Void> errorFuture = executor.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                ZooKeeper otherZooKeeper = newZooKeeper();
                boolean thrown = false;
                try {
                    DistributedCyclicBarrier otherCyclicBarrier = new ZkCyclicBarrier(3, new BaseZkSessionManager(otherZooKeeper), baseBarrierPath);
                    otherCyclicBarrier.await();
                } catch (BrokenBarrierException bbe) {
                    System.out.println("Barrier Broken");
                    thrown = true;
                } finally {
                    otherZooKeeper.close();
                }
                assertTrue("BrokenBarrierException was not thrown!", thrown);
                return null;
            }
        });
        //make sure the other thread is waiting
        while(cyclicBarrier.getNumberWaiting()<=0){
            Thread.sleep(100);
        }
        cyclicBarrier.reset();
        //check for bad things happening
        errorFuture.get();
    }


    private static ZooKeeper newZooKeeper() throws IOException {
        return new ZooKeeper("localhost:2181", timeout,new Watcher() {
            @Override
            public void process(WatchedEvent event) {
//                System.out.println(event);
            }
        });
    }
}
