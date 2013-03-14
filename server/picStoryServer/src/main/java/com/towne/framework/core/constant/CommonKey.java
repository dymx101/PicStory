package com.towne.framework.core.constant;

public class CommonKey {
	// pool名
	public static final String POOL_NAME = "mobile-central";
	// cookie存储的常量key
	public static final String USER_TOKEN_KEY = "USER_TOKEN_KEY:";
	// trader存储的常量key
	public static final String TRADER = "TRADER_";
	// oldtoken存储的常量key
	public static final String MOBILE_USER = "MOBILE_USER";
	// sessionUser存储的常量key
	public static final String MSESSIONID = "msessionid";
	// memcached当前版本号
	public static final String MEMCACHE_VERSION = "107976";
	// 客户端traderName常量化
	public static final String TRADER_NAME_IPAD = "ipadSystem";
	public static final String TRADER_NAME_ANDROID = "androidSystem";
	public static final String TRADER_NAME_IPHONE = "iosSystem";
	public static final String TRADER_NAME_WAP = "wapSystem";
	public static final String TRADER_NAME_WEBSITE = "websiteSystem";
	// session的失效时间.代码由此常量控制，web.xml需要手动修改.
	public static final int SESSION_INVALID_TIME = 1440;
	// userId存储的常量key
	public static final String USERID = "userId_";
	// 授权user存储的常量key
	public static final String MOBILECENTRAL_SSOUSER = "mobile-central_SSOUser_";
	
	
	public static final int MEMCACHE_INVALID_TIME_MINUTE_1 = 1;
	public static final int MEMCACHE_INVALID_TIME_MINUTE_2 = 2;
	public static final int MEMCACHE_INVALID_TIME_MINUTE_3 = 3;
	public static final int MEMCACHE_INVALID_TIME_MINUTE_5 = 5;
	public static final int MEMCACHE_INVALID_TIME_MINUTE_10 = 10;
	public static final int MEMCACHE_INVALID_TIME_MINUTE_15 = 15;
	public static final int MEMCACHE_INVALID_TIME_MINUTE_30 = 30;
	public static final int MEMCACHE_INVALID_TIME_MINUTE_60 = 60;
	
	public static final int MEMCACHE_INVALID_THREE_HOUR = 60*3;
	public static final int MEMCACHE_INVALID_SIX_HOUR = 60*6;
	
	public static final int MEMCACHE_INVALID_TIME_ONE_DAY = 60*24;
	public static final int MEMCACHE_INVALID_TIME_TWO_DAY = 60*24*2;
	public static final int MEMCACHE_INVALID_TIME_THREE_DAY = 60*24*3;
	
	public static final int MEMCACHE_INVALID_TIME_ONE_WEEK = 60*24*7;
	public static final int MEMCACHE_INVALID_TIME_TWO_WEEK = 60*24*7*2;
	
	public static final int MEMCACHE_INVALID_TIME__ONE_DAY_MILLIS= 60*24*60*1000;
	
	//memcache 时效常量
	public static final String MEMCACHE_RETURN_RESULT_FLAG = "return";
	
	public static final int MEMCACHE_INVALID_TIME_MINUTE_6 = 6;
	
	
	public static final int MEMCACHE_INVALID_TIME_ONE_HOUR = 60;
	
	public static final int MEMCACHE_INVALID_TIME_SIX_HOUR = 60 * 6;
	
	//登录验证KEY
	public static final String LOGIN_AUTHENTICATION_MAP_KEY = "loginAuthentication_mapkey_username_";
}
