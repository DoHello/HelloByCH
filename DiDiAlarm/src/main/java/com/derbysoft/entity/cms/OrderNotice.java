package com.derbysoft.entity.cms;

import dy.hrtworkframe.annotation.Table;

@Table(name="sys_orderNotice")
public class OrderNotice {

	private String orderNotice;

	public String getOrderNotice() {
		return orderNotice;
	}

	public void setOrderNotice(String orderNotice) {
		this.orderNotice = orderNotice;
	}
	
	
}
