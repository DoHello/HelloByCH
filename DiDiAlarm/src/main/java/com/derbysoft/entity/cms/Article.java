package com.derbysoft.entity.cms;

import java.io.Serializable;

import dy.hrtworkframe.annotation.Key;
import dy.hrtworkframe.annotation.Table;

@Table(name="cms_article" )
public class Article  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6398145241206246001L;

	/**
	 * 主键
	 */
	@Key
	private String articleID;
	
	/**
	 * 标题 
	 */
	private String title;
	/**
	 * 标签ID
	 */
	private String nodeID;
	
	/**
	 * 录入 人
	 */
	private String publisher;
	
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
	private String sortID;
	
	/**
	 * 点击量
	 */
	private String hitTimes;
	
	/**
	 * 创建时间 
	 */
	private String createTime;
	
	/**
	 * 地址类型 
	 */
	private String localType;

	
	public String getArticleID() {
		return articleID;
	}

	public void setArticleID(String articleID) {
		this.articleID = articleID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNodeID() {
		return nodeID;
	}

	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getAbstractz() {
		return abstractz;
	}

	public void setAbstractz(String abstractz) {
		this.abstractz = abstractz;
	}



	public String getSortID() {
		return sortID;
	}

	public void setSortID(String sortID) {
		this.sortID = sortID;
	}

	public String getHitTimes() {
		return hitTimes;
	}

	public void setHitTimes(String hitTimes) {
		this.hitTimes = hitTimes;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getLocalType() {
		return localType;
	}

	public void setLocalType(String localType) {
		this.localType = localType;
	}
	
}
