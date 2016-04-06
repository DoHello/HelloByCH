package com.derbysoft.entity.cms;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dy.hrtworkframe.annotation.Key;
import dy.hrtworkframe.annotation.Table;
import dy.hrtworkframe.annotation.Temporary;
@Table(name="gms_alarm_receive")
public class GmsAlarmReceive implements Serializable{

	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 8040904155173135799L;
	@Key
	private String alarmReceiveID;
	@JsonIgnore
	private String alarmRefuseID;
	@Temporary
	private String memberHeadImg;
    @Temporary
	private String messageType;
	private String alarmID;
	@Temporary
	private String message;
	@Temporary
	private String messageText;
//	@JsonIgnore
	private String userID;
	
	private String userName;
	
	@JsonIgnore
	private String companyID;
	@Temporary
	private String callPhone;
	@Temporary
	private String callTime;
	@Temporary
	private String callAddress;
	@Temporary
	private String callContext; 
	private String areaID;
	
	private String areaName;
	@Temporary
	private String name;
	@Temporary
	private String audioLength;
	
	@Temporary
	private List<String> img;
	
	private String realName;
	
	
	


	public String getAreaID() {
		return areaID;
	}
	public void setAreaID(String areaID) {
		this.areaID = areaID;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getImg() {
		return img;
	}
	public void setImg(List<String> img) {
		this.img = img;
	}
	public String getAudioLength() {
		return audioLength;
	}
	public void setAudioLength(String audioLength) {
		this.audioLength = audioLength;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessageText() {
		return messageText;
	}
	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}
	public String getMemberHeadImg() {
		return memberHeadImg;
	}
	public void setMemberHeadImg(String memberHeadImg) {
		this.memberHeadImg = memberHeadImg;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getCallAddress() {
		return callAddress;
	}
	public void setCallAddress(String callAddress) {
		this.callAddress = callAddress;
	}
	public String getCallContext() {
		return callContext;
	}
	public void setCallContext(String callContext) {
		this.callContext = callContext;
	}
	public String getCallTime() {
		return callTime;
	}
	public void setCallTime(String callTime) {
		this.callTime = callTime;
	}
	public String getCallPhone() {
		return callPhone;
	}
	public void setCallPhone(String callPhone) {
		this.callPhone = callPhone;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	private String orgName;
	private String refuseContext;
	private String refuseText;
	private String refuseType;
	private String caseType;
	private String caseTypeValue;
	

	public String getCaseTypeValue() {
		return caseTypeValue;
	}
	public void setCaseTypeValue(String caseTypeValue) {
		this.caseTypeValue = caseTypeValue;
	}
	private String inputDate;
	private String presentTime;
	public String getPresentTime() {
		return presentTime;
	}
	public void setPresentTime(String presentTime) {
		this.presentTime = presentTime;
	}
	@JsonIgnore
	private String ischeack;
	private String status;
	@JsonIgnore
	private String addressID;
	private String addressName;
	private String longitude;
	private String latitude;
	@Temporary
	@JsonIgnore
	private String tokenID;
    
    private String phone;
	@Temporary
	private int alarmNum;
	public int getAlarmNum() {
		return alarmNum;
	}
	public void setAlarmNum(int alarmNum) {
		this.alarmNum = alarmNum;
	}
	public String getTokenID() {
		return tokenID;
	}
	public void setTokenID(String tokenID) {
		this.tokenID = tokenID;
	}
	public String getRefuseText() {
		return refuseText;
	}
	public void setRefuseText(String refuseText) {
		this.refuseText = refuseText;
	}
	public String getCaseType() {
		return caseType;
	}
	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}
	public String getAlarmReceiveID() {
		return alarmReceiveID;
	}
	public void setAlarmReceiveID(String alarmReceiveID) {
		this.alarmReceiveID = alarmReceiveID;
	}
	public String getAlarmRefuseID() {
		return alarmRefuseID;
	}
	public void setAlarmRefuseID(String alarmRefuseID) {
		this.alarmRefuseID = alarmRefuseID;
	}
	public String getAlarmID() {
		return alarmID;
	}
	public void setAlarmID(String alarmID) {
		this.alarmID = alarmID;
	}
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
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getRefuseContext() {
		return refuseContext;
	}
	public void setRefuseContext(String refuseContext) {
		this.refuseContext = refuseContext;
	}
	public String getRefuseType() {
		return refuseType;
	}
	public void setRefuseType(String refuseType) {
		this.refuseType = refuseType;
	}
	public String getInputDate() {
		return inputDate;
	}
	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}
	public String getIscheack() {
		return ischeack;
	}
	public void setIscheack(String ischeack) {
		this.ischeack = ischeack;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAddressID() {
		return addressID;
	}
	public void setAddressID(String addressID) {
		this.addressID = addressID;
	}
	public String getAddressName() {
		return addressName;
	}
	public void setAddressName(String addressName) {
		this.addressName = addressName;
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
}
