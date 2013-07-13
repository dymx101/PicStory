package com.towne.spring.chapter;

public class HelloApiInstanceFactory {

	public HelloApi newInstance(String message) {
		return new HelloImpl2(message);
	}
}
