package com.derbysoft.entity.sys;

import dy.hrtworkframe.annotation.Table;

@Table(name="SYS_Date")
public class SYS_Date {
	
private String title;
private String explain;
private String url;
private String dateID;


public String getDateID() {
	return dateID;
}
public void setDateID(String dateID) {
	this.dateID = dateID;
}
public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getExplain() {
	return explain;
}
public void setExplain(String explain) {
	this.explain = explain;
}

}
