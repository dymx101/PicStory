package com.towne.framework.hibernate.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.towne.framework.common.dao.IDao;
import com.towne.framework.hibernate.bo.Feed;
import com.towne.framework.hibernate.service.FeedService;

@Service(value = "feedServiceImplHibernate4")
@Transactional
public class FeedServiceImpl implements FeedService {

	private IDao<Feed,Long> dao;

	@Resource(name = "feedDaoHibernate4")
	public void setDao(IDao<Feed,Long> dao) {
		this.dao = dao;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Feed findById(long id) {
		// TODO Auto-generated method stub
		return dao.get(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(Feed t) {
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
	public void delete(Feed t) {
		// TODO Auto-generated method stub
		dao.delete(t);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Feed> query(String queryString,Object... values) {
		// TODO Auto-generated method stub
		return dao.createQuery(queryString, values).list();
	}

}
