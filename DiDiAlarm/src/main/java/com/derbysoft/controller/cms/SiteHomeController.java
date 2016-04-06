package com.derbysoft.controller.cms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.derbysoft.dao.cms.NewsDao;
import com.derbysoft.dao.sys.UserDao;
import com.derbysoft.entity.cms.Member;
import com.derbysoft.entity.cms.Message;
import com.derbysoft.entity.sys.SYS_User;
import com.derbysoft.util.EmailUtil;
import com.derbysoft.util.SmsRegsiterUtil;

import dy.hrtworkframe.controller.base.BaseController;
import dy.hrtworkframe.entity.User;
import dy.hrtworkframe.util.Const;
import dy.hrtworkframe.util.DateUtil;
import dy.hrtworkframe.util.MD5;
import dy.hrtworkframe.util.MessageUtil;
import dy.hrtworkframe.util.Sha1Util;
import dy.hrtworkframe.util.UUIDUtil;

@Transactional
@Controller("siteHome")
@RequestMapping("siteHome.do")
public class SiteHomeController extends BaseController {

	@Resource(name = "newsDao")
	private NewsDao newsDao;

	@Autowired
	public UserDao userDao;

	@Autowired
	private SmsRegsiterUtil smsRegsiterUtil;

	@SuppressWarnings("all")
	@RequestMapping(params = "p=view")
	public ModelAndView resoures(HttpSession session) throws Exception {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd = getPageData();
		List<Map<String, Object>> newsList = newsDao
				.query("select abstractz, title,DATE_FORMAT(createTime,'%b %D') as CreateTime from cms_article limit 0,4");
		pd.put("newsList", newsList);
		try {
			mv.addObject("model", pd);
			mv.setViewName("/webSite/Resources2");
		} catch (Exception e) {
			return MessageUtil.exception(user, mv, e);
		}
		return MessageUtil.success(mv);
	}

	@SuppressWarnings("all")
	@RequestMapping(params = "p=index")
	public ModelAndView index(HttpSession session) throws Exception {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd = getPageData();
		List<Map<String, Object>> newsList = newsDao
				.query("select abstractz,articleID, (@rowNum:=@rowNum+1) as IndexID ,title,DATE_FORMAT(createTime,'%b %D') as CreateTime from cms_article,(Select (@rowNum :=0) ) b limit 0,4");
		pd.put("newsList", newsList);
		try {
			mv.addObject("model", pd);
			mv.setViewName("/webSite/index");
		} catch (Exception e) {
			return MessageUtil.exception(user, mv, e);
		}
		return MessageUtil.success(mv);
	}

	@SuppressWarnings("all")
	@RequestMapping(params = "p=newsDetail")
	public ModelAndView detail(String indexID, HttpSession session) {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		try {
			pd = getPageData();
			List<Map<String, Object>> newsList = newsDao
					.query("select abstractz,articleID, (@rowNum:=@rowNum+1) as indexID ,title,Context,DATE_FORMAT(createTime,'%M %e %Y') as CreateTime from cms_article,(Select (@rowNum :=0) ) b");
			Integer index = (int) Math.floor(Double.valueOf(indexID));
			if (index - 1 != 0) {
				pd.put("prevEntity", newsList.get(index - 2));
			}
			pd.put("entity", newsList.get(index - 1));
			if (index != newsList.size()) {
				pd.put("nextEntity", newsList.get(index));
			}
			mv.addObject("model", pd);
			mv.setViewName("/webSite/news_detailed");
		} catch (Exception e) {
			return MessageUtil.exception(user, mv, e);
		}

		return MessageUtil.success(mv);
	}

	@SuppressWarnings("all")
	@RequestMapping(params = "p=putMessage")
	public @ResponseBody Map<String, Object> add(
			@ModelAttribute Message entity, HttpSession session) {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		Map<String, Object> map = (Map<String, Object>) (getRequest()
				.getServletContext().getAttribute("Mail"));
		try {
			entity.setCreateTime(DateUtil.getDateTimeString());
			entity.setMessageID(UUIDUtil.get32UUID());
			newsDao.insert(entity);
		} catch (Exception e) {
			return MessageUtil.exception(user, e);
		}
		// "metasearchmanager@derbysoft.com"
		// , "DerbySoft Web<metasearchsendmail@derbysoft.com>"
		EmailUtil emailUtil = new EmailUtil((String) map.get("toMail"),
				(String) map.get("Form"), (String) map.get("password"),
				(String) map.get("stmpHost"));

		String message = "";
		message += "New message<br>";
//		message += "First Name:" + entity.getFirstName() + "<br>";
//		message += "Last Name:" + entity.getLastName() + "<br>";
//		message += "Email:" + entity.getEmail() + "<br>";
//		message += "Phone:" + entity.getPhone() + "<br>";
//		message += "Company:" + entity.getCompany() + "<br>";
//		message += "Job Title:" + entity.getJobTitle() + "<br>";
//		message += "Country:" + entity.getCountry() + "<br>";
//		message += "City:" + entity.getCity() + "<br>";
//		message += entity.getContext() + "<br>";
		emailUtil.startSend("this is DerbySoft Web Site Message", message);
		return MessageUtil.success();
	}

	@RequestMapping(params = "p=loginview")
	public ModelAndView loginview(HttpSession session) throws Exception {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd = getPageData();
		try {
			mv.addObject("model", pd);
			mv.setViewName("/webSite/login");
		} catch (Exception e) {
			return MessageUtil.exception(user, mv, e);
		}
		return MessageUtil.success(mv);
	}

