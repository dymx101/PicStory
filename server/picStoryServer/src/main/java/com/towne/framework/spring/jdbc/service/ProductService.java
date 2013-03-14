package com.towne.framework.spring.jdbc.service;

import java.util.List;

import com.towne.framework.common.service.IService;
import com.towne.framework.spring.jdbc.model.Product;

public interface ProductService extends IService<Product> {

	List<Product> getProductListByCategory(String string);

}
