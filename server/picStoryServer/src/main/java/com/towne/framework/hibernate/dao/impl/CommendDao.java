package com.towne.framework.hibernate.dao.impl;

import org.springframework.stereotype.Repository;

import com.towne.framework.common.dao.SimpleHibernateDao;
import com.towne.framework.hibernate.model.Commend;

@Repository(value = "commendDaoHibernate4")
public class CommendDao extends SimpleHibernateDao<Commend, Long> {

}
