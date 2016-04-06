package com.derbysoft.dao.cms;

import dy.hrtworkframe.annotation.Table;
@Table(name="cms_user_news")
public class MemberAndNews {
   private String userID ;
   private String newsID ;
public String getUserID() {
	return userID;
}
public void setUserID(String userID) {
	this.userID = userID;
}
public String getNewsID() {
	return newsID;
}
public void setNewsID(String newsID) {
	this.newsID = newsID;
}
    	
}
