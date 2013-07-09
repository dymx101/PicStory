package cn.haohaowo.hessian;


import java.net.MalformedURLException;

import com.caucho.hessian.client.HessianProxyFactory;
import cn.haohaowo.hessian.service.UserService;

public class HessionTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/**
		 *  两种 hessian 的配置方式
		 */
		String url = "http://localhost:8080/hessian/test/test";
		String url2 = "http://localhost:8080/hessian/report/userServiceServer";
		
		HessianProxyFactory proxy = new HessianProxyFactory();
		try {
			UserService service = (UserService) proxy.create(UserService.class, url2);
			BasicAPI basic = (BasicAPI) proxy.create(BasicAPI.class, url);
			System.out.println("findAllUser() : " +service.findAllUser());
			System.out.println("sayHello() : " +basic.sayHello());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
