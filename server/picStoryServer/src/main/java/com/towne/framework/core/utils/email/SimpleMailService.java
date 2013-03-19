package com.towne.framework.core.utils.email;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.StringUtils;

/**
 * 纯文本邮件服务类.
 * 
 * @author calvin
 */
public class SimpleMailService {
	private static Logger logger = LoggerFactory
			.getLogger(SimpleMailService.class);

	private JavaMailSender mailSender;
	private String textTemplate;

	/**
	 * 发送纯文本的用户修改通知邮件.
	 */
	@SuppressWarnings("rawtypes")
	public void sendNotificationMail(String fromMail, String toMail, Map argMap) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom(fromMail);
		msg.setTo(toMail);
		msg.setSubject((String) argMap.get("title"));

		// 将用户名与当期日期格式化到邮件内容的字符串模板
		String content = String.format(textTemplate, (Object[]) argMap
				.get("valueArray"));
		msg.setText(content);

		try {
			mailSender.send(msg);
			logger.info("纯文本邮件已发送至{}", StringUtils
					.arrayToCommaDelimitedString(msg.getTo()));
		} catch (Exception e) {
			logger.error("发送邮件失败", e);
		}
	}

	/**
	 * Spring的MailSender.
	 */
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	/**
	 * 邮件内容的字符串模板.
	 */
	public void setTextTemplate(String textTemplate) {
		this.textTemplate = textTemplate;
	}

	public static void main(String[] args) {
		System.out.println(String.format(
				"<![CDATA[MRA用戶%1$s在%2$tF被修改.System Administrator.]]>",
				"yanghui", new Date()));
	}
}
