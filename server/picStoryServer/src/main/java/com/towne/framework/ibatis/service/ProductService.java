package com.towne.framework.ibatis.service;

import java.util.List;

import com.towne.framework.ibatis.model.Product;

public interface ProductService {
	
	List<Product> listAll();
	
	boolean save(Product product);
	
	boolean update(Product product);
	
	Product getModel(int id);
	
	List<Product> selectLikeName(String name);
}
