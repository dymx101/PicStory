package com.towne.framework.hibernate.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.towne.framework.common.dao.IDao;
import com.towne.framework.hibernate.bo.FeedRelation;
import com.towne.framework.hibernate.service.FeedRelationService;

@Service(value = "feedRelationServiceImplHibernate4")
@Transactional
public class FeedRelationServiceImpl implements FeedRelationService {

	private IDao<FeedRelation, Long> dao;

	@Resource(name = "feedRelationDaoHibernate4")
	public void setDao(IDao<FeedRelation, Long> dao) {
		this.dao = dao;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public FeedRelation findById(long id) {
		// TODO Auto-generated method stub
		return dao.get(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteById(long id) {
		// TODO Auto-generated method stub
		dao.delete(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(FeedRelation t) {
		// TODO Auto-generated method stub
		dao.delete(t);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(FeedRelation t) {
		// TODO Auto-generated method stub
		dao.save(t);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<FeedRelation> query(String queryString, Object... values) {
		// TODO Auto-generated method stub
		return dao.createQuery(queryString, values).list();
	}

}
