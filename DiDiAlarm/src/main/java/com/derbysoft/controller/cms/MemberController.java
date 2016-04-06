package com.derbysoft.controller.cms;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.derbysoft.controller.service.BaseDaoController;
import com.derbysoft.dao.cms.EditionDao;
import com.derbysoft.dao.cms.MemberAndNews;
import com.derbysoft.dao.cms.MemberDao;
import com.derbysoft.dao.cms.NewsDao;
import com.derbysoft.dao.cms.User_NewsDao;
import com.derbysoft.dao.sys.AreaDao;
import com.derbysoft.dao.sys.CityDao;
import com.derbysoft.dao.sys.ProvinceDao;
import com.derbysoft.entity.SystemInfo;
import com.derbysoft.entity.cms.GmsAlarmCall;
import com.derbysoft.entity.cms.Member;
import com.derbysoft.entity.cms.Message;
import com.derbysoft.entity.cms.News;
import com.derbysoft.entity.cms.OrderNotice;
import com.derbysoft.entity.cms.Police;
import com.derbysoft.entity.cms.UserOrder;
import com.derbysoft.entity.sys.SYS_Area;
import com.derbysoft.entity.sys.SYS_City;
import com.derbysoft.entity.sys.SYS_Edition;
import com.derbysoft.entity.sys.SYS_Province;
import com.derbysoft.entity.sys.SYS_User;
import com.derbysoft.redis.service.RedisService;
import com.derbysoft.redis.service.UserService;
import com.derbysoft.service.cms.DateService;
import com.derbysoft.service.cms.MemberService;

import dy.hrtworkframe.db.SQLUtil;
import dy.hrtworkframe.entity.Pager;
import dy.hrtworkframe.exception.CustomerException;
import dy.hrtworkframe.util.CheckUtil;
import dy.hrtworkframe.util.Const;
import dy.hrtworkframe.util.DateUtil;
import dy.hrtworkframe.util.ExportUtils;
import dy.hrtworkframe.util.FileUpload;
import dy.hrtworkframe.util.MD5;
import dy.hrtworkframe.util.MessageUtil;
import dy.hrtworkframe.util.Sha1Util;
import dy.hrtworkframe.util.SortUtil;
import dy.hrtworkframe.util.StringUtil;
import dy.hrtworkframe.util.UUIDUtil;

@Transactional
@Controller
@RequestMapping("member.do")
public class MemberController<V> extends BaseDaoController {

	@Autowired
	private NewsDao newsDao;

	@Autowired
	private NewsDao newDao;

	@Autowired
	private User_NewsDao user_NewsDao;

	@Autowired
	private EditionDao editionDao;

	@Autowired
	private CityDao cityDao;

	@Autowired
	private ProvinceDao provinceDao;

	@Autowired
	private AreaDao areaDao;

	@Autowired
	private MemberDao memberDao;

	@Autowired
	private MemberService memberService;

	@Autowired
	private RedisService redisService;

	@Autowired
	private SystemInfo systemInfo;

	@Autowired
	private UserService userService;
	@Autowired
	private DateService dateService;

	
	
	
	private static final String[] IMAGE_TYPE = new String[] { ".bmp", ".jpg",
			".jpeg", ".gif", ".png" };

	@RequestMapping(params = "p=view")
	public ModelAndView showListView(HttpSession session,
			@RequestParam("moduleID") String moduleID) throws Exception {
		SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
		pd = getPageData();
		try {
			// 获取用户权限按钮
			pd.put("userbutton", dateService.findAuthority(user, moduleID));
			mv.addObject("model", pd);
			mv.setViewName("/cms/member/member_view");
		} catch (Exception e) {
			return MessageUtil.exception(user, mv, e);
		}
		return MessageUtil.success(mv);
	}


	@RequestMapping(params = "p=edit")
	public @ResponseBody Map<String, Object> edit(
			@ModelAttribute Member entity, HttpSession session)
			throws Exception {
		SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
		try {
			if (entity.getUserPassword() == null
					|| "******".equals(entity.getUserPassword())
					&& CheckUtil.isNullStr(entity.getConfirmPassword())) {
				entity.setUserPassword(null);
				newsDao.update(entity);
				return MessageUtil.success();
			
			}
			if (entity.getUserPassword() == null
					|| entity.getConfirmPassword() == null) {
			
				throw new CustomerException("两次输入的密码不一致请重新输入");
		    
			}
			
			// System.out.println(entity.getUserPassword().matches("(?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[_]+$).{6,20}"));
			
			// System.out.println(entity.getConfirmPassword().matches("(?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[_]+$).{6,20}"));
			
			if (entity.getUserPassword().matches(
					"(?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[_]+$).{6,20}") != true
					|| entity.getConfirmPassword().matches(
							"(?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[_]+$).{6,20}") != true) {
			
				throw new CustomerException("密码规则:长度为6-20位,由数字、字母或下划线其中的两种组成");
			
			}
			
			if (entity.getUserPassword().matches(
					"(?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[_]+$).{6,20}")
					|| entity.getConfirmPassword().matches(
							"(?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[_]+$).{6,20}")) {
				if (!entity.getUserPassword().matches("[\\w]+")
						|| !entity.getConfirmPassword().matches("[\\w]+")) {
					throw new CustomerException(
							"密码规则:长度为6-20位,由数字、字母或下划线其中的两种组成");
				}
			}
			
			if (entity.getUserPassword().trim() != ""
					&& entity.getConfirmPassword().trim() != "") {
				if (entity.getUserPassword()
						.equals(entity.getConfirmPassword()) != true) {
					throw new CustomerException("两次输入的密码不一致请重新输入");
				}
			}
			try {
				if (entity.getTelephone().matches("^(13|14|15|17|18)\\d{9}$") != true) {
					throw new CustomerException("手机号码不存在,请输入正确的手机号码");
				}
			} catch (Exception e1) {
				throw new CustomerException("手机号码不存在,请输入正确的手机号码");
			}
			String queryphone = SQLUtil.getQuerySQL(Member.class,
					"where 1=1 and telephone = '" + entity.getTelephone()
							+ "' ;");
			List<Member> query = newsDao.query(Member.class, queryphone);
			Member member = query.get(0);
			if (query.size() >= 1) {
				if (!member.getTelephone().equals(entity.getTelephone()))
					throw new CustomerException("手机号码已经绑定,请输入未注册的手机号码");
			}
			entity.setUserName(entity.getTelephone());
			entity.setUserPassword(Sha1Util.getSha1(MD5.md5(entity
					.getUserPassword())));
			// String sql = SQLUtil.getUpdateSQL(entity);
			// newsDao.jdbcTemplate.execute(sql);
			newsDao.update(entity);
		} catch (Exception e) {
			return MessageUtil.exception(user, e);
		}
		return MessageUtil.success();
	}

	// 群众端修改地址
	// 这是传ID和地址都要传
	@RequestMapping(params = "p=editAddress")
	public @ResponseBody Map<String, Object> editAddress(
			@RequestBody JSONObject Body, HttpSession session) throws Exception {
		try {
			JSONObject fromObject = JSONObject.fromObject(Body);
			String tokenID = fromObject.getString("tokenID");
			Member user = userService.getRedisAPPUser(tokenID, Member.class);
		    

		    String address = (String) fromObject.get("address");
	        String areaID = (String) fromObject.get("areaID");
            if (address.length() > 50) {
				return MessageUtil.error("MSG105", 105);
			}
           memberService.updateAddressByAreaID(areaID, address, user);
			
             Member member = new Member();
			 member.setUserID(user.getUserID());
			 Member queryByPrimary = newsDao.queryByPrimary(member);
			 userService.setRedisAPPUser(queryByPrimary, tokenID);
			 
		} catch (Exception e) {
			return MessageUtil.error("MSG6", 500);
		}
		return MessageUtil.success("MSG1", 200);
	}

