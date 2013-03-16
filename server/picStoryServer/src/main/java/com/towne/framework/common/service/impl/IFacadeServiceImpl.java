package com.towne.framework.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
import com.towne.framework.common.service.IFacadeService;
import com.towne.framework.common.model.Trader;
import com.towne.framework.hibernate.model.Moment;
import com.towne.framework.hibernate.model.Page;
import com.towne.framework.hibernate.service.MomentService;
import com.towne.framework.hibernate.service.PageService;
import com.towne.framework.springmvc.model.MomentV;
import com.towne.framework.springmvc.model.PageV;

@Service(value = "ifacadeServiceImpl")
@Transactional
public class IFacadeServiceImpl implements IFacadeService {

	@Resource(name = "momentServiceImplHibernate4")
	MomentService momentService;

	@Resource(name = "pageServiceImplHibernate4")
	PageService pageService;

	@Override
	public Moment findById(Trader trader, long id) {
		// TODO Auto-generated method stub
		return momentService.findById(id);
	}

	@Override
	public void save(Trader trader, Moment t) {
		// TODO Auto-generated method stub
		momentService.save(t);
	}

	@Override
	public void deleteById(Trader trader, long id) {
		// TODO Auto-generated method stub
		momentService.deleteById(id);
	}

	@Override
	public void delete(Trader trader, Moment t) {
		// TODO Auto-generated method stub
		momentService.delete(t);
	}

	@Override
	public List<MomentV> query(Trader trader, String queryString,
			Object... values) {
		// TODO Auto-generated method stub
		List<Moment> moments = momentService.query(queryString, values);
		List<MomentV> momentvs = new ArrayList<MomentV>();
		int i = 0;
		for (Moment m : moments) {
			MomentV mv = new MomentV();
			List<PageV> pagevs = new ArrayList<PageV>();
			for (Page page : m.getPages()) {
				PageV pv = new PageV();
				pv.setContent(page.getContent());
				pv.setMediaType(page.getMediaType());
				pv.setMediaUrl(page.getMediaUrl());
				pagevs.add(pv);
			}
			mv.setPages(pagevs);
			mv.setpMonIndex(i++);
			mv.setpMonDesc("test");
			momentvs.add(mv);
		}
		return momentvs;
	}

	@ReadThroughSingleCache(namespace = "Echo", expiration = 1000)
	@Override
	public List<PageV> findPagesByMomentId(Trader trader,
			@ParameterValueKeyProvider long id) {
		// TODO Auto-generated method stub
		// select a from Moment a , Page b where a.idMOMENT=b.moment.idMOMENT
		List<Page> pages = pageService
				.query("select b from Moment a , Page b where a.idMOMENT=b.moment.idMOMENT and a.idMOMENT=?",
						id);
		List<PageV> pagevs = new ArrayList<PageV>();
		for (Page page : pages) {
			PageV pv = new PageV();
			pv.setContent(page.getContent());
			pv.setMediaType(page.getMediaType());
			pv.setMediaUrl(page.getMediaUrl());
			pagevs.add(pv);
		}
		return pagevs;
	}
}
