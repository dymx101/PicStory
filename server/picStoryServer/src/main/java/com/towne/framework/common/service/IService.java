package com.towne.framework.common.service;

import java.util.List;

public interface IService<T> {

	T findById(long id);

	void update(T t);

	void add(T t);

	void deleteById(long id);

	void delete(T t);

	List<T> query(String queryString);

}
