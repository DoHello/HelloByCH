package com.derbysoft.controller.cms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.derbysoft.controller.service.BaseDaoController;
import com.derbysoft.dao.sys.DicDao;
import com.derbysoft.entity.AlarmCallTime;
import com.derbysoft.entity.cms.GmsAlarmCall;
import com.derbysoft.entity.cms.GmsAlarmCheack;
import com.derbysoft.entity.cms.GmsAlarmReceive;
import com.derbysoft.entity.cms.Member;
import com.derbysoft.entity.sys.SYS_Dic;
import com.derbysoft.entity.sys.SYS_User;
import com.derbysoft.jms.activemq.JpushSender;

import dy.hrtworkframe.annotation.Temporary;
import dy.hrtworkframe.db.SQLUtil;
import dy.hrtworkframe.util.CheckUtil;
import dy.hrtworkframe.util.Const;
import dy.hrtworkframe.util.DateUtil;
import dy.hrtworkframe.util.FileUpload;
import dy.hrtworkframe.util.MapSpaceUtil;
import dy.hrtworkframe.util.MessageUtil;
import dy.hrtworkframe.util.PageData;
import dy.hrtworkframe.util.UUIDUtil;

@Transactional
@Controller("Alarm")
@RequestMapping("Alarm.do")
@SuppressWarnings("all")
public class AlarmController extends BaseDaoController {

	
	private static String ALL = "ALL";// 所有平台
	
	private static String ANDROID = "ANDROID";// 安卓平台
	private static String IOS = "IOS";// 苹果平台
	private static String POLICE = "AllPLICE";// 发送给警察
	private static String POLICESIGN = "POLICE";// 发送给单个警察
	private static String PEOPLE = "AllPEOPLE";// 发送给群众
	private static String PEOPLESIGN = "PEOPLE";// 发送给单个群众
	private static String REFUSE = "REFUSE";// 拒绝接警
	private static String Mysft = "yyyy-MM-dd HH:mm:ss";// 时间格式
	@Autowired
	private JpushSender jpushSender;
	@Resource(name = "systemDicDao")
	private DicDao systemDicDao;

