package com.towne.framework.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 被Spring的Quartz MethodInvokingJobDetailFactoryBean定时执行的普通Spring Bean.
 */
public class QuartzJob {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(QuartzJob.class);

	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	/**
	 * 定时扫描，调用线程池开启任务
	 */
	public void execute() {

		taskExecutor.execute(new Runnable() {

			@Override
			public void run() {
				try {
					System.out.print("this is a QuartzJob!");
				} catch (Exception ex) {
					ex.getStackTrace();
				}
			}

		});

	}
}
