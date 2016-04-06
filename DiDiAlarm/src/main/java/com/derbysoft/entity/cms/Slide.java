package com.derbysoft.entity.cms;

import java.io.Serializable;

import dy.hrtworkframe.annotation.Key;
import dy.hrtworkframe.annotation.Table;

@Table(name="cms_slide")
public class Slide  implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -2724068574443141997L;

	/**
	 * 主键
	 */
	@Key
	private String slideID;
	
	/**
	 * 标题 
	 */
	private String title;

	/**
	 * 摘要 
	 */
	private String abstractz;
	
	/**
	 * 全文
	 */
	private String context;
	
	/**
	 * 标签
	 */
	private String picture;
	
	/**
	 * 地址
	 */
	private String url;
	

	/**
	 * 开始时间
	 */
	private String startTime;
	
	/**
	 * 开始时间
	 */
	private String endTime;
	/**
	 * 标签
	 */
	private String sortId;
	

	/**
	 * 创建时间 
	 */
	private String createTime;
	
	/**
	 * 点击量
	 */
	private String hitTimes;
	
	
	/**
	 * 地址类型 
	 */
	private String localType;


	public String getSlideID() {
		return slideID;
	}


	public void setSlideID(String slideID) {
		this.slideID = slideID;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getAbstractz() {
		return abstractz;
	}


	public void setAbstractz(String abstractz) {
		this.abstractz = abstractz;
	}


	public String getContext() {
		return context;
	}


	public void setContext(String context) {
		this.context = context;
	}


	public String getPicture() {
		return picture;
	}


	public void setPicture(String picture) {
		this.picture = picture;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}



	public String getStartTime() {
		return startTime;
	}


	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}


	public String getEndTime() {
		return endTime;
	}


	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}


	public String getSortId() {
		return sortId;
	}


	public void setSortId(String sortId) {
		this.sortId = sortId;
	}


	public String getCreateTime() {
		return createTime;
	}


	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}


	public String getHitTimes() {
		return hitTimes;
	}


	public void setHitTimes(String hitTimes) {
		this.hitTimes = hitTimes;
	}


	public String getLocalType() {
		return localType;
	}


	public void setLocalType(String localType) {
		this.localType = localType;
	}


	
}
