package com.towne.framework.hibernate.service.impl;

import java.util.List;

import com.towne.framework.hibernate.dao.IDao;
import com.towne.framework.hibernate.model.Moment;
import com.towne.framework.hibernate.service.MomentService;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value="momentServiceImplHibernate4")
@Transactional
public class MomentServiceImpl implements MomentService {

	private IDao<Moment> dao;
	
	@Resource(name="momentDaoHibernate4")
	public void setDao(IDao<Moment> dao) {
		this.dao = dao;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void add(Object object) {
		// TODO Auto-generated method stub
         dao.add(object);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void delete(long id) {
		// TODO Auto-generated method stub
        dao.delete(id);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void update(Object object) {
		// TODO Auto-generated method stub
		dao.update(object);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<Moment> query(String queryString) {
		// TODO Auto-generated method stub
		return dao.query(queryString);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public Moment findById(long id) {
		// TODO Auto-generated method stub
		return dao.findById(id);
	}

}
