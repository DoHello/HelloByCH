package com.derbysoft.controller.appgms;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.derbysoft.dao.sys.DicDao;
import com.derbysoft.dao.sys.RoleDao;
import com.derbysoft.dao.sys.UserDao;
import com.derbysoft.entity.SystemInfo;
import com.derbysoft.entity.cms.Article;
import com.derbysoft.entity.cms.Message;
import com.derbysoft.entity.cms.UserOrder;
import com.derbysoft.entity.sys.SYS_RoleButton;
import com.derbysoft.entity.sys.SYS_User;
import com.derbysoft.redis.service.UserService;

import dy.hrtworkframe.controller.base.BaseController;
import dy.hrtworkframe.db.SQLUtil;
import dy.hrtworkframe.entity.Pager;
import dy.hrtworkframe.entity.User;
import dy.hrtworkframe.util.Const;
import dy.hrtworkframe.util.DateUtil;
import dy.hrtworkframe.util.ExportUtils;
import dy.hrtworkframe.util.FileUpload;
import dy.hrtworkframe.util.MessageUtil;
import dy.hrtworkframe.util.PageData;
import dy.hrtworkframe.util.SortUtil;
import dy.hrtworkframe.util.UUIDUtil;
@Controller
@RequestMapping("order.do")
public class OrderUserConteller extends BaseController {   
	@Autowired
	public UserDao userDao;
	
//	@Autowired
//	public UserOrderDao userOrderDao;
//	
	@Autowired
	public SystemInfo systemInfo;
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private UserService userService;

	
	@Resource(name="systemDicDao")
	private DicDao systemDicDao;
   // private static final String[] IMAGE_TYPE = new String[] { ".bmp", ".jpg", ".jpeg", ".gif", ".png" };
   /* 这是对预约的存储
     *这里有很大的问题
     *没有警方的接口
     *占时用假数据代替
     * */
   
    
    @RequestMapping(params="p=checkFile")
	public @ResponseBody  Map<String,Object> checkFile(HttpServletRequest request, 
			@RequestParam("file") MultipartFile file  , HttpSession session) {
    	
		User user = (User) session.getAttribute(Const.SESSION_USER);
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			
			String fileName = UUID.randomUUID().toString();
			//filePath
			String filePath  = this.getFilePath(fileName,request);
			//真实的名字
			String extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			
			String url =  systemInfo.getUoloadPath();
			
			FileUpload.fileUp(file, filePath, fileName);
			map.put("filePath", url+"/"+fileName+extName);
		} catch (Exception e) {
			return MessageUtil.exception(user, e);
		}
		return MessageUtil.success(map);
	}
    
	/*@RequestMapping(params="p=saveUserOrder")
	public @ResponseBody Map<String,Object> saveOrderUser(@ModelAttribute UserOrder orderUser , HttpSession session) {
		
			SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
			
			String sql="select * from gms_UserOrder where 1=1 and UserId = "+ user.getUserID() ;
			if (userDao.query(UserOrder.class, sql).size() >= 1) {
				return MessageUtil.error();
			}
			try {
				orderUser.setRealName(user.getRealName());
				orderUser.setOrderID(UUIDUtil.get32UUID());
				orderUser.setUserID(user.getUserID());
				orderUser.setUserName(user.getUserName());
				orderUser.setStatus("1");
				String date = DateUtil.getDateString();
				orderUser.setCreateTime(date);
				orderUser.setUpdateTime(date);
				orderUser.setPhone(user.getPhone());
			
//				SQLUtil.getInsertSQL(orderUser);
				userDao.jdbcTemplate.execute(SQLUtil.getInsertSQL(orderUser));
				
			} catch (Exception e) {
				return MessageUtil.error();		

			}
			return MessageUtil.success();		
	}*/
    
    
	

	@RequestMapping(params="p=view")
	public ModelAndView showListView(HttpSession session,@RequestParam("moduleID") String moduleID) throws Exception {

	    SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
		pd = getPageData();
		try {
			String sql = "select * "
					+ " from SYS_RoleButton "
					+ " where ModuleID= '" + moduleID + "' ";
			pd.put("userbutton", roleDao.query(SYS_RoleButton.class, sql));
			mv.addObject("model", pd);
			mv.setViewName("/cms/order/order_view");
		} catch (Exception e) {
			return MessageUtil.exception(user, mv, e);
		}
		
		return MessageUtil.success(mv);

	}
	@SuppressWarnings("deprecation")
	@RequestMapping(params="p=find")
	public @ResponseBody Pager find (Pager pager, HttpSession session,
			HttpServletRequest request, HttpServletResponse response){

		try {

			String w = "realname like '%" + pager.getParameters().get("searchText") + "%'" 
					+ " or phone like '%" + pager.getParameters().get("searchText") + "%'"
					+" and orderTime > "+ DateUtil.getDateString() + SQLUtil.getWhereClause(pager);

				if (pager.getIsExport()) {
					if (pager.getExportAllData()) {
						pager.setExportDatas(userDao.query(SQLUtil.getQuerySQL(UserOrder.class)));
					}
					try {
						ExportUtils.export(request, response, pager);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}
			
				if (pager.getPageSize() == 0) {
					
					@SuppressWarnings("all")
					String sql = SQLUtil.getQuerySQL(UserOrder.class) + " where " + w;
					
				} else {
					
					pager.setAdvanceQuerySorts(SortUtil.sortBuild1("CreateTime", "desc"));
					userDao.queryCount(pager, UserOrder.class, w);
					userDao.queryPager(pager, UserOrder.class, w);
					pager.setIsSuccess(true);
				}
				
				if (pager.getIsExport()) {
					if (pager.getExportAllData()) {
						pager.setExportDatas(userDao.query(SQLUtil.getQuerySQL(Article.class)));
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
					pager.setExhibitDatas(userDao.query(Article.class, sql));
					
				} else {
					//sortFiled
					if(pager.getParameters().get("sortFiled")==null||pager.getParameters().get("sortInt").toString().equals("2")){
						pager.setAdvanceQuerySorts(SortUtil.sortBuild1("CreateTime", "desc"));
						userDao.queryCount(pager, UserOrder.class, w);
						userDao.queryPager(pager, UserOrder.class, w);
					}else{
					if("1".equals(pager.getParameters().get("sortInt").toString())){
						pager.setAdvanceQuerySorts(SortUtil.sortBuild1(pager.getParameters().get("sortFiled").toString(), "desc"));
					}else{
						pager.setAdvanceQuerySorts(SortUtil.sortBuild1(pager.getParameters().get("sortFiled").toString(), "asc"));
					}
					userDao.queryCount(pager, UserOrder.class, w);
					userDao.queryPager(pager, UserOrder.class, w);
					}
//					pager.setAdvanceQuerySorts(SortUtil.sortBuild1("CreateTime", "desc"));
//					userDao.queryCount(pager, UserOrder.class, w);
//					userDao.queryPager(pager, UserOrder.class, w);
//					pager.setIsSuccess(true);
				}	
		} catch (Exception e) {
			e.printStackTrace();
			pager.setIsSuccess(false);
		}
		pager.setIsSuccess(true);
		return pager;
	}
	
	//这是查看详情信息
	@RequestMapping(params = "p=eyeView")
	public ModelAndView showEyeView(@ModelAttribute UserOrder userOrder, HttpSession session) {
		/*User user = (User) session.getAttribute(Const.SESSION_USER);
		PageData pd = getPageData();
		try {
			String sql = SQLUtil.getQuerySQL(UserOrder.class).replace("\n", "")+" where OrderID='"+userOrder.getOrderID() + "'"; 
		      UserOrder entry = userDao.query(UserOrder.class, sql).get(0);
             if(entry.getStatus().equals("1")){
            	 entry.setStatus("待办理");
              }else if(entry.getStatus().equals("2")){
            	entry.setStatus("预约过期");
             }else{
            	 entry.setStatus("办理成功");
             }
             
		      
			mv.addObject("model", pd);
			mv.addObject("model", pd.put("entity", entry));
			mv.setViewName("/cms/order/order_eye");
		} catch (Exception e) {
			return MessageUtil.exception(user, mv, e);
		}
		
		return MessageUtil.success(mv);*/
		SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
		PageData pd = getPageData();
		try {
			pd.put("Reservation", systemDicDao.findByCategory("Reservation"));
//			pd.put("role", roleDao.findByCompany(user.getCompanyID()));
			String sql = SQLUtil.getQuerySQL(UserOrder.class).replace("\n", "")+" where OrderID='"+userOrder.getOrderID() + "'"; 
//			SYS_User entry = userDao.query(SYS_User.class, sql).get(0);
			UserOrder entry = roleDao.query(UserOrder.class, sql).get(0);
			//mv.addObject("model", pd);
			mv.addObject("model", pd.put("entity", entry));
			mv.setViewName("/cms/order/order_eye");
		} catch (Exception e) {
			return MessageUtil.exception(user, mv, e);
		}
		
		return MessageUtil.success(mv);
	}
	
	@RequestMapping(params = "p=edit")
	public @ResponseBody Map<String,Object> edit(@ModelAttribute UserOrder entity, HttpSession session) throws Exception {
		SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
		try {
			userDao.update(entity);			
		} catch (Exception e) {
			return MessageUtil.exception(user, e);
		}
		
		return MessageUtil.success();
	}
	
	
   //返回E:\\userfile \\2015\\09\\17\\1112321321321.jpg
	 private String getFilePath(String sourceFileName,HttpServletRequest request) {
		 
	        String baseFolder = systemInfo.getUoloadPath();//userfile
	        Date nowDate = new Date();
	        // yyyy/MM/dd
	        String fileFolder = request.getServletContext().getRealPath("/")+systemInfo.getUoloadPath()+baseFolder + File.separator + new DateTime(nowDate).toString("yyyy")
	                + File.separator + new DateTime(nowDate).toString("MM") + File.separator
	                + new DateTime(nowDate).toString("dd");
	        File file = new File(fileFolder);
	        if (!file.isDirectory()) {
	            // 如果目录不存在，则创建目录
	            file.mkdirs();
	        }
	        // 生成新的文件名
	        String fileName = UUIDUtil.get32UUID()
	                + RandomUtils.nextInt(100, 9999) + "." + StringUtils.substringAfterLast(sourceFileName, ".");
	        return fileFolder + File.separator + fileName;
	    }
	 
	@SuppressWarnings("all")
	@RequestMapping(params="p=findPolice")
		public @ResponseBody Pager findPolice (Pager pager, HttpSession session,
				HttpServletRequest request, HttpServletResponse response){

			try {

				String w = "realname like '%" + pager.getParameters().get("searchText") + "%'"  + SQLUtil.getWhereClause(pager);

					if (pager.getIsExport()) {
						if (pager.getExportAllData()) {
							pager.setExportDatas(userDao.query(SQLUtil.getQuerySQL(UserOrder.class)));
						}
						try {
							ExportUtils.export(request, response, pager);
						} catch (Exception e) {
							e.printStackTrace();
						}
						return null;
					}
				
					if (pager.getPageSize() == 0) {
						
						String sql = SQLUtil.getQuerySQL(UserOrder.class) + " where " + w;
						
					} else {
						
						pager.setAdvanceQuerySorts(SortUtil.sortBuild1("CreateTime", "desc"));
						userDao.queryCount(pager, UserOrder.class, w);
						userDao.queryPager(pager, UserOrder.class, w);
						pager.setIsSuccess(true);
					}
					
					if (pager.getIsExport()) {
						if (pager.getExportAllData()) {
							pager.setExportDatas(userDao.query(SQLUtil.getQuerySQL(Article.class)));
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
						pager.setExhibitDatas(userDao.query(Article.class, sql));
						
					} else {
						
						pager.setAdvanceQuerySorts(SortUtil.sortBuild1("CreateTime", "desc"));
						userDao.queryCount(pager, UserOrder.class, w);
						userDao.queryPager(pager, UserOrder.class, w);
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
