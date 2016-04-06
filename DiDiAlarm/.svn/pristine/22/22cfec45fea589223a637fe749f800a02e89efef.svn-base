package com.derbysoft.controller.cms;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.derbysoft.dao.cms.NewsDao;
import com.derbysoft.entity.echarts.AreaCount;
import com.derbysoft.entity.echarts.EchartsDemand;
import com.derbysoft.service.cms.EchartsService;

import dy.hrtworkframe.controller.base.BaseController;
@Transactional
@Controller
@RequestMapping("echars.do")
public class EchartsCollection extends BaseController{
	@Resource(name = "newsDao")
	private NewsDao newsDao;
	
	@Autowired
	public EchartsService echarsService;

	@RequestMapping(params = "p=singLogin")
	@SuppressWarnings("all")
	public @ResponseBody Map<String, Object> signLogin(
			@ModelAttribute EchartsDemand echartsDemand, HttpSession session)
			throws Exception {
		List<Class<AreaCount>> areaCount = echarsService.getAreaCount();
				return pd;
		
	}
	
	
}
