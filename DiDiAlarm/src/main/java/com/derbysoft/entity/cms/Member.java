package com.derbysoft.entity.cms;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dy.hrtworkframe.annotation.Key;
import dy.hrtworkframe.annotation.Table;
import dy.hrtworkframe.annotation.Temporary;

@Table(name="cms_user")
public class Member implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8392756495285227278L;

	/**
	 * 用户ID
	 */
	@Key
	private String userID;
	private String userName;
	
	private String firstName;
	
	private String lastName;
	@JsonIgnore
	private String userPassword;
	
	private String joined;
	
	private String lastLogin;
	
	private String passwordQuestion;
	
	private String passwordAnswer;
	
	private String email;
	
	private String name;
	
	private String sex;
	private String sexValue;
	
	public String getSexValue() {
		return sexValue;
	}

	public void setSexValue(String sexValue) {
		this.sexValue = sexValue;
	}

	private String birthDay;
	
	private String zip;
	
	private String telephone;
	
	private String mobile;
	
	private String address;
	
	private String isBindEmail;
	
	private String isBindMobile;
	
	private String authorizationKey;
	
	private String nickName;
	
	private String realName;
	
	private String avatar;
	
	private String headImg;
	@Temporary
	private String beforePassword;
    
	private String tokenID;
	private String areaID;
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

	public String getProvinceID() {
		return provinceID;
	}

	public void setProvinceID(String provinceID) {
		this.provinceID = provinceID;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCityID() {
		return cityID;
	}

	public void setCityID(String cityID) {
		this.cityID = cityID;
	}

	private String area;
	private String provinceID;
	private String province;
	private String cityID;
	private String city;
	public String getTokenID() {
		return tokenID;
	}

	public void setTokenID(String tokenID) {
		this.tokenID = tokenID;
	}

	/**
	 * 身份识别卡ID
	 */
	private String idCard;
	
	private String marriage;
	
	private String hobby;
	
	private String dot;
	
	private String money;
	
	private String company;
	
	private String cmpyTel;
	
	private String cmpyAddress;
	
	private String zipCode;
	
	@Temporary
	private String confirmPassword;
	
	/**
	 * 用户类型
	 */
	private String memberType;
	@Temporary
	private String inputDate;
	@Temporary
	private String addressName;
	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	private String alias;
	private String tag;
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	private String longitude;
	private String latitude;
	private String distance;
	public String getInputDate() {
		return inputDate;
	}

	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getJoined() {
		return joined;
	}

	public void setJoined(String joined) {
		this.joined = joined;
	}

	public String getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getPasswordQuestion() {
		return passwordQuestion;
	}

	public void setPasswordQuestion(String passwordQuestion) {
		this.passwordQuestion = passwordQuestion;
	}

	public String getPasswordAnswer() {
		return passwordAnswer;
	}

	public void setPasswordAnswer(String passwordAnswer) {
		this.passwordAnswer = passwordAnswer;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIsBindEmail() {
		return isBindEmail;
	}

	public void setIsBindEmail(String isBindEmail) {
		this.isBindEmail = isBindEmail;
	}

	public String getIsBindMobile() {
		return isBindMobile;
	}

	public void setIsBindMobile(String isBindMobile) {
		this.isBindMobile = isBindMobile;
	}

	public String getAuthorizationKey() {
		return authorizationKey;
	}

	public void setAuthorizationKey(String authorizationKey) {
		this.authorizationKey = authorizationKey;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getMarriage() {
		return marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getDot() {
		return dot;
	}

	public void setDot(String dot) {
		this.dot = dot;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCmpyTel() {
		return cmpyTel;
	}

	public void setCmpyTel(String cmpyTel) {
		this.cmpyTel = cmpyTel;
	}

	public String getCmpyAddress() {
		return cmpyAddress;
	}

	public void setCmpyAddress(String cmpyAddress) {
		this.cmpyAddress = cmpyAddress;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}


	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getBeforePassword() {
		return beforePassword;
	}

	public void setBeforePassword(String beforePassword) {
		this.beforePassword = beforePassword;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	
}
