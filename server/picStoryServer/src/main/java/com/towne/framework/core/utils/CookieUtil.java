package com.towne.framework.core.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class CookieUtil {

	public static void addCookie( HttpServletResponse reponse,String cookieName,String value,String domain, String cookieMaxAge,String path) {
	    Cookie c =new Cookie(cookieName,value);
	    int max = 7;
	    if(StringUtils.isNotBlank(cookieMaxAge)){
	    	max = Integer.parseInt(cookieMaxAge);
	    }
		c.setMaxAge(max);
		c.setPath(path);
//	    c.setDomain(domain);
		reponse.addCookie(c);
	
	}
	
	public static void addCookie( HttpServletResponse reponse,String cookieName,String value) {
	    Cookie c =new Cookie(cookieName,value);
		c.setMaxAge(7);
		c.setPath("/");
//	    c.setDomain(domain);
		reponse.addCookie(c);
	
	}
	
	public static String  getCookieValue(HttpServletRequest request,String cookieName){
		Cookie[] cookies = request.getCookies();
		
		if(cookies==null) return null;
		
		String value = null;
		
		for(int i=0;i<cookies.length;i++){
			String key = cookies[i].getName();
			if(!key.equals(cookieName))
				continue;
			else{
				value = cookies[i].getValue();
				break;
			}
		}
	
		
		return value;
		
	}
	public static void deleteCokkies(HttpServletResponse reponse,String cookieName,String domain,String path){
		Cookie c = new Cookie(cookieName, null);
		c.setMaxAge(0);
		c.setPath(path);
	    c.setDomain(domain);
		reponse.addCookie(c);

	}
}
