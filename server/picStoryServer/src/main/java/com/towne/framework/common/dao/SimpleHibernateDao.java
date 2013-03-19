package com.towne.framework.common.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.towne.framework.core.utils.reflection.ReflectionUtils;

@Repository(value = "simpleHibernateDaoHibernate4")
public class SimpleHibernateDao<T, PK extends Serializable> implements
		IDao<T, PK> {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected SessionFactory sessionFactory;

	protected Class<T> entityClass;

	/**
	 * 用于Dao层子类使用的构造函数. 通过子类的泛型定义取得对象类型Class. eg. public class UserDao extends
	 * SimpleHibernateDao<User, Long>
	 */
	public SimpleHibernateDao() {
		this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
	}

	/**
	 * 用于用于省略Dao层, 在Service层直接使用通用SimpleHibernateDao的构造函数. 在构造函数中定义对象类型Class.
	 * eg. SimpleHibernateDao<User, Long> userDao = new SimpleHibernateDao<User,
	 * Long>(sessionFactory, User.class);
	 */
	public SimpleHibernateDao(final SessionFactory sessionFactory,
			final Class<T> entityClass) {
		this.sessionFactory = sessionFactory;
		this.entityClass = entityClass;
	}

	/**
	 * 取得sessionFactory.
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.towne.framework.common.dao.IDao#setSessionFactory(org.hibernate.
	 * SessionFactory)
	 */
	@Resource(name = "hibernate4sessionFactory")
	public void setSessionFactory(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.towne.framework.common.dao.IDao#getSession()
	 */
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.towne.framework.common.dao.IDao#save(T)
	 */
	@Override
	public void save(final T entity) {
		Assert.notNull(entity, "entity不能为空");
		getSession().saveOrUpdate(entity);
		logger.debug("save entity: {}", entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.towne.framework.common.dao.IDao#delete(T)
	 */
	@Override
	public void delete(final T entity) {
		Assert.notNull(entity, "entity不能为空");
		getSession().delete(entity);
		logger.debug("delete entity: {}", entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.towne.framework.common.dao.IDao#delete(PK)
	 */
	@Override
	public void delete(final PK id) {
		Assert.notNull(id, "id不能为空");
		delete(get(id));
		logger.debug("delete entity {},id is {}", entityClass.getSimpleName(),
				id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.towne.framework.common.dao.IDao#get(PK)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public T get(final PK id) {
		Assert.notNull(id, "id不能为空");
		return (T) getSession().load(entityClass, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.towne.framework.common.dao.IDao#get(java.util.Collection)
	 */
	@Override
	public List<T> get(final Collection<PK> ids) {
		return find(Restrictions.in(getIdName(), ids));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.towne.framework.common.dao.IDao#getAll()
	 */
	@Override
	public List<T> getAll() {
		return find();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.towne.framework.common.dao.IDao#getAll(java.lang.String,
	 * boolean)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<T> getAll(String orderByProperty, boolean isAsc) {
		Criteria c = createCriteria();
		if (isAsc) {
			c.addOrder(Order.asc(orderByProperty));
		} else {
			c.addOrder(Order.desc(orderByProperty));
		}
		return c.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.towne.framework.common.dao.IDao#findBy(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public List<T> findBy(final String propertyName, final Object value) {
		Assert.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return find(criterion);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.towne.framework.common.dao.IDao#findUniqueBy(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public T findUniqueBy(final String propertyName, final Object value) {
		Assert.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return (T) createCriteria(criterion).uniqueResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.towne.framework.common.dao.IDao#find(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <X> List<X> find(final String hql, final Object... values) {
		return createQuery(hql, values).list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.towne.framework.common.dao.IDao#find(java.lang.String,
	 * java.util.Map)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <X> List<X> find(final String hql, final Map<String, ?> values) {
		return createQuery(hql, values).list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.towne.framework.common.dao.IDao#findUnique(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <X> X findUnique(final String hql, final Object... values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.towne.framework.common.dao.IDao#findUnique(java.lang.String,
	 * java.util.Map)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <X> X findUnique(final String hql, final Map<String, ?> values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.towne.framework.common.dao.IDao#batchExecute(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public int batchExecute(final String hql, final Object... values) {
		return createQuery(hql, values).executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.towne.framework.common.dao.IDao#batchExecute(java.lang.String,
	 * java.util.Map)
	 */
	@Override
	public int batchExecute(final String hql, final Map<String, ?> values) {
		return createQuery(hql, values).executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.towne.framework.common.dao.IDao#createQuery(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public Query createQuery(final String queryString, final Object... values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.towne.framework.common.dao.IDao#createQuery(java.lang.String,
	 * java.util.Map)
	 */
	@Override
	public Query createQuery(final String queryString,
			final Map<String, ?> values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.towne.framework.common.dao.IDao#find(org.hibernate.criterion.Criterion
	 * )
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<T> find(final Criterion... criterions) {
		return createCriteria(criterions).list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.towne.framework.common.dao.IDao#findUnique(org.hibernate.criterion
	 * .Criterion)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public T findUnique(final Criterion... criterions) {
		return (T) createCriteria(criterions).uniqueResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.towne.framework.common.dao.IDao#createCriteria(org.hibernate.criterion
	 * .Criterion)
	 */
	@Override
	public Criteria createCriteria(final Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.towne.framework.common.dao.IDao#initProxyObject(java.lang.Object)
	 */
	@Override
	public void initProxyObject(Object proxy) {
		Hibernate.initialize(proxy);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.towne.framework.common.dao.IDao#flush()
	 */
	@Override
	public void flush() {
		getSession().flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.towne.framework.common.dao.IDao#distinct(org.hibernate.Query)
	 */
	@Override
	public Query distinct(Query query) {
		query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return query;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.towne.framework.common.dao.IDao#distinct(org.hibernate.Criteria)
	 */
	@Override
	public Criteria distinct(Criteria criteria) {
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.towne.framework.common.dao.IDao#getIdName()
	 */
	public String getIdName() {
		ClassMetadata meta = getSessionFactory().getClassMetadata(entityClass);
		return meta.getIdentifierPropertyName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.towne.framework.common.dao.IDao#isPropertyUnique(java.lang.String,
	 * java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean isPropertyUnique(final String propertyName,
			final Object newValue, final Object oldValue) {
		if (newValue == null || newValue.equals(oldValue)) {
			return true;
		}
		Object object = findUniqueBy(propertyName, newValue);
		return (object == null);
	}
}
