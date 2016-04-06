package com.derbysoft.enums;

/**
 * 用户类型枚举
*    
* 项目名称：BOSSclient   
* 类名称：UserTypeEnum   
* 类描述：   
* 创建人：LD   
* 创建时间：2015年4月22日 下午3:34:31   
* 修改人：LD   
* 修改时间：2015年4月22日 下午3:34:31   
* 修改备注：   
* @version    
*
 */
public enum UserTypeEnum {
	
		ADMIN("admin","管理员"),STAFF("staff","职员");
		
		private String name;
		
		private String value;
	
		private UserTypeEnum(String name,String value){
			
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
		
		
		
}