	/*public static void main(String[] args) {
		String w="{ year:{2015:{week:{01:{productionDataList:[39,39,39,39],coSavingDataList:[39,39,39,39],self_consumptionDataList:[39,39,39,39],incomeDataDataList:[39,39,39,39],feed_inDataList:[39,39,39,39],pvModule:{power:[39,39,39,39],voltage:[39,39,39,39]},inverter:{power:[39,39,39,39],voltage:[39,39,39,39]}},02:{productionDataList:[39,39,39,39],coSavingDataList:[39,39,39,39],self_consumptionDataList:[39,39,39,39],incomeDataDataList:[39,39,39,39],feed_inDataList:[39,39,39,39],pvModule:{power:[39,39,39,39],voltage:[39,39,39,39]},inverter:{power:[39,39,39,39],voltage:[39,39,39,39]}}},day:{01.01:{productionDataList:[39,39,39,39],coSavingDataList:[39,39,39,39],self_consumptionDataList:[39,39,39,39],incomeDataDataList:[39,39,39,39],feed_inDataList:[39,39,39,39],pvModule:{power:[39,39,39,39],voltage:[39,39,39,39]},inverter:{power:[39,39,39,39],voltage:[39,39,39,39]}},01.02:{productionDataList:[39,39,39,39],coSavingDataList:[39,39,39,39],self_consumptionDataList:[39,39,39,39],incomeDataDataList:[39,39,39,39],feed_inDataList:[39,39,39,39],pvModule:{power:[39,39,39,39],voltage:[39,39,39,39]},inverter:{power:[39,39,39,39],voltage:[39,39,39,39]}}},month:{02:{productionDataList:[39,39,39,39],coSavingDataList:[39,39,39,39],self_consumptionDataList:[39,39,39,39],incomeDataDataList:[39,39,39,39],feed_inDataList:[39,39,39,39],pvModule:{power:[39,39,39,39],voltage:[39,39,39,39]},inverter:{power:[39,39,39,39],voltage:[39,39,39,39]}},01:{productionDataList:[39,39,39,39],coSavingDataList:[39,39,39,39],self_consumptionDataList:[39,39,39,39],incomeDataDataList:[39,39,39,39],feed_inDataList:[39,39,39,39],pvModule:{power:[39,39,39,39],voltage:[39,39,39,39]},inverter:{power:[39,39,39,39],voltage:[39,39,39,39]}}},productionDataList:[39,39,39,39],coSavingDataList:[39,39,39,39],self_consumptionDataList:[39,39,39,39],incomeDataDataList:[39,39,39,39],feed_inDataList:[39,39,39,39],pvModule:{power:[39,39,39,39],voltage:[39,39,39,39]},inverter:{power:[39,39,39,39],voltage:[39,39,39,39]}}}}";
        String w2="{id : 1 }";
        
		JSONObject jsStr = JSONObject.fromObject(w); //将字符串{“id”：1}
		Map map=(Map)jsStr.get("year");
		

		System.out.println(jsStr.get("yesterdayTime"));
	}*/
	
	// 群众端修改密码
	@RequestMapping(params = "p=editPassword")
	public @ResponseBody Map<String, Object> editPassword(
			@RequestBody JSONObject Body, HttpSession session) throws Exception {
		JSONObject fromObject = JSONObject.fromObject(Body);
		String tokenID = fromObject.getString("tokenID");
		Member user = userService.getRedisAPPUser(tokenID, Member.class);
		String password = (String) fromObject.get("password");// 这是新的password
		String beforePassword = (String) fromObject.get("beforePassword");// 这是过去的password
		String confirmPassword = (String) fromObject.get("confirmPassword");// 确认的password
		Member member = new Member();
		// 这是判断长度
		if (password.length() < 6 && password.length() > 16) {
			return MessageUtil.error("MSG100", 100);
		}
		// 这是判断两个密码
		if (!(confirmPassword.equals(password))) {
			return MessageUtil.error("MSG101", 101);
		}
		if (!(password.matches("(?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[_]+$).{6,20}"))) {
			return MessageUtil.error("MSG14", 414);
		}
		if (password.matches("(?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[_]+$).{6,20}")) {
			if (!password.matches("[\\w]+")) {
				return MessageUtil.error("MSG14", 414);
			}
		}
		member.setUserID(user.getUserID());

		String getpassword = memberService.getpassword(member);
		String beforePassword1 = Sha1Util.getSha1(MD5.md5(beforePassword));
		// String userPassword = user.getUserPassword();
		member.setUserPassword(getpassword);
		// 这是判断现在和过去密码
		if (!(beforePassword1.equals(getpassword))) {
			return MessageUtil.error("MSG102", 102);
		}
		member.setUserPassword(Sha1Util.getSha1(MD5.md5(password)));
		try {
			memberService.editPassword(user, member);
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtil.error(6, 6);
		}
		return MessageUtil.success("MSG1", 200);
	}

	// 群众手机端的个人信息
	@RequestMapping(params = "p=appEyeView")
	public @ResponseBody Map<String, Object> showEyeView1(HttpSession session,
			@RequestBody JSONObject Body) throws Exception {
		JSONObject fromObject = JSONObject.fromObject(Body);
		
		String tokenID = fromObject.getString("tokenID");
		Member user1 = userService.getRedisAPPUser(tokenID, Member.class);
		if(user1 == null){
			return MessageUtil.error("MSG7", 407);
		}
		// Member user1 = (Member)
		// session.getAttribute(Const.SESSION_HAS_MEMBER);
		// pd = getPageData();
		Member member = new Member();
		member.setUserID(user1.getUserID());
		List<Member> query = newsDao.query(member);
		Member user = query.get(0);
		// Map<String, Member> model = new HashMap<String, Member>();
		try {
			String phone = StringUtil
					.replace(3, 7, user.getTelephone(), "****");

			String idCard = StringUtil.replace(6, user.getIdCard().length(),
					user.getIdCard(), "*********");
			user.setTelephone(phone);
			user.setIdCard(idCard);
			user.setAddress(user.getAddress());
			HashMap<String, Member> map = new HashMap<String, Member>();
			// String headImg1 = user.getHeadImg();
			// String concat = DIDIHOST.concat(headImg1);
			// concat = concat.substring(0,concat.length()-1);
			// user.setHeadImg(concat);
			// headImg+"nihao";
			map.put("model", user);
			// mv.setViewName("/cms/member/member_eye");
			return MessageUtil.success(map, "MSG1", 200);
		} catch (Exception e) {
			return MessageUtil.error("MSG6", 500);
		}
	}

	@RequestMapping(params = "p=eyeView")
	public ModelAndView showEyeView(Member entity, HttpSession session) {
		SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
		pd = getPageData();
		@SuppressWarnings("unused")
		List<Member> query = newsDao.query(entity);

		pd.put("entity", newsDao.queryByPrimary(entity));
		try {
			mv.addObject("model", pd);
			mv.setViewName("/cms/member/member_eye");

		} catch (Exception e) {
			return MessageUtil.exception(user, mv, e);
		}

		return MessageUtil.success(mv);
	}

	/*
	 * @RequestMapping(params = "p=showAddView") public ModelAndView
	 * showAddView(@ModelAttribute Member entity, HttpSession session) { User
	 * user = (User) session.getAttribute(Const.SESSION_USER); try { String sql
	 * = SQLUtil.getQuerySQL(Article.class) ; List<Article> range =
	 * memberDao.query(Article.class, sql); mv.addObject("model",
	 * getPageData().put("range", range));
	 * mv.setViewName("/cms/member/member_add"); } catch (Exception e) { return
	 * MessageUtil.exception(user, mv, e); }
	 * 
	 * return MessageUtil.success(mv); }
	 */
	/**/
	// 这是这直接查看省
/*	@RequestMapping(params = "p=province")
	public @ResponseBody Map<String, Object> viewProvince(HttpSession session,

	HttpServletRequest request, HttpServletResponse response) {
		try {

			List<SYS_Province> querys = memberService.queryProvince();
			Map<String, SYS_Province> params = new HashMap<String, SYS_Province>();
			for (SYS_Province query : querys) {

				if (query == null) {
					continue;
				}
				params.put(query.getProvinceID(), query);
			}

			return MessageUtil.success(params, 1, 200);
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtil.error("MSG6", 500);
		}

	}*/

