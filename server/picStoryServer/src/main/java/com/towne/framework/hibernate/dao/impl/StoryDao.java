package com.towne.framework.hibernate.dao.impl;

import org.springframework.stereotype.Repository;

import com.towne.framework.common.dao.HibernateDao;
import com.towne.framework.hibernate.bo.Story;

@Repository(value = "storyDaoHibernate4")
public class StoryDao extends HibernateDao<Story, Long> {

}
