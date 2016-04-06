package com.derbysoft.controller.appgms;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.derbysoft.redis.service.UserService;

import dy.hrtworkframe.util.DateUtil;
@Service
public class RedisQuartz implements Job {

	@Autowired
	private UserService userService;
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		userService.setRedisCheack();
		try {
			String sqlcall="UPDATE  gms_alarm_Call  set STATUS ='timeDisabled'  where  (STATUS = 'nodealing' or STATUS = 'noPolice') and inputDate <= NOW() - interval 24 hour ;";
			userService.setRedisTodayCall();
			userService.editCall(sqlcall);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		System.out.println(DateUtil.getDateTimeString("yyyyMMdd"));
		int i =0;
		System.out.println("00" + ++i);
	}

}
