/*
 * Copyright 2011 Scott Fines
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.zookeeper.KeeperException;

import java.util.concurrent.TimeUnit;

/**
 * @author Scott Fines
 *         Date: Apr 25, 2011
 *         Time: 1:55:52 PM
 */
public class ZkCommandExecutor extends BaseZkCommandExecutor implements IZkCommandExecutor {
    private static final Logger logger = LoggerFactory.getLogger(ZkCommandExecutor.class);

    public ZkCommandExecutor(ZkSessionManager sessionManager) {
        super(sessionManager,DEFAULT_MAX_RETRIES,DEFAULT_DELAY_MILLIS,TimeUnit.MILLISECONDS);
    }

    public ZkCommandExecutor(ZkSessionManager sessionManager,long retryDelay,TimeUnit retryUnits) {
        super(sessionManager,DEFAULT_MAX_RETRIES,retryDelay,retryUnits);
    }

    public ZkCommandExecutor(ZkSessionManager sessionManager,int maxRetries) {
        super(sessionManager,maxRetries,DEFAULT_DELAY_MILLIS,TimeUnit.MILLISECONDS);
    }
	
	public ZkCommandExecutor(ZkSessionManager sessionManager, int maxRetries,long delayMillis, TimeUnit retryUnit){
        super(sessionManager,maxRetries,delayMillis, retryUnit);
    }

    public <T> T execute(ZkCommand<T> command) throws KeeperException {
        KeeperException exception = null;
        for(int retry=0;retry<maxRetries;retry++){
            try{
                return command.execute(sessionManager.getZooKeeper());
            } catch (InterruptedException e) {
                logger.debug("Command "+command+" was interrupted. Retrying");
                doWait(retry, delayMillis);
            } catch (KeeperException.ConnectionLossException kce) {
                if(exception==null){
                    exception = kce;
                }
                logger.debug("Connection was lost while command "+ command +"was attempted. Retrying");
                doWait(retry, delayMillis);
            } catch(KeeperException.SessionExpiredException kse){
                if(exception==null){
                    exception = kse;
                }
                logger.info("Session expired while command "+ command +"was attempted. Retrying with new session");
                throw kse;
            }
        }
        if(exception==null){
            //we got repeated interruptions, so throw a KeeperException of the correct type
            throw new RuntimeException("Repeatedly interrupted during an uninterruptible task caused Task to fail");
        }
        throw exception;
    }
}
