package com.towne.framework.core.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Constance {

	public static final String INPUT = "input";

	public static final String INDEX = "index";

	public static final String SUCCESS = "success";

	public static final String ERROR = "error";

	public static final String FORWARD = "forward";

	public static final String LIST = "list";

	public static final String DELETE = "delete";

	public static final String UPDATE = "update";

	public static final String TARGET_PARENT = "_parent";

	public static final String TARGET_SELF = "_self";

	public static final String TARGET_TOP = "_top";

	public static final String TARGET_BLANK = "_blank";

	public static final String HOME = "home";

	public static final String REGISTER = "register";

	public static final String SHOW_ADD_CONNECTIONS = "showAddConnecions";

	public static SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");

	public static SimpleDateFormat yearFormat = new SimpleDateFormat(
			"yyyy-MM-dd");

	// INVITATION
	public static final Integer TEAM_INVITATION = 0;
	public static final Integer COLLABORATOR_INVITATION = 1;
	public static final Integer ACCEPT_INVITATION = 1;
	public static final Integer DECLINE_INVITATION = 2;
	public static final Integer PENDING_INVITATION = 0;

	// PERSONAL INFORMATION
	public static final String NAME = "Name";
	public static final String GENDER = "Gender";
	public static final String BIRTHDATE = "BirthDate";
	public static final String PLACE_OF_BIRTH = "Place_of_Birth";
	public static final String TITLE = "Title";
	public static final String CITIZENSHIP = "Citizenship";
	public static final String ADDRESS = "Address";
	public static final String PHONE_NUM = "PhoneNum";
	public static final String MOBILE_PHONE_NUM = "MobilePhoneNum";
	public static final String FAX_NUM = "FaxNum";
	public static final String EMAIL = "EMail";
	public static final String WEB_SITE = "WebSite";

	// RESEARCH INFORMATION
	public static final String EDUCATION_BACKGROUND = "Education_Background";
	public static final String CURRENT_POSITION = "Current_Position";
	public static final String RESEARCH_INTERESTS = "Research_Interests";
	public static final String RESEARCH_WORKS = "Research_Works";
	public static final String ACADEMIC_ACTIVITIES = "Academic_Activities";
	public static final String PROJECT = "Project";
	public static final String PUBLICATIONS = "Publications";
	public static final String PATENTS = "Patents";
	public static final String AWARDS = "Awards";
	public static final String INTERNSHIP = "Internship";
	public static final String LANGUAGE_SKILLS = "Language_Skills";
	public static final String WORK_EXPERIENCE = "Work_Experience";
	public static final String RESEARCH_ACHIEVEMENTS = "Research_Achievements";
	public static final String TO_BE_PUBLISHED = "To_Be_Published";

	public static final String HYPERLINK = "HYPERLINK";

	// Punctuation
	public static final String COMMA_EN = ",";
	public static final String COMMA_CH = "\uFF0C";
	public static final String PERIOD_EN = ".";
	public static final String PERIOD_CH = "\u3002";
	public static final String SEMICOLON_EN = ";";
	public static final String SEMICOLON_CH = "\uFF1B";
	public static final String MULTIPLICATION = "*";
	public static final String RIGHT_PARENTHESE_EN = ")";
	public static final String LEFT_PARENTHESE_EN = "(";
	public static final String RIGHT_PARENTHESE_CH = "\uFF09";
	public static final String LEFT_PARENTHESE_CH = "\uFF08";

	public static final String SCI = "SCI";
	public static final String EI = "EI";
	public static final String IF = "IF";

	public static final int NAME_LENGTH_CH = 6;
	public static final int NAME_LENGTH_EN = 40;

	public static String isLinkBCommonInterface = "0";
	public static String under = "0";
	public static String underPage = "under.html";
	public static String defaultLang = "en_US";
	public static Map langMap = new HashMap();
	//tab记忆
	public static String publication = "publication";
	public static String connects = "connects";
	public static String profile = "profile";
	public static String calendar = "calendar";
	public static String welcome = "welcome";
	public static String home = "home";
	public static String researchList = "researchList";
	public static String systemalert = "systemalert";
	public static String newresearch = "research";
	public static String extresearch = "ex_research_";
	public static String cookiesPath = "/mra";
	public static String cookiesexpires = "100";
	public static String cookiesName="selectTab";

	public static final String IE_PLUGIN_REQUEST_LOGIN = "101";
	public static final String IE_PLUGIN__SUCESS = "202";
	public static final String IE_PLUGIN__UNSUCESS = "203";
	public static final String IE_PLUGIN__LOGIN_UNSUCESS = "207";
	public static final String IE_PLUGIN_REQUEST_ARTICLE_INFO = "104";
	public static final String IE_PLUGIN_ACCOUNT_PASSWORD_UPDATE = "205";
	public static final String IE_PLUGIN_ARTICLE_EXIST = "206";
	public static final String IE_PLUGIN_CONFIRM_UPDATE = "107";
	public static final String IE_PLUGIN_ADD_RESEARCH = "108";
	
	public static String MRAPATH = "http://219.234.220.19/mra/";

	static {
		Properties p = null;
		Properties lp = null;
		try {
			p = PropertiesUtils.loadProperties("application.properties");
			lp = PropertiesUtils.loadProperties("Lang.properties");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (null != p) {
			isLinkBCommonInterface = (String) p.get("isLinkBCommonInterface");
			under = (String) p.get("under");
			underPage = (String) p.get("underPage");
			MRAPATH = (String) p.get("mrapath");
			publication = (String) p.get("publication");
			connects = (String) p.get("connects");
			profile = (String) p.get("profile");
			calendar = (String) p.get("calendar");
			welcome = (String) p.get("welcome");
			home = (String) p.get("home");
			researchList = (String) p.get("researchList");
			systemalert = (String) p.get("systemalert");
			newresearch = (String) p.get("newresearch");
			extresearch = (String) p.get("extresearch");
			cookiesPath = (String) p.get("cookiesPath");
			cookiesexpires = (String) p.get("cookiesexpires");
			cookiesName = (String) p.get("cookiesName");
		}
		if (null != lp) {
			Set langSet = lp.keySet();
			if (null != langSet) {
				Iterator iter = langSet.iterator();
				while (iter.hasNext()) {
					String langName = (String) iter.next();
					String proFileName = (String) lp.get(langName);
					try {
						langMap.put(langName, PropertiesUtils
								.loadProperties(proFileName));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static Date operDate(int operType, int dayNum) {
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_YEAR);
		if (1 == operType) {
			calendar.set(Calendar.DAY_OF_YEAR, day + dayNum);
		} else {
			calendar.set(Calendar.DAY_OF_YEAR, day - dayNum);
		}
		return calendar.getTime();
	}

	public static Map getSortMap(String sort) {
		Map sortMap = new HashMap();
		if (StringUtils.isNotBlank(sort)) {
			if ("Date".equals(sort)) {
				sortMap.put("Date", "D");
			} else if ("Relevance".equals(sort)) {
				sortMap.put("Relevance", "D");
			} else if ("TimesCited".equals(sort)) {
				sortMap.put("TimesCited", "D");
			} else if ("Title".equals(sort)) {
				sortMap.put("Title", "A");
			}
		} else {
			sortMap = null;
		}
		return sortMap;
	}

	public static void main(String[] args) {
		Constance.langMap.get("111");
	}
}
