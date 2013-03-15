package com.towne.framework.hibernate.service.impl;

import java.util.List;

import com.towne.framework.common.dao.IDao;
import com.towne.framework.hibernate.model.Moment;
import com.towne.framework.hibernate.service.MomentService;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "momentServiceImplHibernate4")
@Transactional
public class MomentServiceImpl implements MomentService {

	private IDao<Moment> dao;

	@Resource(name = "momentDaoHibernate4")
	public void setDao(IDao<Moment> dao) {
		this.dao = dao;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Moment findById(long id) {
		// TODO Auto-generated method stub
		return dao.findById(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Moment t) {
		// TODO Auto-generated method stub
		dao.update(t);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void add(Moment t) {
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
	public void delete(Moment t) {
		// TODO Auto-generated method stub
		dao.delete(t.getIdMOMENT());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Moment> query(String queryString) {
		// TODO Auto-generated method stub
		return dao.query(queryString);
	}

}
