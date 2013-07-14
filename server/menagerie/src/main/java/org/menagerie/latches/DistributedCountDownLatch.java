package org.menagerie.latches;

import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: steve
 * Date: 5/9/11
 * Time: 4:34 PM
 * To change this template use File | Settings | File Templates.
 */
public interface DistributedCountDownLatch {
    void countDown();

    long getCount();

    void await() throws InterruptedException;

    boolean await(long timeout, TimeUnit unit)throws InterruptedException;

    void closeLatch();
}
