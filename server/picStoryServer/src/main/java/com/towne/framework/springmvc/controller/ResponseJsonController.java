package com.towne.framework.springmvc.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.ssm.Cache;
import com.google.code.ssm.api.format.SerializationType;
import com.google.code.ssm.providers.CacheException;
import com.towne.framework.common.service.IFacadeService;
import com.towne.framework.system.util.GsonUtil;
import com.towne.framework.springmvc.model.Contact;
import com.towne.framework.springmvc.model.Contacts;
import com.towne.framework.common.model.Trader;

/**
 * return json format data
 * @author towne
 *
 */
@Controller
@RequestMapping(value="/json",method={RequestMethod.GET})
public class ResponseJsonController {
	
	@Autowired
	IFacadeService ifacadeService;
	
	@Autowired
	private Cache cache;
	
	@RequestMapping(value="/contact/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Contact getContactInJSON(@PathVariable(value="id")int id) throws TimeoutException, CacheException{
		Trader trader = new Trader();
		trader.setTraderName("towne");
		trader.setTraderPassword("123");
		Contact contact = ifacadeService.findById(trader,id);
		contact.setTrader(trader);
		System.out.println(">>>>>> "+cache.get("USER_LOGVO_127.0.0.1",SerializationType.PROVIDER));
		System.out.println(">>>>>> "+cache.get("USER_SESSION_127.0.0.1",SerializationType.PROVIDER));
		return contact;
	}
	
	
	
	@RequestMapping(value="/contacts",produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Contacts getContactsInJSON(){
		Trader trader = new Trader();
		List<Contact> contact = ifacadeService.listAll(trader);
		Contacts contacts=new Contacts();
		contacts.setContacts(contact);
		return contacts;
	}
	
	@RequestMapping(value="/list/{jparam}",produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Contact tetete(@PathVariable(value="jparam") String jp)
	{
		System.out.println(jp); 
		Contact ctt = (Contact) GsonUtil.jsonToModel(jp,Contact.class);
//		List<Contact> ss =  (List<Contact>) GsonUtil.getJsonValue(jp, "contacts");
		Map<?, ?> map = GsonUtil.jsonToMap(jp);
		System.out.println(map.get("trader"));
		Trader trader = (Trader) GsonUtil.getJsonValue(jp, "trader", Trader.class);
	     ; 
		System.out.println(trader);
//		return trader;
		return ctt;
	}
	
}
