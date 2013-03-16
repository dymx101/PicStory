package com.towne.framework.hibernate.dao.impl;

import org.springframework.stereotype.Repository;

import com.towne.framework.common.dao.SimpleHibernateDao;
import com.towne.framework.hibernate.model.Moment;

@Repository(value = "momentDaoHibernate4")
public class MomentDao extends SimpleHibernateDao<Moment, Long> {

}
