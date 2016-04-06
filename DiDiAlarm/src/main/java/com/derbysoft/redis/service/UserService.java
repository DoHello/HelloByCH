package com.derbysoft.redis.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.derbysoft.dao.cms.NewsDao;
import com.derbysoft.entity.AlarmCallTime;
import com.derbysoft.entity.RegisterPhone;
import com.derbysoft.entity.SystemInfo;
import com.derbysoft.entity.cms.GmsAlarmCall;
import com.derbysoft.entity.cms.GmsAlarmReceive;
import com.derbysoft.entity.cms.Member;
import com.derbysoft.entity.sys.SYS_User;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dy.hrtworkframe.db.SQLUtil;
import dy.hrtworkframe.entity.User;
import dy.hrtworkframe.util.CheckUtil;
import dy.hrtworkframe.util.DateUtil;

@Service
@SuppressWarnings("all")
public class UserService {

	@Autowired
	private NewsDao newDao;

	@Autowired
	public RedisService redisService;

	@Autowired
	private SystemInfo systemInfo;
	private static final Map<Integer, Boolean> TYPE = new HashMap<Integer, Boolean>();

	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final Integer REDIS_TIME_NOKONW = 60*5;
	private static final Integer REDIS_TIME_30 = 60 * 30;
	private static final Integer REDIS_TIME_10 = 60 * 10;
	private static final Integer REDIS_TIME_1D = 60 * 60 * 24;
	private static final String TOKEN_CHEACK_NUM = "TOKEN_CKEACK_NUM:";
	private static final String TOKEN_ALARM_NUM = "TOKEN_ALARM_NUM:";
	private static final String TOKEN = "TOKEN:";
	private static final Integer TOKEN_CHEACK_TIME = 60 * 60 * 24;
	private static final Integer TOKEN_USER_TIME = 60 * 60 * 24 * 30;
	private static final String TOKEN_MEMBER_ADDRESS = "TOKEN_MEMBER_ADDRESS:";
	private static final String TOKEN_USER_ADDRESS = "TOKEN_USER_ADDRESS:";
	private static final String TOKEN_CALL_NUM = "TOKEN_CALL_NUM:";
	private static final String TOKEN_CALL_TIME = "TOKEN_CALL_TIME:";
	private static final String TOKEN_ALARM_USER = "TOKEN_ALARM_USER:";
	private static final String TOKEN_CALL_TIME1 = "TOKEN_CALL_TIME1:";
	private static final String TOKEN_JPUSH_TIME = "TOKEN_JPUSH_TIME:DIDITIME";
	private static final String TOKEN_JPUSH_NUM = "TOKEN_JPUSH_NUM:DIDINUM";
	private static final String TOKEN_OUT_TIME = "TOKEN_OUT_TIME:DIDIOUT";
	private static final String TOKEN_KM_S = "TOKEN_KM_S:KMS";
	private static final String TOKEN_CALL_KM = "TOKEN_CALL_KM:DISTANCE";
	// private static final String TOKEN_JPUSH_NUM_TOP =
	// "TOKEN_JPUSH_NUM_TOP:DIDINUMTOP";
	private static final Integer TOKEN_JPUSH_NUM_TOPTIME = 60 * 60 * 12;

	

	// 这里的逻辑是把user以kv的形式保存到redis中,默认存活时间为30分钟
	public String doLoginToRedis(SYS_User user) throws Exception {
		// 验证写在controller上这里就不需要验证,直接写
		String token = DigestUtils.md5Hex(
				System.currentTimeMillis() + user.getUserName()).toUpperCase();
		this.redisService.set(TOKEN + token, MAPPER.writeValueAsString(user),
				REDIS_TIME_30);
		return token;
	}

	public SYS_User queryUserByToken(String token) throws Exception {
		String key = TOKEN + token;
		String jsonData = this.redisService.get(key);
		if (null == jsonData) {
			// 登录超时
			return null;
		}
		SYS_User user = MAPPER.readValue(jsonData, SYS_User.class);
		// 重新设置生存时间
		this.redisService.expire(key, REDIS_TIME_30);
		return user;
	}

	/**
	 * 查询警员在哪里
	 * 
	 * @Title: queryUserByUser
	 * @Description: TODO
	 * @param token
	 * @return
	 * @throws Exception
	 * @return: SYS_User
	 */
	public SYS_User queryUserByUser(String token) throws Exception {
		String key = TOKEN_USER_ADDRESS
				+ DigestUtils.md5Hex(token).toUpperCase();
		String jsonData = this.redisService.get(key);
		if (null == jsonData||"{}".equals(jsonData)) {
			// 登录超时
			return null;
		}
		SYS_User sys_user = MAPPER.readValue(jsonData, SYS_User.class);
		// 重新设置生存时间
//		this.redisService.expire(key, REDIS_TIME_30);
		return sys_user;
	}

	public Member queryUserByMember(String token) throws Exception {
		String key = TOKEN_MEMBER_ADDRESS
				+ DigestUtils.md5Hex(token).toUpperCase();
		String jsonData = this.redisService.get(key);
		if (null == jsonData||"{}".equals(jsonData)) {
			// 登录超时
			return null;
		}
		Member sys_user = MAPPER.readValue(jsonData, Member.class);
		// 重新设置生存时间
//		this.redisService.expire(key, REDIS_TIME_30);
		return sys_user;
	}

