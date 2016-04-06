package com.derbysoft.entity.cms;

import org.springframework.web.multipart.MultipartFile;

import dy.hrtworkframe.annotation.Key;
import dy.hrtworkframe.annotation.Table;

@Table(name="gms_userOrder" )
public class UserOrder {		

	/**
	 * 主键
	 */
	@Key
	private String orderID;
	private String phone;
	private String police;
	private String policeID;	
	private String wordes;
	/**
	 * 
	 */
	private String userID;
	/**
	 *  
	 */
	private String realName;

	private String userName;
	/**
	 * 警察
	 */
	private String orgName;
	
	/**
	 * 预约时间
	 */
	private String orderTime;
	
	/**
	 * 状态
	 */
	private String status;
	
	/**
	 * 创建时间 
	 */
	private String createTime;
	
	/**
	 * 更新时间
	 */
	
	private String updateTime;
	/**
	 * 上传文件
	 */
	
	private MultipartFile[] file;
	
	public String getWordes() {
		return wordes;
	}
	public void setWordes(String wordes) {
		this.wordes = wordes;
	}
	private String orderFile;
	
	public String getPolice() {
		return police;
	}
	public void setPolice(String police) {
		this.police = police;
	}
	public String getPoliceID() {
		return policeID;
	}
	public void setPoliceID(String policeID) {
		this.policeID = policeID;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public MultipartFile[] getFile() {
		return file;
	}
	public void setFile(MultipartFile[] file) {
		this.file = file;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOrderFile() {
		return orderFile;
	}
	public void setOrderFile(String orderFile) {
		this.orderFile = orderFile;
	}
}
