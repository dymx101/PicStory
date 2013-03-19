package com.towne.framework.hibernate.dao.impl;

import org.springframework.stereotype.Repository;

import com.towne.framework.common.dao.HibernateDao;
import com.towne.framework.hibernate.bo.UserRelation;

@Repository(value = "userRelationDaoHibernate4")
public class UserRelationDao extends HibernateDao<UserRelation, Long> {
}
