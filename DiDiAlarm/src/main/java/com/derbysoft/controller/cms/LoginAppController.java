package com.derbysoft.controller.cms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.derbysoft.controller.service.BaseDaoController;
import com.derbysoft.dao.cms.NewsDao;
import com.derbysoft.dao.sys.UserDao;
import com.derbysoft.entity.cms.Member;
import com.derbysoft.entity.sys.SYS_Area;
import com.derbysoft.entity.sys.SYS_City;
import com.derbysoft.entity.sys.SYS_Province;
import com.derbysoft.entity.sys.SYS_User;
import com.derbysoft.jms.activemq.JpushSender;
import com.derbysoft.redis.service.RedisService;
import com.derbysoft.util.SmsRegsiterUtil;

import dy.hrtworkframe.db.SQLUtil;
import dy.hrtworkframe.exception.CustomerException;
import dy.hrtworkframe.util.CheckUtil;
import dy.hrtworkframe.util.DateUtil;
import dy.hrtworkframe.util.MD5;
import dy.hrtworkframe.util.MessageUtil;
import dy.hrtworkframe.util.Sha1Util;
import dy.hrtworkframe.util.UUIDUtil;

@Transactional
@Controller
@RequestMapping("loginApp.do")
public class LoginAppController extends BaseDaoController {

	private static String TOKENMORE_POLICE = "TOKENMORE_POLICE";// 抢登
	private static String TOKENMORE_PEOPLE = "TOKENMORE_PEOPLE";// 抢登
	@Resource(name = "newsDao")
	private NewsDao newsDao;

	@Autowired
	public UserDao userDao;
	@Autowired
	private JpushSender jpushSender;

	@Autowired
	private SmsRegsiterUtil smsRegsiterUtil;
	@Autowired
	private RedisService redisService;

	private static String TOKENID = "TOKENID:";
	//private List<Map<String, Object>> query;

	public static void main(String[] args) {
		// if(2>3){
		// System.out.println(1);
		// }
		System.out.println();
		System.out.println(DigestUtils
				.md5Hex("6820761a62f746e9a87cb81f1668c612"));
		System.out.println(DigestUtils
				.md5Hex("e3552a3d10f7d4c10b56b37176a91de2"));
		System.out.println("123123abc@"
				.matches("(?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[_]+$).{6,20}"));
	}

