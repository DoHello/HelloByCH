package com.derbysoft.entity.sys;

import dy.hrtworkframe.annotation.Key;

/**
*    
* 项目名称：dy_service   
* 类名称：Parameters   
* 类描述：   
* 系统参数对象
* 创建人：LD   
* 创建时间：2015年2月5日 下午6:15:53   
* 修改人：LD   
* 修改时间：2015年2月5日 下午6:15:53   
* 修改备注：   
* @version    
*
 */
public class Parameters {

	/**
	 * 企业系统参数id
	 */
	@Key
	public String  parametersID;
	/**
	 * 企业id
	 */
	public String companyID;
	
	/**
	 * 企业名称
	 */
	public String companyName;
	
	/**
	 * 模块id
	 */
	public String  moduleID;
	/**
	 * 模块名称
	 */
	public String moduleName;
	
	/**
	 * 字典参数id
	 */
	public String dicParametersID;
	/**
	 * 字典参数名称
	 */
	public String dicParametersName; 
	/**
	 * 字典参数值
	 */
	public String dicParametersValue;
	/**
	 * 应用平台
	 */
	public String appPlatform; 
	/**
	 * 类型id
	 */
	public String categoryID; 
	/**
	 * 类型名称
	 */
	public String categoryName;
	public String getParametersID() {
		return parametersID;
	}
	public void setParametersID(String parametersID) {
		this.parametersID = parametersID;
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
	public String getDicParametersID() {
		return dicParametersID;
	}
	public void setDicParametersID(String dicParametersID) {
		this.dicParametersID = dicParametersID;
	}
	public String getDicParametersName() {
		return dicParametersName;
	}
	public void setDicParametersName(String dicParametersName) {
		this.dicParametersName = dicParametersName;
	}
	public String getDicParametersValue() {
		return dicParametersValue;
	}
	public void setDicParametersValue(String dicParametersValue) {
		this.dicParametersValue = dicParametersValue;
	}
	public String getAppPlatform() {
		return appPlatform;
	}
	public void setAppPlatform(String appPlatform) {
		this.appPlatform = appPlatform;
	}
	public String getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	
	
	
}
