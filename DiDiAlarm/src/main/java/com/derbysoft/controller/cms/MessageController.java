package com.derbysoft.controller.cms;

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

import com.derbysoft.dao.cms.NewsDao;
import com.derbysoft.dao.sys.RoleDao;
import com.derbysoft.entity.cms.Article;
import com.derbysoft.entity.cms.Message;
import com.derbysoft.entity.sys.SYS_RoleButton;
import com.derbysoft.entity.sys.SYS_User;

import dy.hrtworkframe.controller.base.BaseController;
import dy.hrtworkframe.db.SQLUtil;
import dy.hrtworkframe.entity.Pager;
import dy.hrtworkframe.entity.User;
import dy.hrtworkframe.util.Const;
import dy.hrtworkframe.util.DateUtil;
import dy.hrtworkframe.util.ExportUtils;
import dy.hrtworkframe.util.MessageUtil;
import dy.hrtworkframe.util.SortUtil;
import dy.hrtworkframe.util.UUIDUtil;

@Transactional
@Controller("message")
@RequestMapping("message.do")
public class MessageController extends BaseController {
	@Resource(name="newsDao")
	private NewsDao newsDao;
	@Resource(name="roleDao")
	private RoleDao roleDao;
	
//	@Resource(name="messageDao")
//	private MessageDao messageDao;
	//这是保存反馈
	@RequestMapping(params="p=saveMessage")
	public @ResponseBody Map<String, Object> saveMessage  (HttpSession session,@ModelAttribute Message message)  {
		
		try {
			String context = message.getContext();
			SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
			pd = getPageData();
			message.setPhone(user.getPhone());
			message.setCreateTime(DateUtil.getDateString());
			message.setUserID(user.getUserID());
			if(context.length()<11){
				message.setTitle(context);
			}else{message.setTitle(getTitle(context, 10));
			}
			message.setUserName(user.getRealName());
			message.setMessageID(UUIDUtil.get32UUID());
			
			newsDao.insert(message);
			
			/*pd.put("newsList", newsList);*/
           	return MessageUtil.success();			
		} catch (Exception e) {
			return MessageUtil.error();
		}
	}
	public String  getTitle (String context,int i)  {	
	String str = context.substring(0,i);
    return str;
    }
	
	
	//参看所有的反馈信息
	@RequestMapping(params="p=view")
	public ModelAndView showListView(HttpSession session,@RequestParam("moduleID") String moduleID) throws Exception {
		//获取用户权限按钮
	   SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
		pd = getPageData();
		String sql = "select * "
				+ " from SYS_RoleButton "
				+ " where ModuleID= '" + moduleID + "' and roleID = '"+user.getRoleID()+"' ;";
		pd.put("userbutton", roleDao.query(SYS_RoleButton.class, sql));
		try {
			mv.addObject("model", pd);
			mv.setViewName("/cms/message/message_view");
		} catch (Exception e) {
			return MessageUtil.exception(user, mv, e);
		}
		
		return MessageUtil.success(mv);

	}
	
	@RequestMapping(params="p=policeView")
	public ModelAndView policeView(HttpSession session, @RequestParam("moduleID") String moduleID) throws Exception {
		pd = getPageData();
	    SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
	    String sql = "select * "
				+ " from SYS_RoleButton "
				+ " where ModuleID= '" + moduleID + "' and roleID = '"+user.getRoleID()+"' ;";
		pd.put("userbutton", roleDao.query(SYS_RoleButton.class, sql));
		try {
			mv.addObject("model", pd);
			mv.setViewName("/cms/message/message_policeView");
		} catch (Exception e) {
			return MessageUtil.exception(user, mv, e);
		}
		
		return MessageUtil.success(mv);

	}
	
	
	@RequestMapping(params = "p=add")
	public @ResponseBody Map<String,Object> add(@ModelAttribute Message  entity, HttpSession session) {
		SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
		try {
			
			entity.setCreateTime(DateUtil.getDateTimeString());
			
			newsDao.insert(entity);
		} catch (Exception e) {
			return MessageUtil.exception(user, e);
		}
		
		return MessageUtil.success();
	}
	
	
	
	
	@RequestMapping(params = "p=eyeView")
	public ModelAndView showEyeView(Message entity, HttpSession session) {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd = getPageData();
		pd.put("entity", newsDao.queryByPrimary(entity));
		try {
		
			mv.addObject("model", pd);
			mv.setViewName("/cms/message/message_eye");
		} catch (Exception e) {
			return MessageUtil.exception(user, mv, e);
		}
		
		return MessageUtil.success(mv);
	}
	
