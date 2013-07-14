package com.towne.framework.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 被Spring的Quartz MethodInvokingJobDetailFactoryBean定时执行的普通Spring Bean.
 */
public class QuartzJob {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(QuartzJob.class);

	public void work1() {
		System.out.println("Quartz的任务调度！！！work1启用……");
	}

	public void work2() {
		System.out.println("Quartz的任务调度！！！work2启用……");
	}

}
