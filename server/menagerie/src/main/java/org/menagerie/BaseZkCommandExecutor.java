package org.menagerie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.zookeeper.KeeperException;

import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: ASchmucker
 * Date: 2/14/13
 * Time: 4:43 PM
 */
public abstract class BaseZkCommandExecutor implements IZkCommandExecutor {
    protected static final Logger logger = LoggerFactory.getLogger(BaseZkCommandExecutor.class);

    protected static final long DEFAULT_DELAY_MILLIS = 1000;
    protected static final int DEFAULT_MAX_RETRIES = 3;

    protected final int maxRetries;
    protected final long delayMillis;
    protected final ZkSessionManager sessionManager;

    public BaseZkCommandExecutor(ZkSessionManager sessionManager, int maxRetries,long delayMillis, TimeUnit retryUnit){
        this.sessionManager = sessionManager;
        this.maxRetries = maxRetries;
        this.delayMillis = retryUnit.toMillis(delayMillis);
    }

    public <T> T executeInterruptibly(ZkCommand<T> command) throws InterruptedException, KeeperException {
        KeeperException exception = null;
        for(int retry=0;retry<maxRetries;retry++){
            try{
                return command.execute(sessionManager.getZooKeeper());
            } catch (KeeperException.ConnectionLossException kce) {
                if(exception==null){
                    exception = kce;
                }
                logger.debug("Connection was lost while comamnd "+ command +"was attempted. Retrying");
                doWaitInterruptibly(retry, delayMillis);
            } catch(KeeperException.SessionExpiredException kse){
                if(exception==null){
                    exception = kse;
                }
                logger.debug("Session expired while comamnd "+ command +"was attempted. Retrying with new session");
                doWaitInterruptibly(retry, delayMillis);
            }
        }
        throw exception;
    }

    protected void doWait(int retry, long retryDelay) {
        try {
            Thread.sleep(retry*retryDelay);
        } catch (InterruptedException e) {
            logger.debug("sleep interrupted");
        }
    }

    private void doWaitInterruptibly(int retry, long retryDelay) throws InterruptedException{
        Thread.sleep(retry*retryDelay);
    }
}
