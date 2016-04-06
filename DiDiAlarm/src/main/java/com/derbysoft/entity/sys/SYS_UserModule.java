package com.derbysoft.entity.sys;

import dy.hrtworkframe.annotation.Key;
import dy.hrtworkframe.annotation.Table;

@Table(name="SYS_UserModule")
public class SYS_UserModule {
	/**
	 * 用户模块id,主键
	 */
	@Key
	public String userModuleID;

	/**
	 * 用户ID
	 */
	public String roleID;

	/**
	 * 用户账号
	 */
	public String roleName;

	/**
	 * 用户名称
	 */
	public String realName;

	/**
	 * 模块id
	 */
	public String moduleID;

	/**
	 * 模块名称
	 */
	public String moduleName;

	/**
	 * 
	 */
	public String inputDate;

	public String getUserModuleID() {
		return userModuleID;
	}

	public void setUserModuleID(String userModuleID) {
		this.userModuleID = userModuleID;
	}

	public String getRoleID() {
		return roleID;
	}

	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getModuleID() {
		return moduleID;
	}

	public void setModuleID(String moduleID) {
		this.moduleID = moduleID;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getInputDate() {
		return inputDate;
	}

	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}

}
