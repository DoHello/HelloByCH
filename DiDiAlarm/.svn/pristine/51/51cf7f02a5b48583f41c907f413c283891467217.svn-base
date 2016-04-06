package com.derbysoft.controller.sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;












import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.derbysoft.controller.service.BaseDaoController;
import com.derbysoft.entity.cms.GmsAlarmCall;
import com.derbysoft.entity.cms.Member;
import com.derbysoft.entity.sys.SYS_User;
import com.derbysoft.jms.activemq.JpushSender;
import com.derbysoft.redis.service.RedisService;
import com.derbysoft.redis.service.UserService;

import dy.hrtworkframe.exception.CustomerException;
import dy.hrtworkframe.util.Const;
import dy.hrtworkframe.util.MessageUtil;

@Transactional
@Controller("mapLive")
@RequestMapping("mapLive.do")
public class MapLiveController extends BaseDaoController{

	private static final String TOKEN_USER_ADDRESS = "TOKEN_USER_ADDRESS:";
	@Autowired
	private JpushSender jpushSender;
	@Autowired
	public RedisService redisService;
	
	@Autowired
	private UserService userService;
	@RequestMapping(params = "p=casejPushMore")
	public @ResponseBody Map<String, Object> casejPushMore(HttpSession session,
			@RequestBody JSONObject jpushstr
			//, @RequestParam("alarmID") String alarmID
			) {
//		SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
		Map mm = new HashMap();
		try {
			String alarmID = jpushstr.getString("alarmID");
			JSONArray jsonArray = jpushstr.getJSONArray("pushTarget");
//			JSONArray jsonArray = JSONArray.fromObject(unicode(jpushstr));
			List<GmsAlarmCall> query2 = baseDaoImpl.query(GmsAlarmCall.class,
					"select * from gms_alarm_call where alarmID = '"
							+ alarmID + "' ;");
			if(!"nodealing".equals(query2.get(0).getStatus())
					&&!"noPolice".equals(query2.get(0).getStatus())
					){
				mm.put("message", "案件状态已改变,无需推送");
//				throw new CustomerException("案件状态已改变,无需推送");
				return MessageUtil.success(mm,"MSG1", 480);
			}
			String username = "" ;
			for (Object name : jsonArray) {
//				JSONObject fromObject = JSONObject.fromObject(name);
//				SYS_User suser = (SYS_User) fromObject.toBean(fromObject, SYS_User.class);
				List<SYS_User> query = baseDaoImpl.query(SYS_User.class,
						"select * from sys_user where userID = '" + name.toString() + "' ;");
				String key = TOKEN_USER_ADDRESS
						+ DigestUtils.md5Hex(query.get(0).getUserName())
								.toUpperCase();
				String jsonData = this.redisService.get(key);
				if(jsonData!=null){
					if(!"yes".equals(query.get(0).getWorkStauts())){
						Map<String, Object> m = new HashMap<String, Object>();
						m.put("who", "people");
						m.put("peopleCall", query2.get(0));
						m.put("poliseFirst", query.get(0));
						m.put("styleNum", "5");
						jpushSender.send(m, POLICESIGN);
					}else{
						username+=query.get(0).getRealName()+" ";
					}
				}else{
					username +=query.get(0).getRealName()+" ";
				}
			}
			if(!"".equals(username)){
				String[] split = username.split(" ");
				if(split.length>1){
					mm.put("message", username+" 有事在身,请选择其他警员");
					return MessageUtil.success(mm,"MSG1", 482);
				}else{
					mm.put("message", username+" 有事在身,请选择其他警员");
					return MessageUtil.success(mm,"MSG1", 481);
				}
			}
			return MessageUtil.success("MSG1",200);
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtil.error("MSG6", 500);
		}
	}
	