	/**
	 * 查询警察正在处理案件的实时地点
	 * 
	 * @Title: queryUsersByToken
	 * @Description: TODO
	 * @param token
	 * @return
	 * @throws Exception
	 * @return: List<SYS_User>
	 */
	public List<SYS_User> queryUsersAddress() throws Exception {
		List<SYS_User> suser = newDao.query(SYS_User.class);
		// String w = " 1=1 and status = 'nodealing' or status = 'dealing'";
		String w = " 1=1 and status = 'dealing' and isCheack ='noCheack'  and InputDate>=date(now()) and InputDate<DATE_ADD(date(now()),INTERVAL 1 DAY)";
		// List args1 = getList();
		// args1.add("submit");//及时处理中,or failure 未处理
		String sql = SQLUtil.getQuerySQL(GmsAlarmReceive.class) + " where " + w;
		List<GmsAlarmReceive> query = newDao.query(GmsAlarmReceive.class, sql);
		// if (newDao.query(GmsAlarmReceive.class, sql, args1.toArray()) ==
		// null) {
		// return null;
		// }
		// List<GmsAlarmReceive> receive = newDao.query(GmsAlarmReceive.class);
		List<SYS_User> luser = new ArrayList<SYS_User>();
		if (query.size() > 0 && query != null) {
			for (SYS_User sys_User : suser) {
				for (GmsAlarmReceive gmsAlarmReceive : query) {
					if (!gmsAlarmReceive.getUserID().equals(
							sys_User.getUserID())) {
						String key = TOKEN_USER_ADDRESS
								+ DigestUtils.md5Hex(sys_User.getUserName())
										.toUpperCase();
						String jsonData = this.redisService.get(key);
						if(jsonData!=null){
						SYS_User user = MAPPER.readValue(jsonData,
								SYS_User.class);
						if ("okWorking".equals(user.getIsWorking())) {
							if(luser.size()>0){
								for (int i = 0; i < luser.size(); i++) {
									if(!user.getUserID().equals(luser.get(i).getUserID())){
										luser.add(user);
									}
								}
//								for (SYS_User sys_User2 : luser) {
//									if(!user.getUserID().equals(sys_User2.getUserID())){
//										luser.add(user);
//									}
//								}
							}else{
								luser.add(user);
							}
						}
						}
					}
				}
			}
		} else {
			for (SYS_User sys_User : suser) {
				String key = TOKEN_USER_ADDRESS
						+ DigestUtils.md5Hex(sys_User.getUserName())
								.toUpperCase();
				String jsonData = this.redisService.get(key);
				if (jsonData != null) {
					SYS_User user = MAPPER.readValue(jsonData, SYS_User.class);
					if ("okWorking".equals(user.getIsWorking())) {
						luser.add(user);
					}
				}
			}
		}
		if(luser.size()>0){
			List<SYS_User> luser1 = new ArrayList<SYS_User>();
			for (int i = 0; i < luser.size(); i++) {
				for (GmsAlarmReceive gmsAlarmReceive : query) {
					if(gmsAlarmReceive.getUserID().equals(luser.get(i).getUserID())){
						luser1.add(luser.get(i));
//						luser.remove(i);
					}
				}
			}
			luser.removeAll(luser1);
		}
		return luser;
	}

	/**
	 * 查询所有群众实时地点
	 * 
	 * @Title: queryMemberAddress
	 * @Description: TODO
	 * @return
	 * @throws Exception
	 * @return: List<Member>
	 */
	public List<Member> queryMemberAddress() throws Exception {
		List<Member> suser = newDao.query(Member.class);
		// String w = " 1=1 and status = 'nodealing' or status = 'dealing'";
		List<Member> luser = new ArrayList<Member>();
		if (suser.size() <= 1 || suser == null) {
			return null;
		}
		for (Member member : suser) {
			if (member.getUserName() == null) {
				return null;
			}
			String key = TOKEN_MEMBER_ADDRESS
					+ DigestUtils.md5Hex(member.getUserName()).toUpperCase();
			String jsonData = this.redisService.get(key);
			if (jsonData == null) {
				return null;
			}
			Member user = MAPPER.readValue(jsonData, Member.class);
			luser.add(user);
		}

		return luser;
	}

	/**
	 * 设置警员实时地点
	 * 
	 * @Title: setUsersAddress
	 * @Description: TODO
	 * @param user
	 * @throws Exception
	 * @return: void
	 */
	public void setUsersAddress(SYS_User user) throws Exception {
		// 警员
		String key = TOKEN_USER_ADDRESS
				+ DigestUtils.md5Hex(user.getUserName()).toUpperCase();
		// if(queryUserByUser(user.getUserName())!=null){
		// this.redisService.expire(key, REDIS_TIME_30);
		// }
		Integer time = getUserInTime();
		this.redisService.set(key, MAPPER.writeValueAsString(user),
				time);
		String key1 = TOKEN_USER_ADDRESS
				+ DateUtil.getDateTimeString("yyyyMMdd");
		String string = this.redisService.get(key1);
		if (string == null || string.equals("")) {
			this.redisService.set(key1,
					MAPPER.writeValueAsString(new HashMap()), REDIS_TIME_30);
			string = this.redisService.get(key1);
			Map<String, Object> readValue = MAPPER.readValue(string, Map.class);
			readValue.put(user.getUserID(), user);
			this.redisService.set(key1, MAPPER.writeValueAsString(readValue),
					REDIS_TIME_30);
		} else {
			Map<String, Object> readValue = MAPPER.readValue(string, Map.class);
			readValue.put(user.getUserID(), user);
			this.redisService.set(key1, MAPPER.writeValueAsString(readValue),
					REDIS_TIME_30);
		}
	}

	private Integer getUserInTime() {
		String key = TOKEN_OUT_TIME;
		String string = this.redisService.get(key);
		if (string == null) {
			this.redisService.set(key, "300");
			return 300;
		} else {
			return Integer.parseInt(string.trim());
		}
	}
	private void setUserInTime(String time) {
		String key = TOKEN_OUT_TIME;
		this.redisService.set(key, time);
	}

	/**
	 * 
	 * @Title: delUsersAddress
	 * @Description: TODO
	 * @param user
	 * @throws Exception
	 * @return: void
	 */
	public void delUsersAddress(SYS_User user) throws Exception {
		// 警员
		String key = TOKEN_USER_ADDRESS
				+ DigestUtils.md5Hex(user.getUserName()).toUpperCase();
		// if(queryUserByUser(user.getUserName())!=null){
		// this.redisService.expire(key, REDIS_TIME_30);
		// }
		try {
			newDao.jdbcTemplate.execute("update sys_user set isworking = 'noWorking' where userName = '"+user.getUserName()+"' ;");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
		this.redisService.del(key);
		String key1 = TOKEN_USER_ADDRESS
				+ DateUtil.getDateTimeString("yyyyMMdd");
		String string = this.redisService.get(key1);
		if(string==null){
			
		}else{
		Map<String, Object> readValue = MAPPER.readValue(string, Map.class);
		readValue.remove(user.getUserID());
		this.redisService.set(key1, MAPPER.writeValueAsString(readValue),
				REDIS_TIME_30);
		}
		}
	}
	public void delMembersAddress(Member user) throws Exception {
		//群众
		String key = TOKEN_MEMBER_ADDRESS
				+ DigestUtils.md5Hex(user.getUserName()).toUpperCase();
		// if(queryUserByUser(user.getUserName())!=null){
		// this.redisService.expire(key, REDIS_TIME_30);
		// }
		this.redisService.del(key);
		String key1 = TOKEN_MEMBER_ADDRESS
				+ DateUtil.getDateTimeString("yyyyMMdd");
		String string = this.redisService.get(key1);
		Map<String, Object> readValue = MAPPER.readValue(string, Map.class);
		readValue.remove(user.getUserID());
		this.redisService.set(key1, MAPPER.writeValueAsString(readValue),
				REDIS_TIME_30);
	}

