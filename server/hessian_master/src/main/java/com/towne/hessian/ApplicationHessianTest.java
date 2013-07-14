package com.towne.hessian;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.towne.hessian.BasicAPI;
import com.towne.hessian.service.UserService;


public class ApplicationHessianTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		BasicAPI basic = (BasicAPI) context.getBean("myServiceClient");
		System.out.println("sayHello() : " + basic.sayHello());
		
		UserService userService = (UserService) context.getBean("userServiceClient");
		System.out.println("UserService result : " + userService.findAllUser());
		
	}

}
