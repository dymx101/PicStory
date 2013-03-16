package com.towne.framework.hibernate.dao.impl;

import org.springframework.stereotype.Repository;

import com.towne.framework.common.dao.HibernateDao;
import com.towne.framework.hibernate.bo.Moment;

@Repository(value = "momentDaoHibernate4")
public class MomentDao extends HibernateDao<Moment, Long> {

}
