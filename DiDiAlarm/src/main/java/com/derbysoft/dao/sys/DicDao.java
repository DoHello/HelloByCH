package com.derbysoft.dao.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.derbysoft.entity.sys.SYS_Category;
import com.derbysoft.entity.sys.SYS_Dic;

import dy.hrtworkframe.dao.BaseDaoImpl;

@Transactional
@Repository("systemDicDao")
public class DicDao extends BaseDaoImpl {
	public List<SYS_Dic> findByCategory(String categoryID) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("categoryID", categoryID);
		
		List<SYS_Dic> dicList = this.query(SYS_Dic.class, parms);
		return dicList;
	}
	
	public SYS_Category getCategory(String categoryID) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("categoryID", categoryID);
		
		List<SYS_Category> categories = this.query(SYS_Category.class, parms);
		return categories.get(0);
	}
	
	
	public SYS_Dic findByID(String dicID) {
		SYS_Dic dic = new SYS_Dic();
		dic.setDicID(dicID);
		return super.queryByPrimary(dic);
	}
}
