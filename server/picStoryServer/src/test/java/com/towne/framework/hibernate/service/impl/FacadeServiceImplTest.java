package com.towne.framework.hibernate.service.impl;

import java.util.concurrent.TimeoutException;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.code.ssm.Cache;
import com.google.code.ssm.api.format.SerializationType;
import com.google.code.ssm.providers.CacheException;
import com.towne.framework.common.service.IFacadeService;
import com.towne.framework.common.model.Trader;
import com.towne.framework.core.hessian.BasicAPI;
import com.towne.framework.dubbo.DemoService;


@ContextConfiguration(value="classpath:applicationContext.xml")
@RunWith(value=SpringJUnit4ClassRunner.class)
public class FacadeServiceImplTest {

	@Resource(name="ifacadeServiceImpl")
	IFacadeService ifacadeService;
	
	@Autowired
	private DemoService basic;
	
//	@Autowired
//	private Cache cache;
	
	@Test
	public void tete1() throws TimeoutException, CacheException{
//		Trader trader = new Trader();
//		trader.setTraderName("towne");
//		trader.setTraderPassword("123");		
		System.out.println(">>>>" +basic.sayHello("aaa"));
//		System.out.println(">>>>>> "+ifacadeService.findPagesByMomentId(trader, 3l));
//		System.out.println(">>>>>> "+cache.get("USER_LOGVO_127.0.0.1",SerializationType.PROVIDER));
//		System.out.println(">>>>>> "+cache.get("USER_SESSION_127.0.0.1",SerializationType.PROVIDER));
	}
}
