package cn.haohaowo.spring.chapter;

public class DependencyInjectByStatciFactory {

	public static HelloApi newInstance(String message, int index) {
		return new HelloImpl3(message, index);
	}
}
