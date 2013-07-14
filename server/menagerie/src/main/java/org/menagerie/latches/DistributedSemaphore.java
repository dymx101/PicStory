package org.menagerie.latches;

import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: steve
 * Date: 5/9/11
 * Time: 4:37 PM
 * To change this template use File | Settings | File Templates.
 */
public interface DistributedSemaphore {
    void acquire() throws InterruptedException;

    void acquireUninterruptibly();

    boolean tryAcquire();

    boolean tryAcquire(long timeout, TimeUnit unit) throws InterruptedException;

    void release();

    void acquire(int permits) throws InterruptedException;

    void acquireUninterruptibly(int permits);

    boolean tryAcquire(int permits);

    boolean tryAcquire(int permits, long timeout, TimeUnit unit)throws InterruptedException;

    void release(int permits);

    int availablePermits();

    boolean hasQueuedParties();

    int getQueueLength();
}