	// 这是知道省查市(没用)
/*	@RequestMapping(params = "p=findCity")
	public @ResponseBody Map<String, Object> findCity(
			@ModelAttribute SYS_City city, HttpSession session) {
		try {
			List<SYS_City> querys = memberService.findCity(city);
			Map<String, SYS_City> params = new HashMap<String, SYS_City>();
			for (SYS_City query : querys) {

				if (query == null) {
					continue;
				}
				params.put(query.getCityID(), query);
			}
			return MessageUtil.success(params, "MSG1", 200);
		} catch (Exception e) {
			return MessageUtil.error("MSG6", 200);
		}

	}*/

	// 这是知道市查县(没用)
	/*@RequestMapping(params = "p=findArea")
	public @ResponseBody List<Object> findArea(@ModelAttribute SYS_Area area) {

		try {
			// memberService.findArea();
			String w = " 1=1 and cityID =  ? ";
			List<Object> args = new ArrayList<Object>();
			args.add(area.getCityID());
			String sql = SQLUtil.getQuerySQL(SYS_Area.class) + " where " + w;
			List<Object> query = areaDao.query(SYS_Area.class, sql,
					args.toArray());
			// pd = getPageData();
			return query;
		} catch (Exception e) {
			throw new CustomerException("未找到区级");
		}

	}*/

	/*@RequestMapping(params = "p=findProvince")
	public @ResponseBody List<SYS_Province> findProvince(
			@ModelAttribute SYS_Province province, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			
			String sql = SQLUtil.getQuerySQL(SYS_Province.class);
			List<SYS_Province> query = provinceDao.query(SYS_Province.class,
					sql);
			// pd = getPageData();
			return query;
		} catch (Exception e) {
			throw new CustomerException("未找到省");
		}

	}*/

	// 这是城市回显(有用)
	@RequestMapping(params = "p=findCityAndArea")
	public @ResponseBody Map<String, Object> findCityAndArea(
			HttpSession session, @RequestBody JSONObject Body) throws Exception {
		JSONObject fromObject = JSONObject.fromObject(Body);
		String tokenID = fromObject.getString("tokenID");
		Member user = userService.getRedisAPPUser(tokenID, Member.class);
		try {
			if (null == user.getProvince()) {
				user.setProvince("贵州省");
				user.setProvinceID("520000");
				user.setCity("贵阳县");
				user.setCityID("520100");
				user.setArea("花溪区");
				user.setAreaID("520111");
			}
			HashMap<String, Member> map = new HashMap<String, Member>();
			map.put("model", user);
			return MessageUtil.success(map, "MSG1", 200);
		} catch (Exception e) {
			return MessageUtil.error("MSG6", 500);
		}
	}

	// 这是用户端的取消收藏
	@RequestMapping(params = "p=cancelNews")
	public @ResponseBody Map<String, Object> CancelNews(HttpSession session,
			@RequestBody JSONObject Body) throws Exception {
		JSONObject fromObject = JSONObject.fromObject(Body);
		String tokenID = fromObject.getString("tokenID");
		Member user = userService.getRedisAPPUser(tokenID, Member.class);
		// JSONObject fromObject = JSONObject.fromObject(Body);
		String newsID = (String) fromObject.get("newsID");
		try {
			MemberAndNews memberAndNews = new MemberAndNews();
			String userID = user.getUserID();
			memberAndNews.setUserID(userID);
			memberAndNews.setNewsID(newsID);
			List<MemberAndNews> query = user_NewsDao.query(memberAndNews);
			if (query == null || query.size() < 1) {

				return MessageUtil.error("MSG103", 103);
			}
			String w = "DELETE from cms_user_news where newsID='" + newsID
					+ "'and userid='" + userID + "'";

			// user_NewsDao.delete1(memberAndNews);
			user_NewsDao.jdbcTemplate.execute(w);
			return MessageUtil.success("MSG1", 200);
		} catch (Exception e) {
			return MessageUtil.error("MSG6", 500);
		}
	}

	// 这是个人的

	@RequestMapping(params = "p=collectNews")
	public @ResponseBody Map<String, Object> CollectNews(HttpSession session,
			@RequestBody JSONObject Body) throws Exception {
		JSONObject fromObject = JSONObject.fromObject(Body);
		String tokenID = fromObject.getString("tokenID");
		Member user = userService.getRedisAPPUser(tokenID, Member.class);
		String newsID = (String) fromObject.get("newsID");
		try {
			MemberAndNews memberAndNews = new MemberAndNews();
			String userID = user.getUserID();
			memberAndNews.setUserID(userID);
			memberAndNews.setNewsID(newsID);
			List<MemberAndNews> query = user_NewsDao.query(memberAndNews);
			if (query.size() > 0) {
				return MessageUtil.error("MSG104", 104);
			}
			user_NewsDao.insert(memberAndNews);
			return MessageUtil.success("MSG1", 200);
		} catch (Exception e) {
			return MessageUtil.error("MSG6", 500);
		}
	}

	// 这是群众端的版本信息
	@RequestMapping(params = "p=edition")
	public @ResponseBody Map<String, Object> Edition() {

		SYS_Edition edition = new SYS_Edition();
		List<SYS_Edition> query = editionDao.query(edition);
		Map<String, List<SYS_Edition>> hashMap = new HashMap<String, List<SYS_Edition>>();
		hashMap.put("model", query);
		return MessageUtil.success(hashMap, "MSG1", 200);
	}

	// 修改头像
	@RequestMapping(params = "p=editFile")
	public @ResponseBody Map<String, Object> editFile(HttpSession session,
			@RequestBody JSONObject Body) throws Exception {
		JSONObject fromObject = JSONObject.fromObject(Body);
		String tokenID = fromObject.getString("tokenID");
		Member user = userService.getRedisAPPUser(tokenID, Member.class);

		String headImg = (String) fromObject.get("headImg");
		Member entity = new Member();
		try {
			entity.setHeadImg(headImg);
			String userID = user.getUserID();
			entity.setUserID(userID);
			memberDao.update(entity);
		} catch (Exception e) {
			return MessageUtil.error("MSG6", 500);
		}
		return MessageUtil.success("MSG1", 200);
	}

	// 群众端的所有news(没用)
	@RequestMapping(params = "p=findAllNews")
	public @ResponseBody Map<String, Object> findAllNews(HttpSession session,
			@RequestBody JSONObject Body) throws Exception {
		JSONObject fromObject = JSONObject.fromObject(Body);
		String tokenID = fromObject.getString("tokenID");
		Member user = userService.getRedisAPPUser(tokenID, Member.class);

		try {
			String w = " 1=1 and state =  ?  or state = ? ";
			List<Object> args = new ArrayList<Object>();
			args.add("1");
			args.add("3");
			String sql = SQLUtil.getQuerySQL(News.class) + " where " + w;
			List<News> querys = newsDao.query(News.class, sql, args.toArray());
			Map<String, Object> params = new HashMap<String, Object>();

			// 这是找那些是收藏的news
			String w1 = " 1=1 and userid =  ? ";
			List<Object> args1 = new ArrayList<Object>();
			args1.add(user.getUserID());
			String sql1 = SQLUtil.getQuerySQL(MemberAndNews.class) + " where "
					+ w1;
			List<MemberAndNews> querys1 = user_NewsDao.query(
					MemberAndNews.class, sql1, args1.toArray());
//			/Map<String, Object> params1 = new HashMap<String, Object>();

			for (MemberAndNews memberAndNews : querys1) {
				for (News news1 : querys) {
					if (news1.getNewsID().equals(memberAndNews.getNewsID())) {
						news1.setCollectState("1");
					} else {
						news1.setCollectState("0");
					}
				}
			}

			for (News query : querys) {

				if (query == null) {
					continue;
				}
				params.put(query.getNewsID(), query);
			}
			return MessageUtil.success(params, 1, 1, 1, 1);
		} catch (Exception e) {
			return MessageUtil.error(2, 1, 1, 1);
		}

	}

