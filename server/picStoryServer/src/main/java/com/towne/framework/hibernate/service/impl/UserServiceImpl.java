package com.towne.framework.hibernate.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.towne.framework.common.dao.IDao;
import com.towne.framework.hibernate.model.User;
import com.towne.framework.hibernate.service.UserService;

@Service(value = "userServiceImplHibernate4")
@Transactional
public class UserServiceImpl implements UserService {

	private IDao<User> dao;

	@Resource(name = "userDaoHibernate4")
	public void setDao(IDao<User> dao) {
		this.dao = dao;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public User findById(long id) {
		// TODO Auto-generated method stub
		return dao.findById(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(User t) {
		// TODO Auto-generated method stub
		dao.update(t);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void add(User t) {
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
	public void delete(User t) {
		// TODO Auto-generated method stub
		dao.delete(t.getIdUSER());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<User> query(String queryString) {
		// TODO Auto-generated method stub
		return dao.query(queryString);
	}

}
