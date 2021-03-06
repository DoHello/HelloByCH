package com.derbysoft.controller.sys;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.derbysoft.entity.sys.SYS_Module;
import com.derbysoft.entity.sys.SYS_ModuleButt;
import com.derbysoft.entity.sys.SYS_Role;
import com.derbysoft.entity.sys.SYS_RoleModule;

import dy.hrtworkframe.controller.base.BaseController;
import dy.hrtworkframe.dao.BaseDaoImpl;
import dy.hrtworkframe.db.SQLUtil;
import dy.hrtworkframe.entity.Pager;
import dy.hrtworkframe.entity.User;
import dy.hrtworkframe.exception.CustomerException;
import dy.hrtworkframe.util.CheckUtil;
import dy.hrtworkframe.util.Const;
import dy.hrtworkframe.util.LogUtil;
import dy.hrtworkframe.util.MessageUtil;
import dy.hrtworkframe.util.ObjUtil;
import dy.hrtworkframe.util.UUIDUtil;
import dy.hrtworkframe.vo.LogVO;

@Transactional
@Controller
@RequestMapping("systemmodule.do")
public class ModuleController extends BaseController {
	@Resource(name="baseDaoImpl")
	private BaseDaoImpl baseDao;
	
	
	@RequestMapping(params="p=view")
	public ModelAndView showListView(HttpSession session) throws Exception {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		try {
			
			mv.addObject("model", getPageData());
			mv.setViewName("/system/module/system_module_view");
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtil.exception(user, mv, e);
		}
		
		return MessageUtil.success(mv);
	}
	
	
	@RequestMapping(params = "p=showAddView")
	public ModelAndView showAddView(@ModelAttribute SYS_Module entity, HttpSession session) throws Exception {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd = getPageData();
		try {
			entity.setModuleName(unicode(entity.getModuleName()));
			pd.put("entity", entity);
			mv.addObject("model", pd);
			mv.setViewName("/system/module/system_module_add");
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtil.exception(user, mv, e);
		}
		
		return MessageUtil.success(mv);
	}
	
	
	@RequestMapping(params = "p=showButtonAdd")
	public ModelAndView showButtonAdd(@ModelAttribute SYS_ModuleButt entity, String searchText, HttpSession session) throws Exception {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd = getPageData();
		try {
			pd.put("searchText", searchText);
			entity.setModuleID(searchText);
			mv.addObject("model", pd.put("entity", entity));
			mv.setViewName("/system/button/system_button_add");
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtil.exception(user, mv, e);
		}
		
		return MessageUtil.success(mv);
	}
	
	
	@RequestMapping(params = "p=showButtonEdit")
	public ModelAndView showButtonEdit(@ModelAttribute SYS_ModuleButt entity, String searchText, HttpSession session) throws Exception {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd = getPageData();
		try {
			mv.addObject("model", pd.put("entity", baseDao.queryByPrimary(entity)));
			mv.setViewName("/system/button/system_button_add");
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtil.exception(user, mv, e);
		}
		
		return MessageUtil.success(mv);
	}
	
	
	@RequestMapping(params = "p=eyeView")
	public ModelAndView showEyeView(@ModelAttribute SYS_Module entity, HttpSession session) throws Exception {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd = getPageData();
		try {
			String sql = SQLUtil.getQuerySQL(SYS_Module.class).replace("\n", "")+" where moduleID='"+entity.getSearchText()+"'";
		
			SYS_Module entry = baseDao.query(SYS_Module.class, sql).get(0);
			entry.setSearchText(entry.getModuleID());
			entry.setOperatInfo(entry.getParentID());
			mv.addObject("model", pd.put("entity", entry));
			mv.setViewName("/system/module/system_module_eye");
		} catch (Exception e) {
			return MessageUtil.exception(user, mv, e);
		}
		
		return MessageUtil.success(mv);
	}
	
	
	@RequestMapping(params = "p=add")
	public @ResponseBody Map<String,Object> add(@ModelAttribute SYS_Module entity, HttpSession session) throws Exception {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		try {
			if (CheckUtil.isNullStr(entity.getModuleID())) {
				entity.setModuleID(UUIDUtil.get32UUID());
			}
			
			baseDao.insert(entity);
		} catch (Exception e) {
			return MessageUtil.exception(user, e);
		}
		
		return MessageUtil.success();
	}
	
	
	@RequestMapping(params = "p=addbutton")
	public @ResponseBody Map<String,Object> addButton(@ModelAttribute SYS_ModuleButt entity, HttpSession session) throws Exception {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		try {
			entity.setMobuID(UUIDUtil.get32UUID());
			
			if (CheckUtil.isNullStr(entity.getModuleID())) {
				throw new CustomerException("模块编码不能为空");
			}
			
			if (CheckUtil.isNullStr(entity.getButtonID())) {
				throw new CustomerException("按钮标识必须填写");
			}
			
			if (CheckUtil.isNullStr(entity.getButtonName())) {
				throw new CustomerException("按钮名称必须填写");
			}
			
			if (CheckUtil.isNullStr(entity.getRequestMapping())) {
				throw new CustomerException("页面地址必须填写");
			}
			String sql = "select * from SYS_ModuleButt a where a.moduleID = '" + 
			entity.getModuleID() + "' and a.buttonID = '" + entity.getButtonID() + "' ";
			if(baseDao.query(SYS_ModuleButt.class,sql).size()>0){
				throw new CustomerException("该按钮已存在,请重新添加");
			}
			baseDao.insert(entity);
		} catch (Exception e) {
			return MessageUtil.exception(user, e);
		}
		
		return MessageUtil.success();
	}
	
	
	@RequestMapping(params = "p=editbutton")
	public @ResponseBody Map<String,Object> editButton(@ModelAttribute SYS_ModuleButt entity, HttpSession session) throws Exception {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		try {
			if (CheckUtil.isNullStr(entity.getModuleID())) {
				throw new CustomerException("模块编码不能为空");
			}
			
			if (CheckUtil.isNullStr(entity.getButtonID())) {
				throw new CustomerException("按钮标识必须填写");
			}
			
			if (CheckUtil.isNullStr(entity.getButtonName())) {
				throw new CustomerException("按钮名称必须填写");
			}
			
			if (CheckUtil.isNullStr(entity.getRequestMapping())) {
				throw new CustomerException("页面地址必须填写");
			}
			
			baseDao.update(entity);
		} catch (Exception e) {
			return MessageUtil.exception(user, e);
		}
		
		return MessageUtil.success();
	}
	
	
	@RequestMapping(params = "p=edit")
	public @ResponseBody Map<String,Object> edit(@ModelAttribute SYS_Module entity, HttpSession session) throws Exception {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		try {
			baseDao.update(entity);
		} catch (Exception e) {
			return MessageUtil.exception(user, e);
		}
		
		return MessageUtil.success();
	}
	
	
	@RequestMapping(params = "p=del")
	public @ResponseBody Map<String,Object> del(@ModelAttribute SYS_Module entity, HttpSession session) throws Exception {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		try {
			entity.setModuleID(entity.getSearchText());
			String sql = SQLUtil.getDeleteSQL(entity);
			
			String querySQL = SQLUtil.getQuerySQL(SYS_Module.class) + " where " + SQLUtil.getWhereClause(entity);
			SYS_Module temp = baseDao.queryFirst(SYS_Module.class, querySQL);
			
			LogVO l1 = ObjUtil.buildMetaSQL(temp, "第1/1步, 删除模块");
			
			
			String table = "Module:" + entity.getModuleID();
			String oprinfo = "Module:" + l1.getRowid();
			String descriptioin = "删除删除模块(Module:" + entity.getModuleID();
			sql += LogUtil.getLogSQL(user, table, sql, oprinfo, descriptioin);
			
			sql += l1.getSql();
			
			baseDao.jdbcTemplate.execute(sql);
			
		} catch (Exception e) {
			return MessageUtil.exception(user, e);
		}
		
		return MessageUtil.success();
	}
	
	
	@RequestMapping(params = "p=find")
	public @ResponseBody Pager find(SYS_Module entity, SYS_RoleModule roleModule,Pager pager, HttpSession session,
			HttpServletRequest request, HttpServletResponse response, String appPlatform) {
			
		try {
			//List<SYS_RoleModule> roleModules = (List<SYS_RoleModule>) baseDao.query(SYS_RoleModule.class,"select * from sys_rolemodule where roleID = " + "'"+roleModule.getRoleID()+"'");
			//这里是sqlserver的语法与mysql不一样
//			String w = "moduleName like '%" + /*roleModules.get(0).getModuleName()*/"系统管理组设置"+ "%'"  + SQLUtil.getWhereClause(pager) + " and (parentID='0' or isnull(parentID,'')='' or not exists(select 1 from SYS_Module b where a.parentID=b.moduleID))";
			String w = "moduleName like '%" + /*roleModules.get(0).getModuleName()*/"%'"  + SQLUtil.getWhereClause(pager) + " and (parentID ='0' AND parentID is not null or not exists(select 1 from SYS_Module b where a.parentID=b.moduleID))";
			
			if ("AIR".equals(appPlatform)) {
				w += " and AppPlatform='AIR'";
			}
			
			String sql = SQLUtil.getQuerySQL(SYS_Module.class, "a") + " where " + w;
			pager.setExhibitDatas(baseDao.query(SYS_Module.class, sql));
			pager.setPageSize(pager.getExhibitDatas().size());
			pager.setRecordCount(pager.getExhibitDatas().size());
		} catch (Exception e) {
			pager.setIsSuccess(false);
		}
		
		pager.setIsSuccess(true);
		return pager;
	}
	
	
	@RequestMapping(params = "p=findByParentID")
	public @ResponseBody Pager findByParentID(SYS_Module entity, Pager pager, HttpSession session,HttpServletRequest request, HttpServletResponse response) {
		try {
			String sql = SQLUtil.getQuerySQL(SYS_Module.class) + " where parentID='" + entity.getParentID() + "'";
			
			return baseDao.queryPager(sql, pager, null);
		} catch (Exception e) {
			pager.setIsSuccess(false);
		}
		
		pager.setIsSuccess(true);
		return pager;
	}
	
	
	@RequestMapping(params = "p=sub")	
	public @ResponseBody Pager sub(SYS_Module entity, Pager pager, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			//String sql = SQLUtil.getQuerySQL(SYS_Module.class) + " where ParentID='" + entity.getParentID() + "'";
				
			pager.setExhibitDatas(baseDao.query(entity));
			pager.setPageSize(pager.getExhibitDatas().size());
			pager.setRecordCount(pager.getExhibitDatas().size());
		} catch (Exception e) {
			pager.setIsSuccess(false);
		}
		
		pager.setIsSuccess(true);
		return pager;
	}
	
	
	@RequestMapping(params = "p=rolemodule")
	public @ResponseBody Pager rolemodule(SYS_Module entity, SYS_Role role, Pager pager, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String sql = "select a.*, b.ModuleID searchText from " + SQLUtil.getTableName(SYS_Module.class) + " a left join SYS_RoleModule b "
					+ "on a.ModuleID=b.ModuleID and b.RoleID='" + role.getRoleID() + "' where ParentID='" + entity.getParentID() + "'";
			
			pager.setExhibitDatas(baseDao.query(SYS_Module.class, sql));
			pager.setPageSize(pager.getExhibitDatas().size());
			pager.setRecordCount(pager.getExhibitDatas().size());
		} catch (Exception e) {
			pager.setIsSuccess(false);
		}
		
		pager.setIsSuccess(true);
		return pager;
	}
}
