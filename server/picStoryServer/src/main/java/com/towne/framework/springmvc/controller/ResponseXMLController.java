package com.towne.framework.springmvc.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.towne.framework.common.service.IFacadeService;
import com.towne.framework.springmvc.model.Contact;
import com.towne.framework.springmvc.model.Contacts;
import com.towne.framework.common.model.Trader;

/**
 * return xml format data
 * @author towne
 *
 */
@Controller
@RequestMapping(value="/xml",method={RequestMethod.GET})
public class ResponseXMLController {
	
	@Autowired
	IFacadeService ifacadeService;
	
	/**
	 * query a xml date by id
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/contact/{id}",produces=MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody Contact getContactInXML(@PathVariable(value="id")int id){
		Trader trader = new Trader();
		Contact contact = ifacadeService.findById(trader,id);
		return contact;
	}
	
	
	/**
	 * query multiple xml data
	 * @return
	 */
	@RequestMapping(value="/contacts",produces=MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody Contacts getContactsInXML(){
		Trader trader = new Trader();
		List<Contact> contact = ifacadeService.listAll(trader);
		Contacts contacts=new Contacts();
		contacts.setContacts(contact);
		return contacts;
	}
}
