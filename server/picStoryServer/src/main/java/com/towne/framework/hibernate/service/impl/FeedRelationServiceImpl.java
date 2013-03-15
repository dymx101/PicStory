package com.towne.framework.hibernate.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.towne.framework.common.dao.IDao;
import com.towne.framework.hibernate.model.FeedRelation;
import com.towne.framework.hibernate.service.FeedRelationService;

@Service(value = "feedRelationServiceImplHibernate4")
@Transactional
public class FeedRelationServiceImpl implements FeedRelationService {

	private IDao<FeedRelation> dao;

	@Resource(name = "feedRelationDaoHibernate4")
	public void setDao(IDao<FeedRelation> dao) {
		this.dao = dao;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public FeedRelation findById(long id) {
		// TODO Auto-generated method stub
		return dao.findById(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(FeedRelation t) {
		// TODO Auto-generated method stub
		dao.update(t);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void add(FeedRelation t) {
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
	public void delete(FeedRelation t) {
		// TODO Auto-generated method stub
		dao.delete(t.getIdFEEDRELATION());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<FeedRelation> query(String queryString) {
		// TODO Auto-generated method stub
		return dao.query(queryString);
	}

}