	// ===============群众端报警===========================
	/**
	 * 判断距离是否在指定区域
	 * @Title: callByCheck 
	 * @Description: TODO
	 * @param alarmCall
	 * @param session
	 * @return
	 * @return: Map<String,Object>
	 */
	@RequestMapping(params = "p=callByCheck")
	public @ResponseBody Map<String, Object> callByCheck(
			@ModelAttribute GmsAlarmCall alarmCall, HttpSession session) {
		PageData pdapp = getPageAppMData();
		;
		try {
			Member user = (Member) session
					.getAttribute(Const.SESSION_HAS_MEMBER);
			if (getSpace(alarmCall) == false) {
				return MessageUtil.error("MSG11", 411);
			}
			if (user == null || alarmCall.getLongitude() == null
					|| alarmCall.getLatitude() == null) {
				return MessageUtil.error("MSG7", 417);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtil.error("MSG6", 500);
		}
		return MessageUtil.success(pdapp, 1, 1);
	}

	/**
	 * 一键报警
	 * 
	 * @Title: resoures
	 * @Description: TODO
	 * @param session
	 * @return
	 * @throws Exception
	 * @return: ModelAndView
	 */
/*	public static void main(String[] args) {
		// System.out.println(DateUtil.getDateTimeString("yyyyMMddhhmmss")
		// + "88888888888".substring(8, 12));
		// System.out.println(null.length());
		int age = Integer.parseInt(DateUtil.getDateTimeString("yyyy"))-Integer.parseInt("3212811992".substring(6, 10));
		System.out.println(age);
		List l = new ArrayList();
		System.out.println(l.size() + l.toString());
		System.out.println(DigestUtils
				.md5Hex("6820761a62f746e9a87cb81f1668c612"));
	}*/

	@RequestMapping(params = "p=callByOneKey")
	public @ResponseBody Map<String, Object> callByOneKey(
			HttpServletRequest request, @RequestBody JSONObject Body,
			HttpSession session) {
		try {
			
			GmsAlarmCall alarmCall = (GmsAlarmCall) JSONObject.toBean(Body,
					GmsAlarmCall.class);
			// Member user = (Member)
			// session.getAttribute(Const.SESSION_HAS_MEMBER);
			Member user = userService.getRedisAPPUser(alarmCall.getTokenID(),
					Member.class);
			// if(getSpace(user)>20){
			// return MessageUtil.error("MSG7", 401);
			// }
			PageData pdapp = getPageAppMData();
			;
			if (user == null || alarmCall.getLongitude() == null
					|| alarmCall.getLatitude() == null) {
				return MessageUtil.error("MSG7", 407);
			}
			if (getSpace(alarmCall) == false) {
				return MessageUtil.error("MSG11", 411);
			}
			// 贵-GYHXYYYYMMDD(000~999)
			// if(!userService.setRedisCheackNum()){
			// return MessageUtil.error("MSG6", 500);
			// }
			// alarmCall.setAlarmID(DateUtil.getDateTimeString("YYYYMMDDhhmmss")
			// + user.getTelephone().substring(8, 11));
			alarmCall.setAlarmID("贵-GYHXY"
					+ DateUtil.getDateTimeString("yyyyMMdd")
					+ userService.setRedisCheackNum());
			alarmCall.setPhone(user.getTelephone());
			alarmCall.setUserID(user.getUserID());
			alarmCall.setUserName(user.getUserName());
			alarmCall.setName(user.getRealName());
			int age = Integer.parseInt(DateUtil.getDateTimeString("yyyy"))-Integer.parseInt(user.getIdCard().substring(6, 10));
			alarmCall.setAge(age+"");
			alarmCall.setSex(user.getSex());
			// 这里需要dic
			alarmCall.setAlarmLevel("fastAlarm");
			// 这里也需要dic
			alarmCall.setStatus("nodealing");
			// dic这里由页面传过来
			alarmCall.setMessageType("oneKeyAlarm");
			alarmCall.setMessageText("该用户可能急需帮助，但无法说明情况，采用了一键报警");
			alarmCall.setMessage("");
			alarmCall.setInputDate(DateUtil.getDateTimeString());
			// dic
			alarmCall.setIscheack("noCheack");
			// 这里需要返回一个导航状态navigation,按钮状态callstatus
			pdapp.put("alarmID", alarmCall.getAlarmID());
			pdapp.put("navigation", "noAttack");
			pdapp.put("callstatus", "waiting");
			request.setAttribute("call", alarmCall);
			// 发送给警察这里需要获取最近的警察位置
			baseDaoImpl.insert(alarmCall);
			List<SYS_User> list = getPolice(alarmCall);
			if(list==null||list.size()<1){
				return MessageUtil.error("MSG22", 422);
			}
			// 设置报警信息
			userService.setRedisAlarmCall(alarmCall, null);
			// 设置报警时间
			userService.setRedisAlarmCallTime(alarmCall, user);
			try {
				
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("who", "people");
				m.put("peopleCall", alarmCall);
				m.put("poliseFirst", list.get(0));
				m.put("userAlias", user);
				m.put("styleNum", "5");
				jpushSender.send(m, POLICESIGN);
			} catch (Exception e1) {
				e1.printStackTrace();
				return MessageUtil.error("MSG22", 422);
			}
			return MessageUtil.success(pdapp, 1, 1);
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtil.error("MSG6", 500);
		}
	}

	// 不由用户操作的中间层
	/**
	 * 用户报警后,警员端接警成功反馈
	 * 
	 * @Title: call4Revert
	 * @Description: TODO
	 * @param alarmCall
	 * @param session
	 * @return
	 * @return: Map<String,Object>
	 */
	@RequestMapping(params = "p=call4Revert")
	public @ResponseBody Map<String, Object> call4Revert(
			@ModelAttribute GmsAlarmCall alarmCall, HttpSession session) {
		Member user = (Member) session.getAttribute(Const.SESSION_HAS_MEMBER);
		PageData pdapp = getPageAppPData();
		if (user == null || alarmCall == null
				|| alarmCall.getLongitude() == null
				|| alarmCall.getLatitude() == null
				|| alarmCall.getStatus() != "failure"/* dic */) {
			return MessageUtil.error(8, 8);
		}
		if (getSpace(alarmCall) == false) {
			return MessageUtil.error("MSG11", 411);
		}
		// 这里也需要dic
		alarmCall.setStatus("nodealing");

		String w = getSql("alarmID");
		List<String> args = new ArrayList<String>();
		args.add(alarmCall.getAlarmID());
		w += SQLUtil.getQuerySQL(GmsAlarmCall.class);
		try {
			// 数据库查询
			if (baseDaoImpl.query(GmsAlarmCall.class, w, args.toArray()).size() <= 0) {
				return MessageUtil.error("MSG8", 408);
			}
			baseDaoImpl.update(alarmCall);
		} catch (Exception e) {
			return MessageUtil.error("MSG7", 407);
		}
		// 这里需要返回一个导航状态navigation,按钮状态callstatus,警员信息
		pdapp.put("alarmID", alarmCall.getAlarmID());
		pdapp.put("navigation", "noAttack");
		pdapp.put("callstatus", "waiting");

		return MessageUtil.success(pdapp, 1, 1);
	}

	/**
	 * 语音报警需要等待语音上传成功
	 * 
	 * @Title: callByVoice
	 * @Description: TODO
	 * @param alarmCall
	 * @param session
	 * @return
	 * @return: Map<String,Object>
	 */
	@RequestMapping(params = "p=callByVoice")
	public @ResponseBody Map<String, Object> callByVoice(
			@RequestBody JSONObject Body, HttpSession session) {
		try {
			GmsAlarmCall alarmCall = (GmsAlarmCall) JSONObject.toBean(Body,
					GmsAlarmCall.class);
			// Member user = (Member)
			// session.getAttribute(Const.SESSION_HAS_MEMBER);
			Member user = userService.getRedisAPPUser(alarmCall.getTokenID(),
					Member.class);
			PageData pdapp = getPageAppMData();
			;
			if (user == null || alarmCall.getLongitude() == null
					|| alarmCall.getLatitude() == null
					|| alarmCall.getMessage() == null) {
				return MessageUtil.error("MSG7", 407);
			}
			if (getSpace(alarmCall) == false) {
				return MessageUtil.error("MSG11", 411);
			}
			alarmCall.setAlarmID("贵-GYHXY"
					+ DateUtil.getDateTimeString("yyyyMMdd")
					+ userService.setRedisCheackNum());
			alarmCall.setPhone(user.getTelephone());
			alarmCall.setUserID(user.getUserID());
			alarmCall.setUserName(user.getUserName());
			alarmCall.setName(user.getRealName());
			int age = Integer.parseInt(DateUtil.getDateTimeString("yyyy"))-Integer.parseInt(user.getIdCard().substring(6, 10));
			alarmCall.setAge(age+"");
			alarmCall.setSex(user.getSex());
			// 这里需要dic
			alarmCall.setAlarmLevel("appAlarm");
			// 这里也需要dic
			alarmCall.setStatus("nodealing");
			// dic这里由页面传过来
			alarmCall.setMessageType("voiceAlarm");
			alarmCall.setInputDate(DateUtil.getDateTimeString());
			// dic
			alarmCall.setIscheack("noCheack");

			baseDaoImpl.insert(alarmCall);
			// 发送给警察这里需要获取最近的警察位置
			List<SYS_User> list = getPolice(alarmCall);
			if(list==null||list.size()<1){
				return MessageUtil.error("MSG22", 422);
			}
			// 设置报警信息
			userService.setRedisAlarmCall(alarmCall, null);
			// 设置报警时间
			userService.setRedisAlarmCallTime(alarmCall, user);
			// 这里需要返回一个导航状态navigation,按钮状态callstatus
			pdapp.put("alarmID", alarmCall.getAlarmID());
			pdapp.put("navigation", "noAttack");
			pdapp.put("callstatus", "waiting");
			// 发送给警察这里需要获取最近的警察位置
			try {
//				List<SYS_User> list = getPolice(alarmCall);
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("who", "people");
				m.put("peopleCall", alarmCall);
				m.put("poliseFirst", list.get(0));
				m.put("userAlias", user);
				m.put("styleNum", "5");
				jpushSender.send(m, POLICESIGN);
			} catch (Exception e1) {
				e1.printStackTrace();
				return MessageUtil.error("MSG22", 422);
			}
			return MessageUtil.success(pdapp, 1, 1);
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtil.error(6, 6);
		}
	}

	/**
	 * 文字报警
	 * 
	 * @Title: callByText
	 * @Description: TODO
	 * @param alarmCall
	 * @param session
	 * @return
	 * @return: Map<String,Object>
	 */
	@RequestMapping(params = "p=callByText")
	public @ResponseBody Map<String, Object> callByText(
			@RequestBody JSONObject Body, HttpSession session) {
		try {
			// @RequestBody JSONObject Body,
			GmsAlarmCall alarmCall = (GmsAlarmCall) JSONObject.toBean(Body,
					GmsAlarmCall.class);
			// Member user = (Member)
			// session.getAttribute(Const.SESSION_HAS_MEMBER);
			Member user = userService.getRedisAPPUser(alarmCall.getTokenID(),
					Member.class);
			PageData pdapp = getPageAppMData();
			;
			if (user == null || alarmCall.getLongitude() == null
					|| alarmCall.getLatitude() == null
					|| alarmCall.getMessageText() == null) {
				return MessageUtil.error("MSG7", 407);
			}
			if (getSpace(alarmCall) == false) {
				return MessageUtil.error("MSG11", 411);
			}
			if (alarmCall.getMessageText().length() < 1) {
				return MessageUtil.error("MSG23", 423);
			}
			alarmCall.setAlarmID("贵-GYHXY"
					+ DateUtil.getDateTimeString("yyyyMMdd")
					+ userService.setRedisCheackNum());
			alarmCall.setPhone(user.getTelephone());
			alarmCall.setUserID(user.getUserID());
			alarmCall.setUserName(user.getUserName());
			alarmCall.setName(user.getRealName());
			int age = Integer.parseInt(DateUtil.getDateTimeString("yyyy"))-Integer.parseInt(user.getIdCard().substring(6, 10));
			alarmCall.setAge(age+"");
			alarmCall.setSex(user.getSex());
			// 这里需要dic
			alarmCall.setAlarmLevel("appAlarm");
			// 这里也需要dic
			alarmCall.setStatus("nodealing");
			// dic这里由页面传过来
			alarmCall.setMessageType("textAlarm");
			alarmCall.setInputDate(DateUtil.getDateTimeString());
			// dic
			alarmCall.setIscheack("noCheack");
			baseDaoImpl.insert(alarmCall);
			// 发送给警察这里需要获取最近的警察位置
			List<SYS_User> list = getPolice(alarmCall);
			if(list==null||list.size()<1){
				return MessageUtil.error("MSG22", 422);
			}
			// 设置报警信息
			userService.setRedisAlarmCall(alarmCall, null);
			// 设置报警时间
			userService.setRedisAlarmCallTime(alarmCall, user);
			try {
				// 这里需要返回一个导航状态navigation,按钮状态callstatus
				pdapp.put("alarmID", alarmCall.getAlarmID());
				pdapp.put("navigation", "noAttack");
				pdapp.put("callstatus", "waiting");
//				List<SYS_User> list = getPolice(alarmCall);
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("who", "people");
				m.put("peopleCall", alarmCall);
				m.put("poliseFirst", list.get(0));
				m.put("userAlias", user);
				m.put("styleNum", "5");
				jpushSender.send(m, POLICESIGN);
			} catch (Exception e1) {
				e1.printStackTrace();
				return MessageUtil.error("MSG22", 422);
			}
			return MessageUtil.success(pdapp, 1, 1);
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtil.error("MSG6", 406);
		}
	}

	/**
	 * 通过图片报警
	 * 
	 * @Title: callByImg
	 * @Description: TODO
	 * @param alarmCall
	 * @param session
	 * @return
	 * @return: Map<String,Object>
	 */
	@RequestMapping(params = "p=callByImg")
	public @ResponseBody Map<String, Object> callByImg(
			@RequestBody JSONObject Body, HttpSession session) {
		try {
			// @RequestBody JSONObject Body,
			GmsAlarmCall alarmCall = (GmsAlarmCall) JSONObject.toBean(Body,
					GmsAlarmCall.class);
			// Member user = (Member)
			// session.getAttribute(Const.SESSION_HAS_MEMBER);
			Member user = userService.getRedisAPPUser(alarmCall.getTokenID(),
					Member.class);
			PageData pdapp = getPageAppMData();
			;
			if (user == null || alarmCall.getLongitude() == null
					|| alarmCall.getLatitude() == null
					|| alarmCall.getMessage() == null) {
				return MessageUtil.error("MSG7", 407);
			}
			if (getSpace(alarmCall) == false) {
				return MessageUtil.error("MSG11", 411);
			}
			alarmCall.setAlarmID("贵-GYHXY"
					+ DateUtil.getDateTimeString("yyyyMMdd")
					+ userService.setRedisCheackNum());
			alarmCall.setPhone(user.getTelephone());
			alarmCall.setUserID(user.getUserID());
			alarmCall.setUserName(user.getUserName());
			alarmCall.setName(user.getRealName());
			int age = Integer.parseInt(DateUtil.getDateTimeString("yyyy"))-Integer.parseInt(user.getIdCard().substring(6, 10));
			alarmCall.setAge(age+"");
			alarmCall.setSex(user.getSex());
			// 这里需要dic
			alarmCall.setAlarmLevel("appAlarm");
			// 这里也需要dic
			alarmCall.setStatus("nodealing");
			// dic这里由页面传过来
			alarmCall.setMessageType("imgAlarm");
			alarmCall.setInputDate(DateUtil.getDateTimeString());
			// dic
			alarmCall.setIscheack("noCheack");
			baseDaoImpl.insert(alarmCall);
			// 发送给警察这里需要获取最近的警察位置
			List<SYS_User> list = getPolice(alarmCall);
			if(list==null||list.size()<1){
				return MessageUtil.error("MSG22", 422);
			}
			// 设置报警信息
			userService.setRedisAlarmCall(alarmCall, null);
			// 设置报警时间
			userService.setRedisAlarmCallTime(alarmCall, user);
			try {
				// 这里需要返回一个导航状态navigation,按钮状态callstatus
				pdapp.put("alarmID", alarmCall.getAlarmID());
				pdapp.put("navigation", "noAttack");
				pdapp.put("callstatus", "waiting");
//				List<SYS_User> list = getPolice(alarmCall);
				Map<String, Object> m = new HashMap<String, Object>();

				m.put("who", "people");
				m.put("peopleCall", alarmCall);
				m.put("poliseFirst", list.get(0));
				m.put("userAlias", user);
				m.put("styleNum", "5");
				jpushSender.send(m, POLICESIGN);
			} catch (Exception e1) {
				e1.printStackTrace();
				return MessageUtil.error("MSG22", 422);
			}
			return MessageUtil.success(pdapp, 1, 1);
		} catch (Exception e) {
			return MessageUtil.error(6, 6);
		}
	}

	@RequestMapping(params = "p=callByServer")
	public @ResponseBody Map<String, Object> callByServer(
			@ModelAttribute GmsAlarmCall alarmCall, HttpSession session) {
		Member user = (Member) session.getAttribute(Const.SESSION_HAS_MEMBER);
		// Member user = userService.getRedisAPPUser(alarmCall.getTokenID(),
		// Member.class);
		PageData pdapp = getPageAppMData();
		;
		if (user == null || alarmCall.getLongitude() == null
				|| alarmCall.getLatitude() == null
				|| alarmCall.getMessage() == null) {
			return MessageUtil.error("MSG7", 407);
		}
		alarmCall.setAlarmID("贵-GYHXY" + DateUtil.getDateTimeString("yyyyMMdd")
				+ userService.setRedisCheackNum());
		alarmCall.setUserID(user.getUserID());
		alarmCall.setUserName(user.getUserName());
		alarmCall.setName(user.getRealName());
		int age = Integer.parseInt(DateUtil.getDateTimeString("yyyy"))-Integer.parseInt(user.getIdCard().substring(6, 10));
		alarmCall.setAge(age+"");
		alarmCall.setSex(user.getSex());
		// 这里需要dic
		alarmCall.setAlarmLevel("appAlarm");
		// 这里也需要dic
		alarmCall.setStatus("nodealing");
		// dic这里由页面传过来
		alarmCall.setMessageType("图片报警");
		alarmCall.setInputDate(DateUtil.getDateTimeString());
		// dic
		alarmCall.setIscheack("noCheack");
		try {
			baseDaoImpl.insert(alarmCall);
		} catch (Exception e) {
			return MessageUtil.error(6, 6);
		}
		// 这里需要返回一个导航状态navigation,按钮状态callstatus
		pdapp.put("navigation", "noAttack");
		pdapp.put("callstatus", "waiting");
		return MessageUtil.success(pdapp, 1, 1);
	}

	// =====================警员端接警===============================

	/**
	 * 接警去那里
	 * 
	 * @Title: receiveByCallGoHere
	 * @Description: TODO
	 * @param alarmReceive
	 * @param session
	 * @return
	 * @return: Map<String,Object>
	 */
	@RequestMapping(params = "p=receiveGoHere")
	public @ResponseBody Map<String, Object> receiveByCallGoHere(
			@RequestBody JSONObject Body, HttpSession session) {
		// @RequestBody JSONObject Body,
		try {
			GmsAlarmReceive alarmReceive = (GmsAlarmReceive) JSONObject.toBean(
					(JSONObject) Body.get("alarmReceive"),
					GmsAlarmReceive.class);
			// GmsAlarmReceive alarmReceive =(GmsAlarmReceive)
			// JSONObject.toBean((JSONObject)
			// Body.get("alarmReceive"),GmsAlarmReceive.class);
			GmsAlarmCall alarmCall = (GmsAlarmCall) JSONObject.toBean(
					(JSONObject) Body.get("alarmCall"), GmsAlarmCall.class);
			SYS_User user = userService.getRedisAPPUser(
					DigestUtils.md5Hex(alarmCall.getTokenID()), SYS_User.class);
			// SYS_User user = (SYS_User)
			// session.getAttribute(Const.SESSION_HAS_MEMBER);
			PageData pdapp = getPageAppPData();
			if (user == null || alarmReceive.getLongitude() == null
					|| alarmReceive.getLatitude() == null) {
				return MessageUtil.error("MSG7", 407);
			}
			if (alarmReceive.getLongitude() == null
					|| alarmReceive.getLatitude() == null
					|| "".equals(alarmReceive.getLongitude())
					|| "".equals(alarmReceive.getLatitude())) {
				return MessageUtil.error("MSG34", 434);
			}
//			List<GmsAlarmCall> query2 = baseDaoImpl.query(GmsAlarmCall.class, "select * from gms_alarm_call where alarmID = '"+alarmCall.getAlarmID()+"' ;");
//			if(query2.size()<1){
//				return MessageUtil.error("MSG36", 436);
//			}
			List<GmsAlarmCall> query3 = baseDaoImpl.query(GmsAlarmCall.class, "select * from gms_alarm_call where alarmID = '"+alarmCall.getAlarmID()+"' and status = 'cancleAlarm' ;");
			if(query3!=null&&query3.size()>0){
				return MessageUtil.error("MSG36", 436);
			}
			List<GmsAlarmCall> query2 = baseDaoImpl.query(GmsAlarmCall.class, "select * from gms_alarm_call where alarmID = '"+alarmCall.getAlarmID()+"' ;");
			if(query2.size()>0){
				if("dealing".equals(query2.get(0).getStatus())){
					return MessageUtil.error("MSG46", 446);
				}
				if("stop".equals(query2.get(0).getStatus())){
					return MessageUtil.error("MSG47", 447);
				}
				if("okNoReason".equals(query2.get(0).getStatus())){
					return MessageUtil.error("MSG46", 446);
				}
				if("okCheack".equals(query2.get(0).getStatus())){
					return MessageUtil.error("MSG46", 446);
				}
			}
//			if(!userService.queryRedisTodayCallTime1(alarmCall.getAlarmID())&&!userService.queryRedisTodayCallTime(alarmCall.getAlarmID())){
//					return MessageUtil.error("MSG36", 436);
//			}
//			if(userService.queryRedisTodayCallTime1(alarmCall.getAlarmID())&&!userService.queryRedisTodayCallTime(alarmCall.getAlarmID())){
//				return MessageUtil.error("MSG39", 439);
//			}
			alarmReceive.setAlarmReceiveID(UUIDUtil.get32UUID());
			alarmReceive.setAlarmID(alarmCall.getAlarmID());
			alarmReceive.setUserID(user.getUserID());
			alarmReceive.setPhone(user.getPhone());
			alarmReceive.setUserName(user.getUserName());
			alarmReceive.setRealName(user.getRealName());
			alarmReceive.setCompanyID(user.getCompanyID());
			alarmReceive.setOrgName(user.getCompanyName());
			alarmReceive.setIscheack("noCheack");
			alarmReceive.setInputDate(DateUtil.getDateTimeString());
			alarmReceive.setStatus("dealing");
			alarmReceive.setAddressName(alarmCall.getAddressName());
			// alarmReceive.setCaseType(alarmCall.getMessageType());
			// 设置报警信息
			List<GmsAlarmCall> query = baseDaoImpl.query(GmsAlarmCall.class,"select * from gms_alarm_call where 1=1 and alarmid = '"+alarmCall.getAlarmID()+"' ;");
			List<Member> query1 = baseDaoImpl.query(Member.class,"select * from cms_user where 1=1 and telephone = '"+query.get(0).getPhone()+"' ;");
			alarmCall.setStatus("dealing");
			alarmCall.setName(query1.get(0).getRealName());
			alarmCall.setMessage(null);
			alarmCall.setUserName(query1.get(0).getUserName());
			baseDaoImpl.update(alarmCall);
			userService.delRedisAlarmCall(alarmCall, null);
			userService.setRedisAlarmCall(alarmCall, alarmReceive);
			////
			user.setWorkStauts("yes");
	        user.setWorkDealing(alarmCall.getName());
	        user.setIsWorking("isWorkingDealing");
	        baseDaoImpl.update(user);
	        ////
			// 这里需要返回一个导航状态navigation,接警按钮状态receiveStatus,拒绝按钮不显示
			pdapp.put("navigation", "noAttack");
			pdapp.put("receiveStatus", "goHere");
			pdapp.put("refuseStatus", "noRefuse");
			pdapp.put("alarmReceive", alarmReceive);
			try {
				// 发送给群众警察姓与电话的信息,距离
				Map<String, Object> m = new HashMap<String, Object>();
				SYS_User userj = userService
						.queryUserByUser(user.getUserName());
				// Member member =
				// userService.queryUserByMember(alarmCall.getUserName());
				List<Member> member = baseDaoImpl.query(
						Member.class,
						"select * from cms_user where userName = '"
								+ alarmCall.getUserName() + "' ;");
				SYS_User user2 = new SYS_User();
				user2.setRealName(user.getRealName() + "警官");
				user2.setPhone(user.getPhone());
				// user2.setDistance(userj.getDistance());
				user2.setUserName(user.getUserName());
				user2.setLongitude(userj.getLongitude());
				user2.setLatitude(userj.getLatitude());
				user2.setPhone(userj.getPhone());
				// m.put("peopleCall", alarmCall);
				m.put("poliseInfo", user2);
				m.put("userAlias", member.get(0));
				m.put("styleNum", "3");
				m.put("msgCode", "600");
				// 删除redis
				try {
					userService.delRedisAlarmCallTime(alarmCall.getAlarmID());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
				baseDaoImpl.insert(alarmReceive);
				jpushSender.send(m, PEOPLESIGN);
				}
				//////////////
				Map redisTodayCallTime = userService.getRedisTodayCallTime();
				if (redisTodayCallTime != null){
					Iterator its = redisTodayCallTime.values().iterator();
					while (its.hasNext()) {
						JSONObject fromObject = JSONObject.fromObject(its.next());
						AlarmCallTime val = (AlarmCallTime) JSONObject.toBean(
								fromObject, AlarmCallTime.class);
						////////
						Map<String,String> people2 = userService.getAlarmWithPeople(val.getId());
						List<String> listlll = null;
						if(people2!=null&&people2.size()>0){
							Set keySet2 = people2.keySet();
							listlll = new ArrayList(keySet2);
						}
						///////////
						long result = System.currentTimeMillis() - val.getL();
						
						String jpushNumToP = this.userService.getJpushNumToP(val
								.getId());
						if(listlll!=null&&listlll.size()>0){
						for (String object : listlll) {
						if(object.equals(user.getUserID())&&Integer.parseInt(jpushNumToP)==Integer.parseInt(people2.get(user.getUserID()))){
						if ((result < Long.parseLong(this.userService.getJpushTime()) * 1000L)
								&& (Integer.parseInt(jpushNumToP) <= Integer
										.parseInt(this.userService.getJpushNum()))) {
							Map alarmWithPeople = userService.getAlarmWithPeople(val.getId());
							if(alarmWithPeople!=null){
								Set<String> keySet = alarmWithPeople.keySet();
								for (String string : keySet) {
									List<GmsAlarmReceive> queryL = baseDaoImpl.query(GmsAlarmReceive.class, "select * from gms_alarm_receive where 1=1 and ischeack = 'noCheack' and status = 'dealing' and alarmid = '"+val.getId()+"' and userid = '"+string+"' and InputDate>=date(now()) and InputDate<DATE_ADD(date(now()),INTERVAL 1 DAY);");
									if(queryL==null||queryL.size()<1){
										val.setL(System.currentTimeMillis());
										this.userService.setRedisAlarmCallTime(val);
										this.userService.setJpushNumToP(val.getId());
										List query11= this.baseDaoImpl.query(GmsAlarmCall.class,
												"select * from gms_alarm_call where alarmid = '"
														+ val.getId() + "'");
										List list = getPolice((GmsAlarmCall) query11.get(0));
										List queryM = this.baseDaoImpl.query(Member.class,
												"select * from cms_user where username = '"
														+ ((GmsAlarmCall) query11.get(0)).getPhone()
														+ "'");
										Map mM = new HashMap();
										int i = Integer.parseInt(jpushNumToP);
										if (list!=null&&list.size() > 0) {
											mM.put("who", "police");
											mM.put("peopleCall", query1.get(0));
											mM.put("poliseFirst", list.get(0));
											mM.put("userAlias", queryM.get(0));
											mM.put("styleNum", "5");
											this.jpushSender.send(mM, "POLICE");
										} else {
											this.userService.delRedisAlarmCallTime(val.getId());
											GmsAlarmCall alarmCall1 = new GmsAlarmCall();
											Map m1 = new HashMap();
											alarmCall1.setAlarmID(val.getId());
											Member userVal = new Member();
											userVal.setAlias(val.getAlias());
											m1.put("peopleCall", alarmCall1);
											m1.put("userAlias", userVal);
											m1.put("msg", "当前没有空闲警员，我们将通过指挥中心调派。给您带来的不便请谅解!");
											m1.put("msgCode", "601");
											m1.put("styleNum", "3");
											this.jpushSender.send(m1, REFUSE);
										}
									}
								}
							}
						}
						}
					}
				}
				}
				}
				/////////////////
			} catch (Exception e1) {
				e1.printStackTrace();
				return MessageUtil.error("MSG35", 535);
			}
			return MessageUtil.success(pdapp, "MSG1", 200);
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtil.error("MSG6", 500);
		}
	}

	/**
	 * 轮询police到了没
	 * 
	 * @Title: receiveIsWhere
	 * @Description: TODO
	 * @param alarmReceive
	 * @param alarmCheack
	 * @param session
	 * @return
	 * @return: Map<String,Object>
	 */
	@RequestMapping(params = "p=receiveIsWhere")
	public @ResponseBody Map<String, Object> receiveIsWhere(
			@RequestBody JSONObject Body, HttpSession session) {
		try {
			// GmsAlarmReceive alarmReceive =(GmsAlarmReceive)
			// JSONObject.toBean((JSONObject)
			// Body.get("alarmReceive"),GmsAlarmReceive.class);
			// SYS_User user =
			// userService.getRedisAPPUser(DigestUtils.md5Hex(alarmReceive.getTokenID()),
			// SYS_User.class);
			// String
			// longitude=Body.getJSONObject("place").get("longitude").toString();
			// String
			// latitude=Body.getJSONObject("place").get("latitude").toString();
			try {
				String userName = Body.getString("userName");
				String tokenID = Body.getString("tokenID");

				Member user = userService
						.getRedisAPPUser(tokenID, Member.class);
				// SYS_User user = (SYS_User)
				// session.getAttribute(Const.SESSION_HAS_MEMBER);
				PageData pdapp = getPageAppPData();
				if (user == null || "".equals(userName) == true) {
					return MessageUtil.error("MSG7", 407);
				}
				SYS_User queryUserByUser = userService
						.queryUserByUser(userName);
				if (queryUserByUser == null) {
					return MessageUtil.error("MSG44", 444);
				}
				pdapp.put("hasmember", queryUserByUser);
				return MessageUtil.success(pdapp, "MSG1", 200);
			} catch (Exception e) {
				e.printStackTrace();
				return MessageUtil.error("MSG29", 429);
			}
		} catch (Exception e) {
			return MessageUtil.error("MSG6", 500);
		}
	}

	/**
	 * 已经到达(需要解锁客户端) 去备案
	 * 
	 * @Title: receiveIsHere
	 * @Description: TODO
	 * @param alarmRefuse
	 * @param session
	 * @return
	 * @return: Map<String,Object>
	 */
	@RequestMapping(params = "p=receiveIsHere")
	public @ResponseBody Map<String, Object> receiveIsHere(
			@RequestBody JSONObject Body, HttpSession session) {
		try {
			GmsAlarmReceive alarmReceive = (GmsAlarmReceive) JSONObject.toBean(
					(JSONObject) Body.get("alarmCall"), GmsAlarmReceive.class);
			SYS_User user = userService.getRedisAPPUser(
					DigestUtils.md5Hex(alarmReceive.getTokenID()),
					SYS_User.class);
			String longitude = Body.getJSONObject("place").get("longitude")
					.toString();
			String latitude = Body.getJSONObject("place").get("latitude")
					.toString();
			// SYS_User user = (SYS_User)
			// session.getAttribute(Const.SESSION_HAS_MEMBER);
			PageData pdapp = getPageAppPData();
			if (user == null || alarmReceive.getLongitude() == null
					|| alarmReceive.getLatitude() == null
					|| alarmReceive.getAlarmID() == null) {
				return MessageUtil.error("MSG7", 407);
			}
			List<GmsAlarmCall> query = baseDaoImpl.query(GmsAlarmCall.class,
					"select * from gms_alarm_call a where 1=1 and alarmID = '"
							+ alarmReceive.getAlarmID() + "'");
			// SYS_User police = getPolice(user);
			if(query.size()<1){
				return MessageUtil.error("MSG38", 438);
			}
			user.setLongitude(longitude);
			user.setLatitude(latitude);
			if (!getPoliceWithPeople(query.get(0), user)) {
				return MessageUtil.error("MSG13", 413);
			}
			// 发送给群众警察姓与电话的信息,距离
			Map<String, Object> m = new HashMap<String, Object>();
			SYS_User userj = userService.queryUserByUser(user.getUserName());
			SYS_User user2 = new SYS_User();
			String w = " 1=1 and alarmID = ?  ";
			List<Object> args = new ArrayList<Object>();
			args.add(alarmReceive.getAlarmID());
			String sql = SQLUtil.getQuerySQL(GmsAlarmCall.class) + " where "
					+ w;
			GmsAlarmCall object = (GmsAlarmCall) baseDaoImpl.query(
					GmsAlarmCall.class, sql, args.toArray()).get(0);
			List<GmsAlarmReceive> query3 = baseDaoImpl.query(GmsAlarmReceive.class,
					"select * from gms_alarm_Receive a where 1=1 and alarmID = '"
							+ alarmReceive.getAlarmID() + "' and phone = '"+user.getPhone()+"' ;");
			GmsAlarmReceive receive = query3.get(0);
			receive.setPresentTime(DateUtil.getDateTimeString());
			baseDaoImpl.update(receive);
			String w1 = " 1=1 and userID = ?  ";
			List<Object> args1 = new ArrayList<Object>();
			args1.add(object.getUserID());
			String sql1 = SQLUtil.getQuerySQL(Member.class) + " where " + w1;
			List<Member> query2 = baseDaoImpl.query(Member.class, sql1,
					args1.toArray());
			user2.setRealName(user.getRealName() + "警官");
			user2.setPhone(user.getPhone());
			user2.setDistance(userj.getDistance());
			user2.setUserName(user.getUserName());
			user2.setLongitude(userj.getLongitude());
			user2.setLatitude(userj.getLatitude());
			user2.setPhone(userj.getPhone());
			m.put("poliseInfo", user2);
			m.put("userAlias", query2.get(0));
			m.put("isClear", "yes");
			m.put("styleNum", "4");
			jpushSender.send(m, PEOPLESIGN);
			// 接警按钮状态receiveStatus
			// pdapp.put("navigation", "noAttack");
			pdapp.put("receiveStatus", "goCheack");
			pdapp.put("refuseStatus", "noRefuse");
			pdapp.put("alarmReceive", alarmReceive);
			return MessageUtil.success(pdapp, "MSG1", 200);
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtil.error("MSG6", 500);
		}
	}

	/**
	 * 已经到达(需要签到解锁) 去备案
	 * 
	 * @Title: receiveIsHere
	 * @Description: TODO
	 * @param alarmRefuse
	 * @param session
	 * 
	 * @return
	 * @return: Map<String,Object>
	 */
	@SuppressWarnings("all")
	@RequestMapping(params = "p=receiveIsHereGoC")
	public @ResponseBody Map<String, Object> receiveIsHereGo(
			@RequestBody JSONObject Body, HttpSession session) {
		try {
			GmsAlarmCheack alarmCheack = new GmsAlarmCheack();
			GmsAlarmReceive alarmReceive = (GmsAlarmReceive) JSONObject.toBean(
					(JSONObject) Body.get("alarmCall"), GmsAlarmReceive.class);
			SYS_User user = userService.getRedisAPPUser(
					DigestUtils.md5Hex(alarmReceive.getTokenID()),
					SYS_User.class);
			// SYS_User user = (SYS_User)
			// session.getAttribute(Const.SESSION_HAS_MEMBER);
//			String longitude = Body.getJSONObject("place").get("longitude")
//					.toString();
//			String latitude = Body.getJSONObject("place").get("latitude")
//					.toString();
			PageData pdapp = getPageAppPData();
			if (user == null || alarmReceive.getLongitude() == null
					|| alarmReceive.getLatitude() == null
					|| alarmReceive.getAlarmID() == null) {
				return MessageUtil.error("MSG7", 407);
			}
			List<GmsAlarmReceive> query2 = baseDaoImpl.query(
					GmsAlarmReceive.class,
					"select * from gms_alarm_receive  where 1=1 and alarmID = '"
							+ alarmReceive.getAlarmID() + "'"
							+ " and phone = '" + user.getPhone() + "' ;");
			List<GmsAlarmCall> query = baseDaoImpl.query(GmsAlarmCall.class,
					"select * from gms_alarm_call a where 1=1 and alarmID = '"
							+ alarmReceive.getAlarmID() + "'");
			if(query.size()<1||query2.size()<1){
				return MessageUtil.error("MSG38", 438);
			}
//			SYS_User police = getPolice(user);
//			police.setLongitude(longitude);
//			police.setLatitude(latitude);
//			if (!getPoliceWithPeople(query.get(0), police)) {
//				return MessageUtil.error("MSG13", 413);
//			}
			alarmCheack.setAlarmID(alarmReceive.getAlarmID());
			alarmCheack.setInputTime(DateUtil.getDateTimeString());
			alarmCheack.setStauts("submit");
			alarmCheack.setAlarmReceiveID(query2.get(0).getAlarmReceiveID());
			// baseDaoImpl.jdbcTemplate.execute(SQLUtil.getUpdateSQL(alarmReceive)+SQLUtil.getInsertSQL(alarmCheack));
			try {
				try {
					baseDaoImpl.insert(alarmCheack);
				} catch (Exception e) {
					e.printStackTrace();

				} finally {
					// 接警按钮状态receiveStatus
					// 报警类型（,案发地所属辖区,处理状态
					List<SYS_Dic> listStyle = systemDicDao
							.findByCategory("CaseType");
					List l1 = new ArrayList();
					for (SYS_Dic sys_Dic : listStyle) {
						if(sys_Dic.getDicName().equals(query2.get(0).getCaseType())){
							l1.add(sys_Dic);
						}
					}
					for (SYS_Dic sys_Dic : listStyle) {
						if(!sys_Dic.getDicName().equals(query2.get(0).getCaseType())){
							l1.add(sys_Dic);
						}
					}
					List<SYS_Dic> listArea = systemDicDao
							.findByCategory("Under");
					List l2 = new ArrayList();
					for (SYS_Dic sys_Dic : listArea) {
						if(sys_Dic.getDicName().equals(query2.get(0).getAreaName())){
							l2.add(sys_Dic);
						}
					}
					for (SYS_Dic sys_Dic : listArea) {
						if(!sys_Dic.getDicName().equals(query2.get(0).getAreaName())){
							l2.add(sys_Dic);
						}
					}
					List<SYS_Dic> listStauts = systemDicDao
							.findByCategory("CaseStauts");
					
					//TODO 改动phone和username
					GmsAlarmReceive receive = query2.get(0);
//					receive.setPhone(query.get(0).getPhone());
//					GmsAlarmCall call = query.get(0);
//					call.setUserName(call.getName());
					List<GmsAlarmCheack> query3 = baseDaoImpl.query(GmsAlarmCheack.class,"select * from gms_alarm_cheack where AlarmReceiveID = '"+query2.get(0).getAlarmReceiveID()+"' and AlarmID = '"+alarmReceive.getAlarmID()+"' ;");
					checkReceiveTime(receive,query.get(0));
					pdapp.put("alarmReceive", receive);
					pdapp.put("alarmCall", query.get(0));
					pdapp.put("alarmCheack", alarmCheack);
					pdapp.put("alarmStyle", listStyle);
					pdapp.put("alarmArea", listArea);
					pdapp.put("alarmStauts", listStauts);
					if(query3.size()>0){
						pdapp.put("alarmCheack", query3.get(0));
						List l3 = new ArrayList();
						for (SYS_Dic sys_Dic : listStauts) {
							if(sys_Dic.getDicName().equals(query3.get(0).getDealStauts())){
								l3.add(sys_Dic);
							}
						}
						for (SYS_Dic sys_Dic : listStauts) {
							if(!sys_Dic.getDicName().equals(query3.get(0).getDealStauts())){
								l3.add(sys_Dic);
							}
						}
						pdapp.put("alarmStauts", l3);
					}
					return MessageUtil.success(pdapp, "MSG1", 200);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return MessageUtil.success(pdapp, "MSG1", 200);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtil.error("MSG6", 500);
		}
	}
	private void checkReceiveTime(GmsAlarmReceive receive, GmsAlarmCall gmsAlarmCall) throws Exception {
		// TODO Auto-generated method stub
		if(CheckUtil.isNullStr(receive.getPresentTime())){
			double distance = MapSpaceUtil.getDistance(Double.parseDouble(receive.getLongitude()), 
					Double.parseDouble(receive.getLatitude()), 
							Double.parseDouble(gmsAlarmCall.getLongitude()), 
									Double.parseDouble(gmsAlarmCall.getLatitude()));
//			long l = gmsAlarmCall.getInputDate();
			SimpleDateFormat sdf = new SimpleDateFormat(Mysft);
			long l = DateUtil.getLTimeByFmt(gmsAlarmCall.getInputDate(),sdf);
			l = (long) (l + distance*1000*1000/userService.getRedisKmS());
			receive.setPresentTime(DateUtil.getDateTimeString(sdf,l));
		}
	}

	/**
	 * 提交备案
	 * 
	 * @Title: receiveIsCheack
	 * @Description: TODO
	 * @param alarmRefuse
	 * @param session
	 * @return
	 * @return: Map<String,Object>
	 */
	@RequestMapping(params = "p=receiveIsCheack")
	public @ResponseBody Map<String, Object> receiveIsCheack(
			@RequestBody JSONObject Body, HttpSession session) {
		try {
			GmsAlarmReceive alarmReceive = (GmsAlarmReceive) JSONObject.toBean(
					(JSONObject) Body.get("alarmCall"), GmsAlarmReceive.class);
			GmsAlarmCheack alarmCheack = (GmsAlarmCheack) JSONObject.toBean(
					(JSONObject) Body.get("alarmCheack"), GmsAlarmCheack.class);
			SYS_User user = userService.getRedisAPPUser(
					DigestUtils.md5Hex(alarmReceive.getTokenID()),
					SYS_User.class);
			// SYS_User user = (SYS_User)
			// session.getAttribute(Const.SESSION_HAS_MEMBER);
			PageData pdapp = getPageAppPData();
			if (user == null || alarmReceive.getAlarmID() == null) {
				return MessageUtil.error("MSG8", 408);
			}
//			if(alarmReceive.getCaseType()==null||"".equals(alarmReceive.getCaseType())){
//				return MessageUtil.error("MSG40", 440);
//			}
			if(CheckUtil.isNotNullStr(alarmReceive.getCaseType())){
				List<SYS_Dic> query = baseDaoImpl.query(SYS_Dic.class, "select * from sys_dic where categoryName = '案件类型' and dicName = '"+alarmReceive.getCaseType()+"' ;");
				if(query.size()>0){
					alarmReceive.setCaseTypeValue(query.get(0).getDicValue());
				}
			}
			if(CheckUtil.isNotNullStr(alarmReceive.getAreaName())){
				List<SYS_Dic> query = baseDaoImpl.query(SYS_Dic.class, "select * from sys_dic where categoryName = '辖区' and dicName = '"+alarmReceive.getAreaName()+"' ;");
				if(query.size()>0){
					alarmReceive.setAreaID(query.get(0).getDicValue());
				}
			}
//			if(alarmReceive.getAreaName()==null||"".equals(alarmReceive.getAreaName())){
//				return MessageUtil.error("MSG41", 441);
//			}
//			if(alarmCheack.getDealStauts()==null||"".equals(alarmCheack.getDealStauts())){
//				return MessageUtil.error("MSG42", 442);
//			}
			baseDaoImpl.jdbcTemplate
					.execute("update gms_alarm_call set status = 'ok' where 1=1 and alarmID = '"
							+ alarmReceive.getAlarmID() + "' ;");
			List<GmsAlarmCall> query = baseDaoImpl.query(GmsAlarmCall.class,
					"select * from gms_alarm_call where 1=1 and alarmID = '"
							+ alarmReceive.getAlarmID() + "' ;");
			////20160324 1107
			List<GmsAlarmReceive> query1 = baseDaoImpl.query(GmsAlarmReceive.class,
					"select * from gms_alarm_receive where 1=1 and userID = '"+user.getUserID()+"' and alarmID = '"
							+ alarmReceive.getAlarmID() + "' ;");
			alarmReceive.setIscheack("okCheack");
			alarmReceive.setUserID(user.getUserID());
			alarmReceive.setStatus("dealing");
			GmsAlarmCall gmsAlarmCall = query.get(0);
//			GmsAlarmReceive gmsAlarmReceive = query1.get(0);
			gmsAlarmCall.setReceiveList(query1);
			userService.setRedisAlarmCall(gmsAlarmCall, null);
			userService.delRedisAlarmCall(gmsAlarmCall, null);
			alarmCheack.setAlarmID(alarmReceive.getAlarmID());
			alarmCheack.setAlarmReceiveID(alarmReceive.getAlarmReceiveID());
			alarmCheack.setInputTime(DateUtil.getDateTimeString());
			alarmCheack.setDealStauts(alarmCheack.getStauts());
			alarmCheack.setStauts("okCheack");
			GmsAlarmCall alarmCall = query.get(0);
			alarmCall.setStatus("okCheack");
			alarmCall.setIscheack("okCheack");
			try {
				baseDaoImpl.update(alarmCall);
				baseDaoImpl.update(alarmReceive);
				baseDaoImpl.update(alarmCheack);
				user.setWorkStauts("no");
		        user.setWorkDealing(alarmCall.getName());
		        user.setIsWorking("isWorking");
		        baseDaoImpl.update(user);
				// baseDaoImpl.jdbcTemplate.execute(SQLUtil.getUpdateSQL(alarmReceive)+SQLUtil.getInsertSQL(alarmCheack));
				return MessageUtil.success(pdapp, "MSG1", 200);
			} catch (Exception e) {
				e.printStackTrace();
				try {
					baseDaoImpl.update(alarmCall);
					baseDaoImpl.update(alarmReceive);
					baseDaoImpl.update(alarmCheack);
					user.setWorkStauts("no");
			        user.setWorkDealing(alarmCall.getName());
			        user.setIsWorking("isWorking");
			        baseDaoImpl.update(user);
					// baseDaoImpl.jdbcTemplate.execute(SQLUtil.getUpdateSQL(alarmReceive)+SQLUtil.getUpdateSQL(alarmCheack));
					return MessageUtil.success(pdapp, "MSG1", 200);
				} catch (Exception e1) {
					e1.printStackTrace();
					return MessageUtil.error("MSG6", 500);
				}
			}
			// 接警按钮状态receiveStatus
			// pdapp.put("navigation", "noAttack");
			// pdapp.put("receiveStatus", "Cheacked");
			// pdapp.put("refuseStatus", "noRefuse");
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtil.error("MSG6", 500);
		}
	}

	/**
	 * 拒绝接警
	 * 
	 * @Title: refuseByCall
	 * @Description: TODO
	 * @param alarmReceive
	 * @param session
	 * @return
	 * @return: Map<String,Object>
	 */
	@RequestMapping(params = "p=refuseByCall")
	public @ResponseBody Map<String, Object> refuseByCall(
			@RequestBody JSONObject Body, HttpSession session) {
		String id ="";
		try {
			GmsAlarmReceive alarmReceive = (GmsAlarmReceive) JSONObject.toBean(
					(JSONObject) Body.get("alarmReceive"),
					GmsAlarmReceive.class);
			GmsAlarmCall alarmCall = (GmsAlarmCall) JSONObject.toBean(
					(JSONObject) Body.get("alarmCall"), GmsAlarmCall.class);
			id = alarmCall.getAlarmID();
			SYS_User user = userService.getRedisAPPUser(
					DigestUtils.md5Hex(alarmCall.getTokenID()), SYS_User.class);
			// SYS_User user = (SYS_User)
			// session.getAttribute(Const.SESSION_HAS_MEMBER);
			PageData pdapp = getPageAppPData();
			if (user == null || alarmReceive.getLongitude() == null
					|| alarmReceive.getLatitude() == null
					|| alarmCall.getAlarmID() == null) {
				return MessageUtil.error("MSG8", 408);
			}
			List<GmsAlarmCall> query3 = baseDaoImpl.query(GmsAlarmCall.class, "select * from gms_alarm_call where alarmID = '"+alarmCall.getAlarmID()+"' and status = 'cancleAlarm' ;");
			if(query3!=null&&query3.size()>0){
				return MessageUtil.error("MSG36", 436);
			}
			List<GmsAlarmCall> query4 = baseDaoImpl.query(GmsAlarmCall.class, "select * from gms_alarm_call where alarmID = '"+alarmCall.getAlarmID()+"' ;");
			if(query4.size()>0){
				if("stop".equals(query4.get(0).getStatus())){
					return MessageUtil.error("MSG47", 447);
				}
			}
//			if(!userService.queryRedisTodayCallTime(id)){
//				return MessageUtil.error("MSG36", 436);
//			}
			if (!"notDeal".equals(alarmReceive.getRefuseType())) {
//				if (alarmReceive.getRefuseContext() == null
//						|| alarmReceive.getRefuseText() == null
//						|| "".equals(alarmReceive.getRefuseContext())
//						|| "".equals(alarmReceive.getRefuseText())) {
					if (alarmReceive.getRefuseText() == null
							|| "".equals(alarmReceive.getRefuseText())) {
					return MessageUtil.error("MSG12", 412);
				}
			}
			if ("notDeal".equals(alarmReceive.getRefuseType())) {
				// 给报警一个处理完成无需理由的状态
				alarmCall.setStatus("okNoReason");
			}
			alarmReceive.setAlarmReceiveID(UUIDUtil.get32UUID());
			alarmReceive.setAlarmID(alarmCall.getAlarmID());
			alarmReceive.setAlarmRefuseID(UUIDUtil.get32UUID());
			alarmReceive.setUserID(user.getUserID());
			alarmReceive.setUserName(user.getUserName());
			alarmReceive.setRealName(user.getRealName());
			alarmReceive.setPhone(user.getPhone());
			alarmReceive.setCompanyID(user.getCompanyID());
			alarmReceive.setOrgName(user.getCompanyName());
			alarmReceive.setIscheack("noCheack");
			alarmReceive.setInputDate(DateUtil.getDateTimeString());
			alarmReceive.setStatus("nodealing");
			List<Member> query = baseDaoImpl.query(
					Member.class,
					"select * from cms_user where userName = '"
							+ alarmCall.getUserName() + "';");
			if (alarmReceive.getRefuseType().equals("notDeal")) {
				try {
					baseDaoImpl.insert(alarmReceive);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return MessageUtil.error("MSG33", 433);
				}
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("isReceive", "no");
				m.put("peopleCall", alarmCall);
				m.put("userAlias", query.get(0));
				m.put("msg", alarmReceive.getRefuseText());
				m.put("msgCode", "601");
				m.put("styleNum", "3");
				jpushSender.send(m, REFUSE);
				alarmCall.setMessage(null);
				baseDaoImpl.update(alarmCall);
				////20160324 1109
				List list = new ArrayList();
				list.add(alarmReceive);
				alarmCall.setReceiveList(list);
				userService.setRedisAlarmCall(alarmCall, alarmReceive);
				userService.delRedisAlarmCall(alarmCall, null);
				pdapp.put("receiveStatus", "noReceive");
				pdapp.put("refuseStatus", "noRefuse");
				// 删除次数
				userService.delRedisAlarmCallTime(alarmCall.getAlarmID());
//				 delPoliceDegree(user);
				return MessageUtil.success(pdapp, "MSG1", 200);
			} else {
				List<SYS_User> list = getPolice(alarmCall);
				List<GmsAlarmCall> query2 = baseDaoImpl.query(GmsAlarmCall.class, "select * from GMS_Alarm_Call where 1=1 and alarmID = '"+alarmCall.getAlarmID()+"' ;");
				
				int num = userService.setRedisAlarmNum(alarmReceive);
				if (num == -1||list==null||list.size()<1) {
					Map<String, Object> m1 = new HashMap<String, Object>();
					m1.put("isReceive", "no");
					m1.put("peopleCall", alarmCall);
					m1.put("userAlias", query.get(0));
					m1.put("msg", "当前没有空闲警员，我们将通过指挥中心调派。给您带来的不便请谅解!");
					m1.put("msgCode", "602");
					m1.put("styleNum", "3");
					jpushSender.send(m1, REFUSE);
					userService.delRedisAlarmCall(alarmCall, alarmReceive);
					// 删除redis
					userService.delRedisAlarmCallTime(alarmCall.getAlarmID());
					// 同一个id无人接警问题
					alarmCall.setStatus("noPolice");
					alarmCall.setMessage(null);
					baseDaoImpl.update(alarmCall);
					userService.setRedisAlarmCall(alarmCall, alarmReceive);
					// 删除次数
					// delPoliceDegree(user);
					// 设置接拒警情况
					// userService.setRedisAlarmReceive(alarmReceive);
				}else{
					if(query2.size()<1){
						return MessageUtil.error("MSG36", 436);
					}
					Map<String, Object> m = new HashMap<String, Object>();
					m.put("who", "police");
					m.put("peopleCall", query2.get(0));
					m.put("poliseFirst", list.get(0));
					m.put("userAlias", user);
					m.put("styleNum", "5");
					jpushSender.send(m, POLICESIGN);
				}
				// 接警按钮状态receiveStatus,拒绝按钮不显示
				pdapp.put("receiveStatus", "noReceive");
				pdapp.put("refuseStatus", "noRefuse");
				alarmReceive.setRefuseType("needDeal");
				baseDaoImpl.insert(alarmReceive);
				// 设置拒接接警
				userService.setRedisAlarmCall(alarmCall, alarmReceive);
				// userService.delRedisAlarmCall(alarmCall,alarmReceive);

				// 发送给警察,这里需要获取最近的警察位置
//				try {
					// TODO
//					 if(getPoliceNum(alarmCall)){
//					 return MessageUtil.error("MSG24", 424);
//					 }
//					List<SYS_User> list = getPolice(alarmCall);
					
					// 删除次数
					// delPoliceDegree(user);
//				} catch (Exception e) {
//					e.printStackTrace();
//					return MessageUtil.error("MSG24", 424);
//				}
				return MessageUtil.success(pdapp, "MSG1", 200);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			try {
				if(!userService.queryRedisTodayCallTime(id)){
					return MessageUtil.error("MSG36", 436);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return MessageUtil.error("MSG435", 435);
			}
			return MessageUtil.error("MSG435", 435);
		}
	}

	/**
	 * 警察定位(客户端)
	 * 
	 * @Title: policePostition
	 * @Description: TODO
	 * @param Body
	 * @param session
	 * @return
	 * @return: Map<String,Object>
	 */
	@RequestMapping(params = "p=policePostition")
	public @ResponseBody Map<String, Object> policePostition(
			@RequestBody JSONObject Body, HttpSession session) {
		try {
			String tokenID = Body.get("tokenID").toString();
			SYS_User user = userService.getRedisAPPUser(
					DigestUtils.md5Hex(tokenID), SYS_User.class);
			SYS_User user1 = userService.queryUserByUser(user.getUserName());
			String longitude = Body.get("longitude").toString();
			String latitude = Body.get("latitude").toString();
			// SYS_User user = (SYS_User)
			// session.getAttribute(Const.SESSION_HAS_MEMBER);
			PageData pdapp = getPageAppPData();
			if (user == null || longitude == null || latitude == null) {
				return MessageUtil.error("MSG7", 407);
			}
			if(user1== null){
				user1 = user;
			}
			user1.setLatitude(latitude);
			user1.setLongitude(longitude);
			List<SYS_User> query = this.baseDaoImpl.query(SYS_User.class, "select * from sys_user where 1=1 and userID = '" + user1.getUserID() + "' ;");
		      if (query != null && query.size() > 0) {
		    	SYS_User object = query.get(0);
		    	user1.setWorkStauts(object.getWorkStauts());
		    	user1.setWorkDealing(object.getWorkDealing());
		        pdapp.put("hasmember", user1);
		        this.userService.setUsersAddress(user1);
		      } else {
		        pdapp.put("hasmember", user1);
		        this.userService.setUsersAddress(user1);
		      }
			return MessageUtil.success(pdapp, "MSG1", 200);
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtil.error("MSG6", 500);
		}
	}

	/**
	 * 群众定位
	 * 
	 * @Title: peoplePostition
	 * @Description: TODO
	 * @param Body
	 * @param session
	 * @return
	 * @return: Map<String,Object>
	 */
	@RequestMapping(params = "p=peoplePostition")
	public @ResponseBody Map<String, Object> peoplePostition(
			@RequestBody JSONObject Body, HttpSession session) {
		try {
			String tokenID = Body.get("tokenID").toString();
			Member user = userService.getRedisAPPUser(tokenID, Member.class);
			String longitude = Body.get("longitude").toString();
			String latitude = Body.get("latitude").toString();
			Object addressName = Body.get("addressName");
			String address = "";
			// SYS_User user = (SYS_User)
			// session.getAttribute(Const.SESSION_HAS_MEMBER);
			PageData pdapp = getPageAppPData();
			if (user == null || longitude == null || latitude == null) {
				return MessageUtil.error("MSG7", 407);
			}
			if(addressName!=null){
				address = addressName.toString();
				user.setAddressName(address);
			}
			user.setLatitude(latitude);
			user.setLongitude(longitude);
			pdapp.put("hasmember", user);
			userService.setUsersAddress(user);
			return MessageUtil.success(pdapp, "MSG1", 200);
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtil.error("MSG6", 500);
		}
	}

	/**
	 * 发现其他同事
	 * 
	 * @Title: findOtherEltern
	 * @Description: TODO
	 * @param Body
	 * @param session
	 * @return
	 * @return: Map<String,Object>
	 */
	@RequestMapping(params = "p=findOtherEltern")
	public @ResponseBody Map<String, Object> findOtherEltern(
			@RequestBody JSONObject Body, HttpSession session) {
		try {
			String tokenID = Body.get("tokenID").toString();
			SYS_User user = userService.getRedisAPPUser(
					DigestUtils.md5Hex(tokenID), SYS_User.class);
			String longitude = Body.get("longitude").toString();
			String latitude = Body.get("latitude").toString();
			// SYS_User user = (SYS_User)
			// session.getAttribute(Const.SESSION_HAS_MEMBER);
			PageData pdapp = getPageAppPData();
			if (user == null || longitude == null || latitude == null) {
				return MessageUtil.error("MSG7", 407);
			}
			List<SYS_User> workUsers = new ArrayList<SYS_User>();
			Map<String, SYS_User> policeAddress = (Map<String, SYS_User>) userService
					.getPoliceAddress();
			if (policeAddress == null) {
				pdapp.put("otherEltern", workUsers);
				return MessageUtil.success(pdapp, "MSG1", 200);
			}
			Iterator<SYS_User> its = policeAddress.values().iterator();
			while (its.hasNext()) {
				// SYS_User val = (SYS_User) (its.next());
				// workUsers.add((SYS_User) its.next());
				// its.next().toString();
				JSONObject fromObject = JSONObject.fromObject(its.next());
				SYS_User bean = (SYS_User) JSONObject.toBean(fromObject,
						SYS_User.class);
				if (bean.getUserName().equals(user.getUserName()) != true) {
					workUsers.add(bean);
				}
			}
			List<GmsAlarmReceive> query = baseDaoImpl.query(GmsAlarmReceive.class, "select * from gms_alarm_receive where 1=1 and userID = '"+user.getUserID()+"' and InputDate>=date(now()) and InputDate<DATE_ADD(date(now()),INTERVAL 1 DAY)  order by inputDate desc ;");
			if(query!=null&&query.size()>0){
//				List<GmsAlarmReceive> query1 = baseDaoImpl.query(GmsAlarmReceive.class, "select * from gms_alarm_receive where 1=1 and alarmID = '"+query.get(0).getAlarmID()+"' and ischeack = 'okCheack' and userID = '"+user.getUserID()+"' and InputDate>=date(now()) and InputDate<DATE_ADD(date(now()),INTERVAL 1 DAY)  order by inputDate desc ;");
				Member userAddress = userService.getUserAddress(query.get(0).getAlarmID());
//				if(query1==null&&query1.size()<1){
					if(userAddress!=null){
//					map 
						pdapp.put("callMan", userAddress);
					}else{
//						pdapp.put("callMan", "报警人不在服务区");
					}
//				}
			}
			pdapp.put("otherEltern", workUsers);
			return MessageUtil.success(pdapp, "MSG1", 200);
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtil.error("MSG6", 500);
		}
	}
	@RequestMapping(params = "p=findCallMan")
	public @ResponseBody Map<String, Object> findCallMan(
			@RequestBody JSONObject Body, HttpSession session) {
		try {
			String tokenID = Body.get("tokenID").toString();
			SYS_User user = userService.getRedisAPPUser(
					DigestUtils.md5Hex(tokenID), SYS_User.class);
			String longitude = Body.get("longitude").toString();
			String latitude = Body.get("latitude").toString();
			// SYS_User user = (SYS_User)
			// session.getAttribute(Const.SESSION_HAS_MEMBER);
			PageData pdapp = getPageAppPData();
			if (user == null || longitude == null || latitude == null) {
				return MessageUtil.error("MSG7", 407);
			}
			List<SYS_User> workUsers = new ArrayList<SYS_User>();
			Map<String, SYS_User> policeAddress = (Map<String, SYS_User>) userService
					.getPoliceAddress();
			if (policeAddress == null) {
				pdapp.put("otherEltern", workUsers);
				return MessageUtil.success(pdapp, "MSG1", 200);
			}
			Iterator<SYS_User> its = policeAddress.values().iterator();
			while (its.hasNext()) {
				// SYS_User val = (SYS_User) (its.next());
				// workUsers.add((SYS_User) its.next());
				// its.next().toString();
				JSONObject fromObject = JSONObject.fromObject(its.next());
				SYS_User bean = (SYS_User) JSONObject.toBean(fromObject,
						SYS_User.class);
				if (bean.getUserName().equals(user.getUserName()) != true) {
					workUsers.add(bean);
				}
			}
			pdapp.put("callMan", workUsers);
			return MessageUtil.success(pdapp, "MSG1", 200);
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtil.error("MSG6", 500);
		}
	}

	/**
	 * 上传语音
	 * 
	 * @Title: checkFile1
	 * @Description: TODO
	 * @param request
	 * @param file
	 * @param session
	 * @return
	 * @return: Map<String,Object>
	 */
	@RequestMapping(params = "p=checkFile")
	public @ResponseBody Map<String, Object> checkFile(
			HttpServletRequest request, // @RequestParam MultipartFile file,
			HttpSession session) {
		// BufferedReader reader;
		// try {
		// reader = request.getReader();
		// System.out.println(reader.readLine());
		// } catch (IOException e2) {
		// // TODO Auto-generated catch block
		// e2.printStackTrace();
		// }
		MultipartHttpServletRequest mureq = (MultipartHttpServletRequest) request;

		Map<String, MultipartFile> files = mureq.getFileMap();
		if (files == null || files.size() == 0) {
			return MessageUtil.error(6, 6);
		}
		Map.Entry<String, MultipartFile> e = files.entrySet().iterator().next();
		MultipartFile file = e.getValue();
		// 语音上传失败
		boolean isLegal = false;
		for (String type : VOICE_TYPE) {
			if (StringUtils
					.endsWithIgnoreCase(file.getOriginalFilename(), type)) {
				isLegal = true;
				break;
			}
		}
		if (isLegal == false) {
			return MessageUtil.error("MSG9", 409);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// Member user = (Member)
			// session.getAttribute(Const.SESSION_HAS_MEMBER);
			String fileTureName = file.getOriginalFilename();
			String fileName = UUIDUtil.get32UUID();
			// filePath
			String filePath = this.getFilePath(fileName, request);
			String extName = "."
					+ StringUtils.substringAfterLast(fileTureName, ".");// 生成新的文件名

			FileUpload.fileUp(file, request.getServletContext()
					.getRealPath("/") + filePath, fileName);
			long audioLength = AudioLength(request.getServletContext()
					.getRealPath("/") + filePath + fileName + extName);
			String valueOf = String.valueOf(audioLength);
			map.put("audioLength", valueOf);
			map.put("filePath", filePath + fileName + extName);

			// FileUpload.fileUp(file,
			// request.getServletContext().getRealPath("/")+filePath, fileName);
			FileUpload.fileUp(file, systemInfo.absoluteFile + filePath,
					fileName);
			map.put("filePath", systemInfo.staticFile + filePath + fileName
					+ extName);

		} catch (Exception e1) {
			e1.printStackTrace();
			return MessageUtil.error(6, 6);
		}
		return MessageUtil.success(map, 1, 1);
	}

	@RequestMapping(params = "p=checImgkFile")
	public @ResponseBody Map<String, Object> checImgkFile(
			HttpServletRequest request, // @RequestParam MultipartFile[] files,
			HttpSession session) {
		try {
			List<MultipartFile> files = new ArrayList<MultipartFile>();
			Map<String, Object> map = new HashMap<String, Object>();
			String paths = "";
			MultipartHttpServletRequest mureq = (MultipartHttpServletRequest) request;
			Map<String, MultipartFile> filess = mureq.getFileMap();
			if (filess == null || filess.size() == 0) {
				return MessageUtil.error(6, 6);
			}
			Set<Entry<String, MultipartFile>> entrySet = filess.entrySet();
			for (Entry<String, MultipartFile> entry : entrySet) {
				files.add(entry.getValue());
			}
			if (files.size() > 4) {
				return MessageUtil.error("MSG25", 425);
			}
			// 图片上传失败
			for (MultipartFile file : files) {

				boolean isLegal = false;
				for (String type : IMAGE_TYPE) {
					if (StringUtils.endsWithIgnoreCase(
							file.getOriginalFilename(), type)) {
						isLegal = true;
						break;
					}
				}
				if (isLegal == false) {
					return MessageUtil.error("MSG10", 410);
				}
				try {
					/*Member user = (Member) session
							.getAttribute(Const.SESSION_HAS_MEMBER);*/
					String fileTureName = file.getOriginalFilename();
					String fileName = UUIDUtil.get32UUID();
					// filePath
					String filePath = this.getImgPath(fileName, request);

					String extName = "."
							+ StringUtils.substringAfterLast(fileTureName, ".");// 生成新的文件名
					// FileUpload.fileUp(file,
					// request.getServletContext().getRealPath("/")+filePath,
					// fileName);
					FileUpload.fileUp(file, systemInfo.absoluteFile + filePath,
							fileName);
					paths += systemInfo.staticFile + filePath + fileName
							+ extName + ",";
				} catch (Exception e1) {
					e1.printStackTrace();
					return MessageUtil.error(6, 6);
				}
			}
			map.put("filePath", paths);
			return MessageUtil.success(map, 1, 1);
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtil.error(6, 6);
		}
	}

	@RequestMapping(params = "p=findImgFile")
	public @ResponseBody Map<String, Object> findImgFile(
			@RequestBody JSONObject Body) {
		try {
			String tokenID = Body.get("tokenID").toString();
			String alarmID = Body.get("alarmID").toString();
			SYS_User user = userService.getRedisAPPUser(
					DigestUtils.md5Hex(tokenID), SYS_User.class);
			// SYS_User user = (SYS_User)
			// session.getAttribute(Const.SESSION_HAS_MEMBER);
			PageData pdapp = getPageAppPData();
			if (user == null) {
				return MessageUtil.error("MSG7", 407);
			}
			List<GmsAlarmCall> query = baseDaoImpl.query(GmsAlarmCall.class,
					"select * from gms_alarm_call where 1=1 and alarmID = '"
							+ alarmID + "' ;");
			if ("imgAlarm".equals(query.get(0).getMessageType())) {
				pdapp.put("imgFile", query.get(0).getMessage());
				return MessageUtil.success(pdapp, "MSG1", 200);
			} else {
				return MessageUtil.error("MSG32", 432);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtil.error("MSG6", 500);
		}
	}

	@RequestMapping(params = "p=cancleAlarm")
	public @ResponseBody Map<String, Object> cancleAlarm(
			@RequestBody JSONObject Body) {
		try {
			String tokenID = Body.get("tokenID").toString();
			String alarmID = Body.get("alarmID").toString();
			Member user = userService.getRedisAPPUser(tokenID, Member.class);
			// SYS_User user = (SYS_User)
			// session.getAttribute(Const.SESSION_HAS_MEMBER);
			/*PageData pdapp = getPageAppPData();*/
			if (user == null) {
				return MessageUtil.error("MSG7", 407);
			}
			try{
				List<GmsAlarmReceive> query3 = baseDaoImpl.query(GmsAlarmReceive.class, "select * from gms_alarm_receive where 1=1 and alarmID = '"+alarmID+"' and alarmrefuseID is null;");
				List<GmsAlarmCall> query = baseDaoImpl.query(GmsAlarmCall.class, "select * from gms_alarm_call where 1=1 and alarmID = '"+alarmID+"' ;");
				if(query3.size()>0&&query3.get(0).getPresentTime()==null){
					return MessageUtil.error("MSG37",437);
				}
				if(query3.size()>0&&query3.get(0).getPresentTime()!=null){
					return MessageUtil.success("MSG1",200);
				}
			if(query==null||query.size()<1){
				return MessageUtil.success("MSG1",200);
			}
			List<SYS_User> list = getPolice(query.get(0));
			if(list==null){
				list = new ArrayList<SYS_User>();
			}
			long time = System.currentTimeMillis()-Integer.parseInt(userService.getJpushTime())*1000*(list.size()==0?1:list.size());
			SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long millionSeconds = sdfTime.parse(query.get(0).getInputDate()).getTime();//毫秒
			if(time>=millionSeconds){
				return MessageUtil.success("MSG1",200);
			} 
//			List<GmsAlarmReceive> query2 = baseDaoImpl.query(GmsAlarmReceive.class, "select * from gms_alarm_receive where 1=1 and alarmID = '"+alarmID+"' and alarmrefuseID = null;");
			if(query3.size()>0&&query3.get(0).getPresentTime()==null){
				return MessageUtil.error("MSG37",437);
			}
			try {
				userService.delRedisAlarmCallTime(alarmID);
				userService.delRedisAlarmCallTime1(alarmID);
				//删除报警
				userService.delRedisAlarmCall(query.get(0));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
			// 删除次数
			GmsAlarmCall gmsAlarmCall = query.get(0);
			gmsAlarmCall.setStatus("cancleAlarm");
			baseDaoImpl.update(gmsAlarmCall);
			return MessageUtil.success("MSG1",200);
			}
			}catch(Exception e){
				e.printStackTrace();
				return MessageUtil.success("MSG1",200);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtil.error("MSG6", 500);
		}
	}
	@RequestMapping(params = "p=findCallStatus")
	public @ResponseBody Map<String, Object> findCallStatus(
			@RequestBody JSONObject Body) {
		try {
			String tokenID = Body.get("tokenID").toString();
			String alarmID = Body.get("alarmID").toString();
			Member user = userService.getRedisAPPUser(tokenID, Member.class);
			// SYS_User user = (SYS_User)
			// session.getAttribute(Const.SESSION_HAS_MEMBER);
			PageData pdapp = getPageAppPData();
			if (user == null) {
				return MessageUtil.error("MSG7", 407);
			}
			List<GmsAlarmCall> query = baseDaoImpl.query(GmsAlarmCall.class,
					"select * from gms_alarm_call where 1=1 and alarmID = '"
							+ alarmID + "' ;");
			if(query==null||query.size()<1){
				return MessageUtil.error("MSG45", 445);
			}
			List<GmsAlarmReceive> query1 = baseDaoImpl
					.query(GmsAlarmReceive.class,
							"select * from gms_alarm_receive where 1=1 and alarmID = '"
									+ alarmID
									+ "' and (status = 'dealing' or status='nodealing' ) order by inputdate desc;");
			if(query1==null||query1.size()<1){
				
			pdapp.put("status", query.get(0).getStatus());
			pdapp.put("callInfo", query1);
//			pdapp.put("styleNum", "4");
//			userService.delRedisAlarmCallTime(alarmID);
			return MessageUtil.success(pdapp, "MSG1", 200);
			}else{
				pdapp.put("status", query.get(0).getStatus());
				pdapp.put("callInfo", query1.get(0));
				if(CheckUtil.isNullStr(query1.get(0).getPresentTime())){
					pdapp.put("styleNum", "3");
				}else{
					pdapp.put("styleNum", "4");
				}
				return MessageUtil.success(pdapp, "MSG1", 200);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtil.error("MSG6", 500);
		}
	}

}
