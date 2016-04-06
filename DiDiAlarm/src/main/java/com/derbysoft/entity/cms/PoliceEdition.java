package com.derbysoft.entity.cms;

import dy.hrtworkframe.annotation.Key;
import dy.hrtworkframe.annotation.Table;

@Table(name="sys_policeEdition" )
public class PoliceEdition {
	String policeEdition;
	@Key
	String policeEditionID;
	public String getPoliceEdition() {
		return policeEdition;
	}
	public void setPoliceEdition(String policeEdition) {
		this.policeEdition = policeEdition;
	}
	public String getPoliceEditionID() {
		return policeEditionID;
	}
	public void setPoliceEditionID(String policeEditionID) {
		this.policeEditionID = policeEditionID;
	}
}
