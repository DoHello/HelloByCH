package com.derbysoft.dao.sys;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.derbysoft.entity.sys.SYS_Role;
import com.derbysoft.entity.sys.SYS_RoleButton;

import dy.hrtworkframe.dao.BaseDaoImpl;
import dy.hrtworkframe.db.SQLUtil;

@Transactional
@Repository("roleDao")
public class RoleDao extends BaseDaoImpl {
	public SYS_Role findByID(String roleID) {
		String sql = SQLUtil.getQuerySQL(SYS_Role.class) + " where RoleID='" + roleID + "'";
		return super.query(SYS_Role.class, sql).get(0);
	}
	
	
	public List<SYS_Role> findByCompany(String companyID) {
		String sql = SQLUtil.getQuerySQL(SYS_Role.class) + " where CompanyID='" + companyID + "'" ;
		return super.query(SYS_Role.class, sql);
	}
	public List<SYS_Role> findRoleGyID() {
		String sql = SQLUtil.getQuerySQL(SYS_Role.class) + " group by RoleID" ;
		return super.query(SYS_Role.class, sql);
	}
	public List<SYS_Role> findByCompanys(String companyID) {
		String sql = SQLUtil.getQuerySQL(SYS_Role.class) + " group by CompanyID ";
		return super.query(SYS_Role.class, sql);
	}
	
	
	public List<SYS_RoleButton> findButton(String requestMapping, String roleID) {
		String sql = SQLUtil.getQuerySQL(SYS_RoleButton.class) + " where RequestMapping='" + requestMapping + "' and RoleID='" + roleID + "'";
		return super.query(SYS_RoleButton.class, sql);
	}
	
	
	public String findButtonID(String requestMapping, String roleID) {
		String sql = SQLUtil.getQuerySQL(SYS_RoleButton.class) + " where RequestMapping='" + requestMapping + "' and RoleID='" + roleID + "'";
		
		List<SYS_RoleButton> list = super.query(SYS_RoleButton.class, sql);
		
		String buttonID = "";
		for (SYS_RoleButton button : list) {
			buttonID += "|" + button.getButtonID();
		}
		return buttonID;
	}
	
	
}
