package com.towne.framework.schedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 被Spring的Quartz MethodInvokingJobDetailFactoryBean定时执行的普通Spring Bean.
 */
public class QuartzJob {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(QuartzJob.class);

	/**
	 * 定时扫面MRA所有用户对应research列表中的setting配置,找到到期用户发送Email
	 */
	public void execute() {
		System.out.print("this is a QuartzJob!");
	}
}
