package com.towne.framework.hibernate.dao.impl;

import org.springframework.stereotype.Repository;

import com.towne.framework.common.dao.HibernateDao;
import com.towne.framework.hibernate.bo.Feed;

@Repository(value = "feedDaoHibernate4")
public class FeedDao extends HibernateDao<Feed, Long> {

}
