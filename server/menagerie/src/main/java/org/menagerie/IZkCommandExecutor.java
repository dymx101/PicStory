package org.menagerie;

import org.apache.zookeeper.KeeperException;

/**
 * Created with IntelliJ IDEA.
 * User: ASchmucker
 * Date: 2/14/13
 * Time: 4:44 PM
 */
public interface IZkCommandExecutor {
    public <T> T execute(ZkCommand<T> command) throws KeeperException;
}
