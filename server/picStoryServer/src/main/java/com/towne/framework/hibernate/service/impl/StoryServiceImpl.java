package com.towne.framework.hibernate.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.towne.framework.common.dao.IDao;
import com.towne.framework.hibernate.model.Story;
import com.towne.framework.hibernate.service.StoryService;

@Service(value = "storyServiceImplHibernate4")
@Transactional
public class StoryServiceImpl implements StoryService {

	private IDao<Story> dao;

	@Resource(name = "storyDaoHibernate4")
	public void setDao(IDao<Story> dao) {
		this.dao = dao;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Story findById(long id) {
		// TODO Auto-generated method stub
		return dao.findById(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Story t) {
		// TODO Auto-generated method stub
		dao.update(t);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void add(Story t) {
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
	public void delete(Story t) {
		// TODO Auto-generated method stub
		dao.delete(t.getIdSTORY());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Story> query(String queryString) {
		// TODO Auto-generated method stub
		return dao.query(queryString);
	}

}
