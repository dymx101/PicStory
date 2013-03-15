package com.towne.framework.hibernate.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.towne.framework.common.dao.IDao;
import com.towne.framework.hibernate.model.UserRelation;
import com.towne.framework.hibernate.service.UserRelationService;

@Service(value = "userRelationServiceImplHibernate4")
@Transactional
public class UserRelationServiceImpl implements UserRelationService {

	private IDao<UserRelation> dao;

	@Resource(name = "userRelationDaoHibernate4")
	public void setDao(IDao<UserRelation> dao) {
		this.dao = dao;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public UserRelation findById(long id) {
		// TODO Auto-generated method stub
		return dao.findById(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(UserRelation t) {
		// TODO Auto-generated method stub
		dao.update(t);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void add(UserRelation t) {
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
	public void delete(UserRelation t) {
		// TODO Auto-generated method stub
		dao.delete(t.getIdUSERELATION());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<UserRelation> query(String queryString) {
		// TODO Auto-generated method stub
		return dao.query(queryString);
	}

}
