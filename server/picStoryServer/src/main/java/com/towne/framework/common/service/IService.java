package com.towne.framework.common.service;

import java.util.List;

public interface IService<T> {

	T findById(long id);

	void save(T t);

	void deleteById(long id);

	void delete(T t);

	List<T> query(String queryString, Object... values);
}
