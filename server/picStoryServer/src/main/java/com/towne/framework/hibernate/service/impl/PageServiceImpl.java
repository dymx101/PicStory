package com.towne.framework.hibernate.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.towne.framework.common.dao.IDao;
import com.towne.framework.hibernate.model.Page;
import com.towne.framework.hibernate.service.PageService;

@Service(value = "pageServiceImplHibernate4")
@Transactional
public class PageServiceImpl implements PageService {

	private IDao<Page> dao;

	@Resource(name = "pageDaoHibernate4")
	public void setDao(IDao<Page> dao) {
		this.dao = dao;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Page findById(long id) {
		// TODO Auto-generated method stub
		return dao.findById(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Page t) {
		// TODO Auto-generated method stub
		dao.update(t);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void add(Page t) {
		// TODO Auto-generated method stub
		dao.add(t);
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
		dao.delete(t.getIdPAGE());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Page> query(String queryString) {
		// TODO Auto-generated method stub
		return dao.query(queryString);
	}

}