	public void delUserAddress(SYS_User user) throws Exception {
		// 警员
		String key1 = TOKEN_USER_ADDRESS
				+ DateUtil.getDateTimeString("yyyyMMdd");
		String string = this.redisService.get(key1);
		Map readValue = MAPPER.readValue(string, Map.class);
		readValue.remove(user.getUserID());
		this.redisService.set(key1, MAPPER.writeValueAsString(readValue),
				REDIS_TIME_30);
	}
	public void delMemberAddress(Member user) throws Exception {
		// 群众
		String key1 = TOKEN_MEMBER_ADDRESS
				+ DateUtil.getDateTimeString("yyyyMMdd");
		String string = this.redisService.get(key1);
		Map readValue = MAPPER.readValue(string, Map.class);
		readValue.remove(user.getUserID());
		this.redisService.set(key1, MAPPER.writeValueAsString(readValue),
				REDIS_TIME_30);
	}

	/**
	 * 获取警察在线数
	 * 
	 * @Title: getPoliceAddress
	 * @Description: TODO
	 * @return
	 * @throws Exception
	 * @return: List
	 */
	public Map<?, ?> getPoliceAddress() throws Exception {
		String key1 = TOKEN_USER_ADDRESS
				+ DateUtil.getDateTimeString("yyyyMMdd");
		String string = this.redisService.get(key1);
		if (string == null) {
			return null;
		} else {
			return MAPPER.readValue(string, Map.class);
		}
	}

	/**
	 * 设置群众地点
	 * 
	 * @Title: setUsersAddress
	 * @Description: TODO
	 * @param user
	 * @throws Exception
	 * @return: void
	 */
	public void setUsersAddress(Member user) throws Exception {
		// 群众
		String key = TOKEN_MEMBER_ADDRESS
				+ DigestUtils.md5Hex(user.getUserName()).toUpperCase();
		this.redisService.set(key, MAPPER.writeValueAsString(user),
				REDIS_TIME_10);
		String key1 = TOKEN_MEMBER_ADDRESS
				+ DateUtil.getDateTimeString("yyyyMMdd");
		String string = this.redisService.get(key1);
		if (string == null || string.equals("")) {
			this.redisService.set(key1,
					MAPPER.writeValueAsString(new HashMap()), REDIS_TIME_1D);
			string = this.redisService.get(key1);
			Map readValue = MAPPER.readValue(string, Map.class);
			readValue.put(user.getUserID(), user);
			this.redisService.set(key1, MAPPER.writeValueAsString(readValue),
					REDIS_TIME_1D);
		} else {
			Map readValue = MAPPER.readValue(string, Map.class);
			readValue.put(user.getUserID(), user);
			this.redisService.set(key1, MAPPER.writeValueAsString(readValue),
					REDIS_TIME_1D);
		}
		// return this.redisService.get(key);
	}
	
	public Member getUserAddress(String alarmID) throws Exception {
		List<GmsAlarmCall> query = newDao.query(GmsAlarmCall.class, "select * from gms_alarm_call where 1=1 and alarmID = '"+alarmID+"' ;");
		if(query!=null&&query.size()>0){
			String key = TOKEN_MEMBER_ADDRESS
					+ DigestUtils.md5Hex(query.get(0).getUserName()).toUpperCase();
		
			String string = this.redisService.get(key);
			if (string == null) {
				return null;
			} else {
				return MAPPER.readValue(string, Member.class);
			}
		}
		return null;
	}

	/**
	 * 获取所有在线用户
	 * 
	 * @Title: getUsersAddress
	 * @Description: TODO
	 * @return
	 * @throws Exception
	 * @return: List
	 */
	public Map getUsersAddress() throws Exception {
		String key1 = TOKEN_MEMBER_ADDRESS
				+ DateUtil.getDateTimeString("yyyyMMdd");
		String string = this.redisService.get(key1);
		if (string == null) {
			return null;
		} else {
			return MAPPER.readValue(string, Map.class);
		}
		// return this.redisService.get(key);
	}

	/**
	 * 警员退出地点
	 * 
	 * @Title: setUsersAddressOut
	 * @Description: TODO
	 * @param user
	 * @throws Exception
	 * @return: void
	 */
	public void setUsersAddressOut(SYS_User user) throws Exception {
		String key = TOKEN_USER_ADDRESS
				+ DigestUtils.md5Hex(user.getUserName()).toUpperCase();
		this.redisService.del(key);
	}

	public static void main(String[] args) {
		System.out.println("你-sss".toUpperCase());
		System.out.println(DigestUtils.md5Hex("你好").toUpperCase());
		System.out.println(System.currentTimeMillis() - 1455701221321l);
	}

	public void setRedisUserTime(String token) {
		String key = TOKEN + token;
		this.redisService.expire(key, REDIS_TIME_30);
	}

	public <T> void setRedisAPPUser(T t, String token) throws Exception {
		String key = TOKEN + DigestUtils.md5Hex(token).toUpperCase();
		this.redisService.set(key, MAPPER.writeValueAsString(t),
				TOKEN_USER_TIME);
	}

	public void delRedisAPPUser(String token) throws Exception {
		String key = TOKEN + DigestUtils.md5Hex(token).toUpperCase();
		this.redisService.del(key);
	}
	public void delRedisAPPUser(Member user) throws Exception {
		if(user.getTokenID()!=null){
			String key = TOKEN + user.getTokenID().toUpperCase();
			this.redisService.del(key);
		}
	}
	public void delRedisAPPUser(SYS_User user) throws Exception {
		if(user.getTokenID()!=null){
		String key = TOKEN + DigestUtils.md5Hex(user.getTokenID()).toUpperCase();
		this.redisService.del(key);
		}
	}

	/**
	 * 
	 * @discription 根据tokenID获取redis里面的数据
	 * @author Knight
	 * @created 2016年2月16日 下午2:05:26
	 * @param token
	 * @param T
	 * @return
	 * @throws Exception
	 */
	public <T> T getRedisAPPUser(String token, Class T) throws Exception {
		String key = TOKEN + DigestUtils.md5Hex(token).toUpperCase();
		// String key = TOKEN + token.toUpperCase();
		String string = this.redisService.get(key);
		if (null == string) {
			return null;
		}
		T rp = (T) MAPPER.readValue(string, T);
		return (T) rp;
	}

