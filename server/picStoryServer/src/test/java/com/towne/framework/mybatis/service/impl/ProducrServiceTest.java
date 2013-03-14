package com.towne.framework.mybatis.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.towne.framework.mybatis.model.Product;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@ContextConfiguration(value="classpath:spring-mybatis.xml")
@RunWith(value=SpringJUnit4ClassRunner.class)
public class ProducrServiceTest {
	
	@Resource(name="myBatisProductService")
	ProductService productService;
	
//	@Test
	public void save(){
		Product product=new Product();
		product.setName("SONY");
		product.setPrice(79.0f);
		product.setDescription("SONY");
		product.setCategoryid("1");
		productService.save(product);
	}
	
	
	@Test
	public void getProduct(){
		Product product=productService.getProduct("1");
		System.out.println(product.getName()+"\t"+product.getDescription());
	}
	
	@Test
	public void getProductListByCategory(){
		List<Product> lists=productService.getProductListByCategory("1");
		for (Product product : lists) {
			System.out.println(product.getName()+"\t"+product.getDescription()+"\t"+product.getCategoryid());
		}
	}
	
	@Test
	public void searchProductList(){
		List<Product> lists=productService.searchProductList("lenovo");
		for (Product product : lists) {
			System.out.println(product.getName()+"\t"+product.getDescription()+"\t"+product.getCategoryid());
		}
	}
	
	
	@Test
	public void listAll(){
		List<Product> lists=productService.listAll();
		for (Product product : lists) {
			System.out.println(product.getName()+"\t"+product.getDescription()+"\t"+product.getCategoryid());
		}
	}
}
