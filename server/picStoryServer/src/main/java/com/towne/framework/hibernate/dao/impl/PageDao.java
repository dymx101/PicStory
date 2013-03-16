package com.towne.framework.hibernate.dao.impl;

import org.springframework.stereotype.Repository;

import com.towne.framework.common.dao.SimpleHibernateDao;
import com.towne.framework.hibernate.model.Page;

@Repository(value = "pageDaoHibernate4")
public class PageDao extends SimpleHibernateDao<Page, Long> {
}
