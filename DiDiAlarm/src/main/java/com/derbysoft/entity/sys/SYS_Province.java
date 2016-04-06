package com.derbysoft.entity.sys;

import java.util.List;

import dy.hrtworkframe.annotation.Key;
import dy.hrtworkframe.annotation.Table;
import dy.hrtworkframe.annotation.Temporary;
//这是省
    @Table(name="SYS_Province")
public class SYS_Province {
    @Key
	private String id;
	//省的名字
	private String province;
	//省的id
	private String provinceID;
	@Temporary
	private List<SYS_City> City;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getProvinceID() {
		return provinceID;
	}
	public void setProvinceID(String provinceID) {
		this.provinceID = provinceID;
	}
	public List<SYS_City> getCity() {
		return City;
	}
	public void setCity(List<SYS_City> city) {
		City = city;
	}
	
}
