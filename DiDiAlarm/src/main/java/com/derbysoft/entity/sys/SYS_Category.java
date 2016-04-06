package com.derbysoft.entity.sys;

import dy.hrtworkframe.annotation.Key;
import dy.hrtworkframe.annotation.Table;

@Table
public class SYS_Category {
	
	@Key
	private String categoryID;  //类型id
	/**
	 * 类型名称
	 */
	private String categoryName; //类型名称
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
