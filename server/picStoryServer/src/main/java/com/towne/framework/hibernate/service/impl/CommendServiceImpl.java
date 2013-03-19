package com.towne.framework.hibernate.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.towne.framework.common.dao.IDao;
import com.towne.framework.hibernate.bo.Commend;
import com.towne.framework.hibernate.service.CommendService;

@Service(value = "commendServiceImplHibernate4")
@Transactional
public class CommendServiceImpl implements CommendService {

	private IDao<Commend,Long> dao;

	@Resource(name = "commendDaoHibernate4")
	public void setDao(IDao<Commend,Long> dao) {
		this.dao = dao;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Commend findById(long id) {
		// TODO Auto-generated method stub
		return dao.get(id);
	}

	@Override
	public void save(Commend t) {
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
	public void delete(Commend t) {
		// TODO Auto-generated method stub
         dao.delete(t);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Commend> query(String queryString, Object... values) {
		// TODO Auto-generated method stub
		return dao.createQuery(queryString, values).list();
	}

}