	/**
	 * 
	     * @discription 只显示收藏的新闻
	     * @author Knight      
	     * @created 2016年2月23日 上午10:28:06     
	     * @param session
	     * @param Body
	     * @return
	     * @throws Exception
	 */
	@RequestMapping(params = "p=findNews")
	public @ResponseBody Map<String, Object> findNews(HttpSession session,
			@RequestBody JSONObject Body) throws Exception {
		JSONObject fromObject = JSONObject.fromObject(Body);
		String tokenID = fromObject.getString("tokenID");
		//int page = fromObject.getInt("page");
		
		//int i=(page-1)*10;
		
		Member user = userService.getRedisAPPUser(tokenID, Member.class);
		try {
			String w = "SELECT * from cms_news ,cms_user_news where cms_news.NewsID=cms_user_news.NewsID and cms_user_news.UserID= ? order by  publishTime desc";
					/*+ " LIMIT "
			 	+ i + " ,10";*/
			List<Object> args = new ArrayList<Object>();
			args.add(user.getUserID());
			Map<String, List<News>> params = new HashMap<String, List<News>>();
			List<News> querys = newsDao.query(News.class, w, args.toArray());
			params.put("model", querys);
			
			return MessageUtil.success(params, "MSG1", 200);
			
		} catch (Exception e) {
			
			return MessageUtil.error("MSG6", 500);
		
		}

	}

	@SuppressWarnings("all")
	@RequestMapping(params = "p=find")
	public @ResponseBody Pager find(Pager pager, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String w = "telephone like '%"
					+ pager.getParameters().get("searchText") + "%' "
					+ "|| realName like '%"
					+ pager.getParameters().get("searchText") + "%' "
					+ SQLUtil.getWhereClause(pager);

//			if (pager.getIsExport()) {
//				if (pager.getExportAllData()) {
//					pager.setExportDatas(newsDao.query(SQLUtil
//							.getQuerySQL(Member.class)));
//				}
//				try {
//					ExportUtils.export(request, response, pager);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				return null;
//			}
//			if (pager.getPageSize() == 0) {
//
//				String sql = SQLUtil.getQuerySQL(Member.class) + " where " + w;
//				pager.setExhibitDatas(newsDao.query(Member.class, sql));
//
//			} else {
//
//				pager.setAdvanceQuerySorts(SortUtil
//						.sortBuild1("joined", "desc"));
//				newsDao.queryCount(pager, Member.class, w);
//				newsDao.queryPager(pager, Member.class, w);
//				pager.setIsSuccess(true);
//			}

			if (pager.getIsExport()) {
				if (pager.getExportAllData()) {
					pager.setExportDatas(newsDao.query(SQLUtil
							.getQuerySQL(Member.class)));
				}
				try {
					ExportUtils.export(request, response, pager);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

			if (pager.getPageSize() == 0) {

				String sql = SQLUtil.getQuerySQL(Member.class) + " where " + w;
				pager.setExhibitDatas(newsDao.query(Member.class, sql));

			} else {

				//sortFiled
				if(pager.getParameters().get("sortFiled")==null||pager.getParameters().get("sortInt").toString().equals("2")){
					pager.setAdvanceQuerySorts(SortUtil.sortBuild1("joined", "desc"));
					newsDao.queryCount(pager, Member.class, w);
					newsDao.queryPager(pager, Member.class, w);
				}else{
				if("1".equals(pager.getParameters().get("sortInt").toString())){
					pager.setAdvanceQuerySorts(SortUtil.sortBuild1(pager.getParameters().get("sortFiled").toString(), "desc"));
//					w += " group by "+pager.getParameters().get("sortFiled").toString()+" desc ";
					
				}else{
					pager.setAdvanceQuerySorts(SortUtil.sortBuild1(pager.getParameters().get("sortFiled").toString(), "asc"));

//					w += " group by "+pager.getParameters().get("sortFiled").toString()+" asc ";
					
				}
				newsDao.queryCount(pager, Member.class, w);
				newsDao.queryPager(pager, Member.class, w);
				}
			
//				pager.setAdvanceQuerySorts(SortUtil
//						.sortBuild1("joined", "desc"));
//				newsDao.queryCount(pager, Member.class, w);
//				newsDao.queryPager(pager, Member.class, w);
//				pager.setIsSuccess(true);
			}

		} catch (Exception e) {
			e.printStackTrace();
			pager.setIsSuccess(false);
		}

		pager.setIsSuccess(true);
		return pager;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "p=findCityArea")
	public @ResponseBody Map<String, Object> findCityArea(HttpSession session)
			throws Exception {
		//Member user = (Member) session.getAttribute(Const.SESSION_HAS_MEMBER);
	
		String string = redisService.get("CityArea");
		Long a = System.currentTimeMillis();
		if (null == string) {
			List<SYS_Province> province = provinceDao.query(SYS_Province.class);
			try {
				int provinceNum = province.size();
				int i = 0;
				for (; provinceNum > i; i++) {
					SYS_Province sys_Province = province.get(i);
					SYS_City sys_City = new SYS_City();
					sys_City.setProvinceID(sys_Province.getProvinceID());
					List<SYS_City> city = cityDao.query(sys_City);
					int cityNum = city.size();
					int j = 0;
					for (; cityNum > j; j++) {
						SYS_City sys_City2 = city.get(j);
						SYS_Area sys_Area = new SYS_Area();
						sys_Area.setCityID(sys_City2.getCityID());
						List<SYS_Area> query = areaDao.query(sys_Area);
						sys_City2.setArea(query);
					}
					sys_Province.setCity(city);
				}
				Map<String, List<SYS_Province>> map = new HashMap<String, List<SYS_Province>>();
				map.put("model", province);
				String string2 = JSONObject.fromObject(map).toString();
				redisService.set("CityArea", string2, 86400);
				return MessageUtil.success(map, 1, 1);
			} catch (Exception e) {
				return MessageUtil.error(6, 6);

			}
		}
		else {
			ObjectMapper mapper = new ObjectMapper();
			Map readValue = mapper.readValue(string, Map.class);
			System.out.println((System.currentTimeMillis() - a));
			return MessageUtil.success(readValue, 1, 1);
		}
	}

	// 查看详细的新闻
	@RequestMapping(params = "p=openNews")
	public @ResponseBody Map<String, Object> openNews(HttpSession session,
			@RequestBody JSONObject Body) throws Exception {
		JSONObject fromObject = JSONObject.fromObject(Body);
		String newID = (String) fromObject.getString("newID");
		News news = new News();
		MemberAndNews memberAndNews = new MemberAndNews();
		try {
			List<MemberAndNews> query2 = user_NewsDao.query(memberAndNews);
			if (query2.size() > 0) {
				news.setNewsID(newID);
				List<News> query = newDao.query(news);
				if (query.size() < 1) {
					return MessageUtil.error("MSG107", 107);
				}
				query.get(0).setCollectState("okCollect");
				HashMap<String, List<News>> params = new HashMap<String, List<News>>();
				params.put("model", query);
				return MessageUtil.success(params, "MSG1", 200);
			} else {
				news.setNewsID(newID);
				List<News> query = newDao.query(news);
				// query.get(0).setCollectState("noCollect");
				HashMap<String, List<News>> params = new HashMap<String, List<News>>();
				params.put("model", query);
				return MessageUtil.success(params, "MSG1", 200);
			}

		} catch (Exception e) {
			return MessageUtil.error("MSG6", 500);
		}
	}

	/*
	 * @SuppressWarnings("deprecation")
	 * 
	 * @RequestMapping(params = "p=findAllNews1") public @ResponseBody
	 * Map<String, Object> findAllNews1( HttpSession session ,@RequestBody
	 * JSONObject Body) throws Exception { JSONObject fromObject =
	 * JSONObject.fromObject(Body); String
	 * tokenID=fromObject.getString("tokenID"); Member user =
	 * userService.getRedisAPPUser(tokenID, Member.class); String userID =
	 * user.getUserID(); User_News user_News = new User_News(); String w1=
	 * "select * from (select b.* , 'okCollect' as collectState from (SELECT * from  cms_user_news where userid="
	 * +"'"+userID+"'"+
	 * ") a LEFT JOIN  (select * from cms_news where State='peopleNews' or State='allNews') b   on b.newsid=a.newsid   UNION select * , 'noCollect' as collectState from cms_news where newsid  not in (select NewsID from cms_user_news where userid="
	 * +"'"+userID+"'"+
	 * ") and State= 'allNews' or State='peopleNews' ) c order BY c.updatetime DESC"
	 * ; try { user_News.setUserID(user.getUserID()); List<User_News> query =
	 * newsDao.query(user_News); News news = new News(); List<News> newsList =
	 * newsDao.query(News.class); List<Map<String, Object>> query1 =
	 * newsDao.query(w1); for(){
	 * 
	 * }
	 * 
	 * //newsdao //List<Map<String, Object>> query = newsDao.query(w1);
	 * HashMap<String, List<Map<String, Object>>> hashMap = new HashMap<String,
	 * List<Map<String, Object>>>(); HashMap<String, List<Map<String, Object>>>
	 * focusStatusMap = new HashMap<String, List<Map<String, Object>>>();
	 * 
	 * hashMap.put("focusStatus", query); focusStatusMap.put("NofocusStatus",
	 * query2);
	 * 
	 * return MessageUtil.success(hashMap, "MSG1",200); } catch (Exception e) {
	 * return MessageUtil.error("MSG6",500); } }
	 */

	// 这是群众端的公告
	@RequestMapping(params = "p=findNotice")
	public @ResponseBody Map<String, Object> findNotice(HttpSession session,
			@RequestBody JSONObject Body) throws Exception {
/*		JSONObject fromObject = JSONObject.fromObject(Body);
*//*		String tokenID = fromObject.getString("tokenID");
*//*		Member user = userService.getRedisAPPUser(tokenID, Member.class);
*/
		// String newsID =(String) fromObject.get("newsID");
		// String w1="select * from (select b.* , "+"'"+"1"+"'"+
		// "as collectState from (SELECT * from  cms_user_news where userid="+"'"+newsID+"'"+") a LEFT JOIN  (select * from cms_news where State='1' or State='3') b   on b.newsid=a.newsid   UNION select * , "+"'"+"0"+"'"+" as collectState from cms_news where newsid  not in (select NewsID from cms_user_news where userid="+"'"+newsID+"'"+") and State="+"'"+"1"+"'"
		// +"or State="+"'"+"3"+"'" +") c order BY c.updatetime DESC";
		String w = "select * from cms_news where State='allNotice' or state='peopleNotice' order by publishTime desc";
		try {

			@SuppressWarnings("deprecation")
			List<Map<String, Object>> query = newsDao.query(w);
			HashMap<String, List<Map<String, Object>>> hashMap = new HashMap<String, List<Map<String, Object>>>();
			hashMap.put("model", query);
			return MessageUtil.success(hashMap, "MSG1", 200);
		} catch (Exception e) {
			return MessageUtil.error("MSG6", 500);
		}
	}

	// 这是头像的上传文件
	@RequestMapping(params = "p=checkFile")
	public @ResponseBody Map<String, Object> checkFile1(
			HttpServletRequest request, HttpSession session) {
		MultipartHttpServletRequest mureq = (MultipartHttpServletRequest) request;

		Map<String, MultipartFile> files = mureq.getFileMap();
		if (files == null || files.size() == 0) {
			return MessageUtil.error(6, 6);
		}
		Map.Entry<String, MultipartFile> e = files.entrySet().iterator().next();
		MultipartFile file = e.getValue();

		// 图片上传失败
		boolean isLegal = false;
		for (String type : IMAGE_TYPE) {
			if (StringUtils
					.endsWithIgnoreCase(file.getOriginalFilename(), type)) {
				isLegal = true;
				break;
			}
		}
		if (isLegal == false) {
			return MessageUtil.error();
		}
/*		User user = (User) session.getAttribute(Const.SESSION_USER);
*/		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String fileTureName = file.getOriginalFilename();
			String fileName = UUIDUtil.get32UUID();

			String filePath = this.getFilePath1(fileName, request);

			String extName = "."
					+ StringUtils.substringAfterLast(fileTureName, ".");// 生成新的文件名
			FileUpload.fileUp(file, request.getServletContext()
					.getRealPath("/") + filePath, fileName);
			map.put("filePath", systemInfo.absoluteFile + filePath + fileName
					+ extName);
		} catch (Exception e1) {
			return MessageUtil.error("MSG6", 500);
		}
		return MessageUtil.success(map);

	}

