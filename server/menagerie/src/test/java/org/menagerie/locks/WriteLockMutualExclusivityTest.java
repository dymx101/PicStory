package org.menagerie.locks;

import static org.junit.Assert.*;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.menagerie.DefaultZkSessionManager;
import org.menagerie.locks.Locksmith;

/**
 * Contributed by Aditya Muralidharan.
 */
public class WriteLockMutualExclusivityTest {

	private static DefaultZkSessionManager sessionManager;
	
	private ReadWriteLock nodeLock;
	private ReadWriteLock someOtherLock;
	private AtomicInteger counter = new AtomicInteger(0);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		sessionManager = new DefaultZkSessionManager("localhost:2181", 5000);
	}

	@AfterClass
	public static void teardownAfterClass() throws Exception {
		sessionManager.shutdown();
	}
	
	@Before
	public void setUp() throws Exception {
		nodeLock = Locksmith.readWriteLock(sessionManager, "/testLockNode");
		someOtherLock = Locksmith.readWriteLock(sessionManager, "/testLockNode2");
	}

	@Test
	public void testWriteLockIsMutuallyExclusive() throws Exception {
		CountingThread otherThread = new CountingThread();
		
		nodeLock.writeLock().lock();
		try {
//			nodeLock.readLock().lock();
//			try {
				System.out.println("First lock");
//			} finally {
//				nodeLock.readLock().unlock();
//			}
		} finally {
			nodeLock.writeLock().unlock();
		}
		 //Scott's theory here is that the above unlock isn't clearing out the holding thread, so the subsequent lock just increments instead of actually creating a lock node.
		nodeLock.writeLock().lock();
		try {
			nodeLock.readLock().lock();
			try {
				System.out.println("Doing something in the read lock");
			} finally {
				nodeLock.readLock().unlock();
			}
			someOtherLock.writeLock().lock();
			try {
				otherThread.start();
				Thread.sleep(600);
//				nodeLock.writeLock().lock();
//				try {
//					otherThread.start();
//					Thread.sleep(600);
//				} finally {
//					nodeLock.writeLock().unlock();
//				}
//				nodeLock.writeLock().lock();
//				try {
//					counter.incrementAndGet();
//				} finally {
//					nodeLock.writeLock().unlock();
//				}
			} finally {
				someOtherLock.writeLock().unlock();
			}
		} finally {
			nodeLock.writeLock().unlock();
		}
		otherThread.join();
		assertEquals(1, otherThread.count);
	}
	
	private final class CountingThread extends Thread {
		
		private int count = 0;
		public void run() {
			nodeLock.writeLock().lock();
			try {
				count = counter.incrementAndGet();
			} finally {
				nodeLock.writeLock().unlock();
			}
		}
	}
}
