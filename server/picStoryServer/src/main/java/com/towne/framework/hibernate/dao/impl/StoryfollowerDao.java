package com.towne.framework.hibernate.dao.impl;

import org.springframework.stereotype.Repository;

import com.towne.framework.common.dao.HibernateDao;
import com.towne.framework.hibernate.bo.Storyfollower;

@Repository(value = "storyfollowerDaoHibernate4")
public class StoryfollowerDao extends HibernateDao<Storyfollower, Long> {
}
