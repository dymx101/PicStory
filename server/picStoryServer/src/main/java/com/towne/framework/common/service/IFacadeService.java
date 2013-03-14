package com.towne.framework.common.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.towne.framework.springmvc.model.Contact;
import com.towne.framework.common.model.Trader;

public interface IFacadeService {
	
	Contact findById(Trader trade,int id);
	
	Contact save(Trader trade, Contact contact);
	
	Contact modifyContact(Trader trade, Contact contact);

	void delete(Trader trade, Contact contact);

	void delete(Trader trade, int id);

	Page<Contact> findAll(Trader trade, Pageable pageable);

	List<Contact> listAll(Trader trade);

	Page<Contact> findByAgeLessThanEqualOrderByIdDesc(Trader trade, int age,
			Pageable pageable);


}
