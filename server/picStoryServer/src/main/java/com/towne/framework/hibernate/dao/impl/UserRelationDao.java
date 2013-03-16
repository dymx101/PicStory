package com.towne.framework.hibernate.dao.impl;

import org.springframework.stereotype.Repository;

import com.towne.framework.common.dao.SimpleHibernateDao;
import com.towne.framework.hibernate.model.UserRelation;

@Repository(value = "userRelationDaoHibernate4")
public class UserRelationDao extends SimpleHibernateDao<UserRelation, Long> {
}
