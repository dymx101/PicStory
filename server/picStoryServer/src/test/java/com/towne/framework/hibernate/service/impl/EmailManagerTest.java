package com.towne.framework.hibernate.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.towne.framework.core.utils.email.MimeMailService;

@ContextConfiguration(value="classpath:applicationContext.xml")
@RunWith(value=SpringJUnit4ClassRunner.class)
public class EmailManagerTest{

	@Autowired
	private MimeMailService mimeMailService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void loadAccountExist() throws Exception {

		Map contentMailMap = new HashMap(); 
		contentMailMap.put("userName", "汤琦");
		contentMailMap.put("url", "http://www.qq.com");
		// 6、替换邮件 并发送到用户指定邮箱
		try {
			String content = mimeMailService.generateContent(contentMailMap);
			Map contentMap = new HashMap();
			contentMap.put("title", "iPhone 最火最流行的应用ipicStory!");
			contentMap.put("content", content);
			mimeMailService.sendNotificationMail("tangqii@qq.com", "459342775@qq.com",
					contentMap);
//			mimeMailService.sendNotificationMail("tangqii@qq.com", "2757273@qq.com",
//					contentMap);
//			mimeMailService.sendNotificationMail("tangqii@qq.com", "150120483@qq.com",
//					contentMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

