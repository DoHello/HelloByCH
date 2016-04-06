package com.derbysoft.entity.cms;

import java.io.Serializable;

public class TimeAndContext implements Serializable{
  
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3648549155522555708L;
	private String  time;
	private String context;
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}

	
}
