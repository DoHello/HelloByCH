package com.derbysoft.entity.sys;

import dy.hrtworkframe.annotation.Key;
import dy.hrtworkframe.annotation.Table;

/**
 * 模块权限
 * 
 * @author jiangtao 18674842761
 */
@Table(name="SYS_RoleModule")
public class SYS_RoleModule {
	 
	 
	/**
	 * 主键
	 */
	@Key
	private String romoID;

	/**
	 * 用户ID
	 */
	private String roleID;

	/**
	 * 用户名称
	 */
	private String roleName;

	/**
	 * 模块id
	 */
	private String moduleID;

	/**
	 * 模块名称
	 */
	private String moduleName;
	
	/**
	 * 录入时间
	 */
	private String inputDate;

	/**
	 * 录入人员
	 */
	private String inputName;

	public String getRomoID() {
		return romoID;
	}

	public void setRomoID(String romoID) {
		this.romoID = romoID;
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

	public String getInputName() {
		return inputName;
	}

	public void setInputName(String inputName) {
		this.inputName = inputName;
	}

}
