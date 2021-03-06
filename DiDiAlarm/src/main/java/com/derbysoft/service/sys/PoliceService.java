package com.derbysoft.service.sys;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.derbysoft.dao.cms.NewsDao;
import com.derbysoft.dao.sys.UserDao;
import com.derbysoft.entity.cms.GmsAlarmCall;
import com.derbysoft.entity.cms.GmsAlarmReceive;
import com.derbysoft.entity.sys.SYS_User;
  

    
@Service
public class PoliceService {

	@Autowired
	private NewsDao newsDao;
	
	@Resource(name = "userDao")
	private UserDao userDao;
	
	//查询已备案的案件
	
	public List<GmsAlarmCall> findByCategoryByUserID(SYS_User user) throws Exception {
		//int i= (page-1)*10;
        String w="select * from (select * from gms_alarm_call where  AlarmID in (select AlarmID from gms_alarm_receive where AlarmRefuseID  is  null  and ischeack='okcheack' and userid ='"+user.getUserID()+"' )) a LEFT JOIN (select InputDate as receiveUpdate ,AlarmID , AlarmReceiveID from gms_alarm_receive where AlarmRefuseID  is  null  and ischeack='okcheack' and userid ='"+user.getUserID()+"' ) b ON b.AlarmID=a.AlarmID where 1=1 ORDER BY a.inputdate DESC ";//LIMIT "+i+" ,10";
        List<GmsAlarmCall> query2 = newsDao.query(GmsAlarmCall.class, w);
        return query2;
	}

	//查询未备案的案件
	public List<GmsAlarmReceive> findNoCheackByUserID(SYS_User user){
		  //int i= (page-1)*10;
	      String w="select * from (select * from gms_alarm_call where  AlarmID in (select AlarmID from gms_alarm_receive where  AlarmRefuseID  is  null and ischeack='nocheack' and userid ='"+user.getUserID()+"' )) a LEFT JOIN (select InputDate as receiveUpdate ,AlarmID , AlarmReceiveID from gms_alarm_receive where  AlarmRefuseID  is  null and ischeack='nocheack' and userid ='"+user.getUserID()+"' ) b ON b.AlarmID=a.AlarmID where 1=1 ORDER BY a.inputdate DESC";// LIMIT "+i+" ,10";
	      List<GmsAlarmReceive> query = newsDao.query(GmsAlarmReceive.class,w);
		return query;
  	}
	
}