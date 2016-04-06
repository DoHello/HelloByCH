package com.derbysoft.entity.sys;

import dy.hrtworkframe.annotation.Key;
import dy.hrtworkframe.annotation.Table;
@Table(name="SYS_PoliceEdition")
public class SYS_PoliceEdition {

	@Key
	private String policeEditionID;

	private String policeEdition;

	public String getPoliceEditionID() {
		return policeEditionID;
	}

	public void setPoliceEditionID(String policeEditionID) {
		this.policeEditionID = policeEditionID;
	}

	public String getPoliceEdition() {
		return policeEdition;
	}

	public void setPoliceEdition(String policeEdition) {
		this.policeEdition = policeEdition;
	}
}
