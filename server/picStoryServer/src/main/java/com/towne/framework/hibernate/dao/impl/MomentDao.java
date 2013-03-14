package com.towne.framework.hibernate.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.towne.framework.hibernate.dao.IDao;
import com.towne.framework.hibernate.model.Moment;

@Repository(value="momentDaoHibernate4")
public class MomentDao implements IDao<Moment> {

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
	public List<Moment> query(String queryString) {
		// TODO Auto-generated method stub
		return this.sessionFactory.getCurrentSession().createQuery(queryString).list();
	}

	@Override
	public Moment findById(long id) {
		// TODO Auto-generated method stub
		return (Moment) this.sessionFactory.getCurrentSession().get(Moment.class, id);
	}

}
