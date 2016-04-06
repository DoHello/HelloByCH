package com.derbysoft.service.sys;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.derbysoft.dao.cms.NewsDao;
import com.derbysoft.dao.sys.DicDao;
import com.derbysoft.dao.sys.UserDao;
import com.derbysoft.entity.sys.SYS_Dic;
import com.derbysoft.entity.sys.SYS_User;

import dy.hrtworkframe.db.SQLUtil;
  

    
@Service
public class SYS_UserService {
	@Resource(name="systemDicDao")
	private DicDao systemDicDao;
	
	@Autowired
	private NewsDao newDao;
	
	@Resource(name = "userDao")
	private UserDao userDao;
	
	//查询警局的种类
	
	public List<SYS_Dic> findByCategory(String categoryID) throws Exception {
		return 	systemDicDao.findByCategory(categoryID);
	}
	
	public SYS_User findUser(SYS_User entity) throws Exception{
		String sql = SQLUtil.getQuerySQL(SYS_User.class).replace("\n", "")+" where UserID='"+entity.getUserID()+"'";
//		SYS_User entry = userDao.query(SYS_User.class, sql).get(0);
		return userDao.query(SYS_User.class, sql).get(0);	
		
	}
	/**
	 * 
	     * @discription 更新用户信息      
	     * @created 2016年2月15日 下午3:50:34     
	     * @param entity
	     * @throws Exception
	 */
	public void edit(SYS_User entity) throws Exception{
		List<SYS_User> query = userDao.query(SYS_User.class,"select * from sys_user where 1=1 and userName = '"+entity.getUserName()+"' ;");
		entity.setPassword(query.get(0).getPassword());
		userDao.update(entity);		
	}
	/**
	 * 
	     * @discription 更新用户信息   
	     * @created 2016年2月15日 下午3:50:34     
	     * @param entity
	     * @throws Exception
	 */
	public void update(SYS_User entity) throws Exception{
		userDao.update(entity);		
	}
	public List<SYS_User> findUser(String userName) throws   Exception{
		SYS_User sys_User = new SYS_User();
		sys_User.setUserName(userName);
		List<SYS_User> query = userDao.query(sys_User);
	return 	query;
	}
}