	/**
	 * 
	 * @discription 测试用的方法
	 * @author Knight
	 * @created 2016年2月16日 下午2:05:26
	 * @param token
	 * @param T
	 * @return
	 * @throws Exception
	 */
	public <T> T getRedisAPPUser1(String token, Class T) throws Exception {
		String key = TOKEN + token.toUpperCase();
		String string = this.redisService.get(key);
		if (null == string) {
			return null;
		}
		T rp = (T) MAPPER.readValue(string, T);
		return (T) rp;
	}

	/**
	 * 获取备案号
	 * 
	 * @Title: getRedisCheackNum
	 * @Description: TODO
	 * @return
	 * @return: String
	 */
	public String getRedisCheackNum() {
		String key = TOKEN_CHEACK_NUM + DateUtil.getDateTimeString("yyyyMMdd");
		return this.redisService.get(key);
	}

	/**
	 * 设置备案号
	 * 
	 * @Title: setRedisCheackNum
	 * @Description: TODO
	 * @return
	 * @return: String
	 */
	public String setRedisCheackNum() {
		String key = TOKEN_CHEACK_NUM + DateUtil.getDateTimeString("yyyyMMdd");
		String i = getRedisCheackNum();
		if (Integer.parseInt(i) >= 100 && Integer.parseInt(i) < 999) {
			int parseInt = Integer.parseInt(i);
			this.redisService.set(key, ++parseInt + "", TOKEN_CHEACK_TIME);
			return parseInt + "";
		} else if (Integer.parseInt(i) >= 0 && Integer.parseInt(i) < 9) {
			int parseInt = Integer.parseInt(i);
			this.redisService.set(key, "00" + ++parseInt, TOKEN_CHEACK_TIME);
			return "00" + parseInt;
		} else if (Integer.parseInt(i) >= 9 && Integer.parseInt(i) <= 99) {
			int parseInt = Integer.parseInt(i);
			this.redisService.set(key, "0" + ++parseInt, TOKEN_CHEACK_TIME);
			return "0" + parseInt;
		} else {
			return "0";
		}
	}

	/**
	 * 设置备案号
	 * 
	 * @Title: setRedisCheack
	 * @Description: TODO
	 * @return: void
	 */
	public void setRedisCheack() {
		String key = TOKEN_CHEACK_NUM + DateUtil.getDateTimeString("yyyyMMdd");
		if (getRedisCheackNum() == null) {
			this.redisService.set(key, "000", TOKEN_CHEACK_TIME);
		}
	}

	public RegisterPhone queryRegisterByToken(String token) throws Exception {

		String jsonData = this.redisService.get(token);

		if (null == jsonData) {
			return null;
		}
		RegisterPhone rp = MAPPER.readValue(jsonData, RegisterPhone.class);
		return rp;
	}

	/**
	 * 将用户tokenID保存到redis中
	 * 
	 * @Title: userTokenIDToRedis
	 * @Description: TODO
	 * @param token
	 * @param code
	 * @return
	 * @throws Exception
	 * @return: String
	 */
	public String userTokenIDToRedis(String token, String code)
			throws Exception {

		this.redisService.set(token.toUpperCase(),
				MAPPER.writeValueAsString(code), REDIS_TIME_30);
		// this.redisService.expire(token, REDIS_TIME_30);
		return token;
	}

	public String phoneToRedisNoExpire(String token, RegisterPhone code)
			throws Exception {

		this.redisService.set(token, MAPPER.writeValueAsString(code),
				REDIS_TIME_30);
		// this.redisService.expire(token, REDIS_TIME_30);
		return token;
	}

	public String phoneToRedis(String token, RegisterPhone code)
			throws Exception {

		this.redisService.set(token, MAPPER.writeValueAsString(code),
				REDIS_TIME_30);
		this.redisService.expire(token, REDIS_TIME_30);
		return token;
	}

	public String phoneToRedis(String token, String code) {
		this.redisService.set(token, code, REDIS_TIME_30);
		this.redisService.expire(token, REDIS_TIME_30);
		return token;
	}

	public void setRedisRegisterTime(String token) {
		// TODO Auto-generated method stub
		this.redisService.expire(token, REDIS_TIME_30);
	}

	public String getSql(String feild) {
		return " 1=1 and " + feild + " = ? ";
	}

	public List<Object> getList() {
		return new ArrayList<Object>();
	}

	public String getTokenIDByTokenID(String tokenID) {
		String jsonData = this.redisService.get(tokenID);

		if (null == jsonData) {
			return null;
		}
		return jsonData;
	}

	public GmsAlarmReceive getRedisAlarmNum(GmsAlarmReceive alarmReceive)
			throws Exception {
		String key = TOKEN_ALARM_NUM + alarmReceive.getAlarmID().toUpperCase();
		String string = this.redisService.get(key);
		if (null == string) {
			return null;
		}
		GmsAlarmReceive rp = MAPPER.readValue(string, GmsAlarmReceive.class);
		return rp;
	}

	/**
	 * 返回同一个报警的据接警次数
	 * 
	 * @Title: setRedisAlarmNum
	 * @Description: TODO
	 * @param alarmReceive
	 * @return
	 * @throws Exception
	 * @return: String
	 */
	public int setRedisAlarmNum(GmsAlarmReceive alarmReceive) throws Exception {
		String key = TOKEN_ALARM_NUM + alarmReceive.getAlarmID().toUpperCase();
		GmsAlarmReceive redisAlarmNum = getRedisAlarmNum(alarmReceive);
		if (redisAlarmNum == null) {
			alarmReceive.setAlarmNum(1);
			this.redisService.set(key, MAPPER.writeValueAsString(alarmReceive),
					REDIS_TIME_1D);
			return 1;
		} else if (redisAlarmNum.getAlarmNum() < Integer.parseInt(getJpushNum())-1) {
			redisAlarmNum.setAlarmNum(redisAlarmNum.getAlarmNum() + 1);
			this.redisService.set(key, MAPPER.writeValueAsString(redisAlarmNum),
					REDIS_TIME_1D);
			return redisAlarmNum.getAlarmNum() ;
		} else {
			return -1;
		}
	}

