package com.derbysoft.controller.appgms;

import javax.annotation.Resource;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.dao.DataAccessException;

import com.derbysoft.dao.cms.NewsDao;

import dy.hrtworkframe.util.DateUtil;

public class OrderQuartz implements Job{

	
	@Resource(name="newsDao")
	public NewsDao userOrderDao;
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {		
	String dateString = DateUtil.getDateString();	
//	String callDisabled = DateUtil.getDateTimeString();	
		String sql="delete  gms_userorder  set STATUS ='noReservation'  where  OrderTime < " + "'"+dateString+"' ;";
//		String sqlcall="UPDATE  gms_alarmCall  set STATUS ='timeDisabled'  where  (STATUS = 'nodealing' or STATUS = 'noPolice') and OrderTime > NOW() - interval 24 hour ;";
		try {
			userOrderDao.jdbcTemplate.execute(sql);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
