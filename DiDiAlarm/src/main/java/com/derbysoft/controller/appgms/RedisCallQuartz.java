package com.derbysoft.controller.appgms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.sql.dialect.mysql.ast.MysqlForeignKey.On;
import com.derbysoft.controller.service.BaseDaoController;
import com.derbysoft.entity.AlarmCallTime;
import com.derbysoft.entity.cms.GmsAlarmCall;
import com.derbysoft.entity.cms.GmsAlarmReceive;
import com.derbysoft.entity.cms.Member;
import com.derbysoft.entity.sys.SYS_User;
import com.derbysoft.jms.activemq.JpushSender;
import com.derbysoft.redis.service.UserService;

import dy.hrtworkframe.dao.BaseDaoImpl;

@Service
public class RedisCallQuartz extends BaseDaoController implements Job {

	@Autowired
	private UserService userService;

	@Autowired
	private BaseDaoImpl baseDaoImpl;

	@Autowired
	private JpushSender jpushSender;

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			this.userService.delAddressUser();
			this.userService.delAddressMember();
			///////////
			
			///////////
			Map redisTodayCallTime = this.userService.getRedisTodayCallTime();
			if (redisTodayCallTime == null)
//				if (true)
				return;
			Iterator its = redisTodayCallTime.values().iterator();
			while (its.hasNext()) {
				JSONObject fromObject = JSONObject.fromObject(its.next());
				AlarmCallTime val = (AlarmCallTime) JSONObject.toBean(
						fromObject, AlarmCallTime.class);

				long result = System.currentTimeMillis() - val.getL();

				String jpushNumToP = this.userService.getJpushNumToP(val
						.getId());
				if ((result > Long.parseLong(this.userService.getJpushTime())
						* 1000L
						* Integer.parseInt(this.userService.getJpushNum()))
						|| (Integer.parseInt(jpushNumToP) > Integer
								.parseInt(this.userService.getJpushNum()))) {
					this.userService.delRedisAlarmCallTime(val.getId());
					Map m1 = new HashMap();

					GmsAlarmCall alarmCall = new GmsAlarmCall();
					alarmCall.setAlarmID(val.getId());
					Member userVal = new Member();
					userVal.setAlias(val.getAlias());
					m1.put("peopleCall", alarmCall);
					m1.put("userAlias", userVal);
					m1.put("msg", "当前没有空闲警员，我们将通过指挥中心调派。给您带来的不便请谅解!");
					m1.put("msgCode", "601");
					m1.put("styleNum", "3");
					this.jpushSender.send(m1, REFUSE);
					// 同一个id无人接警问题
					alarmCall.setStatus("noPolice");
					alarmCall.setMessage(null);
					baseDaoImpl.update(alarmCall);
				}
//////////
//				if ((result < Long.parseLong(this.userService.getJpushTime()) * 1000L)
//						&& (Integer.parseInt(jpushNumToP) <= Integer
//								.parseInt(this.userService.getJpushNum()))) {
//					Map alarmWithPeople = userService.getAlarmWithPeople(val.getId());
//					if(alarmWithPeople!=null){
//						Set<String> keySet = alarmWithPeople.keySet();
//						for (String string : keySet) {
//							List<GmsAlarmReceive> queryL = baseDaoImpl.query(GmsAlarmReceive.class, "select * from gms_alarm_receive where 1=1 and ischeack = 'noCheack' and stauts = 'dealing' and alarmid = '"+val.getId()+"' and userid = '"+string+"' and InputDate>=date(now()) and InputDate<DATE_ADD(date(now()),INTERVAL 1 DAY);");
//							if(queryL!=null&&queryL.size()>0){
//								val.setL(System.currentTimeMillis());
//								this.userService.setRedisAlarmCallTime(val);
//								this.userService.setJpushNumToP(val.getId());
//								List query1= this.baseDaoImpl.query(GmsAlarmCall.class,
//										"select * from gms_alarm_call where alarmid = '"
//												+ val.getId() + "'");
//								List list = getPolice((GmsAlarmCall) query1.get(0));
//								List queryM = this.baseDaoImpl.query(Member.class,
//										"select * from cms_user where username = '"
//												+ ((GmsAlarmCall) query1.get(0)).getPhone()
//												+ "'");
//								Map m = new HashMap();
//								int i = Integer.parseInt(jpushNumToP);
//								if (list!=null&&list.size() > 0) {
//									m.put("who", "police");
//									m.put("peopleCall", query1.get(0));
//									m.put("poliseFirst", list.get(0));
//									m.put("userAlias", queryM.get(0));
//									m.put("styleNum", "5");
//									this.jpushSender.send(m, "POLICE");
//								} else {
//									this.userService.delRedisAlarmCallTime(val.getId());
//									GmsAlarmCall alarmCall = new GmsAlarmCall();
//									Map m1 = new HashMap();
//									alarmCall.setAlarmID(val.getId());
//									Member userVal = new Member();
//									userVal.setAlias(val.getAlias());
//									m1.put("peopleCall", alarmCall);
//									m1.put("userAlias", userVal);
//									m1.put("msg", "当前没有空闲警员，我们将通过指挥中心调派。给您带来的不便请谅解!");
//									m1.put("msgCode", "601");
//									m1.put("styleNum", "3");
//									this.jpushSender.send(m1, REFUSE);
//								}
//							}
//						}
//					}
//					
//				}
				//////////////
				if ((result > Long.parseLong(this.userService.getJpushTime()) * 1000L)
						&& (Integer.parseInt(jpushNumToP) <= Integer
								.parseInt(this.userService.getJpushNum()))) {
					val.setL(System.currentTimeMillis());
					this.userService.setRedisAlarmCallTime(val);
					this.userService.setJpushNumToP(val.getId());
					List<GmsAlarmCall> query = this.baseDaoImpl.query(GmsAlarmCall.class,
							"select * from gms_alarm_call where alarmid = '"
									+ val.getId() + "'");
					List list = getPolice(query.get(0));
					List queryM = this.baseDaoImpl.query(Member.class,
							"select * from cms_user where username = '"
									+ query.get(0).getPhone()
									+ "'");
					Map m = new HashMap();
					int i = Integer.parseInt(jpushNumToP);
					if (list!=null&&list.size() > 0) {
						m.put("who", "police");
						m.put("peopleCall", query.get(0));
						m.put("poliseFirst", list.get(0));
						m.put("userAlias", queryM.get(0));
						m.put("styleNum", "5");
						this.jpushSender.send(m, "POLICE");
					} else {
						this.userService.delRedisAlarmCallTime(val.getId());
						GmsAlarmCall alarmCall = new GmsAlarmCall();
						Map m1 = new HashMap();
						alarmCall.setAlarmID(val.getId());
						Member userVal = new Member();
						userVal.setAlias(val.getAlias());
						m1.put("peopleCall", alarmCall);
						m1.put("userAlias", userVal);
						m1.put("msg", "当前没有空闲警员，我们将通过指挥中心调派。给您带来的不便请谅解!");
						m1.put("msgCode", "601");
						m1.put("styleNum", "3");
						this.jpushSender.send(m1, REFUSE);
						// 同一个id无人接警问题
						alarmCall.setStatus("noPolice");
						alarmCall.setMessage(null);
						baseDaoImpl.update(alarmCall);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Map people2 = new HashMap();
		people2.put("4", "一");
		people2.put("1", "一");
		people2.put("2", "一");
		people2.put("3", "一");
		people2.put("0", "一");
		people2.put("9", "一");
		System.out.println(people2.toString());
		if(people2!=null&&people2.size()>0){
			Set keySet2 = people2.keySet();
			List list = new ArrayList(keySet2);
			for (Object object : list) {
				
				System.out.println(object.toString());
			}
		}
		List<String> luser = new ArrayList();
		String user = "111";
		int i = 1;
		while(i<10){
		if(luser.size()>0){
			for (int j = 0; j < luser.size(); j++) {
				luser.add(user);
			}
		}else{
			luser.add(user);
		}
		i++;
		}
	}
}