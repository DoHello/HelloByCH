package com.derbysoft.service.sys;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.derbysoft.dao.cms.NewsDao;
import com.derbysoft.dao.sys.DicDao;
import com.derbysoft.dao.sys.UserDao;
import com.derbysoft.entity.cms.GmsAlarmCall;
import com.derbysoft.entity.cms.GmsAlarmCheack;
import com.derbysoft.entity.cms.GmsAlarmReceive;
  

    
@Service
public class CaseService {
	@Resource(name="systemDicDao")
	private DicDao systemDicDao;
	
	@Autowired
	private NewsDao newsDao;
	@Resource(name = "userDao")
	private UserDao userDao;
	
	/**
	 * 
	     * @discription 查找接警
	     * @author Knight      
	     * @created 2016年2月22日 下午3:45:20     
	     * @param receiveID
	     * @return
	     * @throws Exception
	 */
	
	public GmsAlarmReceive findReceiveByReceiveID (String  receiveID) throws Exception{
		GmsAlarmReceive gmsAlarmReceive = new GmsAlarmReceive();
		gmsAlarmReceive.setAlarmReceiveID(receiveID);
		List<GmsAlarmReceive> receiveList = newsDao.query(gmsAlarmReceive);
	    return receiveList.get(0);
	}
	
    /**
     * 
         * @discription 查看备案信息
         * @author Knight      
         * @created 2016年2月22日 下午2:53:07     
         * @param gmsAlarmReceive
         * @return
     */
     
	public List<GmsAlarmCheack> findCheackByCheackID (String  cheackID) throws Exception{
		GmsAlarmCheack gmsAlarmCheack = new GmsAlarmCheack();
		gmsAlarmCheack.setAlarmID(cheackID);
		List<GmsAlarmCheack> cheackList = newsDao.query(gmsAlarmCheack);
	    return cheackList;
	}
    /**
     * 
         * @discription 查看报警信息
         * @author Knight      
         * @created 2016年2月22日 下午2:53:07     
         * @param gmsAlarmReceive
         * @return
     */
     
	public List<GmsAlarmCall> findCallByCheackID (String  cheackID) throws Exception{
		GmsAlarmCall gmsAlarmCall = new GmsAlarmCall();
		gmsAlarmCall.setAlarmID(cheackID);
		List<GmsAlarmCall> cheackList = newsDao.query(gmsAlarmCall);
	    return cheackList;
	}
	
}