package com.towne.framework.spring.data.jpa.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.towne.framework.springmvc.model.Dept;

/**
 * 
 * @author fengjing
 *
 */
public interface DeptRepository extends PagingAndSortingRepository<Dept, Integer> {

}
