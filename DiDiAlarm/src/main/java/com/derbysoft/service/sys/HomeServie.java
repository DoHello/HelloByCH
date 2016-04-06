package com.derbysoft.service.sys;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.stereotype.Service;



import dy.hrtworkframe.util.DateUtil;

@Service
public class HomeServie {
	/**
	 * 
	     * @discription 以yyyy.MM.dd的形式获取前一天的时间
	     * @author Knight      
	     * @created 2016年2月24日 下午5:39:47     
	     * @return
	     * @throws Exception
	 */
	public String findByCategoryByUserID() throws Exception {
		 
		 SimpleDateFormat dateFm = new SimpleDateFormat("yyyy.MM.dd"); //格式化当前系统日期
	     String afterDayDate = DateUtil.getAfterDayDate("-1");
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		 long millionSeconds = 0;
		try {
			millionSeconds = sdf.parse(afterDayDate).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		 return dateFm.format(millionSeconds);
	}
	
}
