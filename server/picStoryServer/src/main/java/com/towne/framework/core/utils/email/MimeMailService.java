package com.towne.framework.core.utils.email;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;



/**
 * MIME邮件服务类.
 * 
 * 演示由Freemarker引擎生成的的html格式邮件, 并带有附件.
 * 
 */
public class MimeMailService {

	private static final String DEFAULT_ENCODING = "utf-8";

	private static Logger logger = LoggerFactory
			.getLogger(MimeMailService.class);

	private JavaMailSender mailSender;
	private Template template;

	private Configuration conf;

	public Configuration getConf() {
		return conf;
	}

	public void setConf(Configuration conf) {
		this.conf = conf;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	/**
	 * Spring的MailSender.
	 */
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	/**
	 * 注入Freemarker引擎配置,构造Freemarker 邮件内容模板.
	 */
	public void setFreemarkerConfiguration(Configuration freemarkerConfiguration)
			throws IOException {
		// 根据freemarkerConfiguration的templateLoaderPath载入文件.
		template = freemarkerConfiguration.getTemplate("mailTemplate.ftl",
				DEFAULT_ENCODING);
	}

	/**
	 * 发送MIME格式的用户修改通知邮件.
	 */
	@SuppressWarnings("rawtypes")
	public void sendNotificationMail(String fromMail, String toMail, Map argMap) {

		try {
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true,
					DEFAULT_ENCODING);

			helper.setTo(toMail);
			helper.setFrom(fromMail);
			helper.setSubject((String) argMap.get("title"));

			// String content = generateContent(argMap);
			String content = (String) argMap.get("content");
			helper.setText(content, true);
			// 暂时不需要附件
			// File attachment = generateAttachment();
			// helper.addAttachment("mailAttachment.txt", attachment);

			mailSender.send(msg);
			logger.info("HTML版邮件已发送至" + toMail);
		} catch (MessagingException e) {
			logger.error("构造邮件失败", e);
		} catch (Exception e) {
			logger.error("发送邮件失败", e);
		}
	}

	/**
	 * 使用Freemarker生成html格式内容.
	 */
	@SuppressWarnings({ "rawtypes" })
	public String generateContent(Map argMap) throws MessagingException {

		try {
			// Map context = Collections.singletonMap("userName", "yanghui");
			if (null != argMap.get("templateName")) {
				String templateName = (String) argMap.get("templateName");
				template = conf.getTemplate(templateName, DEFAULT_ENCODING);
			}
			return FreeMarkerTemplateUtils.processTemplateIntoString(template,
					argMap);
		} catch (IOException e) {
			logger.error("生成邮件内容失败, FreeMarker模板不存在", e);
			throw new MessagingException("FreeMarker模板不存在", e);
		} catch (TemplateException e) {
			logger.error("生成邮件内容失败, FreeMarker处理失败", e);
			throw new MessagingException("FreeMarker处理失败", e);
		}
	}

	/**
	 * 获取classpath中的附件.
	 */
	public File generateAttachment() throws MessagingException {
		try {
			Resource resource = new ClassPathResource(
					"/email/mailAttachment.txt");
			return resource.getFile();
		} catch (IOException e) {
			logger.error("构造邮件失败,附件文件不存在", e);
			throw new MessagingException("附件文件不存在", e);
		}
	}
}
