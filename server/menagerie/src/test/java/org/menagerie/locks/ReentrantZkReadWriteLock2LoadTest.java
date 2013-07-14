package org.menagerie.locks;

import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.menagerie.BaseZkSessionManager;
import org.menagerie.ZkSessionManager;
import org.menagerie.ZkUtils;
import org.menagerie.util.TestingThreadFactory;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.menagerie.TestUtils.newZooKeeper;

/**
 * @author Scott Fines
 *         Date: 7/1/11
 *         Time: 9:08 PM
 */
public class ReentrantZkReadWriteLock2LoadTest {

    private static final Logger logger = Logger.getLogger(ReentrantZkLock2Test.class);
    private static final String hostString = "localhost:2181";
    private static final String baseLockPath = "/test-readwrite-locks-writelock-tests-2";
    private static final int timeout = 2000;
    private static final ExecutorService testService = Executors.newFixedThreadPool(2, new TestingThreadFactory());

    private static ZooKeeper zk;
    private static ZkSessionManager zkSessionManager;

    @Before
    public void setup() throws Exception {
        zk = newZooKeeper(hostString,timeout);

        //be sure that the lock-place is created
        ZkUtils.recursiveSafeDelete(zk, baseLockPath, -1);
        zk.create(baseLockPath,new byte[]{}, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        zkSessionManager = new BaseZkSessionManager(zk);
    }

    @After
    public void tearDown() throws Exception{
        try{
            List<String> children = zk.getChildren(baseLockPath,false);
            for(String child:children){
                ZkUtils.safeDelete(zk, baseLockPath +"/"+child,-1);
            }
            ZkUtils.safeDelete(zk, baseLockPath,-1);

        }catch(KeeperException ke){
            //suppress because who cares what went wrong after our tests did their thing?
        }finally{
            zk.close();
        }
    }

}
