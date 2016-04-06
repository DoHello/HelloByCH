package com.derbysoft.controller.sys;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.derbysoft.dao.sys.DicDao;
import com.derbysoft.entity.sys.SYS_Category;
import com.derbysoft.entity.sys.SYS_Dic;
import com.derbysoft.entity.sys.SYS_RoleButton;
import com.derbysoft.entity.sys.SYS_User;
import com.derbysoft.enums.DataStatusEnum;

import dy.hrtworkframe.controller.base.BaseController;
import dy.hrtworkframe.db.SQLUtil;
import dy.hrtworkframe.entity.Pager;
import dy.hrtworkframe.entity.User;
import dy.hrtworkframe.exception.CustomerException;
import dy.hrtworkframe.util.CheckUtil;
import dy.hrtworkframe.util.Const;
import dy.hrtworkframe.util.ExportUtils;
import dy.hrtworkframe.util.MessageUtil;
import dy.hrtworkframe.util.SortUtil;
import dy.hrtworkframe.util.UUIDUtil;

@Transactional
@Controller
@RequestMapping("dic.do")
public class DicController extends BaseController {
	
	@Resource
	private DicDao dicDao;

	@RequestMapping(params="p=view")
	public ModelAndView showListView(HttpSession session,@RequestParam("moduleID") String moduleID) throws Exception {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd = getPageData();
		try {
			//获取用户权限按钮
			String sql = "select * "
					+ " from SYS_RoleButton "
					+ " where ModuleID= '" + moduleID + "' and roleID = '"+user.getRoleID()+"' ;";
			pd.put("userbutton", dicDao.query(SYS_RoleButton.class, sql));
			List<SYS_Category> categories = dicDao.query(SYS_Category.class);
			pd.put("category", categories);
//			mv.addObject("model", getPageData().put("category", categories));
			mv.addObject("model", pd);
			mv.setViewName("/system/dic/parameter_view");
		} catch (Exception e) {
			return MessageUtil.exception(user, mv, e);
		}
		
		return MessageUtil.success(mv);
	}
	
	
	@RequestMapping(params = "p=add")
	public @ResponseBody Map<String,Object> add(@ModelAttribute SYS_Dic  entity, HttpSession session) {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		try {
			checkDic(entity);
			entity.setDicID(UUIDUtil.get32UUID());
			entity.setDicValue(UUIDUtil.get32UUID());
			entity.setStatus(DataStatusEnum.TAKE_EFFECT.getValue());
			dicDao.insert(entity);
		} catch (Exception e) {
			return MessageUtil.exception(user, e);
		}
		
		return MessageUtil.success();
	}
	
	
	private void checkDic(SYS_Dic entity) {
		// TODO Auto-generated method stub
		if("分类查看".equals(entity.getCategoryName())){
			throw new CustomerException("请选择类型");
		}
		if(CheckUtil.isNullStr(entity.getDicName())){
			throw new CustomerException("请输入正确的字典名");
		}
	}


	@RequestMapping(params = "p=eyeView")
	public ModelAndView showEyeView(SYS_Dic entity, HttpSession session) {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd = getPageData();
		pd.put("entity", dicDao.queryByPrimary(entity));
		try {
			List<SYS_Category> categories = dicDao.query(SYS_Category.class);
			pd.put("category", categories);
			mv.addObject("model", pd);
			mv.setViewName("/system/dic/parameter_eye");
			
		} catch (Exception e) {
			return MessageUtil.exception(user, mv, e);
		}
		return MessageUtil.success(mv);
	}
	
	

	
	
	@RequestMapping(params = "p=showAddView")
	public ModelAndView showAddView(@ModelAttribute SYS_Dic entity, HttpSession session) {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		try {
			List<SYS_Category> categories = dicDao.query(SYS_Category.class);
			mv.addObject("model", getPageData().put("category", categories));
			mv.setViewName("/system/dic/parameter_add");
		} catch (Exception e) {
			return MessageUtil.exception(user, mv, e);
		}
		
		return MessageUtil.success(mv);
	}
	
	
	
