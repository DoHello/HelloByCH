package com.derbysoft.controller.sys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.derbysoft.dao.sys.DicDao;
import com.derbysoft.dao.sys.RoleDao;
import com.derbysoft.dao.sys.UserDao;
import com.derbysoft.entity.sys.SYS_Company;
import com.derbysoft.entity.sys.SYS_Dic;
import com.derbysoft.entity.sys.SYS_Role;
import com.derbysoft.entity.sys.SYS_User;
import com.derbysoft.service.cms.DateService;
import com.derbysoft.service.sys.DicService;
import com.derbysoft.service.sys.SYS_UserService;
import com.derbysoft.util.SmsRegsiterUtil;

import dy.hrtworkframe.controller.base.BaseController;
import dy.hrtworkframe.db.SQLUtil;
import dy.hrtworkframe.entity.Pager;
import dy.hrtworkframe.exception.CustomerException;
import dy.hrtworkframe.util.CheckUtil;
import dy.hrtworkframe.util.Const;
import dy.hrtworkframe.util.DateUtil;
import dy.hrtworkframe.util.ExportUtils;
import dy.hrtworkframe.util.LogUtil;
import dy.hrtworkframe.util.MD5;
import dy.hrtworkframe.util.MessageUtil;
import dy.hrtworkframe.util.ObjUtil;
import dy.hrtworkframe.util.PageData;
import dy.hrtworkframe.util.Sha1Util;
import dy.hrtworkframe.util.SortUtil;
import dy.hrtworkframe.util.UUIDUtil;
import dy.hrtworkframe.vo.LogVO;

@Transactional
@Controller("user")
@RequestMapping("user.do")
public class UserController extends BaseController {
	@Resource(name="userDao")
	private UserDao userDao;
	
	
	@Autowired
	private DicService dicService;
	
	@Resource(name="systemDicDao")
	private DicDao systemDicDao;
	
	@Autowired
	private DateService dateService;
	
	@Resource(name="roleDao")
	private RoleDao roleDao;
	
	@Autowired
	private SYS_UserService sys_UserService;
	
	@Autowired
	private SmsRegsiterUtil smsRegsiterUtil;
	
