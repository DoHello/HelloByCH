package com.derbysoft.servlet;

/*import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import dy.hrtworkframe.dao.BaseDaoImpl;

@Service
public class DyApplicationParameterServlet implements Servlet {
	   
	private BaseDaoImpl baseDao;
	
	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		try {
		ServletContext context = servletConfig.getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		baseDao = (BaseDaoImpl) ctx.getBean("baseDaoImpl");
		Properties prop = new Properties();   
		System.out.println(context.getRealPath("/")+"WEB-INF"+System.getProperty("file.separator")+"classes"+System.getProperty("file.separator")+"system.properties");
	    
				
	    	FileInputStream in = new FileInputStream(context.getRealPath("/")+"WEB-INF"+System.getProperty("file.separator")+"classes"+System.getProperty("file.separator")+"system.properties");
			prop.load(in);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("toMail", prop.getProperty("Mail.toMail").trim());
			map.put("Form", prop.getProperty("Mail.Form").trim());
			map.put("password", prop.getProperty("Mail.password").trim());
			map.put("stmpHost", prop.getProperty("Mail.stmpHost").trim());
			context.setAttribute("Mail", map);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//
//		List<SYS_Module> modules = baseDao.query(SYS_Module.class);
//		Map<String, Object> moduleMap = new HashMap<String, Object>();
//		for (SYS_Module module : modules) {
//			moduleMap.put(module.getModuleID(), module);
//		}
//		context.setAttribute("MODULE", moduleMap);
	}

	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		
	}






	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}






	@Override
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}






	@Override
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null;
	}
	
}*/import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import dy.hrtworkframe.dao.BaseDaoImpl;


public class DyApplicationParameterServlet implements Servlet {
	   
	private BaseDaoImpl baseDao;
	    
	@Override
	public void destroy() {

	}

	@Override
	public ServletConfig getServletConfig() {
		return null;
	}

	@Override
	public String getServletInfo() {
		return null;
	}

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		ServletContext context = servletConfig.getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		baseDao = (BaseDaoImpl) ctx.getBean("baseDaoImpl");
		Properties prop = new Properties();   
		System.out.println(context.getRealPath("/")+"WEB-INF"+System.getProperty("file.separator")+"classes"+System.getProperty("file.separator")+"system.properties");
	    try {
	    	FileInputStream in = new FileInputStream(context.getRealPath("/")+"WEB-INF"+System.getProperty("file.separator")+"classes"+System.getProperty("file.separator")+"system.properties");
			prop.load(in);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("toMail", prop.getProperty("Mail.toMail").trim());
			map.put("Form", prop.getProperty("Mail.Form").trim());
			map.put("password", prop.getProperty("Mail.password").trim());
			map.put("stmpHost", prop.getProperty("Mail.stmpHost").trim());
			context.setAttribute("Mail", map);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//
//		List<SYS_Module> modules = baseDao.query(SYS_Module.class);
//		Map<String, Object> moduleMap = new HashMap<String, Object>();
//		for (SYS_Module module : modules) {
//			moduleMap.put(module.getModuleID(), module);
//		}
//		context.setAttribute("MODULE", moduleMap);
	}

	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		
	}
	
}
