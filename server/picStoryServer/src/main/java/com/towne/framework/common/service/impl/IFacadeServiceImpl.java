package com.towne.framework.common.service.impl;

import java.util.List;

import javax.annotation.Resource;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.towne.framework.common.service.IFacadeService;
import com.towne.framework.spring.data.jpa.service.ContactService;
import com.towne.framework.springmvc.model.Contact;
import com.towne.framework.common.model.Trader;

@Service(value="ifacadeServiceImpl")
@Transactional
public class IFacadeServiceImpl implements IFacadeService  {

	@Resource(name="contactServiceImpl")
    ContactService contactService;
	
	@Override
	public Contact findById(Trader trade,int id) {
		// TODO Auto-generated method stub
		return contactService.findById(id);
	}

	@Override
	public Contact save(Trader trade,Contact contact) {
		// TODO Auto-generated method stub
		return contactService.save(contact);
	}

	@Override
	public Contact modifyContact(Trader trade,Contact contact) {
		// TODO Auto-generated method stub
		return contactService.modifyContact(contact);
	}

	@Override
	public void delete(Trader trade,Contact contact) {
		// TODO Auto-generated method stub
		contactService.delete(contact);
	}

	@Override
	public void delete(Trader trade,int id) {
		// TODO Auto-generated method stub
		contactService.delete(id);
	}

	@Override
	public Page<Contact> findAll(Trader trade,Pageable pageable) {
		// TODO Auto-generated method stub
		return contactService.findAll(pageable);
	}

	@Override
	public List<Contact> listAll(Trader trade) {
		// TODO Auto-generated method stub
		return contactService.listAll();
	}

	@Override
	public Page<Contact> findByAgeLessThanEqualOrderByIdDesc(Trader trade,int age,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return contactService.findByAgeLessThanEqualOrderByIdDesc(age, pageable);
	}

}
