package com.derbysoft.entity.cms;

import java.io.Serializable;



@SuppressWarnings("all")
public class CaseStatus implements Serializable{


	
	private String Status;
	private String InputDate;
	
	private String AlarmID;

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getInputDate() {
		return InputDate;
	}

	public void setInputDate(String inputDate) {
		InputDate = inputDate;
	}

	public String getAlarmID() {
		return AlarmID;
	}

	public void setAlarmID(String alarmID) {
		AlarmID = alarmID;
	}
	

	
	
}
