package com.towne.framework.common.service;

import java.util.List;

import com.towne.framework.common.model.Trader;

public interface IService<T> {
	
	T findById(Trader trader ,long id);
	
	void update(Trader trader,T t);
	
	void add(Trader trader,T t);
	
	void deleteById(Trader trader,long id);
	
	void delete(Trader trader,T t);
	
	List<T> query(Trader trader,String queryString);
	
}
