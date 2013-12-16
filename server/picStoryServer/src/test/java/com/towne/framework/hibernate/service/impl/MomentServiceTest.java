package com.towne.framework.hibernate.service.impl;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.towne.framework.hibernate.bo.Moment;
import com.towne.framework.hibernate.bo.Page;
import com.towne.framework.hibernate.service.MomentService;

@ContextConfiguration(locations = "classpath:spring-hibernate.xml")
@RunWith(value = SpringJUnit4ClassRunner.class)
public class MomentServiceTest {

	@Resource(name = "momentServiceImplHibernate4")
	MomentService momentService;

	 @Test
	public void tetstts() {

		Moment moment = new Moment();
		moment.setpMonIndex(2);
		moment.setpMonDesc("add desc!!!");

		Set<Page> set = new HashSet<Page>();
		Page page = new Page();
		page.setMediaUrl("www.qq.com");
		page.setContent("this is a pic!");
		page.setMediaType(0);
		page.setMoment(moment);
		set.add(page);

		Page page1 = new Page();
		page1.setMediaUrl("www.sohu.com");
		page1.setContent("this is a media!");
		page1.setMediaType(1);
		page1.setMoment(moment);
		set.add(page1);

		moment.setPages(set);

		momentService.save(moment);
	}

//	@Test
	public void tesdasd() {

		System.out
				.print(momentService
						.query("select b from Moment a , Page b where a.idMOMENT=b.moment.idMOMENT"));

	}

}
