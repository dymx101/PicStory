package com.towne.framework.hibernate.dao.impl;

import org.springframework.stereotype.Repository;

import com.towne.framework.common.dao.SimpleHibernateDao;
import com.towne.framework.hibernate.model.Storyfollower;

@Repository(value = "storyfollowerDaoHibernate4")
public class StoryfollowerDao extends SimpleHibernateDao<Storyfollower, Long> {
}
