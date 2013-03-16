package com.towne.framework.hibernate.dao.impl;

import org.springframework.stereotype.Repository;

import com.towne.framework.common.dao.HibernateDao;
import com.towne.framework.hibernate.bo.User;

@Repository(value = "userDaoHibernate4")
public class UserDao extends HibernateDao<User, Long> {

}