	@RequestMapping(params = "p=showAddView")
	public ModelAndView showAddView(@ModelAttribute Message entity, HttpSession session) {
		
		User user = (User) session.getAttribute(Const.SESSION_USER);
		try {
			String sql = SQLUtil.getQuerySQL(Message.class) ;
			List<Message> range = newsDao.query(Message.class, sql);
			mv.addObject("model", getPageData().put("range", range));
			mv.setViewName("/cms/message/message_add");
		} catch (Exception e) {
			return MessageUtil.exception(user, mv, e);
		}
		return MessageUtil.success(mv);
	}
	
	
	
	@RequestMapping(params = "p=edit")
	public @ResponseBody Map<String,Object> edit(@ModelAttribute Message entity, HttpSession session) throws Exception {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		try {
			
			String sql = SQLUtil.getUpdateSQL(entity);
			newsDao.jdbcTemplate.execute(sql);
			
		} catch (Exception e) {
			return MessageUtil.exception(user, e);
		}
		
		return MessageUtil.success();
	}
	
	
	@RequestMapping(params = "p=del")
	public @ResponseBody Map<String,Object> del(@ModelAttribute Message entity, HttpSession session) {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		try {
			newsDao.delete(entity);
		} catch (Exception e) {
			return MessageUtil.exception(user, e);
		}
		
		return MessageUtil.success();
	}
	
	
	@SuppressWarnings("all")
	@RequestMapping(params = "p=find")
	public @ResponseBody Pager find( Pager pager, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			
			String w = "UserName like '%" + pager.getParameters().get("searchText") + "%'"  + SQLUtil.getWhereClause(pager) +" and status='peopleMessage'";

				if (pager.getIsExport()) {
					if (pager.getExportAllData()) {
						pager.setExportDatas(newsDao.query(SQLUtil.getQuerySQL(Message.class)));
					}
					try { 
						ExportUtils.export(request, response, pager);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}
			
				if (pager.getPageSize() == 0) {
					
					
					String sql = SQLUtil.getQuerySQL(Message.class) + " where " + w;
					
				} else {
					
					pager.setAdvanceQuerySorts(SortUtil.sortBuild1("CreateTime", "desc"));
					newsDao.queryCount(pager, Message.class, w);
					newsDao.queryPager(pager, Message.class, w);
					pager.setIsSuccess(true);
				}
				
				if (pager.getIsExport()) {
					if (pager.getExportAllData()) {
						pager.setExportDatas(newsDao.query(SQLUtil.getQuerySQL(Article.class)));
					}
					try {
						ExportUtils.export(request, response, pager);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}
			
				if (pager.getPageSize() == 0) {
					
					String sql = SQLUtil.getQuerySQL(Message.class) + " where " + w;
					pager.setExhibitDatas(newsDao.query(Article.class, sql));
					
				} else {

					//sortFiled
					if(pager.getParameters().get("sortFiled")==null||pager.getParameters().get("sortInt").toString().equals("2")){
						pager.setAdvanceQuerySorts(SortUtil.sortBuild1("CreateTime", "desc"));
						newsDao.queryCount(pager, Message.class, w);
						newsDao.queryPager(pager, Message.class, w);
					}else{
					if("1".equals(pager.getParameters().get("sortInt").toString())){
						pager.setAdvanceQuerySorts(SortUtil.sortBuild1(pager.getParameters().get("sortFiled").toString(), "desc"));
					}else{
						pager.setAdvanceQuerySorts(SortUtil.sortBuild1(pager.getParameters().get("sortFiled").toString(), "asc"));
					}
					newsDao.queryCount(pager, Message.class, w);
					newsDao.queryPager(pager, Message.class, w);
					}
				
//					pager.setAdvanceQuerySorts(SortUtil.sortBuild1("CreateTime", "desc"));
//					newsDao.queryCount(pager, Message.class, w);
//					newsDao.queryPager(pager, Message.class, w);
//					pager.setIsSuccess(true);
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			pager.setIsSuccess(false);
		}
		
		pager.setIsSuccess(true);
		return pager;
	}
	
	
	@SuppressWarnings("all")
	@RequestMapping(params = "p=policeFind")
	public @ResponseBody Pager policeFind( Pager pager, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			
			String w = "UserName like '%" + pager.getParameters().get("searchText") + "%'"  + SQLUtil.getWhereClause(pager) +" and status='policeMessage'";

				if (pager.getIsExport()) {
					if (pager.getExportAllData()) {
						pager.setExportDatas(newsDao.query(SQLUtil.getQuerySQL(Message.class)));
					}
					try { 
						ExportUtils.export(request, response, pager);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}
			
				if (pager.getPageSize() == 0) {
					
					String sql = SQLUtil.getQuerySQL(Message.class) + " where " + w;
					
				} else {
					
					pager.setAdvanceQuerySorts(SortUtil.sortBuild1("CreateTime", "desc"));
					newsDao.queryCount(pager, Message.class, w);
					newsDao.queryPager(pager, Message.class, w);
					pager.setIsSuccess(true);
				}
				
				if (pager.getIsExport()) {
					if (pager.getExportAllData()) {
						pager.setExportDatas(newsDao.query(SQLUtil.getQuerySQL(Article.class)));
					}
					try {
						ExportUtils.export(request, response, pager);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}
			
				if (pager.getPageSize() == 0) {
					
					String sql = SQLUtil.getQuerySQL(Message.class) + " where " + w;
					pager.setExhibitDatas(newsDao.query(Article.class, sql));
					
				} else {
					//sortFiled
					if(pager.getParameters().get("sortFiled")==null||pager.getParameters().get("sortInt").toString().equals("2")){
						pager.setAdvanceQuerySorts(SortUtil.sortBuild1("CreateTime", "desc"));
						newsDao.queryCount(pager, Message.class, w);
						newsDao.queryPager(pager, Message.class, w);
					}else{
					if("1".equals(pager.getParameters().get("sortInt").toString())){
						pager.setAdvanceQuerySorts(SortUtil.sortBuild1(pager.getParameters().get("sortFiled").toString(), "desc"));
					}else{
						pager.setAdvanceQuerySorts(SortUtil.sortBuild1(pager.getParameters().get("sortFiled").toString(), "asc"));
					}
					newsDao.queryCount(pager, Message.class, w);
					newsDao.queryPager(pager, Message.class, w);
					}
//					pager.setAdvanceQuerySorts(SortUtil.sortBuild1("CreateTime", "desc"));
//					newsDao.queryCount(pager, Message.class, w);
//					newsDao.queryPager(pager, Message.class, w);
//					pager.setIsSuccess(true);
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			pager.setIsSuccess(false);
		}
		
		pager.setIsSuccess(true);
		return pager;
	}
	
/*	@RequestMapping(params="p=del")
	public ModelAndView del(HttpSession session,@RequestParam("moduleID") String moduleID) throws Exception {
		//获取用户权限按钮
	   SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);

		String sql = "select * "
				+ " from SYS_RoleButton "
				+ " where ModuleID= '" + moduleID + "' and roleID = '"+user.getRoleID()+"' ;";
		pd.put("userbutton", roleDao.query(SYS_RoleButton.class, sql));
		pd = getPageData();
		try {
			mv.addObject("model", pd);
			mv.setViewName("/cms/message/message_view");
		} catch (Exception e) {
			return MessageUtil.exception(user, mv, e);
		}
		
		return MessageUtil.success(mv);

	}*/
	
	
}
