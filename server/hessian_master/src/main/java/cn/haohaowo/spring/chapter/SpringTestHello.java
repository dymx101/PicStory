package cn.haohaowo.spring.chapter;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTestHello {

//	@Test
	public void testHello() {
		BeanFactory beanFactory = new ClassPathXmlApplicationContext("cn/haohaowo/spring/chapter/chapter-app.xml");
		HelloApi helloApi1 = beanFactory.getBean("bean1", HelloApi.class);
		helloApi1.sayHello();
		HelloApi helloApi2 = beanFactory.getBean("alias1", HelloApi.class);
		helloApi2.sayHello();
		HelloApi helloApi3 = beanFactory.getBean("bean2", HelloApi.class);
		helloApi3.sayHello();
		
		String[] bean3Alias = beanFactory.getAliases("bean2");
		Assert.assertEquals(0, bean3Alias.length);
	}
	
//	@Test
	public void testHelloSetMethod() {
		BeanFactory beanFactory = new ClassPathXmlApplicationContext("cn/haohaowo/spring/chapter/chapter-app.xml");
		HelloApi helloApi3 = beanFactory.getBean("bean3", HelloApi.class);
		helloApi3.sayHello();
		HelloApi helloApi4 = beanFactory.getBean("bean4", HelloApi.class);
		helloApi4.sayHello();
	}
	
//	@Test
	public void testHelloStaticFactory() {
		BeanFactory beanFactory = new ClassPathXmlApplicationContext("cn/haohaowo/spring/chapter/chapter-app.xml");
		HelloApi helloApi5 = beanFactory.getBean("bean5", HelloApi.class);
		helloApi5.sayHello();
	}
	
//	@Test
	public void testHelloInstanceFactory() {
		BeanFactory beanFactory = new ClassPathXmlApplicationContext("cn/haohaowo/spring/chapter/chapter-app.xml");
		HelloApi helloApi6 = beanFactory.getBean("bean6", HelloApi.class);
		helloApi6.sayHello();
	}
	
//	@Test
	public void testHelloConstructorDependencyInject() {
		BeanFactory beanFactory = new ClassPathXmlApplicationContext("cn/haohaowo/spring/chapter/chapter-app.xml");
		HelloApi helloApi7 = beanFactory.getBean("byIndex", HelloApi.class);
		helloApi7.sayHello();
		HelloApi helloApi8 = beanFactory.getBean("byType", HelloApi.class);
		helloApi8.sayHello();
		HelloApi helloApi9 = beanFactory.getBean("byName", HelloApi.class);
		helloApi9.sayHello();
	}
	
//	@Test
	public void testHelloConstructorDependencyInjectByStatic() {
		BeanFactory beanFactory = new ClassPathXmlApplicationContext("cn/haohaowo/spring/chapter/chapter-app.xml");
		HelloApi helloApi10 = beanFactory.getBean("byIndexStatic", HelloApi.class);
		helloApi10.sayHello();
		HelloApi helloApi11 = beanFactory.getBean("byTypeStatic", HelloApi.class);
		helloApi11.sayHello();
		HelloApi helloApi12 = beanFactory.getBean("byNameStatic", HelloApi.class);
		helloApi12.sayHello();
	}
	
//	@Test
	public void testHelloConstructorDependencyInjectByInstance() {
		BeanFactory beanFactory = new ClassPathXmlApplicationContext("cn/haohaowo/spring/chapter/chapter-app.xml");
		HelloApi helloApi13 = beanFactory.getBean("byIndexInstance", HelloApi.class);
		helloApi13.sayHello();
		HelloApi helloApi14 = beanFactory.getBean("byTypeInstance", HelloApi.class);
		helloApi14.sayHello();
		HelloApi helloApi15 = beanFactory.getBean("byNameInstance", HelloApi.class);
		helloApi15.sayHello();
	}
	
//	@Test
	public void testHelloSetMethodNew() {
		BeanFactory beanFactory = new ClassPathXmlApplicationContext("cn/haohaowo/spring/chapter/chapter-app.xml");
		HelloApi helloApi16 = beanFactory.getBean("beanSetMethod", HelloApi.class);
		helloApi16.sayHello();
	}
	
//	@Test
	public void testListTestBean() {
		BeanFactory beanFactory = new ClassPathXmlApplicationContext("cn/haohaowo/spring/chapter/chapter-app.xml");
		ListTestBean listBean = beanFactory.getBean("listBean", ListTestBean.class);
		System.out.println(listBean.getValues().size());
		Assert.assertEquals(3, listBean.getValues().size());
	}
	
	@Test
	public void testDecorator() {
		BeanFactory beanFactory = new ClassPathXmlApplicationContext("chapter-app.xml");
		HelloApi decorator1 = beanFactory.getBean("decorator1", HelloApi.class);
		decorator1.sayHello();
		HelloApi decorator2 = beanFactory.getBean("decorator2", HelloApi.class);
		decorator2.sayHello();
	}
}
