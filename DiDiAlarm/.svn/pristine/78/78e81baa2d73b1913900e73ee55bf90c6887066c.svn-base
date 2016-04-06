package com.derbysoft.dao.sys;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.derbysoft.entity.sys.SYS_RoleAccess;

import dy.hrtworkframe.dao.BaseDaoImpl;
import dy.hrtworkframe.db.SQLUtil;

@Transactional
@Repository("roleAccessDao")
public class RoleAccessDao extends BaseDaoImpl {
	public List<SYS_RoleAccess> findData(String dataType, String roleID) {
		String sql = SQLUtil.getQuerySQL(SYS_RoleAccess.class) + " where DataType='" + dataType + "' and RoleID='" + roleID + "'";
		
		return this.query(SYS_RoleAccess.class, sql);
	}
}
