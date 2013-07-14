package org.menagerie.locks;

import static org.junit.Assert.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.CountDownLatch;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.menagerie.DefaultZkSessionManager;
import org.menagerie.locks.Locksmith;

/**
 * Test submitted by Aditya Muralidharan, discovered in production code.
 *
 *
 */
public class WeirdReentrantReadWriteLockDowngradeTest {

	private static DefaultZkSessionManager sessionManager;
	
	private ReadWriteLock nodeLock;
	private AtomicBoolean workflowThreadCompleted;

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
		workflowThreadCompleted = new AtomicBoolean(false);
		nodeLock = Locksmith.readWriteLock(sessionManager, "/testReadWriteLock");
	}

	@Test(timeout = 1000l)
	public void test() throws Exception {
		nodeLock.writeLock().lock();
		//should have 1 write lock, 0 read locks
		CountDownLatch latch = new CountDownLatch(1);
		try {
			
			initializeReportingJob(latch);
			//should have 1 write lock node, 0 read lock nodes
			nodeLock.readLock().lock(); // lock downgrade. the readLock.lock() must always be the last line in this try block
			//should have 1 write lock, 1 read lock
		} finally {
			System.out.println("Attempting to unlock write lock");
			nodeLock.writeLock().unlock();
			//should have 0 write lock, 1 read lock
		} try { // for the downgrade to behave correctly, there must be no code between the two try-finallys
			System.out.println("Attempting reentrant status retrieval");
			retrieveStatus(); // returns the status to the client
			//above gains and released 1 read lock
		} finally {
			System.out.println("Attempting to unlock read lock");
			nodeLock.readLock().unlock();
		}
		latch.await();
		assertTrue(workflowThreadCompleted.get());
		
	}

	private Object retrieveStatus() {
		nodeLock.readLock().lock();
		try {
			System.out.println("Retrieve protobuf status from the zk node");
			return new Object();
		} finally {
			nodeLock.readLock().unlock();
		}
	}

	private void initializeReportingJob(CountDownLatch latch) {
		Object status = retrieveStatus();
		// Some checks here, and then
		submitWorkflow(latch);
	}

	private void submitWorkflow(final CountDownLatch latch) {
		nodeLock.writeLock().lock();
		try {
			Thread workflowThread = new Thread(new Runnable() {

				@Override
				public void run() {
					try{
					updateStatus("workflow");
					workflowThreadCompleted.set(true);
					latch.countDown();
					System.out.printf("[%s]: Successfully did stuff in opposite thread%n");
					}catch(Throwable t){
						System.out.printf("[%s]: ERROR!. %s%n",Thread.currentThread(),t);
					}
				}
				
			});
			workflowThread.start();
			updateStatus("main");
		} finally {
			nodeLock.writeLock().unlock();
		}
	}

	private void updateStatus(String threadName) {
		nodeLock.writeLock().lock();
		try {
			updateStatusInner(threadName);
		} finally {
			nodeLock.writeLock().unlock();
		}
	}

	private void updateStatusInner(String threadName) {
		retrieveStatus();
		// Perform some checks on the status
		nodeLock.writeLock().lock();
		try {
			System.out.println("Updating the status of the zk node in the " + threadName + " thread");
		} finally {
			nodeLock.writeLock().unlock();
		}
	}

	
}
