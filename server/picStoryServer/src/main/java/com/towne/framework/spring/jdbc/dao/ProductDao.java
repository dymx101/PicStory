package com.towne.framework.spring.jdbc.dao;

import java.util.List;

import com.towne.framework.common.dao.IDao;
import com.towne.framework.spring.jdbc.model.Product;

public interface ProductDao extends IDao<Product> {

	List<Product> getProductListByCategory(String name);

}