	@RequestMapping(params="p=view")
	public ModelAndView showListView(HttpSession session,@RequestParam("moduleID") String moduleID) throws Exception {
		try {
		SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
		PageData pd = getPageData();
			//获取用户权限按钮
			pd.put("userbutton", dateService.findAuthority(user, moduleID));
			mv.addObject("model", pd);
			mv.setViewName("/system/user/system_user_view");
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("/system/home/login");
			return MessageUtil.success(mv);
		}
		
		return MessageUtil.success(mv);
	}
	
	
	@RequestMapping(params = "p=showAddView")
	public ModelAndView showAddView(@ModelAttribute SYS_User user, HttpSession session) {
		user = (SYS_User) session.getAttribute(Const.SESSION_USER);
		PageData pd = getPageData();
		try {
			pd.put("sex", dicService.findByCategory("sex"));
//			pd.put("role", roleDao.findByCompany(user.getCompanyID()));
			pd.put("role", roleDao.findRoleGyID());
			pd.put("roles", roleDao.findByCompanys(user.getCompanyID()));
			//police level
			pd.put("companyAll", dicService.query(SYS_Company.class));
			pd.put("jobAll", dicService.findByCategory("Job"));
			pd.put("underAll", dicService.findByCategory("Under"));
			pd.put("levelAll", dicService.findByCategory("PoliceLevel"));
			mv.addObject("model", pd);
			mv.setViewName("/system/user/system_user_add");
		} catch (Exception e) {
			return MessageUtil.exception(user, mv, e);
		}
		return MessageUtil.success(mv);
	}
	
	
	@RequestMapping(params = "p=eyeView")
	public ModelAndView showEyeView(@ModelAttribute SYS_User entity, HttpSession session) throws Exception {
		SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
		PageData pd = getPageData();
		try {
			pd.put("sex", dicService.findByCategory("sex"));
//			pd.put("role", roleDao.findByCompany(user.getCompanyID()));
			//角色
			SYS_User entry = sys_UserService.findUser(entity);
			//角色
			List<SYS_Role> entryAll = new ArrayList<SYS_Role>();
			for (SYS_Role sys_Role : roleDao.findRoleGyID()) {
				if((sys_Role.getRoleID().equals(entry
						.getRoleID()))){
					entryAll.add(sys_Role);
				}
			}
			for (SYS_Role sys_Role : roleDao.findRoleGyID()) {
				if(!(sys_Role.getRoleID().equals(entry
						.getRoleID()))){
					entryAll.add(sys_Role);
				}
			}
			//====职位
			List<SYS_Dic> list = dicService.findByCategory("Job");
			List<SYS_Dic> jobAll = new ArrayList<SYS_Dic>();
			String jobValue = entry.getJobValue();
			for (SYS_Dic sys_Dic : list) {
				if(sys_Dic.getDicValue().equals(jobValue)){
					jobAll.add(sys_Dic);
				}
			}
			for (SYS_Dic sys_Dic : list) {
				if(!sys_Dic.getDicValue().equals(jobValue)){
					jobAll.add(sys_Dic);
				}
			}
			//====辖区
			List<SYS_Dic> listUnder = dicService.findByCategory("Under");
			List<SYS_Dic> underAll = new ArrayList<SYS_Dic>();
			for (SYS_Dic sys_Dic : listUnder) {
				if(sys_Dic.getDicValue().equals(entry.getUnderValue())){
					underAll.add(sys_Dic);
				}
			}
			for (SYS_Dic sys_Dic : listUnder) {
				if(!sys_Dic.getDicValue().equals(entry.getUnderValue())){
					underAll.add(sys_Dic);
				}
			}
			//police level
			List<SYS_Dic> listLevel = dicService.findByCategory("PoliceLevel");
			List<SYS_Dic> listLv = new  ArrayList<SYS_Dic>();
			for (SYS_Dic sys_Dic : listLevel) {
				if(sys_Dic.getDicValue().equals(entry.getPoliceLevelValue())){
					listLv.add(sys_Dic);
				}
			}
			for (SYS_Dic sys_Dic : listLevel) {
				if(!sys_Dic.getDicValue().equals(entry.getPoliceLevelValue())){
					listLv.add(sys_Dic);
				}
			}
			List<SYS_Company> listCompy = new ArrayList<SYS_Company>();
			List<SYS_Company> query = dicService.query(SYS_Company.class);
			//SYS_Company s = new SYS_Company();
			for (SYS_Company sys_Company : query) {
				if(sys_Company.getCompanyID().equals(entry.getCompanyID())){
					listCompy.add(sys_Company);
				}
			}
			for (SYS_Company sys_Company : query) {
				if(!sys_Company.getCompanyID().equals(entry.getCompanyID())){
					listCompy.add(sys_Company);
				}
			}
			pd.put("jobAll", jobAll);
			pd.put("underAll", underAll);
			pd.put("roleAll", entryAll);
			pd.put("companyAll", listCompy);
			pd.put("levelAll", listLv);
			mv.addObject("model", pd);
			mv.addObject("model", pd.put("entity", entry));
		mv.setViewName("/system/user/system_user_eye");
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("/system/home/login");
			return MessageUtil.exception(user, mv, e);
		}
		
		return MessageUtil.success(mv);
	}
	
	
	@RequestMapping(params = "p=add")
	public @ResponseBody Map<String,Object> add(SYS_User entity, HttpSession session) throws Exception {
		SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
		try {
			checkEntityByAdd(entity);
			//String password = entity.getPassword();
			entity.setUserID(UUIDUtil.get32UUID());
			entity.setInputName(user.getUserName());
			entity.setInputDate(DateUtil.getDateTimeString());
			entity.setPassword(Sha1Util.getSha1(MD5.md5(entity.getPassword())));
			userDao.insert(entity);
//			smsRegsiterUtil.sendPasswordByPhone2(password,entity.getPhone(),entity.getUserName());
		} catch (Exception e) {
			return MessageUtil.exception(user, e);
		}
		return MessageUtil.success();
	}
	@RequestMapping(params = "p=edit")
	public @ResponseBody Map<String,Object> edit( SYS_User entity, HttpSession session) throws Exception {
		try {
			checkEntityByEdit(entity);
			String password = entity.getPassword();
			if(password.equals("******")){
				/*List<SYS_User> query = userDao.query(SYS_User.class,"select * from sys_user where 1=1 and userName = '"+entity.getUserName()+"' ;");
				entity.setPassword(query.get(0).getPassword());
				userDao.update(entity);*/
				sys_UserService.edit(entity);
			}else{
			entity.setPassword(Sha1Util.getSha1(MD5.md5(entity.getPassword())));
			sys_UserService.update(entity);
//			smsRegsiterUtil.sendPasswordByPhone1(password,entity.getPhone());
			}
			return MessageUtil.success();
		} catch (Exception e) {
			return MessageUtil.exception(entity, e);
		}
		
	}
	
	
	private void checkEntityByAdd(SYS_User entity) {
		if (CheckUtil.isNullStr(entity.getUserName())) {
			throw new CustomerException("登录名必须填写");
		}
		List<SYS_User> query2;
		try {
			query2 = sys_UserService.findUser(entity.getUserName());
		} catch (Exception e) {
			throw new CustomerException("网路连接失败");
		} 
		
		if(query2.size()>0){
				throw new CustomerException("登录名不可重复");
			}
		String reg15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
		String reg18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";
		if(entity.getIdCard()==null){
			throw new CustomerException("身份证不能为空");
		}
		if(CheckUtil.isNullStr(entity.getPhone())){
			throw new CustomerException("手机号码必须填写");
		}
		if (!entity.getPhone().matches("^(13|14|15|17|18)\\d{9}$")) {
			throw new CustomerException("请输入正确的手机号码格式");
		}
		if(CheckUtil.isNullStr(entity.getRealName())){
			throw new CustomerException("必须填写真实姓名");
		}
		if(CheckUtil.isNullStr(entity.getPoliceNumber())){
			throw new CustomerException("必须填写警号");
		}
//		if (!entity.getIdCard().matches(reg15) && !entity.getIdCard().matches(reg18)) 
//		{
//			throw new CustomerException("身份证错误,请重新输入");
//		}
		if (entity.getPassword().matches("(?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[_]+$).{6,20}")!=true) {
			throw new CustomerException("您的密码应该由数字、字母或下划线组成,长度在6-20 之间");
		}
		if (entity.getProvince()==null||entity.getProvinceID()==null||
				"".equals(entity.getProvince())||"".equals(entity.getProvinceID())) {
			throw new CustomerException("请选择地址");
		}
		if(CheckUtil.isNullStr(entity.getRealName())){
			entity.setRealName(entity.getUserName());
		}
		if (entity.getPassword().matches("(?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[_]+$).{6,20}")) {
			if(!entity.getPassword().matches("[\\w]+")){
				throw new CustomerException("您的密码应该由数字、字母或下划线组成,长度在6-20 之间");
			}
		}
	}
	
