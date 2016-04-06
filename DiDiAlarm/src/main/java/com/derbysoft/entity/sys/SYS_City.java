package com.derbysoft.entity.sys;

import java.util.List;

import dy.hrtworkframe.annotation.Key;
import dy.hrtworkframe.annotation.Table;
import dy.hrtworkframe.annotation.Temporary;

@Table(name="sys_city")
public class SYS_City {
    @Key
	private String id;
	private String cityID;
	private String city;
	private String provinceID;
	@Temporary
	private List <SYS_Area> area;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCityID() {
		return cityID;
	}
	public void setCityID(String cityID) {
		this.cityID = cityID;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvinceID() {
		return provinceID;
	}
	public void setProvinceID(String provinceID) {
		this.provinceID = provinceID;
	}
	public List<SYS_Area> getArea() {
		return area;
	}
	public void setArea(List<SYS_Area> area) {
		this.area = area;
	}

	
	
}
