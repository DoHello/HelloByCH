package com.derbysoft.entity.cms;

import dy.hrtworkframe.annotation.Table;

@Table(name="cms_news")
public class User_News {


	private String newsID;
    
    private String userID;

	public String getNewsID() {
		return newsID;
	}

	public void setNewsID(String newsID) {
		this.newsID = newsID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}


    
}
