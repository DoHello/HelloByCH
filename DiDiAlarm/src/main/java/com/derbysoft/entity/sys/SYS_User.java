package com.derbysoft.entity.sys;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dy.hrtworkframe.annotation.Column;
import dy.hrtworkframe.annotation.Key;
import dy.hrtworkframe.annotation.Table;
import dy.hrtworkframe.annotation.Temporary;
import dy.hrtworkframe.entity.User;

/**
 *    
 * 项目名称：dy_service   
 * 类名称：UserInformation   
 * 类描述：   
 * 用户基本信息类
 * 创建人：LD   
 * 创建时间：2015年2月6日 上午9:13:33   
 * 修改人：LD   
 * 修改时间：2015年2月6日 上午9:13:33   
 * 修改备注：   
 * @version    
 *
 */
@Table(name="SYS_User")
public class SYS_User extends User implements Serializable{
	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 367394899696257405L;
	@Temporary
	private String operatInfo;
	@Temporary
	private String searchText;
	private String tokenID;
    @Temporary
	private String alarmID;
	
	@Temporary
	private String isWorking;
	private String policeNumber;
	@Temporary
	private String degree;
	
	public String getAlarmID() {
		return alarmID;
	}

	public void setAlarmID(String alarmID) {
		this.alarmID = alarmID;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	
	public String getOperatInfo() {
		return operatInfo;
	}

	public void setOperatInfo(String operatInfo) {
		this.operatInfo = operatInfo;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getPoliceNumber() {
		return policeNumber;
	}

	public void setPoliceNumber(String policeNumber) {
		this.policeNumber = policeNumber;
	}




	public String getTokenID() {
		return tokenID;
	}

	public void setTokenID(String tokenID) {
		this.tokenID = tokenID;
	}

	/**
	 * 用户id,主键
	 */
	@Key
	private String userID;

	/**
	 * 用户名称
	 */
	private String userName;

	/**
	 * 用户密码
	 */
	@JsonIgnore
	private String password;
	/**
	 * 用户头像
	 */
	private String headImg;
	private String job;
	private String jobValue;
	private String underArea;
	private String underValue;
	private String policeLevel;
	
	public String getPoliceLevel() {
		return policeLevel;
	}

	public void setPoliceLevel(String policeLevel) {
		this.policeLevel = policeLevel;
	}

	public String getPoliceLevelValue() {
		return policeLevelValue;
	}

	public void setPoliceLevelValue(String policeLevelValue) {
		this.policeLevelValue = policeLevelValue;
	}

	private String policeLevelValue;
	public String getUnderArea() {
		return underArea;
	}

	public void setUnderArea(String underArea) {
		this.underArea = underArea;
	}

	public String getUnderValue() {
		return underValue;
	}

	public void setUnderValue(String underValue) {
		this.underValue = underValue;
	}

	public String getJobValue() {
		return jobValue;
	}

	public void setJobValue(String jobValue) {
		this.jobValue = jobValue;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	/**
	 * 单位id
	 */
	@Column(name="orgID")
	private String companyID;

	/**
	 * 单位名称
	 */
	@Column(name="orgName")
	private String companyName;
	
	
	private String longitude;
	private String latitude;
	private String distance;
	public String workStauts;
	public String workDealing;
	public String getWorkStauts() {
		return workStauts;
	}

	public void setWorkStauts(String workStauts) {
		this.workStauts = workStauts;
	}

	public String getWorkDealing() {
		return workDealing;
	}

	public void setWorkDealing(String workDealing) {
		this.workDealing = workDealing;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
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
	
	private String storeID;

	private String storeName;
	
	/**
	 * 真实姓名
	 */
	private String realName;

	/**
	 * 手机
	 */
	private String phone;

	/**
	 * QQ号
	 */
	private String qq;

	/**
	 * 电子邮件
	 */
	private String email;

	/**
	 * 身份证
	 */
	private String idCard;

	/**
	 * 角色ID
	 */
	private String roleID;

	/**
	 * 角色名称
	 */
	private String roleName;

	/**
	 * 性别
	 */
	private String sexID;

	/**
	 * 性别
	 */
	private String sexName;

	/**
	 * 录入人员编码
	 */
	private String inputID;

	/**
	 * 录入人员
	 */
	private String inputName;

	/**
	 * 创建日期
	 */
	private String inputDate;

	/**
	 * 修改人
	 */
	private String modifyID;

	/**
	 * 修改人
	 */
	private String modifyName;

	/**
	 * 修改人
	 */
	private String modifyDate;
	private String alias;
	private String tag;
	
	private String areaID;
	private String area;
	private String province;
	private String provinceID;
	private String city;
	private String cityID;

	public String getIsWorking() {
		return isWorking;
	}

	public void setIsWorking(String isWorking) {
		this.isWorking = isWorking;
	}

	public String getAreaID() {
		return areaID;
	}

	public void setAreaID(String areaID) {
		this.areaID = areaID;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getProvinceID() {
		return provinceID;
	}

	public void setProvinceID(String provinceID) {
		this.provinceID = provinceID;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCityID() {
		return cityID;
	}

	public void setCityID(String cityID) {
		this.cityID = cityID;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getRegistrationID() {
		return registrationID;
	}

	public void setRegistrationID(String registrationID) {
		this.registrationID = registrationID;
	}

	private String registrationID;
	
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
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

	public String getSexID() {
		return sexID;
	}

	public void setSexID(String sexID) {
		this.sexID = sexID;
	}

	public String getSexName() {
		return sexName;
	}

	public void setSexName(String sexName) {
		this.sexName = sexName;
	}

	public String getInputID() {
		return inputID;
	}

	public void setInputID(String inputID) {
		this.inputID = inputID;
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

	public String getModifyID() {
		return modifyID;
	}

	public void setModifyID(String modifyID) {
		this.modifyID = modifyID;
	}

	public String getModifyName() {
		return modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getStoreID() {
		return storeID;
	}

	public void setStoreID(String storeID) {
		this.storeID = storeID;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	
}
