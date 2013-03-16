package com.towne.framework.hibernate.dao.impl;

import org.springframework.stereotype.Repository;

import com.towne.framework.common.dao.SimpleHibernateDao;
import com.towne.framework.hibernate.model.Story;

@Repository(value = "storyDaoHibernate4")
public class StoryDao extends SimpleHibernateDao<Story, Long> {

}