	/**
	 * 完善个人信息
	 * 
	 * @Title: signLogin
	 * @Description: TODO
	 * @param entity
	 * @param session
	 * @return
	 * @throws Exception
	 * @return: Map<String,Object>
	 */
	@RequestMapping(params = "p=signLogin")
	public @ResponseBody Map<String, Object> signLogin(
			@ModelAttribute SYS_User user, HttpSession session)
			throws Exception {
		// 这里防止用户跳过手机验证直接注册,完善个人信息或者重复注册
		if (user.getPhone() != null
				&& userDao.queryUserByPhone(user.getPhone()) != null) {
			return MessageUtil.error();
		}
		pd = getPageData();
		user.setInputDate(DateUtil.getDateTimeString());
		user.setUserID(UUIDUtil.get32UUID());
		// 密码加密
		user.setPassword(Sha1Util.getSha1(MD5.md5(user.getPassword())));
		newsDao.insert(user);
		return MessageUtil.success();
	}

	/**
	 * 手机验证
	 * 
	 * @Title: regsiterByPhone
	 * @Description: TODO
	 * @param entity
	 * @param session
	 * @return
	 * @return: Map<String,Object>
	 */
	@RequestMapping(params = "p=regsiterByPhone")
	public @ResponseBody Map<String, Object> regsiterByPhone(
			@RequestParam("mobile") String mobile, HttpSession session) {
		// 验证手机唯一性,并且限制注册人身份(这里为群众)
		String sql = "select * from SYS_User where 1=1 and Phone ='" + mobile
				+ "'";// +" and RoleID = '' ";

		if (newsDao.query(SYS_User.class, sql).size() >= 1) {
			return MessageUtil.error();
		}
		// 这里是获取手机验证码
		smsRegsiterUtil.sendResgsiterCode(mobile);
		return MessageUtil.success();
	}

	// 验证验证码
	@RequestMapping(params = "p=regsiter")
	public @ResponseBody Map<String, Object> regsiter(
			@RequestParam("mobile") String mobile,
			@RequestParam("code") String code, HttpSession session) {
		// TODO这里是手机验证码已经填写,验证手机验证码的正确
		try {
			if (smsRegsiterUtil.queryCodeByToken(mobile) != null) {
				if (smsRegsiterUtil.queryCodeByToken(mobile).getRegCode() == code) {
					return MessageUtil.success();
				} else {
					return MessageUtil.error();
				}
			} else {
				return MessageUtil.error();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return MessageUtil.error();
		}
	}

	// 警员端忘记密码
//	@RequestMapping(params = "p=forgotPasswordToPhone")
//	public @ResponseBody Map<String, Object> forgotPasswordToPhone(
//			@RequestParam("mobile") String mobile, HttpSession session) {
//		// 验证手机唯一性,并且限制注册人身份
//		if (!mobile.matches("^(13|14|15|17|18)\\d{9}$")) {
//			return MessageUtil.error();
//		}
//		String sql = "select * from SYS_User where 1=1 and Phone ='" + mobile
//				+ "'";// +" and RoleID = '' ";
//		List<SYS_User> l_user = newsDao.query(SYS_User.class, sql);
//		if (l_user.size() <= 0) {
//			return MessageUtil.error();
//		}
//		// 这里是获取密码
//		smsRegsiterUtil
//				.sendPasswordByPhone(l_user.get(0).getPassword(), mobile);
//		return MessageUtil.success();
//	}

	// 群众端忘记密码
	@RequestMapping(params = "p=forgotPasswordByCode")
	public @ResponseBody Map<String, Object> forgotPasswordByCode(
			@RequestParam("mobile") String mobile, HttpSession session) {
		// 验证手机唯一性,并且限制注册人身份(这里为群众),手机号规范性真假验证
		// 合法原则 1.以“13,14,15,17,18”开头 2.11位 3.纯数字
		if (!mobile.matches("^(13|14|15|17|18)\\d{9}$")) {
			return MessageUtil.error();
		}
		String sql = "select * from SYS_User where 1=1 and Phone ='" + mobile
				+ "'";// +" and RoleID = '' ";

		if (newsDao.query(SYS_User.class, sql).size() <= 0) {
			return MessageUtil.error();
		}
		// 这里是获取手机验证码
		boolean boo = smsRegsiterUtil.sendResgsiterCode(mobile);
		if (boo) {
			return MessageUtil.success();
		} else {
			return MessageUtil.error();
		}
	}


	@RequestMapping(params = "p=forgotPassword")
	public @ResponseBody Map<String, Object> forgotPassword(
			@ModelAttribute Member entity, HttpSession session) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("email", entity.getEmail());
		List<Member> list = newsDao.query(Member.class, parms);
		if (list.size() > 0) {
			return MessageUtil.success();
		} else {

			return MessageUtil.error();
		}

	}

	@RequestMapping(params = "p=reg")
	public @ResponseBody Map<String, Object> regUser(
			@ModelAttribute Member entity, HttpSession session) {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		try {

			newsDao.insert(entity);
		} catch (Exception e) {
			return MessageUtil.exception(user, e);
		}

		return MessageUtil.success();
	}

	@RequestMapping(params = "p=login")
	public @ResponseBody Map<String, Object> login(
			@ModelAttribute Member entity, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", entity.getEmail());
		map.put("userPassword", entity.getUserPassword());
		List<Member> list = newsDao.query(Member.class, map);
		if (list.size() > 0) {
			session.setAttribute(Const.SESSION_HAS_MEMBER, Const.TRUE);
			session.setAttribute(Const.SESSION_USERROL, list.get(0));
			entity = list.get(0);
			entity.setLastLogin(DateUtil.getDateTimeString());
			newsDao.update(entity);
			return MessageUtil.success();
		} else {
			return MessageUtil.error();
		}

	}

}
