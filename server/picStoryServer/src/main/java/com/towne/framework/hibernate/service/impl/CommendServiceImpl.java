package com.towne.framework.hibernate.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.towne.framework.common.dao.IDao;
import com.towne.framework.hibernate.model.Commend;
import com.towne.framework.hibernate.service.CommendService;

@Service(value = "commendServiceImplHibernate4")
@Transactional
public class CommendServiceImpl implements CommendService {

	private IDao<Commend> dao;

	@Resource(name = "commendDaoHibernate4")
	public void setDao(IDao<Commend> dao) {
		this.dao = dao;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Commend findById(long id) {
		// TODO Auto-generated method stub
		return dao.findById(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Commend t) {
		// TODO Auto-generated method stub
          dao.update(t);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void add(Commend t) {
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
	public void delete(Commend t) {
		// TODO Auto-generated method stub
         dao.delete(t.getIdCOMMEND());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Commend> query(String queryString) {
		// TODO Auto-generated method stub
		return dao.query(queryString);
	}

}
