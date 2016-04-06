package com.derbysoft.service.sys;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.derbysoft.dao.cms.NewsDao;
import com.derbysoft.dao.sys.DicDao;
import com.derbysoft.dao.sys.UserDao;
import com.derbysoft.entity.sys.SYS_Dic;
  

    
@Service
public class DicService {
	@Resource(name="systemDicDao")
	private DicDao systemDicDao;
	
	@Autowired
	private NewsDao newDao;
	@Resource(name = "userDao")
	private UserDao userDao;
	
	//查询警局的种类
	
	public List<SYS_Dic> findByCategory(String categoryID){
		return 	systemDicDao.findByCategory(categoryID);
	}

	
	public <T> List<T> query( final Class<T> class1){
		return 	systemDicDao.query(class1);
	}
}