	/**
	 * 设置当天报警信息
	 * 
	 * @Title: setRedisTodayCall
	 * @Description: TODO
	 * @return: void
	 * @throws Exception
	 */
	public void setRedisTodayCall() throws Exception {
		String key = TOKEN_CALL_NUM + DateUtil.getDateTimeString("yyyyMMdd");
		if (getRedisTodayCall() == null) {
			Map m = new HashMap();
			// Object o = new Object();
			HashMap<String, GmsAlarmCall> hashMap = new HashMap<String, GmsAlarmCall>();
			GmsAlarmCall alarmCall = new GmsAlarmCall();
			List arrayList = new ArrayList();
			arrayList.add(new GmsAlarmReceive());
			alarmCall.setReceiveList(arrayList);
			// hashMap.put("test1", alarmCall);
			m.put("noPolice", hashMap);
			m.put("nodealing", hashMap);
			m.put("nodealingNum", hashMap);

			HashMap<String, GmsAlarmCall> hashMap2 = new HashMap<String, GmsAlarmCall>();
			// hashMap2.put("test2", alarmCall);
			m.put("dealing", hashMap2);
			m.put("okNoReason", hashMap2);
			m.put("ok", hashMap2);
			m.put("nodealReason", hashMap2);
			m.put("cancleAlarm", hashMap2);
			// m.put("dealing", new HashMap<String,Map<String,GmsAlarmCall>>());
			// m.put("okNoReason", new
			// HashMap<String,Map<String,GmsAlarmCall>>());
			// m.put("ok", new HashMap<String,Map<String,GmsAlarmCall>>());
			// m.put("nodealReason", new
			// HashMap<String,Map<String,GmsAlarmCall>>());

			ArrayList list = new ArrayList();
			// list.add(new GmsAlarmReceive());
			m.put("needDeal", list);
			// m.put("needDeal", new ArrayList<GmsAlarmReceive>());
			this.redisService.set(key, MAPPER.writeValueAsString(m),
					TOKEN_CHEACK_TIME);
		}
	}

	private String getRedisTodayCall() {
		String key = TOKEN_CALL_NUM + DateUtil.getDateTimeString("yyyyMMdd");
		return this.redisService.get(key);
	}

	// private String getRedisTodayReceive() {
	// String key = TOKEN_CALL_NUM + DateUtil.getDateTimeString("yyyyMMdd");
	// return this.redisService.get(key);
	// }

	/**
	 * 获取当天的报警信息
	 * 
	 * @Title: queryTodayCall
	 * @Description: TODO
	 * @return
	 * @return: Map<String,List<GmsAlarmCall>>
	 * @throws Exception
	 */
	public Map<String, Object> queryTodayCall() throws Exception {
		// TODO Auto-generated method stub
		if (getRedisTodayCall() == null) {
			setRedisTodayCall();
			return MAPPER.readValue(getRedisTodayCall(), Map.class);
		}
		return MAPPER.readValue(getRedisTodayCall(), Map.class);
	}

	/**
	 * 设置接警信息
	 * 
	 * @Title: setRedisAlarmReceive
	 * @Description: TODO
	 * @param alarmReceive
	 * @return: void
	 * @throws Exception
	 */
	public void setRedisAlarmReceive(GmsAlarmReceive alarmReceive)
			throws Exception {
		String key = TOKEN_CALL_NUM + DateUtil.getDateTimeString("yyyyMMdd");
		// if (getRedisTodayCall() == null) {
		// Map m = new HashMap();
		// Object o = new Object();
		// m.put("nodealing", o);
		// m.put("dealing", o);
		// m.put("okNoReason", o);
		// m.put("ok", o);
		// m.put("nodealReason", o);
		// m.put("needDeal", o);
		// this.redisService.set(key, MAPPER.writeValueAsString(m),
		// TOKEN_CHEACK_TIME);
		// }
		Map<String, Object> allAlarmCall = (Map<String, Object>) MAPPER
				.readValue(getRedisTodayCall(), Map.class);
		List object = (List) allAlarmCall.get("needDeal");
		object.add(alarmReceive);
		allAlarmCall.put("needDeal", object);
		this.redisService.set(key, MAPPER.writeValueAsString(allAlarmCall),
				TOKEN_CHEACK_TIME);
	}

