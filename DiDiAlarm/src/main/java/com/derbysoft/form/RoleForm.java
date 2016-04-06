package com.derbysoft.form;

import java.util.List;

import com.derbysoft.entity.sys.SYS_Role;

import dy.hrtworkframe.entity.UserModule;

public class RoleForm  {
	
	private SYS_Role role;
	
	private List<UserModule> userModules;


	public SYS_Role getRole() {
		return role;
	}

	public void setRole(SYS_Role role) {
		this.role = role;
	}

	public List<UserModule> getUserModules() {
		return userModules;
	}

	public void setUserModules(List<UserModule> userModules) {
		this.userModules = userModules;
	}

	
	
}