	private String getFilePath1(String sourceFileName,
			HttpServletRequest request) {
/*		String baseFolder = systemInfo.getUoloadPath();// userfile
*/		Date nowDate = new Date();
		// yyyy/MM/dd
		String fileFolder = systemInfo.getUoloadPath()
				+ new DateTime(nowDate).toString("yyyy") + "/"
				+ new DateTime(nowDate).toString("MM") + "/"
				+ new DateTime(nowDate).toString("dd");
		File file = new File(systemInfo.absoluteFile + fileFolder);
		if (!file.isDirectory()) {
			// 如果目录不存在，则创建目录
			file.mkdirs();
		}

		return fileFolder + "/";
	}
/**
 * 
     * @discription 查找报警
     * @author Knight      
     * @created 2016年2月22日 上午11:54:27     
     * @param session
     * @param Body
     * @return
     * @throws Exception
 */
	@RequestMapping(params = "p=findCall")
	public @ResponseBody Map<String, Object> findCall(HttpSession session,
		   @RequestBody JSONObject Body) throws Exception {
		JSONObject fromObject = JSONObject.fromObject(Body);
		String tokenID = fromObject.getString("tokenID");
		//int page=fromObject.getInt("page");
		Member user = userService.getRedisAPPUser1(tokenID, Member.class);
		String userID = user.getUserID();
/*		GmsAlarmCall gmsAlarmCall = new GmsAlarmCall();
		gmsAlarmCall.setUserID(userID);*/
		// gmsAlarmCall.setDeleteStatus("no");
		// String w="";
		try {
			//int p= (page - 1) * 10;
			HashMap<String, List<GmsAlarmCall>> hashMap = new HashMap<String, List<GmsAlarmCall>>();
			//List<GmsAlarmCall> query2 = newsDao.query(gmsAlarmCall);
         
			String sql ="select * from GMS_Alarm_Call where userid='"+userID+"'  order by InputDate desc";// LIMIT " + p + " ,10";

            List<GmsAlarmCall> query2 = newsDao.query(GmsAlarmCall.class, sql);
            
			ArrayList<GmsAlarmCall> arrayList = new ArrayList<GmsAlarmCall>();
			for (int i = 0; i < query2.size(); i++) {
				if ("".equals(query2.get(i).getDeleteStatus())
						|| null == (query2.get(i).getDeleteStatus())
						|| "no".equals(query2.get(i).getDeleteStatus())) {
					if ("nodealing".equals(query2.get(i).getStatus())
							|| "waiting".equals(query2.get(0).getStatus())
							|| "noPolice".equals(query2.get(0).getStatus())
							|| "timeDisabled".equals(query2.get(0).getStatus())
							|| "stop".equals(query2.get(0).getStatus())
							) {
						query2.get(i).setCallStatus("nodealing");

					} else if("cancleAlarm".equals(query2.get(i).getStatus())) {
						query2.get(i).setCallStatus("cancleAlarm");
					} else {
					query2.get(i).setCallStatus("dealing");
				}
					arrayList.add(query2.get(i));
				}
			}
			hashMap.put("model", arrayList);
			return MessageUtil.success(hashMap, "MSG1", 200);
		} catch (Exception e) {
			return MessageUtil.error("MSG6", 500);
		}
	}
//
	@RequestMapping(params = "p=findDetailedCall")
	public @ResponseBody Map<String, Object> findDetailedCall(
			HttpSession session, @RequestBody JSONObject Body) throws Exception {
		JSONObject fromObject = JSONObject.fromObject(Body);
		// String tokenID=fromObject.getString("tokenID");
		// Member user = userService.getRedisAPPUser(tokenID, Member.class);
		// Member user = (Member)
		// session.getAttribute(Const.SESSION_HAS_MEMBER);
		String alarmID = fromObject.get("alarmID").toString();
		// alarmID = new String(alarmID.getBytes(""), "utf-8");
		// String userID = user.getUserID();
		// GmsAlarmCall gmsAlarmCall =  0iknew GmsAlarmCall();
		// GmsAlarmCall gmsAlarmCall = new GmsAlarmCall();

		// GmsAlarmReceive gmsAlarmReceive = new GmsAlarmReceive();
		// gmsAlarmCall.setAlarmID(alarmID);
		try {

			HashMap<String, List<GmsAlarmCall>> hashMap = new HashMap<String, List<GmsAlarmCall>>();
			GmsAlarmCall gmsAlarmCall = new GmsAlarmCall();
			gmsAlarmCall.setAlarmID(alarmID);
			List<GmsAlarmCall> query2 = newsDao.query(gmsAlarmCall);
			if (0 == query2.size()) {
				return MessageUtil.error("MSG6", 500);
			}
			GmsAlarmCall gmsAlarmCall2 = query2.get(0);
			List<GmsAlarmCall> query=null ;
			/*if ("nodealing".equals(gmsAlarmCall2.getStatus())
					|| "waiting".equals(gmsAlarmCall2.getStatus())) {

				gmsAlarmCall2.setCallStatus("nodealing");

				hashMap.put("model", query2);
				return MessageUtil.success(hashMap, "MSG1", 200);
			}*/
			String sql = "select a.* , b.phone as userPhone ,b.`userName` as `policeName` from (select * from gms_alarm_call where AlarmID='"
					+ alarmID
					+ "') a  , (select * from gms_alarm_receive where alarmid='"
					+ alarmID + "') b where a.AlarmID =b.alarmid order by a.inputdate desc";// 贵-GYHXY201512307
			 query = newsDao.query(GmsAlarmCall.class, sql);
			if (query.size() > 0) {

				for (int i = 0; i < query.size(); i++) {
					if ("dealing".equals(query.get(i).getStatus())
							|| "okCheack".equals(query.get(0).getStatus())|| "okNoReason".equals(query.get(0).getStatus())) {
						query.get(i).setCallStatus("dealing");
					} else {
						query.get(i).setCallStatus("nodealing");
					}
				}
			}else{
				String sql3 ="select * from gms_alarm_call where AlarmID='"+alarmID+"'";
				query= newsDao.query(GmsAlarmCall.class, sql3);

				for (int i = 0; i < query.size(); i++) {
					if ("dealing".equals(query.get(i).getStatus())
							|| "okCheack".equals(query.get(0).getStatus())|| "okNoReason".equals(query.get(0).getStatus())) {
						query.get(i).setCallStatus("dealing");
					} else {
						query.get(i).setCallStatus("nodealing");
					}
				}
			}

			hashMap.put("model", query);
			return MessageUtil.success(hashMap, "MSG1", 200);
		} catch (Exception e) {
			return MessageUtil.error("MSG6", 500);
		}
	}

