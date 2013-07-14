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

import org.apache.zookeeper.data.ACL;
import org.menagerie.Beta;
import org.menagerie.ZkSessionManager;
import org.menagerie.latches.spi.ZkCountDownLatch;
import org.menagerie.latches.spi.ZkCyclicBarrier;
import org.menagerie.latches.spi.ZkSemaphore;

import java.util.List;

/**
 * TODO: Document this crap.
 */
@Beta
public final class LatchBuilder {

    public final static DistributedCountDownLatch newCountDownLatch(long total, String latchNode, ZkSessionManager zkSessionManager) {
        return new ZkCountDownLatch(total, latchNode, zkSessionManager);
    }

    public final static DistributedCountDownLatch newCountDownLatch(long total, String latchNode, ZkSessionManager zkSessionManager, List<ACL> privileges) {
        return new ZkCountDownLatch(total, latchNode, zkSessionManager, privileges);
    }

    public final static DistributedCountDownLatch newCountDownLatch(long total, String latchNode, ZkSessionManager zkSessionManager, List<ACL> privileges, boolean tolerateFailures) {
        return new ZkCountDownLatch(total, latchNode, zkSessionManager, privileges, tolerateFailures);
    }

    public final static DistributedCyclicBarrier newCyclicBarrier(long size, ZkSessionManager zkSessionManager, String barrierNode) {
        return new ZkCyclicBarrier(size, zkSessionManager, barrierNode);
    }

    public final static DistributedCyclicBarrier newCyclicBarrier(long size, ZkSessionManager zkSessionManager, String barrierNode, List<ACL> privileges) {
        return new ZkCyclicBarrier(size, zkSessionManager, barrierNode, privileges);
    }

    public final static DistributedCyclicBarrier newCyclicBarrier(long size, ZkSessionManager zkSessionManager, String barrierNode, List<ACL> privileges, boolean tolerateFailures) {
        return new ZkCyclicBarrier(size, zkSessionManager, barrierNode, privileges, tolerateFailures);
    }

    public final static DistributedSemaphore newSemaphore(int numPermits, String baseNode, ZkSessionManager zkSessionManager, List<ACL> privileges) {
        return new ZkSemaphore(numPermits, baseNode, zkSessionManager, privileges);
    }

    public final static DistributedSemaphore newSemaphore(int numPermits, String baseNode, ZkSessionManager zkSessionManager) {
        return new ZkSemaphore(numPermits, baseNode, zkSessionManager);
    }
}
