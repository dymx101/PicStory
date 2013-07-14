package org.menagerie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.zookeeper.KeeperException;

import java.util.concurrent.TimeUnit;

/**
 * This class allows for automatic recovery of session expiration when calling execute.  Note that this class should
 * never be used when using ephemeral nodes.  If the session expires the data in the ephemeral nodes would disappear
 * and we would be none the wiser.
 * User: ASchmucker
 * Date: 2/14/13
 * Time: 4:42 PM
 */
public class SessionRecoverableZKCommandExecutor extends BaseZkCommandExecutor implements IZkCommandExecutor {
    private static final Logger logger = LoggerFactory.getLogger(SessionRecoverableZKCommandExecutor.class);

    public SessionRecoverableZKCommandExecutor(ZkSessionManager sessionManager) {
        super(sessionManager,DEFAULT_MAX_RETRIES,DEFAULT_DELAY_MILLIS, TimeUnit.MILLISECONDS);
    }

    public SessionRecoverableZKCommandExecutor(ZkSessionManager sessionManager,long retryDelay,TimeUnit retryUnits) {
        super(sessionManager,DEFAULT_MAX_RETRIES,retryDelay,retryUnits);
    }

    public SessionRecoverableZKCommandExecutor(ZkSessionManager sessionManager,int maxRetries) {
        super(sessionManager,maxRetries,DEFAULT_DELAY_MILLIS,TimeUnit.MILLISECONDS);
    }
	
	public SessionRecoverableZKCommandExecutor(ZkSessionManager sessionManager, int maxRetries,long delayMillis, TimeUnit retryUnit){
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
                logger.debug("Session expired while command "+ command +"was attempted. Retrying with new session");
                doWait(retry, delayMillis);
            }
        }
        if(exception==null){
            //we got repeated interruptions, so throw a KeeperException of the correct type
            throw new RuntimeException("Repeatedly interrupted during an uninterruptible task caused Task to fail");
        }
        throw exception;
    }
}