	@RequestMapping(params = "p=deleteCall")
	public @ResponseBody Map<String, Object> deleteCall(HttpSession session,
			@RequestBody JSONObject Body) throws Exception {
		try {
			JSONObject fromObject = JSONObject.fromObject(Body);
			String alarmID = fromObject.get("alarmID").toString();
			GmsAlarmCall entity = new GmsAlarmCall();
			entity.setAlarmID(alarmID);
			List<GmsAlarmCall> query = newsDao.query(entity);
			GmsAlarmCall gmsAlarmCall = query.get(0);
			gmsAlarmCall.setDeleteStatus("yes");
			newsDao.update(gmsAlarmCall);
			return MessageUtil.success("MSG1", 200);
		} catch (Exception e) {
			return MessageUtil.error("MSG6", 500);

		}
	}
	@RequestMapping(params = "p=del")
	public @ResponseBody Map<String, Object> deleteMember(HttpSession session,
			@RequestParam("userID") String userID) throws Exception {
		
		SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
		try {
			if(user == null){
				throw new CustomerException("请登陆后操作");
			}
			Member m = new Member();
			m.setUserID(userID);
			newsDao.delete(m);
			return MessageUtil.success();
		} catch (Exception e) {
			return MessageUtil.exception(user, e);
		}
	}

	// 群众端保存反馈
	@RequestMapping(params = "p=saveMessage")
	public @ResponseBody Map<String, Object> saveMessage(HttpSession session,
			@RequestBody JSONObject Body) throws Exception {
		// Member user = (Member)
		// session.getAttribute(Const.SESSION_HAS_MEMBER);
		JSONObject fromObject = JSONObject.fromObject(Body);
		String tokenID = fromObject.getString("tokenID");
		Member user = userService.getRedisAPPUser(tokenID, Member.class);
		String context = fromObject.getString("context");
		Message message = new Message();
		message.setContext(context);
		message.setPhone(message.getPhone());
		message.setStatus("peopleMessage");
		try {
			message.setCreateTime(DateUtil.getDateString());
			message.setUserID(user.getUserID());
			message.setUserName(user.getRealName());
			message.setMessageID(UUIDUtil.get32UUID());
			message.setPhone(user.getTelephone());
			newsDao.insert(message);

			/* pd.put("newsList", newsList); */
			return MessageUtil.success("MSG1", 200);
		} catch (Exception e) {
			return MessageUtil.error("MSG6", 500);
		}
	}