	/**
	 * 设置报警信息
	 * 
	 * @Title: setRedisAlarmCall
	 * @Description: TODO
	 * @param alarmCall
	 * @return: void
	 * @throws Exception
	 */
	public void setRedisAlarmCall(GmsAlarmCall alarmCall,
			GmsAlarmReceive alarmReceive) throws Exception {
		// TODO Auto-generated method stub
		String key = TOKEN_CALL_NUM + DateUtil.getDateTimeString("yyyyMMdd");
		setRedisTodayCall();
		Map<String, Object> allAlarmCall = (Map<String, Object>) MAPPER
				.readValue(getRedisTodayCall(), Map.class);
		if ("noPolice".equals(alarmCall.getStatus())) {// 这是该报警无人监听,人工推送
			Map object = (Map) allAlarmCall.get("noPolice");
			object.put(alarmCall.getAlarmID(), alarmCall);
			allAlarmCall.put("noPolice", object);
		}
		if ("nodealing".equals(alarmCall.getStatus())) {// 这是报警数量
			Map<String, Object> object = (Map) allAlarmCall.get("nodealingNum");
			object.put(alarmCall.getAlarmID(), alarmCall);
			allAlarmCall.put("nodealingNum", object);
		}
		if ("nodealing".equals(alarmCall.getStatus())) {// 这是未处理
			Map<String, Object> object = (Map) allAlarmCall.get("nodealing");
			object.put(alarmCall.getAlarmID(), alarmCall);
			allAlarmCall.put("nodealing", object);
		}
		if ("dealing".equals(alarmCall.getStatus())) {// 这是处理中需要携带警员数据的
			if (alarmReceive.getAlarmID().equals(alarmCall.getAlarmID())
					&& alarmReceive.getRefuseType() == null) {
				// Map m = new HashMap();
				// m.put(alarmCall.getAlarmID(),alarmReceive);
				List<GmsAlarmReceive> l = new ArrayList<GmsAlarmReceive>();
				l.add(alarmReceive);
				alarmCall.setReceiveList(l);
				Map object = (Map) allAlarmCall.get("dealing");
				object.put(alarmCall.getAlarmID(), alarmCall);
				allAlarmCall.put("dealing", object);
			}
		}
		if ("okNoReason".equals(alarmCall.getStatus())) {// 这是处理完成,是无需理由的处理
			Map object = (Map) allAlarmCall.get("okNoReason");
			object.put(alarmCall.getAlarmID(), alarmCall);
			// ((Map)allAlarmCall.get("okNoReason")).put(alarmCall.getAlarmID(),alarmCall);
			allAlarmCall.put("okNoReason", object);
			///////////全部添加
			Map object1 = (Map) allAlarmCall.get("ok");
			object1.put(alarmCall.getAlarmID(), alarmCall);
			allAlarmCall.put("ok", object1);
		}
		if ("ok".equals(alarmCall.getStatus())) {// 这是处理完成
			Map object = (Map) allAlarmCall.get("ok");
			object.put(alarmCall.getAlarmID(), alarmCall);
			// ((Map)allAlarmCall.get("ok")).put(alarmCall.getAlarmID(),alarmCall);
			allAlarmCall.put("ok", object);
		}
		// 查询警察拒绝接警的案件以及理由
		if (alarmReceive != null) {
			if ("needDeal".equals(alarmReceive.getRefuseType())) {// 这是未处理,且有理由的
				// List<GmsAlarmCall> list = allAlarmCall.get("nodealReason");
				List<GmsAlarmReceive> list1 = (List<GmsAlarmReceive>) allAlarmCall
						.get("needDeal");
				List<GmsAlarmReceive> list2 = new ArrayList<GmsAlarmReceive>();
				if (list1.size() < 1) {
					// Map m = new HashMap();
					// m.put(alarmCall,list2);
					// List l = new ArrayList();
					// l.add(alarmReceive);
					alarmCall.setReceiveList(list2);
					Map object = (Map) allAlarmCall.get("nodealReason");
					object.put(alarmCall.getAlarmID(), alarmCall);
					// ((Map)allAlarmCall.get("nodealReason")).put(alarmCall.getAlarmID(),m);
					allAlarmCall.put("nodealReason", object);
				} else {
					for (GmsAlarmReceive gmsAlarmReceive : list1) {
						if (gmsAlarmReceive.getAlarmID().equals(
								alarmCall.getAlarmID())) {
							list2.add(gmsAlarmReceive);
						}
					}
					// Map m = new HashMap();
					// m.put(alarmCall,list2);
					// List l = new ArrayList();
					// l.add(alarmReceive);
					alarmCall.setReceiveList(list2);
					Map object = (Map) allAlarmCall.get("nodealReason");
					object.put(alarmCall.getAlarmID(), alarmCall);
					// ((Map)allAlarmCall.get("nodealReason")).put(alarmCall.getAlarmID(),m);
					allAlarmCall.put("nodealReason", object);
				}
			}
		}
		this.redisService.set(key, MAPPER.writeValueAsString(allAlarmCall),
				TOKEN_CHEACK_TIME);// , TOKEN_CHEACK_TIME
	}

	/**
	 * 删除重复报警数据
	 * 
	 * @Title: delRedisAlarmCall
	 * @Description: TODO
	 * @param alarmCall
	 * @param alarmReceive
	 * @throws Exception
	 * @return: void
	 */
	public void delRedisAlarmCall(GmsAlarmCall alarmCall,
			GmsAlarmReceive alarmReceive) throws Exception {
		// TODO Auto-generated method stub
		String key = TOKEN_CALL_NUM + DateUtil.getDateTimeString("yyyyMMdd");
		Map<String, Object> allAlarmCall = (Map<String, Object>) MAPPER
				.readValue(getRedisTodayCall(), Map.class);
		if ("noPolice".equals(alarmCall.getStatus())) {// 这是人工推送
			Map object = (Map) allAlarmCall.get("noPolice");
			object.remove(alarmCall.getAlarmID());
			allAlarmCall.put("noPolice", object);
		}
		if ("dealing".equals(alarmCall.getStatus())) {// 这是未处理
			Map object = (Map) allAlarmCall.get("nodealing");
			object.remove(alarmCall.getAlarmID());
			allAlarmCall.put("nodealing", object);
		}
		if ("okNoReason".equals(alarmCall.getStatus())) {// 这是处理完成,是无需理由的处理
			Map object = (Map) allAlarmCall.get("nodealing");
			object.remove(alarmCall.getAlarmID());
			allAlarmCall.put("nodealing", object);
			Map object1 = (Map) allAlarmCall.get("dealing");
			object1.remove(alarmCall.getAlarmID());
			allAlarmCall.put("dealing", object1);
			//////////////201603023
			
			
		}
		if ("ok".equals(alarmCall.getStatus())) {// 这是处理完成
			Map object = (Map) allAlarmCall.get("nodealing");
			object.remove(alarmCall.getAlarmID());
			allAlarmCall.put("nodealing", object);
			Map object1 = (Map) allAlarmCall.get("dealing");
			object1.remove(alarmCall.getAlarmID());
			allAlarmCall.put("dealing", object1);
		}
		if (alarmReceive != null) {
			Map object = (Map) allAlarmCall.get("nodealReason");
			object.remove(alarmCall.getAlarmID());
			allAlarmCall.put("nodealReason", object);
		}
		this.redisService.set(key, MAPPER.writeValueAsString(allAlarmCall),
				TOKEN_CHEACK_TIME);// , TOKEN_CHEACK_TIME
	}
	public void delRedisAlarmCall(GmsAlarmCall alarmCall
			) throws Exception {
		// TODO Auto-generated method stub
		String key = TOKEN_CALL_NUM + DateUtil.getDateTimeString("yyyyMMdd");
		Map<String, Object> allAlarmCall = (Map<String, Object>) MAPPER
				.readValue(getRedisTodayCall(), Map.class);
		if(allAlarmCall!=null){
			if ("nodealing".equals(alarmCall.getStatus())) {// 这是未处理
				Map object = (Map) allAlarmCall.get("nodealing");
				Map object1 = (Map) allAlarmCall.get("cancleAlarm");
				object.remove(alarmCall.getAlarmID());
				object1.put(alarmCall.getAlarmID(), alarmCall);
				allAlarmCall.put("nodealing", object);
				allAlarmCall.put("cancleAlarm", object1);
			}
			if ("stop".equals(alarmCall.getStatus())) {// 这是终止的
				Map object = (Map) allAlarmCall.get("nodealing");
				Map object1 = (Map) allAlarmCall.get("ok");
				object.remove(alarmCall.getAlarmID());
				object1.put(alarmCall.getAlarmID(), alarmCall);
				allAlarmCall.put("nodealing", object);
				allAlarmCall.put("ok", object1);
			}
			this.redisService.set(key, MAPPER.writeValueAsString(allAlarmCall),
					TOKEN_CHEACK_TIME);// , TOKEN_CHEACK_TIME
		}
	}

