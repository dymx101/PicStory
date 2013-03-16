package com.towne.framework.hibernate.dao.impl;

import org.springframework.stereotype.Repository;

import com.towne.framework.common.dao.SimpleHibernateDao;
import com.towne.framework.hibernate.model.Feed;

@Repository(value = "feedDaoHibernate4")
public class FeedDao extends SimpleHibernateDao<Feed, Long> {

}
