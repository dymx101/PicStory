package com.towne.framework.core.utils;

import java.util.Map;
import java.util.concurrent.TimeoutException;
import com.google.code.ssm.Cache;
import com.google.code.ssm.api.format.SerializationType;
import com.google.code.ssm.providers.CacheException;
import com.towne.framework.common.model.Trader;
import com.towne.framework.core.constant.CommonKey;
import com.towne.framework.core.ex.SystemMobileSessionException;
import com.towne.framework.system.filter.vo.CookieVO;

public class MobileCommonUtil {
	
	
	public static Trader getTrader(Cache me,String token) throws TimeoutException, CacheException{
	    	Trader result = null;
	    	Object obj = me.get(CommonKey.TRADER + token, SerializationType.PROVIDER);
	    	if (obj != null) {
	    		result = (Trader) obj;
	    	} else {
	    		// 再取一次trader，防止memcached命中失败的情况
	    		obj = me.get(CommonKey.TRADER + token, SerializationType.PROVIDER);
	    		if (obj != null) {
	    			result = (Trader) obj;
	    		} else {
	    			removeSessionInfo(me, token);
	        		throw new SystemMobileSessionException("用户Token过期,请重新登录");
	    		}
	    	}        
	    	return result;
	    }
	    
	    public static void removeSessionInfo(Cache me, String oldToken) throws TimeoutException, CacheException {
			if (oldToken != null) {
				Object obj = me.get(CommonKey.USER_TOKEN_KEY + oldToken, SerializationType.PROVIDER);
				if (obj != null) {
					@SuppressWarnings("unchecked")
					Map<String, CookieVO> oneUsercookieMap = (Map<String, CookieVO>) obj;
					CookieVO cookieVO = oneUsercookieMap.get(CommonKey.MSESSIONID);
					if (cookieVO != null) {
//						String msessionidValue = cookieVO.getValue();
//						me.remove(ActionConstants.USER_SESSION_KEY + "_" + msessionidValue + "_107976");
					}
				}
				me.delete(CommonKey.TRADER + oldToken);
	    		me.delete(CommonKey.USER_TOKEN_KEY + oldToken);
	    		me.delete(CommonKey.USERID + oldToken);
	    		me.delete(CommonKey.MOBILECENTRAL_SSOUSER + oldToken);    				
			}
	    }
}
