package com.towne.framework.hibernate.dao.impl;

import org.springframework.stereotype.Repository;

import com.towne.framework.common.dao.HibernateDao;
import com.towne.framework.hibernate.bo.Page;

@Repository(value = "pageDaoHibernate4")
public class PageDao extends HibernateDao<Page, Long> {
}
