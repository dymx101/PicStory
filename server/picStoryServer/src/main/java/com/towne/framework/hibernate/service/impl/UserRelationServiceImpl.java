package com.towne.framework.hibernate.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.towne.framework.common.dao.IDao;
import com.towne.framework.hibernate.bo.UserRelation;
import com.towne.framework.hibernate.service.UserRelationService;

@Service(value = "userRelationServiceImplHibernate4")
@Transactional
public class UserRelationServiceImpl implements UserRelationService {

	private IDao<UserRelation,Long> dao;

	@Resource(name = "userRelationDaoHibernate4")
	public void setDao(IDao<UserRelation,Long> dao) {
		this.dao = dao;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public UserRelation findById(long id) {
		// TODO Auto-generated method stub
		return dao.get(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(UserRelation t) {
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
	public void delete(UserRelation t) {
		// TODO Auto-generated method stub
		dao.delete(t);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<UserRelation> query(String queryString,Object... values) {
		// TODO Auto-generated method stub
		return dao.createQuery(queryString, values).list();
	}

}