	@RequestMapping(params = "p=edit")
	public @ResponseBody Map<String,Object> edit(@ModelAttribute SYS_Dic entity, HttpSession session) throws Exception {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		try {
				dicDao.update(entity);
		} catch (Exception e) {
			return MessageUtil.exception(user, e);
		}
		return MessageUtil.success();
	}
	
	
	@RequestMapping(params = "p=del")
	public @ResponseBody Map<String,Object> del(@ModelAttribute SYS_Dic entity, HttpSession session) {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		try {
//			entity.setStatus(DataStatusEnum.DELETE.getValue());
//			dicDao.update(entity);
			dicDao.delete(entity);
		} catch (Exception e) {
			return MessageUtil.exception(user, e);
		}
		
		return MessageUtil.success();
	}
	
	
	@SuppressWarnings("deprecation")
	@RequestMapping(params = "p=find")
	public @ResponseBody Pager find( Pager pager, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		List<Object> args1 = null;
		String w = null;
		try {
			List<Object> args;
			if("1".equals(pager.getParameters().get("searchinfo"))){
			 w = " and CategoryName like ?  and status = 'takeEffect' "+ SQLUtil.getWhereClause(pager);
			 args = new ArrayList<Object>();
			args.add("%"+pager.getParameters().get("searchText")+"%");
			args1 = args;
			}else{
			w = " and dicName like ? and status = 'takeEffect' "+ SQLUtil.getWhereClause(pager);
				args = new ArrayList<Object>();
				args.add("%"+pager.getParameters().get("searchText")+"%");
				args1 = args;
			}
				if (pager.getIsExport()) {
					if (pager.getExportAllData()) {
						pager.setExportDatas(dicDao.query(SQLUtil.getQuerySQL(SYS_Dic.class)));
					}
					try {
						ExportUtils.export(request, response, pager);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}
			
				if (pager.getPageSize() == 0) {
					
					String sql = SQLUtil.getQuerySQL(SYS_Dic.class) + " where " + w;
					pager.setExhibitDatas(dicDao.query(SYS_Dic.class, sql,args.toArray()));
					} else {
						if(pager.getParameters().get("sortFiled")==null||pager.getParameters().get("sortInt").toString().equals("2")){
							pager.setAdvanceQuerySorts(SortUtil.sortBuild1("DicID", "desc"));
							dicDao.queryCount(pager, SYS_Dic.class, w, args.toArray());
							dicDao.queryPager(SYS_Dic.class, w, args.toArray(), pager);
							}else{
						if("1".equals(pager.getParameters().get("sortInt").toString())){
							pager.setAdvanceQuerySorts(SortUtil.sortBuild1(pager.getParameters().get("sortFiled").toString(), "desc"));
						}else{
							pager.setAdvanceQuerySorts(SortUtil.sortBuild1(pager.getParameters().get("sortFiled").toString(), "asc"));
						}
//					pager.setAdvanceQuerySorts(SortUtil.sortBuild1("DicID", "desc"));
					dicDao.queryCount(pager, SYS_Dic.class, w, args.toArray());
					dicDao.queryPager(SYS_Dic.class, w, args.toArray(), pager);
						}
				}
		} catch (Exception e) {
			e.printStackTrace();
			pager.setAdvanceQuerySorts(SortUtil.sortBuild1("DicID", "desc"));
			try {
				pager.setNowPage(1);
				pager.setStartRecord(0);
				dicDao.queryCount(pager, SYS_Dic.class, w, args1.toArray());
				dicDao.queryPager(SYS_Dic.class, w, args1.toArray(), pager);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}finally{
				
			pager.setIsSuccess(true);
			}
		}
		
		pager.setIsSuccess(true);
		return pager;
	}
	
	
	
	@SuppressWarnings("deprecation")
	@RequestMapping(params = "p=find1")
	public @ResponseBody Pager find1( Pager pager, HttpSession session,
			HttpServletRequest request, HttpServletResponse response,@ModelAttribute SYS_Dic entity) {
		try {
/*			String w = " and dicName like ?  "+ SQLUtil.getWhereClause(pager);
				List<Object> args = new ArrayList<Object>();
				args.add("%"+pager.getParameters().get("searchText")+"%");*/
			String w=null;
			List<Object> args = new ArrayList<Object>();
			if(null==entity.getCategoryID()||"".equals(entity.getCategoryID())){
				 w = " and CategoryName like ?   and status = 'takeEffect' "+ SQLUtil.getWhereClause(pager);
				 args = new ArrayList<Object>();
				args.add("%"+pager.getParameters().get("searchText")+"%");
				
			}else{
				 w = " and dicName like ? and CategoryID = ?  and status = 'takeEffect' "+ SQLUtil.getWhereClause(pager);
					args = new ArrayList<Object>();
					args.add("%"+pager.getParameters().get("searchText")+"%");
			        args.add(entity.getCategoryID());
			}
			

				if (pager.getIsExport()) {
					if (pager.getExportAllData()) {
						pager.setExportDatas(dicDao.query(SQLUtil.getQuerySQL(SYS_Dic.class)));
					}
					try {
						ExportUtils.export(request, response, pager);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}
			
				if (pager.getPageSize() == 0) {
					
					String sql = SQLUtil.getQuerySQL(SYS_Dic.class) + " where " + w;
					pager.setExhibitDatas(dicDao.query(SYS_Dic.class, sql,args.toArray()));
					} else {
					pager.setAdvanceQuerySorts(SortUtil.sortBuild1("DicID", "desc"));
					dicDao.queryCount(pager, SYS_Dic.class, w, args.toArray());
					dicDao.queryPager(SYS_Dic.class, w, args.toArray(), pager);
					pager.setIsSuccess(true);
				}
		} catch (Exception e) {
			e.printStackTrace();
			pager.setIsSuccess(false);
		}
		
		pager.setIsSuccess(true);
		return pager;
	}
}