	public void setRedisAlarmCallTime(GmsAlarmCall alarmCall, Member user)
			throws Exception {
		// TODO Auto-generated method stub
		String key = TOKEN_CALL_TIME + DateUtil.getDateTimeString("yyyyMMdd");
		String key1 = TOKEN_CALL_TIME1 + DateUtil.getDateTimeString("yyyyMMdd");
		Map callTime = getRedisTodayCallTime();
		if (callTime == null) {
			callTime = new HashMap<String, Object>();
		}
		Map callTime1 = getRedisTodayCallTime1();
		if (callTime1 == null) {
			callTime1 = new HashMap<String, Object>();
		}
		// Map<String, Object> allCallTime = new HashMap<String, Object>();
		AlarmCallTime a = new AlarmCallTime();
		a.setId(alarmCall.getAlarmID());
		a.setL(System.currentTimeMillis());
		a.setAlias(user.getAlias());
		callTime.put(alarmCall.getAlarmID(), a);
		callTime1.put(alarmCall.getAlarmID(), a);
		this.redisService.set(key, MAPPER.writeValueAsString(callTime),
				TOKEN_CHEACK_TIME);// , TOKEN_CHEACK_TIME
		this.redisService.set(key1, MAPPER.writeValueAsString(callTime1),
				TOKEN_CHEACK_TIME);// , TOKEN_CHEACK_TIME
	}

	public void setRedisAlarmCallTime(AlarmCallTime val) throws Exception {
		// TODO Auto-generated method stub
		String key = TOKEN_CALL_TIME + DateUtil.getDateTimeString("yyyyMMdd");
		String key1 = TOKEN_CALL_TIME1 + DateUtil.getDateTimeString("yyyyMMdd");
		Map callTime = getRedisTodayCallTime();
		if (callTime == null) {
			callTime = new HashMap<String, Object>();
		}
		Map callTime1 = getRedisTodayCallTime1();
		if (callTime1 == null) {
			callTime1 = new HashMap<String, Object>();
		}
		// Map<String, Object> allCallTime = new HashMap<String, Object>();
		callTime.put(val.getId(), val);
		callTime1.put(val.getId(), val);
		this.redisService.set(key, MAPPER.writeValueAsString(callTime),
				TOKEN_CHEACK_TIME);// , TOKEN_CHEACK_TIME
		this.redisService.set(key1, MAPPER.writeValueAsString(callTime1),
				TOKEN_CHEACK_TIME);// , TOKEN_CHEACK_TIME
	}

	public void delRedisAlarmCallTime(String id) throws Exception {
		// TODO Auto-generated method stub
		String key = TOKEN_CALL_TIME + DateUtil.getDateTimeString("yyyyMMdd");
		Map<String, Object> allCallTime = (Map<String, Object>) getRedisTodayCallTime();
		if(allCallTime!=null){
			allCallTime.remove(id);
			this.redisService.set(key, MAPPER.writeValueAsString(allCallTime),
					TOKEN_CHEACK_TIME);// , TOKEN_CHEACK_TIME
		}
	}

	public void delRedisAlarmCallTime1(String id) throws Exception {
		// TODO Auto-generated method stub
		String key = TOKEN_CALL_TIME1 + DateUtil.getDateTimeString("yyyyMMdd");
		Map<String, Object> allCallTime = (Map<String, Object>) getRedisTodayCallTime1();
		if(allCallTime!=null){
			allCallTime.remove(id);
			this.redisService.set(key, MAPPER.writeValueAsString(allCallTime),
					TOKEN_CHEACK_TIME);// , TOKEN_CHEACK_TIME
		}
	}

	public Map<?, ?> getRedisTodayCallTime() throws Exception {
		String key = TOKEN_CALL_TIME + DateUtil.getDateTimeString("yyyyMMdd");
		String string = this.redisService.get(key);
		if (string == null || string.equals("{}")) {
			return null;
		} else {
			return MAPPER.readValue(string, Map.class);
		}

	}

	public Map<?, ?> getRedisTodayCallTime1() throws Exception {
		String key = TOKEN_CALL_TIME1 + DateUtil.getDateTimeString("yyyyMMdd");
		String string = this.redisService.get(key);
		if (string == null || string.equals("{}")) {
			return null;
		} else {
			return MAPPER.readValue(string, Map.class);
		}

	}

