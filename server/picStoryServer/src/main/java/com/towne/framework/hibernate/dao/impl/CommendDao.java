package com.towne.framework.hibernate.dao.impl;

import org.springframework.stereotype.Repository;

import com.towne.framework.common.dao.HibernateDao;
import com.towne.framework.hibernate.bo.Commend;

@Repository(value = "commendDaoHibernate4")
public class CommendDao extends HibernateDao<Commend, Long> {

}
