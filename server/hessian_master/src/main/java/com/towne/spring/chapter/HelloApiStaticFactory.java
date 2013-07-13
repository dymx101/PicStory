package com.towne.spring.chapter;

public class HelloApiStaticFactory {
	
	public static HelloApi newInstance(String message) {
		return new HelloImpl2(message);
	}
}
