package org.menagerie.latches;

import org.apache.zookeeper.KeeperException;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by IntelliJ IDEA.
 * User: steve
 * Date: 5/9/11
 * Time: 4:36 PM
 * To change this template use File | Settings | File Templates.
 */
public interface DistributedCyclicBarrier {
    void await() throws InterruptedException, BrokenBarrierException;

    void await(long timeout, TimeUnit unit) throws InterruptedException, BrokenBarrierException, TimeoutException;

    long getNumberWaiting();

    long getParties();

    boolean isBroken();

    void reset();

    boolean isCleared() throws InterruptedException, KeeperException;
}
