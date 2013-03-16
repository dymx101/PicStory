package com.towne.framework.hibernate.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.towne.framework.common.dao.IDao;
import com.towne.framework.hibernate.bo.Page;
import com.towne.framework.hibernate.service.PageService;

@Service(value = "pageServiceImplHibernate4")
@Transactional
public class PageServiceImpl implements PageService {

	private IDao<Page,Long> dao;

	@Resource(name = "pageDaoHibernate4")
	public void setDao(IDao<Page,Long> dao) {
		this.dao = dao;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Page findById(long id) {
		// TODO Auto-generated method stub
		return dao.get(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(Page t) {
		// TODO Auto-generated method stub
		dao.save(t);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteById(long id) {
		// TODO Auto-generated method stub
		dao.delete(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Page t) {
		// TODO Auto-generated method stub
		dao.delete(t);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Page> query(String queryString,Object... values) {
		// TODO Auto-generated method stub
		return dao.createQuery(queryString, values).list();
	}

}
