package com.derbysoft.entity.sys;

import dy.hrtworkframe.annotation.Key;
import dy.hrtworkframe.annotation.Table;

@Table(name="SYS_Role")
public class SYS_Role  extends dy.hrtworkframe.entity.Role {

	/**
	 * 角色ID,主键
	 */
	@Key
	private String roleID;

	/**
	 * 角色名称
	 */
	private String roleName;

	/**
	 * 角色所属单位ID
	 */
	private String companyID;

	/**
	 * 角色所属单位名称
	 */
	private String companyName;

	/**
	 * 录入人员
	 */
	private String inputName;

	/**
	 * 录入时间
	 */
	private String inputDate;

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

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getInputName() {
		return inputName;
	}

	public void setInputName(String inputName) {
		this.inputName = inputName;
	}

	public String getInputDate() {
		return inputDate;
	}

	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}
}
