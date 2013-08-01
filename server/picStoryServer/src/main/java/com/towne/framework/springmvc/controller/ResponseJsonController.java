package com.towne.framework.springmvc.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeoutException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import com.google.code.ssm.Cache;
import com.google.code.ssm.api.format.SerializationType;
import com.google.code.ssm.providers.CacheException;
import com.towne.framework.common.service.IFacadeService;
import com.towne.framework.springmvc.model.MomentVO;
import com.towne.framework.springmvc.model.Moments;
import com.towne.framework.springmvc.model.PageVO;
import com.towne.framework.system.filter.vo.MobileLogger;
import com.towne.framework.common.model.Trader;
import com.towne.framework.core.ex.SystemMobileRuntimeException;
import com.towne.framework.core.hessian.BasicAPI;
import com.towne.framework.core.utils.GsonUtil;
import com.towne.framework.hibernate.bo.Moment;
import com.towne.framework.hibernate.bo.Page;

/**
 * return json format data
 * @author towne
 *
 */
@Controller
@RequestMapping(value="/json",method={RequestMethod.GET})
public class ResponseJSONController {
	
	@Autowired
	IFacadeService ifacadeService;
	
	@Autowired
	private Cache me;
	
	@Autowired
	private BasicAPI basic;
	
	static String userIP = "127.0.0.1";
	
	
	@RequestMapping(value="/moment/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<PageVO> getContactInJSON(@PathVariable(value="id")long id) throws TimeoutException, CacheException{
		Trader trader = new Trader();
		trader.setTraderName("towne");
		trader.setTraderPassword("123");
		List<PageVO> pvs = ifacadeService.findPagesByMomentId(trader, id);
		System.out.println("sayHello() : " + basic.sayHello());
		System.out.println(">>>>>> "+me.get("USER_LOGVO_"+userIP,SerializationType.PROVIDER));
		System.out.println(">>>>>> "+me.get("USER_SESSION_"+userIP,SerializationType.PROVIDER));
		return pvs;
	}
	
	@RequestMapping(value="/momentpage/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody PageVO getMomentpageInJSON(@PathVariable(value="id")long id) throws TimeoutException, CacheException{
		Trader trader = new Trader();
		trader.setTraderName("towne");
		trader.setTraderPassword("123");
		List<PageVO> pvs = ifacadeService.findPagesByMomentId(trader, id);
		System.out.println(">>>>>> "+me.get("USER_LOGVO_"+userIP,SerializationType.PROVIDER));
		System.out.println(">>>>>> "+me.get("USER_SESSION_"+userIP,SerializationType.PROVIDER));
		return pvs.get(1);
	}
	
	@RequestMapping(value="/moments",produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Moments getContactsInJSON(){
		Trader trader = new Trader();
		List<MomentVO> mo = ifacadeService.query(trader, "select a from Moment a , Page b where a.idMOMENT=b.moment.idMOMENT");
		Moments moments=new Moments();
		moments.setTname("towne");
		moments.setMoments(mo);
		return moments;
	}
	
	//@PathVariable 的json 参数以/结尾否则 json串中的.号处理有问题
    //但是当 key 值有「.」时就会出错，比如说「/release/a.b.c」，到了 @PathVariable 就只剩下「a.b」，「.c」不见了，原因出现 Spring MVC 预设会切掉最后一个点以后的字符串，应该是在处理「*.do」这样的 Url pattern 的关系。
    //解决方式：{jparam:.*}
	@RequestMapping(value="/list/{jparam:.*}",produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody PageVO tetete(@PathVariable(value="jparam") String jp)
	{
		System.out.println(jp); 
		PageVO ctt = (PageVO) GsonUtil.jsonToModel(jp,PageVO.class);
//		List<Contact> ss =  (List<Contact>) GsonUtil.getJsonValue(jp, "contacts");
		Map<?, ?> map = GsonUtil.jsonToMap(jp);
		System.out.println(map.get("trader"));
		Trader trader = (Trader) GsonUtil.getJsonValue(jp, "trader", Trader.class);
	     ; 
		System.out.println(trader);
//		return trader;
		return ctt;
	}
	
	@RequestMapping(value ="/addtest/{jparam:.*}",produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Moment addtest1(@PathVariable(value="jparam") String jp)
	{
		MobileLogger logVO = null;
		String tokenString = null;
		try {
			logVO = (MobileLogger)me.get("USER_LOGVO_"+ userIP, SerializationType.PROVIDER);
			System.out.println(logVO.toString());
			tokenString = (String)me.get("USER_SESSION_"+userIP, SerializationType.PROVIDER);
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Map<?, ?> map = GsonUtil.jsonToMap(jp);
		
		Moment moment = new Moment();
		moment.setpMonIndex(2);
		moment.setpMonDesc("this is a new moment in 20130712");

		Set<Page> set = new HashSet<Page>();
		Page page = new Page();
		page.setMediaUrl((String) map.get("mediaUrl"));
		page.setContent((String) map.get("content"));
		page.setMediaType(((Double) map.get("mediaType")).intValue());
		page.setMoment(moment);
		set.add(page);
		moment.setPages(set);
		ifacadeService.save(tokenString,moment);
		return moment;
	}
	
    /** 
     * 运行时异常页面控制 
     *  
     * @param runtimeException 
     * @return 
     * @throws UnsupportedEncodingException 
     */  
    @ExceptionHandler(RuntimeException.class)  
    public @ResponseBody  
    Map<String,Object> runtimeExceptionHandler(RuntimeException runtimeException) throws UnsupportedEncodingException { 
    	String RESTR = "";
    	if (runtimeException instanceof SystemMobileRuntimeException) {
    		RESTR = runtimeException.getMessage();
		}

        Map<String, Object> model = new TreeMap<String, Object>();  
        model.put("status", RESTR);  
        return model;  
    }   
}