	/*
	 * 群众端app的登录方法 没有验证码
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(params = "p=peopleLoginApp")
	public @ResponseBody Map<String, Object> loginValidation(
			@RequestBody JSONObject Body) {
		JSONObject fromObject = Body;
		String password = (String) fromObject.get("userPassword");
		String userName = (String) fromObject.get("userName");
		String tokenID = (String) fromObject.get("tokenID");
		String alias = (String) fromObject.get("alias");
		String tag = (String) fromObject.get("tag");
		//Map<String, Object> map = new HashMap<String, Object>();
		try {
			
			if (CheckUtil.isNullStr(alias)||CheckUtil.isNullStr(tokenID)) {
				return MessageUtil.error("MSG28", 428);
			}
			if (!password.matches("(?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[_]+$).{6,20}")) {
				return MessageUtil.error("MSG14", 414);
			}
			if (password.matches("(?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[_]+$).{6,20}")) {
				if (!password.matches("[\\w]+")) {
					return MessageUtil.error("MSG14", 414);
				}
			}
			String w1 = " 1=1 and telephone = ?  ";
			List<Object> args1 = new ArrayList<Object>();
			args1.add(userName);
			String sql1 = SQLUtil.getQuerySQL(Member.class) + " where " + w1;
			if (newsDao.query(Member.class, sql1, args1.toArray())
					.size() <= 0) {
				return MessageUtil.error("MSG19", 419);
			}

			if (userService.getTokenIDByTokenID(TOKENID
					+ DigestUtils.md5Hex(tokenID)) == null) {
				userService.userTokenIDToRedis(
						TOKENID + DigestUtils.md5Hex(tokenID), tokenID);
			}
			// 密码错误Sha1Util.getSha1(MD5.md5(user.getUserPassword()))
			Member user = userDao.query(
					Member.class,
					"select * from cms_user where telePhone = '" + userName
							+ "' ;").get(0);
			String userPassword = user.getUserPassword();
			if (!userPassword.equals(Sha1Util.getSha1(MD5.md5(password)))) {
				return MessageUtil.error("MSG19", 419);
			}
			if (!DigestUtils.md5Hex(tokenID).equals(user.getTokenID())) {
				// 这里需要推送给当初安装注册这个帐号的设备
				Map<String,Object> m = new HashMap<String,Object>();
				m.put("hasmember", user);
				m.put("msg", "您的账号在另一地点登录，您已被迫下线");
				m.put("styleNum", "6");
				m.put("msgCode", "430");
				userService.delRedisAPPUser(user);
				user.setTokenID(DigestUtils.md5Hex(tokenID));
				jpushSender.send(m, TOKENMORE_PEOPLE);
			}
			if ("".equals(alias)) {
				return MessageUtil.error("MSG20", 420);
			}
			if (!"tag_people".equals(tag)) {
				return MessageUtil.error("MSG20", 420);
			}
			String aliass = "update cms_user set alias = null where alias = '"+alias+"' ;";
			userDao.jdbcTemplate.execute(aliass);
//			userDao.update(aliass);
			user.setAlias(alias);
			user.setTag(tag);
			userDao.update(user);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("hasmember", user);
			// session
			userService.setRedisAPPUser(user, tokenID);
			userService.setUsersAddress(user);
			// session.setAttribute(Const.SESSION_HAS_MEMBER, user);
			return MessageUtil.success(result, "MSG1", 200);
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtil.error("MSG6", 500);
		}
	}

	@RequestMapping(params = "p=loginOut")
	public @ResponseBody Map<String, Object> loginOutApp(
			@RequestBody JSONObject Body) {
		try {
			JSONObject fromObject = Body;
			String tokenID = (String) fromObject.get("tokenID");
			Object tag = fromObject.get("tag");
			if(tag==null){
				userService.delRedisAPPUser(tokenID);
				return MessageUtil.success("MSG1", 200);
			}else{
				SYS_User user = userService.getRedisAPPUser(
						DigestUtils.md5Hex(tokenID), SYS_User.class);
				userService.delRedisAPPUser(DigestUtils.md5Hex(tokenID));
				userService.delUsersAddress(user);
				return MessageUtil.success("MSG1", 200);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return MessageUtil.error("MSG6", 500);
		}
	}

	/*
	 * 警员端app的登录方法 没有验证码
	 */
	@RequestMapping(params = "p=policeLoginApp")
	public @ResponseBody Map<String, Object> policeLoginApp(
			@RequestBody JSONObject Body) {
		JSONObject fromObject = Body;
		String password = (String) fromObject.get("userPassword");
		String userName = (String) fromObject.get("userName");
		String tokenID = (String) fromObject.get("tokenID");
		String alias = (String) fromObject.get("alias");
		String tag = (String) fromObject.get("tag");
		//Map<String, Object> map = new HashMap<String, Object>();
		//PageData pdapp = this.getPageAppPData();
		try {
			if (alias == null || tokenID == null) {
				return MessageUtil.error("MSG28", 428);
			}
			if (!password.matches("(?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[_]+$).{6,20}")) {
				return MessageUtil.error("MSG14", 414);
			}
			if (password.matches("(?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[_]+$).{6,20}")) {
				if (!password.matches("[\\w]+")) {
					return MessageUtil.error("MSG14", 414);
				}
			}
			String w = getSql("userName");
			List<String> args = new ArrayList<String>();
			args.add(userName);
			w = SQLUtil.getQuerySQL(SYS_User.class) + " where " + w;
			if (userDao.query(SYS_User.class, w, args.toArray()).size() <= 0) {
				return MessageUtil.error("MSG19", 419);
			}

			if (userService.getTokenIDByTokenID(TOKENID
					+ DigestUtils.md5Hex(tokenID)) == null) {
				userService.userTokenIDToRedis(
						TOKENID + DigestUtils.md5Hex(tokenID), tokenID);
			}
			// 密码错误Sha1Util.getSha1(MD5.md5(user.getUserPassword()))
			SYS_User user = userDao.query(
					SYS_User.class,
					"select * from sys_user where userName = '" + userName
							+ "' ;").get(0);
			String userPassword = user.getPassword();
			if (!Sha1Util.getSha1(MD5.md5(password)).equals(userPassword)) {
				return MessageUtil.error("MSG19", 419);
			}
			if (!DigestUtils.md5Hex(tokenID).equals(user.getTokenID())) {
				// 这里需要推送给当初安装注册这个帐号的设备
				Map<String ,Object> m = new HashMap<String ,Object>();
				m.put("hasmember", user);
				m.put("msg", "您的账号在另一地点登录，您已被迫下线");
				m.put("styleNum", "6");
				m.put("msgCode", "430");
				userService.delRedisAPPUser(user);
				user.setTokenID(DigestUtils.md5Hex(tokenID));
				jpushSender.send(m, TOKENMORE_POLICE);
				//离岗
				userService.delUsersAddress(user);
			}
			if (!"tag_police".equals(tag)) {
				return MessageUtil.error("MSG20", 420);
			}
			String aliass = "update sys_user set alias = null where alias = '"+alias+"' ;";
//			String tokens = "update sys_user set tokenID = null where tokenID = '"+DigestUtils.md5Hex(tokenID)+"' ;";
			userDao.jdbcTemplate.execute(aliass);
//			userDao.update(aliass);
			user.setTokenID(DigestUtils.md5Hex(tokenID));
			user.setAlias(alias);
			user.setTag(tag);
			user.setIsWorking("noWorking");
			userDao.update(user);
			Map<String, Object> result = new HashMap<String, Object>();
			SYS_User user2 = userService.queryUserByUser(user.getUserName());
			if(user2==null){
				result.put("isWorking", "noWorking");
			}else{
				result.put("isWorking", "okWorking");
			}
			result.put("hasmember", user);
			userService.setRedisAPPUser(user, user.getTokenID());
//			 userService.setUsersAddress(user);
			// session.setAttribute(Const.SESSION_HAS_MEMBER, user);
			return MessageUtil.success(result, "MSG1", 200);
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtil.error("MSG6", 500);
		}
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
	@RequestMapping(params = "p=singLogin")
	public @ResponseBody Map<String, Object> signLogin(
			@RequestBody JSONObject Body) {
		try {

			Member user = (Member) JSONObject.toBean(Body, Member.class);
			Map<String, Object> entityByApp = checkEntityByApp(user);
			if (entityByApp != null) {
				return entityByApp;
			}
			// String w = " 1=1 and username = ?  ";
			// List<Object> args = new ArrayList<Object>();
			// args.add(user.getUserName());
			// String sql = SQLUtil.getQuerySQL(Member.class) + " where " + w;
			String w1 = " 1=1 and telephone = ?  ";
			List<Object> args1 = new ArrayList<Object>();
			args1.add(user.getTelephone());
			String sql1 = SQLUtil.getQuerySQL(Member.class) + " where " + w1;
			// 这里防止用户跳过手机验证直接注册,完善个人信息或者重复注册
			if (!user.getUserPassword().matches(
					"(?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[_]+$).{6,20}")) {
				return MessageUtil.error("MSG14", 414);
			}
			if (user.getUserPassword().matches(
					"(?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[_]+$).{6,20}")) {
				if (!user.getUserPassword().matches("[\\w]+")) {
					return MessageUtil.error("MSG14", 414);
				}
			}
			if (user.getTelephone() != null
					&& newsDao.query(Member.class, sql1, args1.toArray())
							.size() > 0) {
				return MessageUtil.error("MSG15", 415);
			}
			if (user.getAddress().length() > 50) {
				return MessageUtil.error("MSG105", 105);
			}
			// if (newsDao.query(Member.class, sql, args.toArray()) != null) {
			// return MessageUtil.error("MSG16", 409);
			// }
			pd = getPageData();
			user.setJoined(DateUtil.getDateTimeString());
			user.setUserID(UUIDUtil.get32UUID());
			user.setUserName(user.getTelephone());
			user.setTokenID(DigestUtils.md5Hex(user.getTokenID()));
			// 密码加密
			user.setUserPassword(Sha1Util.getSha1(MD5.md5(user
					.getUserPassword())));
			List<SYS_Area> query2 = userDao.query(
					SYS_Area.class,
					"select * from sys_area where areaID = '"
							+ user.getAreaID() + "' ;");
			List<SYS_City> query3 = userDao.query(SYS_City.class,
					"select * from SYS_City where cityID = '"
							+ query2.get(0).getCityID() + "' ;");
			List<SYS_Province> query4 = userDao.query(SYS_Province.class,
					"select * from SYS_Province where provinceID = '"
							+ query3.get(0).getProvinceID() + "' ;");
			user.setArea(query2.get(0).getArea());
			user.setAreaID(user.getAreaID());
			user.setCity(query3.get(0).getCity());
			user.setCityID(query3.get(0).getCityID());
			user.setProvince(query4.get(0).getProvince());
			user.setProvinceID(query4.get(0).getProvinceID());
//			user.setAddressName(user.getProvince()+user.getCity()+user.getArea()+user.getAddressName());
			baseDaoImpl.insert(user);
			return MessageUtil.success(1, 1, 1, 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return MessageUtil.error("MSG6", 500);
		}
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
			@RequestBody JSONObject Body) {

		// 验证手机唯一性,并且限制注册人身份(这里为群众)
		try {
			String mobile = Body.get("telephone").toString();
			Object findPwd = Body.get("findPwd");
			if (!mobile.matches("^(13|14|15|17|18)\\d{9}$")) {
				return MessageUtil.error("MSG2", 402);
			}
			String w = " 1=1 and telePhone = ?  ";
			List<Object> args = new ArrayList<Object>();
			args.add(mobile);
			String sql = SQLUtil.getQuerySQL(Member.class) + " where " + w;
			// String sql = "select * from cms_user where 1=1 and telePhone ='"
			// +
			// mobile
			// + "'";// +" and RoleID = '' ";
			if (findPwd == null || !findPwd.toString().equals("findPwd")) {
				if (newsDao.query(Member.class, sql, args.toArray()).size() >= 1) {
					return MessageUtil.error("MSG15", 415);
				}
				// 这里是获取手机验证码
				boolean boo = smsRegsiterUtil.sendResgsiterCode(mobile);
				if (boo) {
					return MessageUtil.success("MSG1", 200);
				} else {
					return MessageUtil.error("MSG17", 417);
				}
			}
			if (findPwd != null && findPwd.toString().equals("findPwd")) {
				if (newsDao.query(Member.class, sql, args.toArray()).size() < 1) {
					return MessageUtil.error("MSG2", 402);
				}
				// 这里是获取手机验证码
				boolean boo = smsRegsiterUtil.sendResgsiterCode(mobile);
				if (boo) {
					return MessageUtil.success("MSG1", 200);
				} else {
					return MessageUtil.error("MSG17", 417);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtil.error("MSG17", 417);
		}
		return MessageUtil.error("MSG17", 417);
	}

	// public static void main(String[] args) {
	// String regRode= "9999991";
	//
	// System.out.println(regRode.matches("^\\d{6}$"));
	// }
	// 验证验证码
	@RequestMapping(params = "p=register")
	public @ResponseBody Map<String, Object> register(
			@RequestBody JSONObject Body) {
		// TODO这里是手机验证码已经填写,验证手机验证码的正确
		try {
			JSONObject fromObject = Body;
			String phone = (String) fromObject.get("telephone");
			String regRode = (String) fromObject.get("regCode");
			String password = (String) fromObject.get("userPassword");

			if (!phone.matches("^(13|14|15|17|18)\\d{9}$")) {
				return MessageUtil.error("MSG2", 402);
			}
			if (!regRode.matches("^\\d{6}$")) {
				return MessageUtil.error("MSG4", 414);
			}
			if (!password.matches("(?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[_]+$).{6,20}")) {
				return MessageUtil.error("MSG14", 414);
			}
			if (password.matches("(?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[_]+$).{6,20}")) {
				if (!password.matches("[\\w]+")) {
					return MessageUtil.error("MSG14", 414);
				}
			}
			if (smsRegsiterUtil.queryCodeByToken(phone) != null) {
				if (smsRegsiterUtil.queryCodeByToken(phone).getRegCode()
						.equals(regRode)) {
					Map<String, Object> result = new HashMap<String, Object>();
					result.put("phone", phone);
					result.put("userPassword", password);
					return MessageUtil.success(result, "MSG1", 200);
				} else {
					return MessageUtil.error("MSG4", 404);
				}
			} else {
				return MessageUtil.error("MSG3", 403);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return MessageUtil.error("MSG6", 500);
		}
		// JSONObject fromObject = Body;
		// String phone = (String) fromObject.get("telephone");
		// String regRode = (String) fromObject.get("regRode");
		// String password = (String) fromObject.get("userPassword");
		// Map<String, Object> result = new HashMap<String, Object>();
		// result.put("phone", phone);
		// result.put("userPassword", password);
		// return MessageUtil.success(result, "MSG1", 200);
	}

	// 警员端忘记密码
	// @RequestMapping(params = "p=forgotPasswordToPhone")
	// public @ResponseBody Map<String, Object> forgotPasswordToPhone(
	// @RequestParam("mobile") String mobile) {
	// // 验证手机唯一性,并且限制注册人身份
	// if (!mobile.matches("^(13|14|15|17|18)\\d{9}$")) {
	// return MessageUtil.error();
	// }
	// String sql = "select * from SYS_User where 1=1 and Phone ='" + mobile
	// + "'";// +" and RoleID = '' ";
	// List<SYS_User> l_user = newsDao.query(SYS_User.class, sql);
	// if (l_user.size() <= 0) {
	// return MessageUtil.error();
	// }
	// // 这里是获取密码
	// smsRegsiterUtil
	// .sendPasswordByPhone(l_user.get(0).getPassword(), mobile);
	// return MessageUtil.success();
	// }

	// 群众端忘记密码
	@RequestMapping(params = "p=forgotPasswordByCode")
	public @ResponseBody Map<String, Object> forgotPasswordByCode(
			@RequestBody JSONObject Body) {
		// 验证手机唯一性,并且限制注册人身份(这里为群众),手机号规范性真假验证
		// 合法原则 1.以“13,14,15,17,18”开头 2.11位 3.纯数字
		try {
			String mobile = Body.get("telephone").toString();
			if (!mobile.matches("^(13|14|15|17|18)\\d{9}$")) {
				return MessageUtil.error("MSG2", 402);
			}
			String w = " 1=1 and telephone = ?  ";
			List<Object> args = new ArrayList<Object>();
			args.add(mobile);
			String sql = SQLUtil.getQuerySQL(Member.class) + " where " + w;
			// String sql = "select * from cms_user where 1=1 and telephone ='"
			// +
			// mobile
			// + "'";// +" and RoleID = '' ";

			if (newsDao.query(Member.class, sql, args.toArray()).size() <= 0) {
				return MessageUtil.error("MSG2", 402);
			}
			// 这里是获取手机验证码
			boolean boo = smsRegsiterUtil.sendResgsiterCode(mobile);
			if (boo) {
				return MessageUtil.success("MSG1", 200);
			} else {
				return MessageUtil.error("MSG17", 417);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return MessageUtil.error("MSG6", 500);
		}
	}

	// 群众端忘记密码后重置密码
	@RequestMapping(params = "p=forgotPasswordToNew")
	public @ResponseBody Map<String, Object> forgotPasswordToNew(
			@RequestBody JSONObject Body) {
		// 验证手机唯一性,并且限制注册人身份(这里为群众),手机号规范性真假验证
		// 合法原则 1.以“13,14,15,17,18”开头 2.11位 3.纯数字
		try {
			String mobile = Body.get("telephone").toString();
			String regCode = Body.get("regCode").toString();
			String newPassword = Body.get("newPassword").toString();
			String continuePassword = Body.get("continuePassword").toString();
			if (!mobile.matches("^(13|14|15|17|18)\\d{9}$")) {
				return MessageUtil.error("MSG2", 402);
			}
			if (!newPassword
					.matches("(?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[_]+$).{6,20}")
					|| !continuePassword
							.matches("(?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[_]+$).{6,20}")) {
				return MessageUtil.error("MSG14", 414);
			}
			if (newPassword
					.matches("(?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[_]+$).{6,20}")) {
				if (!newPassword.matches("[\\w]+")) {
					return MessageUtil.error("MSG14", 414);
				}
			}
			if (!newPassword.equals(continuePassword)) {
				return MessageUtil.error("MSG21", 421);
			}
			String w = " 1=1 and telephone = ?  ";
			// String sql = "select * from cms_user where 1=1 and telephone ='"
			// +
			// mobile
			// + "'";// +" and RoleID = '' ";
			List<Object> args = new ArrayList<Object>();
			args.add(mobile);
			String sql = SQLUtil.getQuerySQL(Member.class) + " where " + w;
			List<Member> query = newsDao.query(Member.class, sql,
					args.toArray());
			if (newsDao.query(Member.class, sql, args.toArray()).size() <= 0) {
				return MessageUtil.error("MSG18", 418);
			}
			if (smsRegsiterUtil.queryCodeByToken(mobile) != null) {
				if (regCode != null
						&& regCode.equals(smsRegsiterUtil.queryCodeByToken(
								mobile).getRegCode())) {
					Member member = query.get(0);
					member.setUserPassword(Sha1Util.getSha1(MD5
							.md5(newPassword)));
					newsDao.update(member);
					return MessageUtil.success("MSG1", 200);
				} else {
					return MessageUtil.error("MSG4", 404);
				}
			} else {
				return MessageUtil.error("MSG3", 403);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return MessageUtil.error("MSG3", 403);
		}
	}

	// 群众端个人中心重置密码
	@RequestMapping(params = "reSetPassword")
	public @ResponseBody Map<String, Object> reSetPassword(
			@RequestParam("oldPassword") String oldPassword,
			@RequestParam("newPassword") String newPassword,
			@RequestParam("tokenID") String tokenID) {
		try {
			Member user = userService.getRedisAPPUser(tokenID, Member.class);
			// String sql = "select * from cms_user where 1=1 and telephone ='"
			// +
			// user.getTelephone()
			// + "'";// +" and RoleID = '' ";
			String w = " 1=1 and telephone = ?  ";
			List<Object> args = new ArrayList<Object>();
			args.add(user.getTelephone());
			String sql = SQLUtil.getQuerySQL(Member.class) + " where " + w;
			if (newsDao.query(Member.class, sql, args.toArray()).size() <= 0) {
				throw new CustomerException("用户不存在！");
			} else {
				if (((Member) newsDao.query(Member.class, sql, args.toArray())
						.get(0)).getUserPassword() == Sha1Util.getSha1(MD5
						.md5(oldPassword))) {
					user.setUserPassword(Sha1Util.getSha1(MD5.md5(newPassword)));
					newsDao.update(user);
					return MessageUtil.success();
				} else {
					throw new CustomerException("密码错误,请重新输入旧密码！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtil.error("MSG6", 500);
		}
	}

	@SuppressWarnings("all")
	@RequestMapping(params = "p=findCityArea")
	public @ResponseBody Map<String, Object> findCityArea() throws Exception {
		String string = redisService.get("CityArea");
		Long a = System.currentTimeMillis();
		if (null == string) {
			List<SYS_Province> province = userDao.query(SYS_Province.class);
			try {
				int provinceNum = province.size();
				int i = 0;
				for (; provinceNum > i; i++) {
					SYS_Province sys_Province = province.get(i);
					SYS_City sys_City = new SYS_City();
					sys_City.setProvinceID(sys_Province.getProvinceID());
					List<SYS_City> city = userDao.query(sys_City);
					int cityNum = city.size();
					int j = 0;
					for (; cityNum > j; j++) {
						SYS_City sys_City2 = city.get(j);
						SYS_Area sys_Area = new SYS_Area();
						sys_Area.setCityID(sys_City2.getCityID());
						List<SYS_Area> query = userDao.query(sys_Area);
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
}