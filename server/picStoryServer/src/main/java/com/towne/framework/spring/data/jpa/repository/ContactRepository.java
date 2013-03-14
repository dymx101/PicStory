package com.towne.framework.spring.data.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.towne.framework.springmvc.model.Contact;

/**
 * Spring Data Jpa Repository
 * @author towne
 *
 */
public interface ContactRepository extends PagingAndSortingRepository<Contact, Integer>{
	
	/**
	 * iSqurey com.towne.framework.model.Contact @NamedQuery(name="findById",query="select a from Contact a where a.id= ?1")
	 * @see com.towne.framework.model.Contact<br/>@NamedQuery(name="Contact.findById",query="select a from Contact a where a.id= ?1")
	 * @param id
	 * @return
	 */
	Contact findById(int id);
	
	/**
	 * age < 150 && id desc
	 * @param age
	 * @param pageable
	 * @return
	 */
	Page<Contact> findByAgeLessThanEqualOrderByIdDesc(int age,Pageable pageable);
	
}
