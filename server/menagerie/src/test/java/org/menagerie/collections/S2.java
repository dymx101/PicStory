package org.menagerie.collections;


import org.apache.zookeeper.KeeperException;
import org.menagerie.DefaultZkSessionManager;
import org.menagerie.JavaSerializer;

/**
 * @author Milind Parikh
 *         Date: Apr 25, 2011
 */
public class S2 {
    private static final String testElement = "Test element";

    public static void  main(String [] args) throws InterruptedException, KeeperException {
        DefaultZkSessionManager dzsm = new DefaultZkSessionManager("localhost:2181", 2000);

//        dzsm.getZooKeeper().create("/cassandra_clusters",new byte[]{}, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        ZkBlockingQueue <String> testQueue   = new ZkBlockingQueue <String> ("/cassandra_clusters/def", new JavaSerializer <String> () , dzsm, 100);


        testQueue.put(testElement);
        int reportedSize = testQueue.size();
        System.out.println(reportedSize);
        dzsm.shutdown();
    }

}
