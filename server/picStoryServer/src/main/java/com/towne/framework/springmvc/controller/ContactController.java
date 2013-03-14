package com.towne.framework.springmvc.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.towne.framework.common.service.IFacadeService;
import com.towne.framework.springmvc.model.Contact;
import com.towne.framework.common.model.Trader;

@Controller(value="contactController")
@SessionAttributes
public class ContactController {
	
	@Autowired
	IFacadeService ifacadeService;
	
	public static final int PAGE_SIZE=5; 
	public static final int PAGE_NUMBER=0; 
	
	
	/**
	 * add method
	 * @param contact
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String addContact(@ModelAttribute(value="model") @Valid Contact contact,BindingResult result){
		
		if(result.hasErrors()){
			return "contact";
		}else{
			//System.out.println("First Name:" + contact.getFirstname() + "Last Name:" + contact.getLastname());
			//HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			Trader trader = new Trader();
			ifacadeService.save(trader,contact);
			return "redirect:/listAll.do";
		}
	}
	
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value="/npage",method=RequestMethod.GET)
	public ModelAndView newModelPage(){
		return new ModelAndView("contact","model",new Contact());//jump to contact.jsp as model
	}
	
	
	/**
	 * Query all return json string
	 * @param contact
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/ajson",method=RequestMethod.GET)
	public @ResponseBody List<Contact> alltoJson(@ModelAttribute(value="contact") Contact contact,BindingResult result){
		Trader trader = new Trader();
		List<Contact> contacts = ifacadeService.listAll(trader);
		return contacts;
	}
	
	
	
	/**
	 * Modify Jump
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/modify/{id}")
	public ModelAndView modifyPage(@PathVariable(value="id")Integer id){
		Trader trader = new Trader();
		Contact contact = ifacadeService.findById(trader,id);
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("modifycontact");//jump to modifycontact.jsp
		modelAndView.addObject("model", contact);//model's name model
		return modelAndView;
	}
	
	
	
	/**
	 * Modify Jump
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/listAll/{pageNumber}/{pageSize}")
	public ModelAndView listAll(@PathVariable(value="pageNumber") int pageNumber,@PathVariable(value="pageSize") int pageSize){
		
		if(pageNumber<0){
			pageNumber=PAGE_NUMBER;
		}
		if(pageSize<0){
			pageSize=PAGE_SIZE;
		}
		Trader trader = new Trader();
		PageRequest pageRequest=new PageRequest(pageNumber, pageSize);
		
		Page<Contact> pagingdata = ifacadeService.findAll(trader,pageRequest);
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("list");//jump to list.jsp
		modelAndView.addObject("alllist", pagingdata.getContent());
		modelAndView.addObject("number", pagingdata.getNumber());
		modelAndView.addObject("size", pagingdata.getSize());
		modelAndView.addObject("totalElements", pagingdata.getTotalElements());
		modelAndView.addObject("totalPages", pagingdata.getTotalPages());
		return modelAndView;
	}
	
	
	
	/**
	 * del
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/del/{id}")
	public String del(@PathVariable(value="id")int id){
		Trader trader = new Trader();
		ifacadeService.delete(trader,id);
		return "redirect:/listAll.do";
	}
	
	
	
	/**
	 * query age < ? && order by id desc
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/listAll/{age}/{pageNumber}/{pageSize}")
	public ModelAndView findByAgeLessThanOrderByIdDesc(@PathVariable(value="pageNumber") int pageNumber,@PathVariable(value="pageSize") int pageSize,@PathVariable(value="age") int age){
		
		if(pageNumber<0){
			pageNumber=PAGE_NUMBER;
		}
		if(pageSize<0){
			pageSize=PAGE_SIZE;
		}
		
		PageRequest pageRequest=new PageRequest(pageNumber, pageSize);
		Trader trader = new Trader();
		Page<Contact> pagingdata = ifacadeService.findByAgeLessThanEqualOrderByIdDesc(trader,age, pageRequest);
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("list");//jump to list.jsp
		modelAndView.addObject("alllist", pagingdata.getContent());
		modelAndView.addObject("number", pagingdata.getNumber()+1);
		modelAndView.addObject("size", pagingdata.getSize());
		modelAndView.addObject("totalElements", pagingdata.getTotalElements());
		modelAndView.addObject("totalPages", pagingdata.getTotalPages());
		return modelAndView;
	}
	
	
}
