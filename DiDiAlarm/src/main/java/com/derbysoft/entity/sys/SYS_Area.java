package com.derbysoft.entity.sys;

import dy.hrtworkframe.annotation.Key;
import dy.hrtworkframe.annotation.Table;

//这是区包括县
@Table(name="SYS_Area")
public class SYS_Area {
 private String id;
 //地区id
 @Key
 private String areaID;
 //地区名
 private String area;
  //上级的父
 private String cityID;
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getAreaID() {
	return areaID;
}
public void setAreaID(String areaID) {
	this.areaID = areaID;
}
public String getArea() {
	return area;
}
public void setArea(String area) {
	this.area = area;
}
public String getCityID() {
	return cityID;
}
public void setCityID(String cityID) {
	this.cityID = cityID;
}

 
 
}
