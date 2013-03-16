package com.towne.framework.web.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@SuppressWarnings("serial")
public class InitPageProfileServlet extends HttpServlet {

	Logger logger = LoggerFactory.getLogger(getClass());

	@SuppressWarnings("static-access")
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String i18n = req.getParameter("request_locale");
		if(StringUtils.isBlank(i18n)){
			i18n ="zh_CN";	
		}
		req.getLocale().setDefault(new Locale(i18n));
		Map<String, String> pageMap = new HashMap<String, String>();
		String pageType = req.getParameter("pageType");
		String id = req.getParameter("id");
		pageMap.put("pageType", pageType);
		if (StringUtils.isNotBlank(id))
			pageMap.put("id", id);
		req.getSession().setAttribute("pageMap", pageMap);	
		resp.sendRedirect("users.htm");

	}

}
