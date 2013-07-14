package org.menagerie.collections;


import org.menagerie.DefaultZkSessionManager;
import org.menagerie.JavaSerializer;

import java.util.concurrent.TimeUnit;

/**
 * @author Milind Parikh
 *         Date: Apr 25, 2011
 *         Time: 12:33:05 PM
 */
public class S3 {
    private static final String testElement = "Test element";

    public static void  main(String [] args) throws InterruptedException {
        DefaultZkSessionManager dzsm = new DefaultZkSessionManager("localhost:2181", 2000);


        ZkBlockingQueue <String> testQueue   = new ZkBlockingQueue <String> ("/cassandra_clusters/def", new JavaSerializer <String> () , dzsm,100);



        String peekedElement = testQueue.peek();
        System.out.println(peekedElement);
        long timeout = 5;
        String polledElement = testQueue.poll(timeout, TimeUnit.SECONDS);
        System.out.println(polledElement);
        dzsm.shutdown();
    }

}
