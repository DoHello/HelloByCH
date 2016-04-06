package com.derbysoft.entity.cms;

import java.io.Serializable;

import dy.hrtworkframe.annotation.Key;
import dy.hrtworkframe.annotation.Table;
import dy.hrtworkframe.annotation.Temporary;
@SuppressWarnings("all")
@Table(name="gms_alarm_cheack")
public class GmsAlarmCheack implements Serializable{
    
	@Key
	public String alarmReceiveID;
	public String alarmID;
	public String inputTime;
	public String members;
	public String carts;
	public String bads;
	public String stauts;
	public String context;

	
	public String dealStauts;
	public String getDealStauts() {
		return dealStauts;
	}
	public void setDealStauts(String dealStauts) {
		this.dealStauts = dealStauts;
	}
	@Temporary
	private String callStutes;
	
	public String getAlarmReceiveID() {
		return alarmReceiveID;
	}
	public void setAlarmReceiveID(String alarmReceiveID) {
		this.alarmReceiveID = alarmReceiveID;
	}
	public String getCallStutes() {
		return callStutes;
	}
	public void setCallStutes(String callStutes) {
		this.callStutes = callStutes;
	}
	public String getAlarmID() {
		return alarmID;
	}
	public void setAlarmID(String alarmID) {
		this.alarmID = alarmID;
	}
	public String getInputTime() {
		return inputTime;
	}
	public void setInputTime(String inputTime) {
		this.inputTime = inputTime;
	}
	public String getMembers() {
		return members;
	}
	public void setMembers(String members) {
		this.members = members;
	}
	public String getCarts() {
		return carts;
	}
	public void setCarts(String carts) {
		this.carts = carts;
	}
	public String getBads() {
		return bads;
	}
	public void setBads(String bads) {
		this.bads = bads;
	}
	public String getStauts() {
		return stauts;
	}
	public void setStauts(String stauts) {
		this.stauts = stauts;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	
}
