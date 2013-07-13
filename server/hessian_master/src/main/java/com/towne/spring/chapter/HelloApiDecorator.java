package com.towne.spring.chapter;

public class HelloApiDecorator implements HelloApi {

	private HelloApi helloApi;
	
	public HelloApiDecorator() {}
	
	public HelloApiDecorator(HelloApi helloApi) {
		this.helloApi = helloApi;
	}
	
	public HelloApi getHelloApi() {
		return helloApi;
	}
	
	public void setHelloApi(HelloApi helloApi) {
		this.helloApi = helloApi;
	}

	public void sayHello() {
		System.out.println("======修饰一下======");
		helloApi.sayHello();
		System.out.println("======修饰一下======");
	}

}