	@RequestMapping(params = "p=getAllInfo")
	@SuppressWarnings("all")
	public @ResponseBody Map<String , Object> getAllInfo(HttpSession session){
//		Map result = new HashMap();
		try{
			//获取所有警员数量(看数据量,需不需要做优化)
			List<SYS_User> allUsers = baseDaoImpl.query(SYS_User.class);
			//获取在岗警员数量
			List<SYS_User> workUsers = new ArrayList<SYS_User>();
			List<SYS_User> workWait = new ArrayList<SYS_User>();
			List<SYS_User> workDeal = new ArrayList<SYS_User>();
			Map policeAddress = userService.getPoliceAddress();
			if(policeAddress!=null){
				
				Iterator its = policeAddress.values().iterator();
				while (its.hasNext()) {
					Map su= (Map) its.next();
					JSONObject object = JSONObject.fromObject(su);
					SYS_User val = (SYS_User) object.toBean(object, SYS_User.class);
//					SYS_User val = (SYS_User)its.next();
					workUsers.add(val);
					if("yes".equals(val.getWorkStauts())){
						workDeal.add(val);
					}else{
						workWait.add(val);
					}
				}
			}
			//获取在线用户数量
			List<Member> membersOL = new ArrayList<Member>();
			Map usersAddress = userService.getUsersAddress();
			if(usersAddress!=null){
				Iterator it = usersAddress.values().iterator();
				while (it.hasNext()) {
					Map su= (Map) it.next();
					JSONObject object = JSONObject.fromObject(su);
					Member val = (Member) object.toBean(object, Member.class);
//					Member val = (Member)it.next();
					membersOL.add(val);
				}
			}
			//获取所有报警信息
			Map alarmCall = userService.queryTodayCall();
			if(workUsers==null){
				alarmCall.put("workUsers", null);
				alarmCall.put("workWait", null);
				alarmCall.put("workDeal", null);
			}else{
				alarmCall.put("workUsers", workUsers);
				alarmCall.put("workWait", workWait);
				alarmCall.put("workDeal", workDeal);
			}
//			result.put("alarmCall", alarmCall);
//			alarmCall.remove("needDeal");
			return MessageUtil.success(alarmCall,"MSG1",200);
		}catch(Exception e){
			e.printStackTrace();
			MessageUtil.error("MSG6",500);
		}
		return MessageUtil.error("MSG6",500);
	}
//	@RequestMapping(params = "p=getAllInfo")
//	@SuppressWarnings("all")
//	public @ResponseBody Map<String , Object> getAllInfo(HttpSession session){
////		Map result = new HashMap();
//		try{
//			//获取所有警员数量(看数据量,需不需要做优化)
//			List<SYS_User> allUsers = baseDaoImpl.query(SYS_User.class);
//			//获取在岗警员数量
//			List<SYS_User> workUsers = new ArrayList<SYS_User>();
//			Map policeAddress = userService.getPoliceAddress();
//			if(policeAddress!=null){
//				
//				Iterator its = policeAddress.values().iterator();
//				while (its.hasNext()) {
//					SYS_User val = (SYS_User)its.next();
//					workUsers.add(val);
//				}
//			}
//			//获取在线用户数量
//			List<Member> membersOL = new ArrayList<Member>();
//			Map usersAddress = userService.getUsersAddress();
//			if(usersAddress!=null){
//				Iterator it = usersAddress.values().iterator();
//				while (it.hasNext()) {
//					Member val = (Member)it.next();
//					membersOL.add(val);
//				}
//			}
//			//获取所有报警信息
//			Map alarmCall = userService.queryTodayCall();
////			alarmCall.put("allUsers", allUsers.size());
//			if(allUsers==null){
//				alarmCall.put("allUsers", 0);
//			}else{
//				alarmCall.put("allUsers", allUsers.size());
//			}
//			if(workUsers==null){
//				alarmCall.put("workUsers", 0);
//				alarmCall.put("workMove", null);
//			}else{
//				alarmCall.put("workUsers", workUsers.size());
//				alarmCall.put("workMove", workUsers);
//			}
//			if(membersOL==null){
//				alarmCall.put("membersOL", 0);
//			}else{
//				alarmCall.put("membersOL", membersOL.size());
//			}
////			result.put("alarmCall", alarmCall);
//			if((Map)alarmCall.get("nodealingNum")==null){
//				alarmCall.put("nodealingNum",0);
//			}else{
//				alarmCall.put("nodealingNum", ((Map)alarmCall.get("nodealingNum")).size());
//			}
//			alarmCall.remove("needDeal");
//			return MessageUtil.success(alarmCall,"MSG1",200);
//		}catch(Exception e){
//			e.printStackTrace();
//			MessageUtil.error("MSG6",500);
//		}
//		return MessageUtil.error("MSG6",500);
//	}
	public static void main(String[] args) {
		System.out.println();
	}
}
