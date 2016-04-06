package com.derbysoft.entity;

/**
 * 验证码pojo
 * 
 * @ClassName: RegisterPhone
 * @Description: TODO
 * @author: dhl
 * @date: 2015年12月2日 下午5:15:48
 */
public class RegisterPhone {

	// 验证码
	public String regCode;

	// 状态reg为注册时,4get为找回密码
	public String status;
	// 时间:用于限制用户规定时间类重复发送
	public Long time;
	// 次数
	public int count;


	public String getRegCode() {
		return regCode;
	}

	public void setRegCode(String regCode) {
		this.regCode = regCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
