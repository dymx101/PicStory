package com.towne.framework.common.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.towne.framework.springmvc.model.MomentV;
import com.towne.framework.springmvc.model.PageV;

@Service(value="ifacadeServiceImpl")
@Transactional
public class IFacadeServiceImpl implements IFacadeService  {

	@Resource(name="momentServiceImplHibernate4")
    MomentService momentService;

	@Override
	public Moment findById(Trader trader, long id) {
		// TODO Auto-generated method stub
		return momentService.findById(id);
	}

	@Override
	public void update(Trader trader, Moment t) {
		// TODO Auto-generated method stub
		momentService.update(t);
	}

	@Override
	public void add(Trader trader, Moment t) {
		// TODO Auto-generated method stub
		momentService.add(t);
	}

	@Override
	public void deleteById(Trader trader, long id) {
		// TODO Auto-generated method stub
		momentService.delete(id);
	}

	@Override
	public void delete(Trader trader, Moment t) {
		// TODO Auto-generated method stub
		momentService.delete(t.getIdMOMENT());
	}

	@Override
	public List<Moment> query(Trader trader, String queryString) {
		// TODO Auto-generated method stub
		return momentService.query(queryString);
	}

	@ReadThroughSingleCache(namespace = "Echo", expiration = 1000)
	@Override
	public MomentV findPages(Trader trader,@ParameterValueKeyProvider long id) {
		// TODO Auto-generated method stub
		Moment moment = momentService.findById(id);
		MomentV mv = new MomentV();
		mv.setpMonDesc(moment.getpMonDesc());
		mv.setpMonIndex(moment.getpMonIndex());
		Set<PageV> pagevs = new HashSet<PageV>();
		for(Page page : moment.getPages())
		{
			PageV pv = new PageV();
			pv.setContent(page.getContent());
			pv.setMediaType(page.getMediaType());
			pv.setMediaUrl(page.getMediaUrl());
			pagevs.add(pv);
		}
		mv.setPages(pagevs);
		return mv;
	}
}
