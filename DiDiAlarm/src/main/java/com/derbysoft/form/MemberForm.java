package com.derbysoft.form;

import java.util.ArrayList;
import java.util.List;

import com.derbysoft.entity.cms.Member;
import com.derbysoft.entity.sys.UserRole;

import dy.hrtworkframe.entity.Pager;
import dy.hrtworkframe.entity.UserModule;

public class MemberForm {



	private Member user=new  Member() ;
	
	private List<UserModule> modelList = new ArrayList<UserModule>();
	
	
	private List<UserRole>  roleList = new ArrayList<UserRole>();
	
	
	private Pager pager;
	/**
	 * 查询条件开始日期
	 */
	private String starDate;
	/**
	 * 查询条件结束日期
	 */
	private String endDate;
	/**
	 * 验证码
	 */
	private String ValidationCode;

	
	public MemberForm() {
		super();
	}

	public MemberForm(String userName) {
		this.user.setUserName(userName);
	}
	
	
	/**
	 * 
	
	* @Title: getPage 
	
	* @Description: TODO(获取分页数据 ) 
	
	* @param @param phone    获取分页数据
	
	* @return void    返回类型 
	
	* @throws
	 */
	public Pager getPager() {
		return pager;
	}
	public void setPager(Pager pager) {
		this.pager = pager;
	}
	public String getStarDate() {
		return starDate;
	}
	public void setStarDate(String starDate) {
		this.starDate = starDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Member getUser() {
		return user;
	}

	public void setUser(Member user) {
		this.user = user;
	}

	public String getValidationCode() {
		return ValidationCode;
	}

	public void setValidationCode(String validationCode) {
		ValidationCode = validationCode;
	}

	public List<UserModule> getModelList() {
		return modelList;
	}

	public void setModelList(List<UserModule> modelList) {
		this.modelList = modelList;
	}

	public List<UserRole> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<UserRole> roleList) {
		this.roleList = roleList;
	}
	
	

	
}