	@RequestMapping(params = "p=findAllNews2")
	public @ResponseBody Map<String, Object> findAllNews2(HttpSession session,
			@RequestBody JSONObject Body) throws Exception {
		JSONObject fromObject = JSONObject.fromObject(Body);
		String tokenID = fromObject.getString("tokenID");
		int page = fromObject.getInt("page");
		Member user = userService.getRedisAPPUser(tokenID, Member.class);
		String userID = user.getUserID();
		// User_News user_News = new User_News();
		// String
		// w1="select * from (select b.* , 'okCollect' as collectState from (SELECT * from  cms_user_news where userid="+"'"+userID+"'"+") a LEFT JOIN  (select * from cms_news where State='peopleNews' or State='allNews') b   on b.newsid=a.newsid   UNION select * , 'noCollect' as collectState from cms_news where newsid  not in (select NewsID from cms_user_news where userid="+"'"+userID+"'"+") and State= 'allNews' or State='peopleNews' ) c order BY c.updatetime DESC";
		// 这是查新闻不是焦点
		int i = (page - 1) * 10;
		String w2 = "select * from (select b.* , 'okCollect' as collectState from (SELECT * from  cms_user_news where userid="
				+ "'"
				+ userID
				+ "'"
				+ ") a LEFT JOIN  (select * from (select * from cms_news where  PublishState='all' or PublishState= 'member' ) t where    t.FocusStatus='noFocus'   AND t.State= 'news' ) b   on b.newsid=a.newsid   UNION select * , 'noCollect' as collectState from (select * from cms_news where  PublishState='all' or PublishState= 'member' ) t where t.newsid  not in (select NewsID from cms_user_news where userid="
				+ "'"
				+ userID
				+ "'"
				+ ") and t.State= 'news' and t.FocusStatus='noFocus' ) c where 1=1 order BY c.updatetime DESC LIMIT "
				+ i + " ,10";
		// 这是查公告不是焦点
		// String
		// w3="select * from (select b.* , 'okCollect' as collectState from (SELECT * from  cms_user_news where userid="+"'"+userID+"'"+") a LEFT JOIN  (select * from (select * from cms_news where  PublishState='all' or PublishState= 'member' ) t where    t.FocusStatus='noFocus'   AND t.State= 'notice' ) b   on b.newsid=a.newsid   UNION select * , 'noCollect' as collectState from (select * from cms_news where  PublishState='all' or PublishState= 'member' ) t where t.newsid  not in (select NewsID from cms_user_news where userid="+"'"+userID+"'"+") and t.State= 'notice' and t.FocusStatus='noFocus' ) c where 1=1 order BY c.updatetime DESC LIMIT 0,10";
		// 这是查新闻是焦点
		String w4 = "select * from (select b.* , 'okCollect' as collectState from (SELECT * from  cms_user_news where userid="
				+ "'"
				+ userID
				+ "'"
				+ ") a LEFT JOIN  (select * from (select * from cms_news where  PublishState='all' or PublishState= 'member' ) t where    t.FocusStatus='yesFocus'   AND t.State= 'news' ) b   on b.newsid=a.newsid   UNION select * , 'noCollect' as collectState from (select * from cms_news where  PublishState='all' or PublishState= 'member' ) t where t.newsid  not in (select NewsID from cms_user_news where userid="
				+ "'"
				+ userID
				+ "'"
				+ ") and t.State= 'news' and t.FocusStatus='yesFocus' ) c order BY c.updatetime DESC ";
		// 这是查公告是焦点
		// String
		// w5="select * from (select b.* , 'okCollect' as collectState from (SELECT * from  cms_user_news where userid="+"'"+userID+"'"+") a LEFT JOIN  (select * from (select * from cms_news where  PublishState='all' or PublishState= 'member' ) t where    t.FocusStatus='yesFocus'   AND t.State= 'notice' ) b   on b.newsid=a.newsid   UNION select * , 'noCollect' as collectState from (select * from cms_news where  PublishState='all' or PublishState= 'member' ) t where t.newsid  not in (select NewsID from cms_user_news where userid="+"'"+userID+"'"+") and t.State= 'notice' and t.FocusStatus='yesFocus' ) c order BY c.updatetime DESC";
		try {
			List<News> query2 = newsDao.query(News.class, w2);
			List<News> query4 = newsDao.query(News.class, w4);
			if (query4.size() > 0) {

				for (int j = 0; j < query4.size(); j++) {
					if (null == query4.get(j).getNewsID()) {
						query4.remove(j);

					}
				}
			}
			if (query2.size() > 0) {
				for (int j = 0; j < query2.size(); j++) {
					if (null == query2.get(j).getNewsID()) {
						query2.remove(j);
					}
				}
			}
			HashMap<String, List<News>> hashMap = new HashMap<String, List<News>>();
			hashMap.put("nofocusAndNews", query2);
			// hashMap.put("nofocusAndNotice", query3);
			hashMap.put("yesFocusAndNews", query4);
			// hashMap.put("yesFocusAndNotice",query5);
			// newsdao
			// List<Map<String, Object>> query = newsDao.query(w1);
			// HashMap<String, List<Map<String, Object>>> nofocusAndNews = new
			// HashMap<String, List<Map<String, Object>>>();
			// HashMap<String, List<Map<String, Object>>> nofocusAndNotice = new
			// HashMap<String, List<Map<String, Object>>>();
			// HashMap<String, List<Map<String, Object>>> yesFocusAndNews = new
			// HashMap<String, List<Map<String, Object>>>();
			// HashMap<String, List<Map<String, Object>>> yesFocusAndNotice =
			// new HashMap<String, List<Map<String, Object>>>();
			if (query2.size() == 0) {
				return MessageUtil.success(hashMap, "MSG108", 108);
			}
			return MessageUtil.success(hashMap, "MSG1", 200);
		} catch (Exception e) {
			return MessageUtil.error("MSG6", 500);
		}
	}

	@RequestMapping(params = "p=findAllNews3")
	public @ResponseBody Map<String, Object> findAllNews3(HttpSession session,
			@RequestBody JSONObject Body) throws Exception {
		JSONObject fromObject = JSONObject.fromObject(Body);
		String tokenID = fromObject.getString("tokenID");
		int page = fromObject.getInt("page");
		Member user = userService.getRedisAPPUser(tokenID, Member.class);
		String userID = user.getUserID();
		// User_News user_News = new User_News();
		// String
		// w1="select * from (select b.* , 'okCollect' as collectState from (SELECT * from  cms_user_news where userid="+"'"+userID+"'"+") a LEFT JOIN  (select * from cms_news where State='peopleNews' or State='allNews') b   on b.newsid=a.newsid   UNION select * , 'noCollect' as collectState from cms_news where newsid  not in (select NewsID from cms_user_news where userid="+"'"+userID+"'"+") and State= 'allNews' or State='peopleNews' ) c order BY c.updatetime DESC";
		// 这是查新闻不是焦点
		// String
		// w2="select * from (select b.* , 'okCollect' as collectState from (SELECT * from  cms_user_news where userid="+"'"+userID+"'"+") a LEFT JOIN  (select * from (select * from cms_news where  PublishState='all' or PublishState= 'member' ) t where    t.FocusStatus='noFocus'   AND t.State= 'news' ) b   on b.newsid=a.newsid   UNION select * , 'noCollect' as collectState from (select * from cms_news where  PublishState='all' or PublishState= 'member' ) t where t.newsid  not in (select NewsID from cms_user_news where userid="+"'"+userID+"'"+") and t.State= 'news' and t.FocusStatus='noFocus' ) c where 1=1 order BY c.updatetime DESC LIMIT 0,10";
		int i = (page - 1) * 10;
		// 这是查公告不是焦点
		String w3 = "select * from (select b.* , 'okCollect' as collectState from (SELECT * from  cms_user_news where userid="
				+ "'"
				+ userID
				+ "'"
				+ ") a LEFT JOIN  (select * from (select * from cms_news where  PublishState='all' or PublishState= 'member' ) t where    t.FocusStatus='noFocus'   AND t.State= 'notice' ) b   on b.newsid=a.newsid   UNION select * , 'noCollect' as collectState from (select * from cms_news where  PublishState='all' or PublishState= 'member' ) t where t.newsid  not in (select NewsID from cms_user_news where userid="
				+ "'"
				+ userID
				+ "'"
				+ ") and t.State= 'notice' and t.FocusStatus='noFocus' ) c where 1=1 order BY c.updatetime DESC LIMIT "
				+ i + " ,10";
		// 这是查新闻是焦点
		// String
		// w4="select * from (select b.* , 'okCollect' as collectState from (SELECT * from  cms_user_news where userid="+"'"+userID+"'"+") a LEFT JOIN  (select * from (select * from cms_news where  PublishState='all' or PublishState= 'member' ) t where    t.FocusStatus='yesFocus'   AND t.State= 'news' ) b   on b.newsid=a.newsid   UNION select * , 'noCollect' as collectState from (select * from cms_news where  PublishState='all' or PublishState= 'member' ) t where t.newsid  not in (select NewsID from cms_user_news where userid="+"'"+userID+"'"+") and t.State= 'news' and t.FocusStatus='yesFocus' ) c order BY c.updatetime DESC";
		// 这是查公告是焦点
		String w5 = "select * from (select b.* , 'okCollect' as collectState from (SELECT * from  cms_user_news where userid="
				+ "'"
				+ userID
				+ "'"
				+ ") a LEFT JOIN  (select * from (select * from cms_news where  PublishState='all' or PublishState= 'member' ) t where    t.FocusStatus='yesFocus'   AND t.State= 'notice' ) b   on b.newsid=a.newsid   UNION select * , 'noCollect' as collectState from (select * from cms_news where  PublishState='all' or PublishState= 'member' ) t where t.newsid  not in (select NewsID from cms_user_news where userid="
				+ "'"
				+ userID
				+ "'"
				+ ") and t.State= 'notice' and t.FocusStatus='yesFocus' ) c order BY c.updatetime DESC LIMIT "
				+ i + " ,10";
		try {
			// List<Map<String, Object>> query2 = newsDao.query(w2);
			// List<Map<String, Object>> query3 = newsDao.query(w3);
			// List<Map<String, Object>> query4 = newsDao.query(w4);
			// List<Map<String, Object>> query5 = newsDao.query(w5);
			List<News> query3 = newsDao.query(News.class, w3);
			List<News> query5 = newsDao.query(News.class, w5);

			HashMap<String, List<News>> hashMap = new HashMap<String, List<News>>();
			if (query5.size() > 0) {

				for (int j = 0; j < query5.size(); j++) {
					query5.get(j).setContext(query5.get(i).getTitle());
					if (null == query5.get(j).getNewsID()) {
						query5.remove(j);
					}
				}
			}
			if (query3.size() > 0) {
				for (int j = 0; j < query3.size(); j++) {
					query3.get(j).setContext(query3.get(i).getTitle());
					if (null == query3.get(j).getNewsID()) {
						query3.remove(j);
					}
				}
			}
			// hashMap.put("nofocusAndNews",query2);
			hashMap.put("noFocusAndNotice", query3);
			// hashMap.put("yesFocusAndNews",query4);
			hashMap.put("yesFocusAndNotice", query5);
			// List<Map<String, Object>> query = newsDao.query(w1);
			// HashMap<String, List<Map<String, Object>>> nofocusAndNews = new
			// HashMap<String, List<Map<String, Object>>>();
			// HashMap<String, List<Map<String, Object>>> nofocusAndNotice = new
			// HashMap<String, List<Map<String, Object>>>();
			// HashMap<String, List<Map<String, Object>>> yesFocusAndNews = new
			// HashMap<String, List<Map<String, Object>>>();
			// HashMap<String, List<Map<String, Object>>> yesFocusAndNotice =
			// new HashMap<String, List<Map<String, Object>>>();
			return MessageUtil.success(hashMap, "MSG1", 200);
		} catch (Exception e) {
			return MessageUtil.error("MSG6", 500);
		}
	}

