package cn.haohaowo.hessian;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.haohaowo.hessian.BasicAPI;
import cn.haohaowo.hessian.service.UserService;

public class ApplicationHessianTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-app.xml");
		BasicAPI basic = (BasicAPI) context.getBean("myServiceClient1");
		System.out.println("sayHello() : " + basic.sayHello());
		
		UserService userService = (UserService) context.getBean("userServiceClient");
		System.out.println("UserService result : " + userService.findAllUser());
		
	}

}
