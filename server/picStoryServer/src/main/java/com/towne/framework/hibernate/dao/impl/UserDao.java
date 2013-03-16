package com.towne.framework.hibernate.dao.impl;

import org.springframework.stereotype.Repository;

import com.towne.framework.common.dao.SimpleHibernateDao;
import com.towne.framework.hibernate.model.User;

@Repository(value = "userDaoHibernate4")
public class UserDao extends SimpleHibernateDao<User, Long> {

}
