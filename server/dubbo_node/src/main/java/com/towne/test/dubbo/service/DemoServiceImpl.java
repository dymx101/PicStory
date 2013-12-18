package com.towne.test.dubbo.service;

public class DemoServiceImpl implements DemoService {
	/**
	 * 
	 */
	private static final long serialVersionUID = -352594398371778760L;

	/**
	 * 
	 */

	@Override
	// public String sayHello(String name) {
	// return "Hello " + name;
	// }
	public String sayHello(String name) {
//		System.out.println("["
//				+ new SimpleDateFormat("HH:mm:ss").format(new Date())
//				+ "] Hello " + name + ", request from consumer: "
//				+ RpcContext.getContext().getRemoteAddress());
//		return "Hello " + name + ", response form provider: "
//				+ RpcContext.getContext().getLocalAddress();
		return "dubbo in test"+name;
	}

}
