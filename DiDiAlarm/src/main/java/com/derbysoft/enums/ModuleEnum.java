package com.derbysoft.enums;


@SuppressWarnings("all")
public enum ModuleEnum {
	
	BOSS_SYSTEM("BOSS","老板","boss"), CHAIN_ENTERPRISE_SYSTEM("CHAIN_ENTERPRISE","连锁企业","chainEnterprise");
	
	private static String[][] pharModule = {{"300","系统维护"},{"399","人员角色"},{"7","人员管理"},{"8","人员角色"},{"",""},{"",""},{"",""},{"",""},{"",""},{"",""},{"",""},{"3","首页"},{"1","企业管理"},{"4","门店管理"}};
	private static String[][] chainModule = {{"1","企业管理"},{"2","企业信息"},{"3","首页"},{"4","门店管理"},{"5","门店邀请码"}};
	
	private String system;

	private String name;

	private String value;

	private ModuleEnum(String system,String name, String value){
		this.system = system;
		this.name = name;
		this.value = value;
	}
	

	public static ModuleEnum valueIn(String value){
		for (ModuleEnum moduleEnum : ModuleEnum.values()) {
			if(moduleEnum.getValue().equals(value)){
				return moduleEnum;
			}
		}
		return null;
	}
	
//	
//	public static List<SYS_RoleModule> getSystemModule(ModuleEnum mnum,SYS_User user) {
//		List<SYS_RoleModule> moduleList = new ArrayList<SYS_RoleModule>();
//		SYS_RoleModule module;
//		switch (mnum) {
//			case BOSS_SYSTEM:
//				for (String[] modulez : pharModule) {
//					module = new SYS_RoleModule();
//					module.setRomoID(UUID.randomUUID().toString());
//					module.setModuleID(modulez[0]);
//					module.setModuleName(modulez[1]);
//					module.setRoleID(user.getRoleID());
//					module.setRoleName(user.getRoleName());
//					module.setInputName(user.getUserName());
//					moduleList.add(module);
//				}
//				break;	
//			case CHAIN_ENTERPRISE_SYSTEM:
//				for (String[] modulez : chainModule) {
//					module = new SYS_RoleModule();
//					module.setRomoID(UUID.randomUUID().toString());
//					module.setModuleID(modulez[0]);
//					module.setModuleName(modulez[1]);
//					module.setRoleID(user.getRoleID());
//					module.setRoleName(user.getRoleName());
//					module.setInputName(user.getUserName());
//					moduleList.add(module);
//				}
//				break;
//			default:
//				break;
//		}
//		return moduleList;
//	}

	public String getSystem() {
		return system;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}
}
