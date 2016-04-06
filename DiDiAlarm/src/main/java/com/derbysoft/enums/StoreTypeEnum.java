package com.derbysoft.enums;

public enum StoreTypeEnum {
	
	Direct("isDirect","直营店"),Chain("isChain","加盟店");
	
	private String value;
	
	private String name;
	
	private  StoreTypeEnum(String value,String name){
		
		this.value = value;
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
}

