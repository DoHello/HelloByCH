package com.derbysoft.controller.sys;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.derbysoft.dao.sys.DistrictDao;
import com.derbysoft.entity.sys.SYS_District;

import dy.hrtworkframe.controller.base.BaseController;
import dy.hrtworkframe.entity.User;
import dy.hrtworkframe.util.Const;
import dy.hrtworkframe.util.MessageUtil;

@Transactional
@Controller("sysdistrict")
@RequestMapping("sysdistrict.do")
public class DistrictController extends BaseController {
	@Resource(name="systemDistrictDao")
	private DistrictDao systemDistrictDao;
	
	
	@RequestMapping(params = "p=findProvince")
	public @ResponseBody Map<String, Object> findProvince(HttpSession session, SYS_District dis) {
		Map<String ,Object> rest = new HashMap<String, Object>();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		try {
			rest.put("province", systemDistrictDao.findProvince());
		} catch (Exception e) {
			return MessageUtil.exception(user, e);
		}
		
		return MessageUtil.success(rest);
	}
	
	
	@RequestMapping(params = "p=findCity")
	public @ResponseBody Map<String, Object> findCity(HttpSession session, String provinceID) {
		Map<String ,Object> rest = new HashMap<String, Object>();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		try {
			rest.put("city", systemDistrictDao.findCity(provinceID));
		} catch (Exception e) {
			return MessageUtil.exception(user, e);
		}
		
		return MessageUtil.success(rest);
	}
	
	
	@RequestMapping(params = "p=findCounty")
	public @ResponseBody Map<String, Object> findCounty(HttpSession session, String cityID) {
		Map<String ,Object> rest = new HashMap<String, Object>();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		try {
			rest.put("county", systemDistrictDao.findCounty(cityID));
		} catch (Exception e) {
			return MessageUtil.exception(user, e);
		}
		
		return MessageUtil.success(rest);
	}
}