	private void checkEntityByEdit(SYS_User entity) {
		if (CheckUtil.isNullStr(entity.getUserName())) {
			throw new CustomerException("登录名必须填写");
		}
		List<SYS_User> query = userDao.query(SYS_User.class,"select * from sys_user a where a.userid = '"+entity.getUserID()+"' ;");
		if (query.size()>0) {
			if(query.get(0)!=null&&query.get(0).getUserName().equals(entity.getUserName())!=true){
				List<SYS_User> query2 = userDao.query(SYS_User.class);
				for (SYS_User sys_User : query2) {
					if(sys_User.getUserName().equals(entity.getUserName())){
						throw new CustomerException("登录名不可重复");
					}
				}
			}
		}
		if(entity.getPassword()==null){
			throw new CustomerException("请输入密码");
		}
		if(entity.getPassword()!=null&&!entity.getPassword().equals("******")){
		if (entity.getPassword().matches("(?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[_]+$).{6,20}")!=true) {
			throw new CustomerException("您的密码应该由数字、字母或下划线组成,长度在6-20 之间");
		}
		if (entity.getPassword().matches("(?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[_]+$).{6,20}")) {
			if(!entity.getPassword().matches("[\\w]+")){
				throw new CustomerException("您的密码应该由数字、字母或下划线组成,长度在6-20 之间");
			}
		}
		}
		String reg15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
		String reg18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";
		if(entity.getIdCard()==null){
			throw new CustomerException("身份证不能为空");
		}
		if (!entity.getIdCard().matches(reg15) && !entity.getIdCard().matches(reg18)) 
		{
			throw new CustomerException("身份证错误,请重新输入");
		}
//		if (entity.getPassword().matches("(?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[_]+$).{6,20}")!=true) {
//			throw new CustomerException("您的密码应该由数字、字母或下划线组成,长度在6-20 之间");
//		}
//		if (entity.getPassword().matches("(?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[_]+$).{6,20}")) {
//			if(!entity.getPassword().matches("[\\w]+")){
//				throw new CustomerException("您的密码应该由数字、字母或下划线组成,长度在6-20 之间");
//			}
//		}
	}
	
	
	@RequestMapping(params = "p=del")
	public @ResponseBody Map<String,Object> del(@ModelAttribute SYS_User entity, HttpSession session) throws Exception {
		SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
		try {
			String sql = SQLUtil.getDeleteSQL(entity);
			String querySQL = SQLUtil.getQuerySQL(SYS_User.class) + " where " + SQLUtil.getWhereClause(entity);
			SYS_User temp = userDao.query(SYS_User.class, querySQL).get(0);
			LogVO l = ObjUtil.buildMetaSQL(temp, "第1/1步,删除");
			String table = "SYS_User:" + entity.getUserID();
			String oprinfo = "SYS_User:" + l.getRowid();
			String descriptioin = "删除角色(编码:" + entity.getUserID();
			sql += LogUtil.getLogSQL(user, table, sql, oprinfo, descriptioin);
			sql += l.getSql();
			userDao.jdbcTemplate.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtil.success();
//			MessageUtil.exception(null, e);
		}
		return MessageUtil.success();
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(params = "p=find")
	public @ResponseBody Pager find(SYS_User entity, Pager pager, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String w = "  UserName like '%" + pager.getParameters().get("searchText")  + "%' || RealName like '%" + pager.getParameters().get("searchText")  + "%' " + SQLUtil.getWhereClause(pager);
		
			if (pager.getIsExport()) {
				if (pager.getExportAllData()) {
					pager.setExportDatas(userDao.query(SQLUtil.getQuerySQL(entity.getClass())));
				}
				try {
					ExportUtils.export(request, response, pager);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		
			if (pager.getPageSize() == 0) {
				
				String sql = SQLUtil.getQuerySQL(SYS_User.class) + " where " + w;
				pager.setExhibitDatas(userDao.query(SYS_User.class, sql));
				
			} else {
				//sortFiled
				if(pager.getParameters().get("sortFiled")==null||pager.getParameters().get("sortInt").toString().equals("2")){
					pager.setAdvanceQuerySorts(SortUtil.sortBuild1("InputDate", "1"));
					userDao.queryCount(pager, SYS_User.class, w);
					userDao.queryPager(pager, SYS_User.class, w);
				}else{
				if("1".equals(pager.getParameters().get("sortInt").toString())){
					pager.setAdvanceQuerySorts(SortUtil.sortBuild1(pager.getParameters().get("sortFiled").toString(), "desc"));
//					w += " group by "+pager.getParameters().get("sortFiled").toString()+" desc ";
					
				}else{
					pager.setAdvanceQuerySorts(SortUtil.sortBuild1(pager.getParameters().get("sortFiled").toString(), "asc"));

//					w += " group by "+pager.getParameters().get("sortFiled").toString()+" asc ";
					
				}
					userDao.queryCount(pager, SYS_User.class, w);
					userDao.queryPager(pager, SYS_User.class, w);
				}
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			pager.setIsSuccess(false);
		}
		
		pager.setIsSuccess(true);
		return pager;
	}
}
//@SuppressWarnings("deprecation")
//@RequestMapping(params = "p=find")
//public @ResponseBody Pager find(SYS_User entity, Pager pager, HttpSession session,
//		HttpServletRequest request, HttpServletResponse response) {
//	try {
//		String w = "  UserName like '%" + pager.getParameters().get("searchText")  + "%' || RealName like '%" + pager.getParameters().get("searchText")  + "%' " + SQLUtil.getWhereClause(pager);
//		
//		if (pager.getIsExport()) {
//			if (pager.getExportAllData()) {
//				pager.setExportDatas(userDao.query(SQLUtil.getQuerySQL(entity.getClass())));
//			}
//			try {
//				ExportUtils.export(request, response, pager);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			return null;
//		}
//		
//		if (pager.getPageSize() == 0) {
//			
//			String sql = SQLUtil.getQuerySQL(SYS_User.class) + " where " + w;
//			pager.setExhibitDatas(userDao.query(SYS_User.class, sql));
//			
//		} else {
//			
//			pager.setAdvanceQuerySorts(SortUtil.sortBuild1("InputDate", "1"));
//			userDao.queryCount(pager, SYS_User.class, w);
//			userDao.queryPager(pager, SYS_User.class, w);
//			pager.setIsSuccess(true);
//		}
//		
//	} catch (Exception e) {
//		e.printStackTrace();
//		pager.setIsSuccess(false);
//	}
//	
//	pager.setIsSuccess(true);
//	return pager;
//}
//}
