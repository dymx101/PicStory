package org.menagerie.locks;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.menagerie.DefaultZkSessionManager;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * Attempts to track down a race condition, may never fail, but is still
 * illustrative of the possible effects of write lock contentions.
 *
 * Donated by Aditya Muralidharan.
 *
 */
public class WriteLockContentionTest {

	private static DefaultZkSessionManager sessionManager;
	
	private ReadWriteLock nodeLock;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		sessionManager = new DefaultZkSessionManager("10.27.2.184:2181,10.27.2.185:2181,10.27.2.240:2181", 5000);
	}

	@AfterClass
	public static void teardownAfterClass() throws Exception {
		sessionManager.shutdown();
	}
	
	@Before
	public void setUp() throws Exception {
		nodeLock = Locksmith.readWriteLock(sessionManager, "/multi_domain_reports/qa/TestReport/2012-01-09-06-00-00-2012-01-10-06-00-00");
	}
	
	@Test
	public void testContention() throws Exception {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				lockAndPrint("other");
			}
			
		}, "other");
		t.start();
		lockAndPrint("main");
		t.join();
	}

	private void lockAndPrint(String thread) {
		nodeLock.writeLock().lock();
		try {
			System.out.println("I am " + thread);
		} finally {
			nodeLock.writeLock().unlock();
		}
	}
}