	@RequestMapping(params = "p=orderNotice")
	
	public @ResponseBody Map<String, Object> orderNotice(HttpSession session,
			@RequestBody JSONObject Body) throws Exception {
		try {
			OrderNotice orderNotice = new OrderNotice();
			List<OrderNotice> query = newsDao.query(orderNotice);
			OrderNotice orderNotice2 = query.get(0);
			HashMap<String, OrderNotice> hashMap = new HashMap<String, OrderNotice>();
			hashMap.put("model", orderNotice2);
			return MessageUtil.success(hashMap, "MSG1", 200);
		} catch (Exception e) {
			return MessageUtil.error("MSG6", 500);
		}
	}

	@RequestMapping(params = "p=saveUserOrder")
	public @ResponseBody Map<String, Object> saveOrderUser(
			@RequestBody JSONObject Body, HttpSession session) {
		try {
			int i = 0;
			JSONObject fromObject = JSONObject.fromObject(Body);
			String orderTimes = (String) fromObject.get("orderTime");
			String tokenID = (String) fromObject.get("tokenID");
			//String police = (String) fromObject.get("police");
			//String policeID = (String) fromObject.get("policeID");
			String company = (String) fromObject.get("police");
			String companyID = (String) fromObject.get("policeID");
			
			
			UserOrder orderUser = new UserOrder();
			Member user = userService.getRedisAPPUser(tokenID, Member.class);
			String dateString = DateUtil.getDateString();

			try {
				i = compare_date(dateString, orderTimes);
			} catch (Exception e) {
				return MessageUtil.error("MSG110", 110);
			}
		
			UserOrder userOrder = new UserOrder();
			userOrder.setOrderTime(orderTimes);
			userOrder.setUserName(user.getUserName());
			;
/*			List<UserOrder> query = newDao.query(userOrder);
*/
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

			// SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");

			Date dt = df.parse(dateString);
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(dt);
			// rightNow.add(Calendar.YEAR,-1);//日期减1年
			// rightNow.add(Calendar.MONTH,3);//日期加3个月
			rightNow.add(Calendar.DAY_OF_YEAR, 9);// 日期加9天
/*			Date dt1 = rightNow.getTime();
*//*			String reStr = df.format(dt1);
*/			// System.out.println(reStr);

			String w = "delete from gms_userorder where   userid='"
					+ user.getUserID() + "'";
			newDao.jdbcTemplate.execute(w);
			orderUser.setRealName(user.getRealName());
			orderUser.setRealName(user.getRealName());
			orderUser.setOrderID(UUIDUtil.get32UUID());
			orderUser.setUserID(user.getUserID());
			orderUser.setUserName(user.getUserName());
			orderUser.setStatus("noReservation");
			String date = DateUtil.getDateString();
			orderUser.setCreateTime(date);
			orderUser.setUpdateTime(date);
			orderUser.setPhone(user.getTelephone());
			orderUser.setOrderTime(orderTimes);
			orderUser.setPolice(company);
			orderUser.setPoliceID(companyID);
			String wordes = null;
			if(fromObject.containsKey("wordes") ){
				wordes = (String) fromObject.get("wordes");
			}
			orderUser.setWordes(wordes);
			newDao.insert(orderUser);

			// SQLUtil.getInsertSQL(orderUser);
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtil.error(6, 6);
		}
		return MessageUtil.success(1, 1);
	}

	private int compare_date(String DATE1, String DATE2) throws ParseException {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		Date dt1 = null;
		Date dt2 = null;

		dt1 = df.parse(DATE1);

		dt2 = df.parse(DATE2);

		Calendar aCalendar = Calendar.getInstance();

		aCalendar.setTime(dt1);

		int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);

		aCalendar.setTime(dt2);

		int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);

		return day2 - day1;
	}

	@RequestMapping(params = "p=findPolice")
	public @ResponseBody Map<String, Object> findPolice(HttpSession session,
			@RequestBody JSONObject Body) throws Exception {
		try {
			 Police police = new Police();
			 List<Police> query = newsDao.query(police);
			//SYS_Company company = new SYS_Company();
			//List<SYS_Company> query = newsDao.query(company);
			HashMap<String, List<Police>> hashMap = new HashMap<String, List<Police>>();
			hashMap.put("model", query);
			return MessageUtil.success(hashMap, "MSG1", 200);
		} catch (Exception e) {
			return MessageUtil.error("MSG6", 500);
		}
	}
	@RequestMapping(params = "p=findOrder")
	public @ResponseBody Map<String, Object> findOrder(HttpSession session,
			@RequestBody JSONObject Body) throws Exception {
		JSONObject fromObject = JSONObject.fromObject(Body);
		String tokenID = fromObject.getString("tokenID");
		Member user = userService.getRedisAPPUser(tokenID, Member.class);

		try {
			UserOrder userOrder = new UserOrder();
			userOrder.setUserID(user.getUserID());
			List<UserOrder> query2 = newsDao.query(userOrder);
			if (query2.size() == 0) {
				HashMap<String, UserOrder> hashMap = new HashMap<String, UserOrder>();
				return MessageUtil.success(hashMap, "MSG1", 200);
			}
			UserOrder userOrder2 = query2.get(0);
			HashMap<String, UserOrder> hashMap = new HashMap<String, UserOrder>();
			hashMap.put("model", userOrder2);
			return MessageUtil.success(hashMap, "MSG1", 200);
		} catch (Exception e) {
			return MessageUtil.error("MSG6", 500);
		}
	}
	@RequestMapping(params = "p=editInformation")
	public @ResponseBody Map<String, Object> editInformation(HttpSession session,
			@RequestBody JSONObject Body) throws Exception {
		try {
			Map jsonMember = (Map)JSONObject.fromObject(Body);
			 Member member = new Member();
			// Member member =(Member)JSONObject.toBean(Body);
			//String IDCard = fromObject.getString("IDCard");
		//	String realName = fromObject.getString("realName");
			;
			if(jsonMember.containsKey("IDCard")){
				member.setIdCard((String)jsonMember.get("IDCard"));
			}
			if(jsonMember.containsKey("realName")){
				member.setRealName((String)jsonMember.get("realName"));
			}
			if(jsonMember.containsKey("sex")){
				member.setSex((String)jsonMember.get("sex"));
			}
		
			
			/*if(IDCard!=null&&IDCard!=""){
				
	        }
			if(realName!=null&&realName!=""){
				member.setSex(realName);
	        }*/
			
		String tokenID =(String)jsonMember.get("tokenID");
		    Member user = userService.getRedisAPPUser(tokenID, Member.class);
			 //Police police = new Police();
			 //List<Police> query = newsDao.query(police);
			//SYS_Company company = new SYS_Company();
			//List<SYS_Company> query = newsDao.query(company);
		     member.setUserID(user.getUserID());
		     newsDao.update(member);
			return MessageUtil.success("MSG1", 200);
		} catch (Exception e) {
			return MessageUtil.error("MSG6", 500);
		}
	}

}