	public boolean queryRedisTodayCallTime(String id) throws Exception {
		String key = TOKEN_CALL_TIME + DateUtil.getDateTimeString("yyyyMMdd");
		String string = this.redisService.get(key);
		if (string == null || string.equals("{}")) {
			return false;
		} else {
			Map readValue = MAPPER.readValue(string, Map.class);
			Iterator its = readValue.values().iterator();
			while (its.hasNext()) {
				JSONObject fromObject = JSONObject.fromObject(its.next());
				AlarmCallTime val = (AlarmCallTime) JSONObject.toBean(
						fromObject, AlarmCallTime.class);
				if (val.getId().equals(id)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean queryRedisTodayCallTime1(String id) throws Exception {
		String key = TOKEN_CALL_TIME1 + DateUtil.getDateTimeString("yyyyMMdd");
		String string = this.redisService.get(key);
		if (string == null || string.equals("{}")) {
			return false;
		} else {
			Map readValue = MAPPER.readValue(string, Map.class);
			Iterator its = readValue.values().iterator();
			while (its.hasNext()) {
				JSONObject fromObject = JSONObject.fromObject(its.next());
				AlarmCallTime val = (AlarmCallTime) JSONObject.toBean(
						fromObject, AlarmCallTime.class);
				if (val.getId().equals(id)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 设置jpush发送间隔时间
	 * 
	 * @Title: setJpushTime
	 * @Description: TODO
	 * @param time
	 * @return: void
	 */
	public void setJpushTime(String time) {
		String key = TOKEN_JPUSH_TIME;
		this.redisService.set(key, time);
	}

	/**
	 * 获取jpush间隔时间
	 * 
	 * @Title: getJpushTime
	 * @Description: TODO
	 * @return
	 * @return: String
	 */
	public String getJpushTime() {
		String key = TOKEN_JPUSH_TIME;
		String string = this.redisService.get(key);
		if (string == null) {
			return "120";
		} else {
			return string;
		}
	}

	/**
	 * get police push number
	 * 
	 * @Title: getJpushNum
	 * @Description: TODO
	 * @return
	 * @return: String
	 */
	public String getJpushNum() {
		String key = TOKEN_JPUSH_NUM;
		String string = this.redisService.get(key);
		if (string == null) {
			return "3";
		} else {
			return string;
		}

	}

	public void setJpushNum(String num) {
		String key = TOKEN_JPUSH_NUM;
		this.redisService.set(key, num);
	}

	public String getJpushNumToP(String id) throws Exception {
		String key = TOKEN_JPUSH_NUM + id;
		String string = this.redisService.get(key);
		String key1 = TOKEN_ALARM_NUM + id.toUpperCase();
		String string1 = this.redisService.get(key1);
		if (string == null) {
			if (null == string1) {
//				GmsAlarmReceive rpn = new GmsAlarmReceive();
//				rpn.setAlarmNum(1);
//				rpn.setAlarmID(id);
//				setRedisAlarmNum(rpn);
//				setJpushNumToP(id);
				return "1";
			}
			GmsAlarmReceive rp = MAPPER.readValue(string1, GmsAlarmReceive.class);
//			setRedisAlarmNum(rp);
//			setJpushNumToP(id);
			return rp.getAlarmNum()+1+"";
		} else {
			GmsAlarmReceive rp = MAPPER.readValue(string1, GmsAlarmReceive.class);
//			setRedisAlarmNum(rp);
//			setJpushNumToP(id);
			return rp.getAlarmNum() + 1 +"";
		}
	}

	public void setJpushNumToP(String id) throws Exception {
		String key = TOKEN_JPUSH_NUM + id;
		String string = this.redisService.get(key);
		String key1 = TOKEN_ALARM_NUM + id.toUpperCase();
		String string1 = this.redisService.get(key1);
		if (string == null) {
			if (null == string1) {
				this.redisService.set(key, "1", TOKEN_JPUSH_NUM_TOPTIME);
				GmsAlarmReceive rpn = new GmsAlarmReceive();
				rpn.setAlarmNum(1);
				rpn.setAlarmID(id);
				setRedisAlarmNum(rpn);
			}else{
			GmsAlarmReceive rp = MAPPER.readValue(string1, GmsAlarmReceive.class);
			this.redisService.set(key, rp.getAlarmNum()+"", TOKEN_JPUSH_NUM_TOPTIME);
			}
			} else {
			this.redisService.set(key, Integer.parseInt(string) + 1 + "",
					TOKEN_JPUSH_NUM_TOPTIME);
		}
	}

	// queryUserByUser
	public void delAddressUser() throws Exception {
		// TODO
		List<SYS_User> query = newDao.query(SYS_User.class,
				"select * from sys_user where tag ='tag_police';");
		String key1 = TOKEN_USER_ADDRESS
				+ DateUtil.getDateTimeString("yyyyMMdd");
		String string = this.redisService.get(key1);
		if (string == null) {

		} else {
			Map readValue = MAPPER.readValue(string, Map.class);

			for (SYS_User sys_User : query) {
				SYS_User user = queryUserByUser(sys_User.getUserName());

				if (user == null) {
					// delUsersAddress(user);
					readValue.remove(sys_User.getUserID());
				} else {
					if (CheckUtil.isNullStr(sys_User.getAlias())) {
						delUsersAddress(user);
					}
				}
			}
			this.redisService.set(key1, MAPPER.writeValueAsString(readValue),
					REDIS_TIME_30);

		}
	}

	public void delAddressMember() throws Exception {
		// TODO
		List<Member> query = newDao.query(Member.class,
				"select * from cms_user where tag ='tag_people';");
		String key1 = TOKEN_MEMBER_ADDRESS
				+ DateUtil.getDateTimeString("yyyyMMdd");
		String string = this.redisService.get(key1);
//		delMemberDisable(query);
		if (string == null) {

		} else {
			Map readValue = MAPPER.readValue(string, Map.class);
			for (Member cms_User : query) {
				Member user = queryUserByMember(cms_User.getUserName());
				if (user == null) {
					// delUsersAddress(user);
					readValue.remove(cms_User.getUserID());
				} else {
					if (CheckUtil.isNullStr(cms_User.getAlias())) {
						delMemberAddress(user);
					}
				}
			}
			this.redisService.set(key1, MAPPER.writeValueAsString(readValue),
					REDIS_TIME_30);
		}
	}
//	public void delMemberDisable(List<Member> query) throws Exception {
//		if(query!=null&&query.size()>0){
//			for (Member member : query) {
//				String string = this.redisService.get(key1);
//			}
//		}
//	}

	public Map getAlarmWithPeople(String alarmID) throws Exception {
		String key = TOKEN_ALARM_USER+alarmID.toUpperCase();
		String string = this.redisService.get(key);
		if (string == null||"{}".equals(string)) {
			return null;
		} else {
			Map readValue = MAPPER.readValue(string, Map.class);
			return readValue;
		}
	}

	public void setAlarmWithPeople(String alarmID, SYS_User sys_User) throws Exception {
		// TODO Auto-generated method stub
		String key = TOKEN_ALARM_USER+alarmID.toUpperCase();
		String string = this.redisService.get(key);
		if (string == null) {
			Map m = new HashMap();
			m.put(sys_User.getUserID(), "1");
			this.redisService.set(key, MAPPER.writeValueAsString(m),
					REDIS_TIME_1D);
		} else {
			Map readValue = MAPPER.readValue(string, Map.class);
			readValue.put(sys_User.getUserID(), readValue.size()+1+"");
			this.redisService.set(key, MAPPER.writeValueAsString(readValue),
					REDIS_TIME_1D);
		}
	}

	public double getRedisKmS() {
		String key = TOKEN_KM_S;
		String string = this.redisService.get(key);
		if (string == null) {
			setRedisKmS("8");
			return 8;
		} else {
			return Double.parseDouble(string.trim());
		}

	}
	
	public void setRedisKmS(String num) {
		String key = TOKEN_KM_S;
		this.redisService.set(key, num);
	}

	public String getCallKm() {
		String key = TOKEN_CALL_KM;
		String string = this.redisService.get(key);
		if (string == null) {
			setCallKm("99999");
			return "99999";
		} else {
			return string.trim();
		}
	}
	public void setCallKm(String num) {
		String key = TOKEN_CALL_KM;
		this.redisService.set(key, num);
	}

	public void editCall(String sqlcall) {
		// TODO Auto-generated method stub
		newDao.jdbcTemplate.execute(sqlcall);
	}
}
