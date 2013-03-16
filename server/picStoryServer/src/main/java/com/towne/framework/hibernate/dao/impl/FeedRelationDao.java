package com.towne.framework.hibernate.dao.impl;

import org.springframework.stereotype.Repository;

import com.towne.framework.common.dao.HibernateDao;
import com.towne.framework.hibernate.bo.FeedRelation;

@Repository(value = "feedRelationDaoHibernate4")
public class FeedRelationDao extends HibernateDao<FeedRelation, Long> {
}
