package com.derbysoft.entity;

import java.beans.Transient;

import dy.hrtworkframe.annotation.Temporary;


public class AlarmCallTime {

	public String id;
	public long l;
	public String  alias;
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getL() {
		return l;
	}
	public void setL(long l) {
		this.l = l;
	}
}
