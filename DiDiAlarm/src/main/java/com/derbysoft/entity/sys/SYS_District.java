package com.derbysoft.entity.sys;

import dy.hrtworkframe.annotation.Key;
import dy.hrtworkframe.annotation.Table;

@Table(name="SYS_District")
public class SYS_District {
	/**
	 * 行政区划编码
	 */
	@Key
	private String districtID;

	/**
	 * 行政区划名称
	 */
	private String districtName;

	/**
	 * 行政区划全称
	 */
	private String fullName;

	/**
	 * 简称
	 */
	private String briefName;

	/**
	 * 新增区划等级，中国采用三级划分
	 */
	private String districtLevel;

	/**
	 * 省编码
	 */
	private String provinceID;

	/**
	 * 省名称
	 */
	private String provinceName;

	/**
	 * 市编码
	 */
	private String cityID;

	/**
	 * 是名称
	 */
	private String cityName;

	/**
	 * 乡镇编码
	 */
	private String countyID;

	/**
	 * 乡镇名称
	 */
	private String countyName;

	/**
	 * 纬度
	 */
	private String latitude;

	/**
	 * 经度
	 */
	private String longitude;

	public String getDistrictID() {
		return districtID;
	}

	public void setDistrictID(String districtID) {
		this.districtID = districtID;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getBriefName() {
		return briefName;
	}

	public void setBriefName(String briefName) {
		this.briefName = briefName;
	}

	public String getDistrictLevel() {
		return districtLevel;
	}

	public void setDistrictLevel(String districtLevel) {
		this.districtLevel = districtLevel;
	}

	public String getProvinceID() {
		return provinceID;
	}

	public void setProvinceID(String provinceID) {
		this.provinceID = provinceID;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityID() {
		return cityID;
	}

	public void setCityID(String cityID) {
		this.cityID = cityID;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountyID() {
		return countyID;
	}

	public void setCountyID(String countyID) {
		this.countyID = countyID;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	
}
