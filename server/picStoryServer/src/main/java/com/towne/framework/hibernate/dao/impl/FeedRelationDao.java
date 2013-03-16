package com.towne.framework.hibernate.dao.impl;

import org.springframework.stereotype.Repository;

import com.towne.framework.common.dao.SimpleHibernateDao;
import com.towne.framework.hibernate.model.FeedRelation;

@Repository(value = "feedRelationDaoHibernate4")
public class FeedRelationDao extends SimpleHibernateDao<FeedRelation, Long> {
}
