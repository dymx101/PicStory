package com.towne.framework.hibernate.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.towne.framework.common.dao.IDao;
import com.towne.framework.hibernate.model.Story;

@Repository(value = "storyDaoHibernate4")
public class StoryDao implements IDao<Story> {

	private SessionFactory sessionFactory;

	@Resource(name = "hibernate4sessionFactory")
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Story> query(String queryString) {
		// TODO Auto-generated method stub
		return this.sessionFactory.getCurrentSession().createQuery(queryString)
				.list();
	}

	@Override
	public Story findById(long id) {
		// TODO Auto-generated method stub
		return (Story) this.sessionFactory.getCurrentSession().get(Story.class,
				id);
	}

}
