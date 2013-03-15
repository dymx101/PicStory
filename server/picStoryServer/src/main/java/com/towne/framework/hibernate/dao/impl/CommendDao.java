package com.towne.framework.hibernate.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.towne.framework.common.dao.IDao;
import com.towne.framework.hibernate.model.Commend;

@Repository(value="commendDaoHibernate4")
public class CommendDao implements IDao<Commend> {

	private SessionFactory sessionFactory;
	
	@Resource(name="hibernate4sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	@Override
	public void add(Object object) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().save(object);
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().delete(findById(id));
	}

	@Override
	public void update(Object object) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().update(object);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Commend> query(String queryString) {
		// TODO Auto-generated method stub
		return this.sessionFactory.getCurrentSession().createQuery(queryString).list();
	}

	@Override
	public Commend findById(long id) {
		// TODO Auto-generated method stub
		return (Commend) this.sessionFactory.getCurrentSession().get(Commend.class, id);
	}

}
