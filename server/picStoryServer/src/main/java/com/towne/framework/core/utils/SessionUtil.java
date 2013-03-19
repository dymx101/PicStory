package com.towne.framework.core.utils;

import java.util.UUID;
import java.util.concurrent.TimeoutException;
import com.google.code.ssm.Cache;
import com.google.code.ssm.api.format.SerializationType;
import com.google.code.ssm.providers.CacheException;
import com.towne.framework.core.constant.CommonKey;
import com.towne.framework.system.filter.vo.MobileLogger;

public class SessionUtil {
	
	
	public static void setLogVO(Cache me,MobileLogger logVO) throws TimeoutException, CacheException
	{
		String userIP =getClientIP();
		System.out.println("USER_LOGVO_"+ userIP);
		me.set("USER_LOGVO_"+ userIP,CommonKey.MEMCACHE_INVALID_TIME_MINUTE_60*60,logVO,SerializationType.PROVIDER);
		String sessionid = (String)me.get("USER_SESSION_"+userIP,SerializationType.PROVIDER);
		if(sessionid == null)
			System.out.println("USER_SESSION_"+ userIP);
			me.set("USER_SESSION_"+userIP, CommonKey.MEMCACHE_INVALID_TIME_MINUTE_60*60,UUID.randomUUID().toString().replace("-",""),SerializationType.PROVIDER);
	}
	protected static String getClientIP()
	{
//		HttpServletRequest request = ServletActionContext.getRequest();
//		if (request.getHeader("x-forwarded-for") != null) {
//            return request.getHeader("x-forwarded-for");
//        } else {
//            return request.getRemoteAddr();
//        }
		return "127.0.0.1";

	}
	public static void clearObjects(Cache me) throws TimeoutException, CacheException
	{
		String userIP = getClientIP();
		me.delete("USER_LOGVO_"+ userIP);
		me.delete("USER_SESSION_"+userIP);
	}
	
	public static void setUserToken(Cache me,String token) throws TimeoutException, CacheException
	{
		String userIP = getClientIP();
		MobileLogger logVO = (MobileLogger)me.get("USER_LOGVO_"+ userIP, SerializationType.PROVIDER);
		if(logVO == null)
			return;
		logVO.setUserToken(token);
		me.set("USER_LOGVO_"+ userIP,CommonKey.MEMCACHE_INVALID_TIME_MINUTE_60*60,logVO,SerializationType.PROVIDER);
	}
	
	public static void setUserGUID(Cache me,String guid) throws TimeoutException, CacheException
	{
		String userIP = getClientIP();
		me.set("USER_GUID_"+ userIP,CommonKey.MEMCACHE_INVALID_TIME_MINUTE_60*60,guid,SerializationType.PROVIDER);
	}
}
