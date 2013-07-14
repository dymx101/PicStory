package org.menagerie;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author Scott Fines
 *         Date: 5/30/11
 *         Time: 6:47 AM
 */
public class TestUtils {

    public static ZooKeeper newZooKeeper(String host,int timeout) throws IOException {
        return new ZooKeeper(host, timeout,new Watcher() {
            @Override
            public void process(WatchedEvent event) {
//                System.out.println(event);
            }
        });
    }

    public static ZooKeeper newLocalZooKeeper(int timeout) throws IOException{
        return new ZooKeeper("localhost",timeout,new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                //do nothing
            }
        });
    }

    public static ZooKeeper newLocalZooKeeper(int timeout, final Logger eventLogger) throws IOException{
        return new ZooKeeper("localhost",timeout,new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                eventLogger.info(event.toString());
            }
        });
    }
}
