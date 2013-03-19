package com.towne.framework.hibernate.service.impl;

import java.util.List;

import com.towne.framework.common.dao.IDao;
import com.towne.framework.hibernate.bo.Moment;
import com.towne.framework.hibernate.service.MomentService;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "momentServiceImplHibernate4")
@Transactional
public class MomentServiceImpl implements MomentService {

	private IDao<Moment, Long> dao;

	@Resource(name = "momentDaoHibernate4")
	public void setDao(IDao<Moment, Long> dao) {
		this.dao = dao;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Moment findById(long id) {
		// TODO Auto-generated method stub
		return dao.get(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(Moment t) {
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
	public void delete(Moment t) {
		// TODO Auto-generated method stub
		dao.delete(t);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Moment> query(String queryString, Object... values) {
		// TODO Auto-generated method stub
		return dao.createQuery(queryString, values).list();
	}

}
