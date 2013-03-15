package com.towne.framework.hibernate.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.towne.framework.common.dao.IDao;
import com.towne.framework.hibernate.model.Feed;
import com.towne.framework.hibernate.service.FeedService;

@Service(value = "feedServiceImplHibernate4")
@Transactional
public class FeedServiceImpl implements FeedService {

	private IDao<Feed> dao;

	@Resource(name = "feedDaoHibernate4")
	public void setDao(IDao<Feed> dao) {
		this.dao = dao;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Feed findById(long id) {
		// TODO Auto-generated method stub
		return dao.findById(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Feed t) {
		// TODO Auto-generated method stub
		dao.update(t);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void add(Feed t) {
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
	public void delete(Feed t) {
		// TODO Auto-generated method stub
		dao.delete(t.getIdFEEDS());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Feed> query(String queryString) {
		// TODO Auto-generated method stub
		return dao.query(queryString);
	}

}
