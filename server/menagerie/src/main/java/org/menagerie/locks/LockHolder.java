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

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
/**
 * @author Scott Fines
 *         Date: 5/27/11
 *         Time: 2:46 PM
 */
final class LockHolder{
		private static final Logger LOGGER = Logger.getLogger(LockHolder.class);
    private final AtomicReference<Thread> holdingThread = new AtomicReference<Thread>();
    private volatile String lockNode;
    private final AtomicInteger holdCount = new AtomicInteger(0);

    public void setHoldingThread(String lockNode){
			LOGGER.trace("Set holding thread value");
			synchronized(this){
        holdingThread.set(Thread.currentThread());
        holdCount.set(1);
      	this.lockNode = lockNode;
			}
    }

    boolean increment(){
        if(Thread.currentThread().equals(holdingThread.get())){
            int count =holdCount.incrementAndGet();
						LOGGER.trace("Incrementing lockHolder value to "+ count);
            return true;
        }else{
            return false;
        }
    }

    int decrement(){
        if(Thread.currentThread().equals(holdingThread.get())){
            int count = holdCount.decrementAndGet();
//            if(count<=0){
//                holdingThread.set(null);
//            }
            return count;
        }else{
            return holdCount.get();
        }
    }

    String getLockNode(){
        if(Thread.currentThread().equals(holdingThread.get()))
            return lockNode;
        else
            return null;
    }

    void clear(){
			synchronized(this){
				if(Thread.currentThread().equals(holdingThread.get())){
	        holdingThread.set(null);
	        holdCount.set(0);
				}
			}
    }
}
