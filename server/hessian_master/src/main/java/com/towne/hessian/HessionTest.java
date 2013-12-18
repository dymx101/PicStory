package com.towne.hessian;


import java.net.MalformedURLException;

import com.caucho.hessian.client.HessianProxyFactory;
import com.towne.hessian.service.UserService;

public class HessionTest {

	public interface DemoService {  
		  
	    String sayHello();  

	}  

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/**
		 *  两种 hessian 的配置方式
		 */
		String url = "http://localhost:8888/dubbonode/demoService?application=consumer-of-helloworld-app&dubbo=2.5.3&interface=com.towne.framework.dubbo.DemoService&methods=sayHello&pid=4579&side=consumer&timestamp=1387346352002";
//		String url2 = "http://localhost:8080/hessian/report/userServiceServer";
		
		HessianProxyFactory proxy = new HessianProxyFactory();
		try {
			DemoService service = (DemoService) proxy.create(DemoService.class, url);
			System.out.println("sayHello() : " +service.sayHello());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
