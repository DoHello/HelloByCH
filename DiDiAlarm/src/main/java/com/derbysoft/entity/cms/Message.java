package com.derbysoft.entity.cms;

import java.io.Serializable;

import dy.hrtworkframe.annotation.Key;
import dy.hrtworkframe.annotation.Table;


@Table(name="cms_message")
public class Message  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6946436138176250033L;

	/**
	 * 主键id
	 */
	@Key
	private String messageID;
	
	private String userName;
	
	private String title;
	
	private String userID;
	
	private String context;
	
	private String createTime;
	private String phone;
	//1为群众,0为警察
    private String status;
	public String getMessageID() {
		return messageID;
	}

	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}




	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	
}
