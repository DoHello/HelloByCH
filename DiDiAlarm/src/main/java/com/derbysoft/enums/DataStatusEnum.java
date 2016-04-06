package com.derbysoft.enums;

/**
 * 
*    
* 项目名称：dy_service   
* 类名称：DataStatusEnum   
* 类描述：   数据状态Enum类
* 	包含：
* 	SUBMIT-提交,
*  THROUGH-通过,
* DELETE-删除,
* FAILURE-失效，
* TAKE_EFFECT-生效
* 创建人：LD   
* 创建时间：2015年3月18日 上午7:46:50   
* 修改人：LD   
* 修改时间：2015年3月18日 上午7:46:50   
* 修改备注：   
* @version    
*
 */
public enum DataStatusEnum {
	SUBMIT("submit","提交"),THROUGH("through","通过"),DELETE("delete","删除"),FAILURE("failure","失效"),TAKE_EFFECT("takeEffect","生效"),End("end","结束");

	private String value;
	
	private String name;
	
	private DataStatusEnum(String value,String name){
		
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
