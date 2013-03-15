package com.towne.framework.common.dao;

import java.util.List;

public interface IDao<T> {

	public void add(Object object);

	public void delete(long id);

	public void update(Object object);

	public List<T> query(String queryString);

	public T findById(long id);

}
