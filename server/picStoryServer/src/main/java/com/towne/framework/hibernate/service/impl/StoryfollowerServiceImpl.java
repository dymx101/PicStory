package com.towne.framework.hibernate.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.towne.framework.common.dao.IDao;
import com.towne.framework.hibernate.model.Storyfollower;
import com.towne.framework.hibernate.service.StoryfollowerService;

@Service(value = "storyfollowerServiceImplHibernate4")
@Transactional
public class StoryfollowerServiceImpl implements StoryfollowerService {

	private IDao<Storyfollower> dao;

	@Resource(name = "storyfollowerDaoHibernate4")
	public void setDao(IDao<Storyfollower> dao) {
		this.dao = dao;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Storyfollower findById(long id) {
		// TODO Auto-generated method stub
		return dao.findById(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Storyfollower t) {
		// TODO Auto-generated method stub
		dao.update(t);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void add(Storyfollower t) {
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
	public void delete(Storyfollower t) {
		// TODO Auto-generated method stub
		dao.delete(t.getIdSTORYFOLLOWER());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Storyfollower> query(String queryString) {
		// TODO Auto-generated method stub
		return dao.query(queryString);
	}

}
