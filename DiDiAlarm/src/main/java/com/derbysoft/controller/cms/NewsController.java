package com.derbysoft.controller.cms;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.derbysoft.controller.service.BaseDaoController;
import com.derbysoft.dao.cms.NewsDao;
import com.derbysoft.entity.SystemInfo;
import com.derbysoft.entity.cms.GmsAlarmReceive;
import com.derbysoft.entity.cms.Message;
import com.derbysoft.entity.cms.News;
import com.derbysoft.entity.cms.PicUploadResult;
import com.derbysoft.entity.cms.User_News;
import com.derbysoft.entity.sys.SYS_User;
import com.derbysoft.jms.activemq.JpushSender;
import com.derbysoft.redis.service.CompressPicService;
import com.derbysoft.redis.service.NewsService;
import com.derbysoft.service.cms.DateService;
import com.derbysoft.service.sys.DicService;

import dy.hrtworkframe.db.SQLUtil;
import dy.hrtworkframe.entity.Pager;
import dy.hrtworkframe.entity.User;
import dy.hrtworkframe.util.Const;
import dy.hrtworkframe.util.DateUtil;
import dy.hrtworkframe.util.ExportUtils;
import dy.hrtworkframe.util.FileUpload;
import dy.hrtworkframe.util.MessageUtil;
import dy.hrtworkframe.util.SortUtil;
import dy.hrtworkframe.util.UUIDUtil;

@Transactional
@Controller("news")
@RequestMapping("news.do")
public class NewsController extends BaseDaoController {
	private static String PEOPLE = "AllPEOPLE";//发送给群众
	private static String POLICE = "AllPLICE";//发送给警察
	private static String ALL = "ALL";//所有平台
	@Autowired
	private JpushSender jpushSender;
	
	@Resource(name="system")
	SystemInfo system;
	
	//private static String  DIDIMOULD = "F:/tomcat/apache-tomcat-7.0.57/me-webapps/DiDiAlarm/";//所有平台
	//private static String  DIDIHOST = "http://10.40.200.56:8083/DiDiFile/";//发送给群众
	@Autowired
	private NewsService newsServcie;
    @Autowired
	private  CompressPicService compressPicService;
	@Autowired
	private DicService dicService;
	
	@Resource(name="newsDao")
	private NewsDao newsDao;
/*	
	@Resource(name = "roleDao")
	private RoleDao roleDao;*/
	@Autowired
	private DateService dateService;

	
	@RequestMapping(params="p=view")
	public ModelAndView showListView(HttpSession session , @RequestParam("moduleID") String moduleID) throws Exception {
		SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
		pd = getPageData();
		try {
			//获取用户权限按钮
			//获取用户权限按钮
			pd.put("userbutton", dateService.findAuthority(user, moduleID));
			mv.addObject("model", pd);
			mv.setViewName("/cms/news/news_view");
		} catch (Exception e) {
			return MessageUtil.exception(user, mv, e);
		}
		
		return MessageUtil.success(mv);
	}
	
	
/*	@RequestMapping(params = "p=add")
	public @ResponseBody Map<String,Object> add(@ModelAttribute Article  entity, HttpSession session) {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		try {
			entity.setCreateTime(DateUtil.getDateTimeString());
			entity.setLocalType("en_news");
			entity.setPublisher(user.getUserName());
			entity.setSortID("0");
			entity.setArticleID(UUIDUtil.get32UUID());
			newsDao.insert(entity);
		} catch (Exception e) {
			return MessageUtil.exception(user, e);
		}
		
		return MessageUtil.success();
	}*/
	
	@RequestMapping(params = "p=add")
	public @ResponseBody Map<String,Object> add(@ModelAttribute News  entity, HttpSession session) {
	     SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
		try {	
			String dateTimeString = DateUtil.getDateTimeString();
			entity.setRealName(user.getRealName());
			entity.setCreateTime(dateTimeString);
			entity.setUpdateTime(dateTimeString);		
			entity.setUserID(user.getUserID());
	        entity.setPublishState("noPublish");
			entity.setNewsID(UUIDUtil.get32UUID());
			entity.setPushNum("0");
/*			if(null==entity.getSavepath()||"".equals(entity.getSavepath())){
				entity.setSavepath();
			}*/
			
			String html =system.absoluteFile+"Demo.html";//模板文件地址
            String filePath = system.absoluteFile+"html/"+ entity.getNewsID() + ".html";//生成文件地址
         
            String url=system.absoluteFile1+"html/"+entity.getNewsID()+".html";
            
            entity.setUrl(url);           
            //entity.setPublishTime(DateUtil.getDateTimeString());
            entity.setFilePath(filePath);
            
            newsServcie.JspToHtmlFile(html,filePath,entity);
            
            newsServcie.add(entity);
			
		} catch (Exception e) {
			return MessageUtil.exception(user, e);
		}
		
		return MessageUtil.success();
	}
	
	@RequestMapping(params = "p=eyeView")
	public ModelAndView showEyeView(News entity, HttpSession session) {
		
		SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
		try {
		pd = getPageData();
		pd.put("NewsState", dicService.findByCategory("infoType"));
		
		pd.put("NewsFocus", dicService.findByCategory("NewsFocus"));
		
		pd.put("entity", newsServcie.queryByPrimary(entity));
		
			mv.addObject("model", pd);
			mv.setViewName("/cms/news/news_eye");
			
		} catch (Exception e) {
			return MessageUtil.exception(user, mv, e);
		}
		
		return MessageUtil.success(mv);
	}

	@RequestMapping(params = "p=listPage")
	public ModelAndView listPage(News entity, HttpSession session) {
		
		SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
		try {
		pd = getPageData();
		pd.put("entity", newsServcie.queryByPrimary(entity));
		
			mv.addObject("model", pd);
			mv.setViewName("/webSite/news_detailed");
		} catch (Exception e) {
			return MessageUtil.exception(user, mv, e);
		}
		
		return MessageUtil.success(mv);
	}
	
	
	@RequestMapping(params = "p=showAddView")
	public ModelAndView showAddView(@ModelAttribute News entity, HttpSession session) {
		SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
		try {
			String sql = SQLUtil.getQuerySQL(News.class) ;
			List<News> range = newsServcie.queryBySql(sql);
			mv.addObject("model", getPageData().put("range", range));
			mv.setViewName("/cms/news/news_add");
		} catch (Exception e) {
			return MessageUtil.exception(user, mv, e);
		}
		
		return MessageUtil.success(mv);
	}
	
	
	
	@RequestMapping(params = "p=edit")
	public @ResponseBody Map<String,Object> edit(@ModelAttribute News entity, HttpSession session) throws Exception {
		SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
		String filePath=null;
		try {
			if(null==entity.getSavepath()){
			News queryByPrimary = newsDao.queryByPrimary(entity);
			filePath = queryByPrimary.getSavepath();
			entity.setSavepath(filePath);
			}
			
            filePath = system.absoluteFile+"html/"+ entity.getNewsID() + ".html";//生成文件地址
        
			entity.setPublishState("noPublish");
			String html =system.absoluteFile+"Demo.html";//模板文件地址
		    String url=system.absoluteFile1+"html/"+entity.getNewsID()+".html";
            entity.setUrl(url);           
            entity.setFilePath(filePath);
            newsServcie.JspToHtmlFile(html,filePath,entity);
            entity.setPublishState("noPublish");
			newsServcie.updateNews(entity);
		} catch (Exception e) {
			return MessageUtil.exception(user, e);
		}
		
		return MessageUtil.success();
	}
	
	
	@RequestMapping(params = "p=del")
	public @ResponseBody Map<String,Object> del(@ModelAttribute News entity, HttpSession session) {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		try {
			User_News user_News = new  User_News();
			user_News.setNewsID(entity.getNewsID());
			//newsDao.delete(user_News);
			
		    News news = newsServcie.queryByPrimary(entity);
		    news.setUrl("");
		    news.setSavepath("");
			File file = new File(news.getSavepath());
			if(file.exists()){
				file.delete();
			}
			newsServcie.delConllect(entity);
			newsServcie.delNews(entity);
		} catch (Exception e) {
			return MessageUtil.exception(user, e);
		}
		
		return MessageUtil.success();
	}
	
	
	@SuppressWarnings("deprecation")
	@RequestMapping(params = "p=find")
	public @ResponseBody Pager find( Pager pager, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		/*try {
			String w = "Title like '%" + pager.getParameters().get("searchText") + "%'"  + SQLUtil.getWhereClause(pager);

				if (pager.getIsExport()) {
					if (pager.getExportAllData()) {
						pager.setExportDatas(newsDao.query(SQLUtil.getQuerySQL(News.class)));
					}
					try {
						ExportUtils.export(request, response, pager);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}
			
				if (pager.getPageSize() == 0) {
					
					String sql = SQLUtil.getQuerySQL(News.class) + " where " + w;
					pager.setExhibitDatas(newsDao.query(News.class, sql));
					
				} else {
					
					pager.setAdvanceQuerySorts(SortUtil.sortBuild1("createTime", "desc"));
					newsDao.queryCount(pager, News.class, w);
					newsDao.queryPager(pager, News.class, w);
					pager.setIsSuccess(true);
				}
				
				if (pager.getIsExport()) {
					if (pager.getExportAllData()) {
						pager.setExportDatas(newsDao.query(SQLUtil.getQuerySQL(News.class)));
					}
					try {
						ExportUtils.export(request, response, pager);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}
			
				if (pager.getPageSize() == 0) {
					
					String sql = SQLUtil.getQuerySQL(News.class) + " where " + w;
					pager.setExhibitDatas(newsDao.query(News.class, sql));
					
				} else {
					
					pager.setAdvanceQuerySorts(SortUtil.sortBuild1("CreateTime", "desc"));
					newsDao.queryCount(pager, News.class, w);
					newsDao.queryPager(pager, News.class, w);
					pager.setIsSuccess(true);
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			pager.setIsSuccess(false);
		}*/
	   
	   String  sql="select * from  cms_news where Title like '%" + pager.getParameters().get("searchText") + "%'  order by publishTime is null DESC ,  ";//updateTime Desc  
	 //sortFiled
		if(pager.getParameters().get("sortFiled")==null||pager.getParameters().get("sortInt").toString().equals("2")){
			sql+=" CONVERT( updateTime USING gbk) Desc  ";
			}else{
		if("1".equals(pager.getParameters().get("sortInt").toString())){
			sql+=" CONVERT("+pager.getParameters().get("sortFiled")+" USING gbk)  Desc ";
			}else{
				sql+=" CONVERT("+pager.getParameters().get("sortFiled")+" USING gbk) asc  ";
		}
		}
	   List<News> query1 = newsDao.query(News.class, sql);
		pager.setRecordCount(query1.size());
      List<News> query = newsDao.query(News.class, sql+" limit "+SQLUtil.mySqlPagerStartWith(pager)+","+
				SQLUtil.pagerEndedWith(pager)+";");
		pager.setExhibitDatas(query);

		pager.setIsSuccess(true);
		return pager;
	  
	    /*for(int i=0 ;i<exhibitDatas.size();i++){
	    	News news = (News)exhibitDatas.get(i);
	    	String publishState = news.getPublishState();
	    	if("member".equals(publishState)){
            news.setPublishState("群众");		}else 	
			if("police".equals(publishState)){
			news.setPublishState("警察");
			}else
			if("all".equals(publishState)){
		    news.setPublishState("所有人");
			}else{
				news.setPublishState("还未发布");
			}*/
	    //}
	
    //List<Map<String, Object>> exportDatas = pager.getExportDatas();  
		/*for(int i=0;i<exportDatas.size();i++){
		 String publishState =(String) exportDatas.get(i).get("publishState");
		if("member".equals(publishState)){}
		exportDatas.get(i).put("publishState", "群众");
		
		if("police".equals(publishState)){
			exportDatas.get(i).put("publishState", "警察");
		}
		if("all".equals(publishState)){
			exportDatas.get(i).put("publishState", "所有人");
		}
        
	}*/
		}
	//新闻发布
	@RequestMapping(params = "p=publish")
	public @ResponseBody Map<String,Object> publish(@ModelAttribute News entity, HttpSession session,HttpServletRequest request) {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		try {			
			//String dateString = DateUtil.getDateString();	
			News news2 = new News();
			news2.setNewsID(entity.getNewsID());
			News news=newsServcie.queryByPrimary(news2);
			news.setPublishTime(DateUtil.getDateTimeString());
			
			String html =system.absoluteFile+"Demo.html";//模板文件地址
            String filePath = system.absoluteFile+"html/"+ news.getNewsID() + ".html";//生成文件地址
         
            String url=system.absoluteFile1+"html/"+news.getNewsID()+".html";
            news.setUrl(url);           
            news.setPublishState(entity.getPublishState());
            news.setPublishTime(DateUtil.getDateTimeString());
            news.setFilePath(filePath);
            
            newsServcie.JspToHtmlFile(html,filePath,news);
            newsServcie.updateNews(news);
           
		} catch (Exception e) {
			return MessageUtil.exception(user, e);
		}
		return MessageUtil.success();
	}
	
	@RequestMapping(params = "p=cancelPublish")
	public @ResponseBody Map<String,Object> cancelpublish(@ModelAttribute News entity, HttpSession session) {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		try {
			//entity.setPublishTime(dateString);			
			User_News user_News = new User_News();
			user_News.setNewsID(entity.getNewsID());
			News news = newsServcie.queryByPrimary(entity);
		    news.setUrl("");
		    
		    news.setPublishTime(null);
		    news.setPublishState("noPublish");
		    //newsDao.delete(user_News);
			newsServcie.delConllect(entity);
		    File file = new File(news.getFilePath());
			if(file.exists()){
				file.delete();
			}			
			
			newsServcie.updateNews(news);
		   // News news = query.get(0);
		   // String publishState = news.getPublishState();

		} catch (Exception e) {
			return MessageUtil.exception(user, e);
		}
		return MessageUtil.success();
	}
	//新闻推送
	@RequestMapping(params = "p=push")
	public @ResponseBody Map<String, Object> push(News entity, HttpSession session) {
		try {
			//SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
			Map<String, Object> m = new HashMap<String, Object>();
			String pushStatus = entity.getPushStatus();
			News news2 = new News();
			news2.setNewsID(entity.getNewsID());
			News news = newsServcie.queryByPrimary(news2);
			

			String infoType = news.getPublishState();
			if ("news".equals(infoType)) {
				if ("member".equals(pushStatus)) {
					m.put("styleNum", "2");
					jpushSender.send(m, PEOPLE);
					news.setPushNum(Integer.toString(Integer.parseInt((news.getPushNum()))+ 1) );
				}
				if ("police".equals(pushStatus)) {
					m.put("styleNum", "2");
					jpushSender.send(m, POLICE);
					news.setPushNum(Integer.toString(Integer.parseInt((news.getPushNum()))+ 1) );
				}
				if ("all".equals(pushStatus)) {
					m.put("styleNum", "2");
					jpushSender.send(m, ALL);
					news.setPushNum(Integer.toString(Integer.parseInt((news.getPushNum()))+ 1) );
				}

			} else {
				if ("member".equals(pushStatus)) {
					m.put("styleNum", "1");
					jpushSender.send(m, PEOPLE);
					news.setPushNum(Integer.toString(Integer.parseInt((news.getPushNum()))+ 1) );
				}
				if ("police".equals(pushStatus)) {
					m.put("styleNum", "1");
					jpushSender.send(m, POLICE);
					news.setPushNum(Integer.toString(Integer.parseInt((news.getPushNum()))+ 1) );
				}
				if ("all".equals(pushStatus)) {
					m.put("styleNum", "1");
					jpushSender.send(m, ALL);
					news.setPushNum(Integer.toString(Integer.parseInt((news.getPushNum()))+ 1) );
				}
			}
			newsServcie.updateNews(news);
		} catch (Exception e) {
			e.printStackTrace();
     return MessageUtil.error();
		}
		return MessageUtil.success();
	   
	}
	
	
	
	//新闻推送
	@RequestMapping(params = "p=push1")
	public Map<String, Object> push1(News entity, HttpSession session) {
		//SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
		try {
			List<News> newsList = newsServcie.queryNews(entity);
            if(newsList.size()==0){
            	
            }
            Map<String, Object> m = new HashMap<String, Object>();
            News news = newsList.get(0);
            String state = news.getState();
			if("1".equals(state)){
			m.put("styleNum", "1");
			//m.put("type","notice");
			m.put("info",news);
			jpushSender.send(m, POLICE);
			news.setPushStatus("已推送");
			}
			
			if("2".equals(state)){
			m.put("type", "news");
			//
			m.put("info",news);
			jpushSender.send(m, PEOPLE);
			news.setPushStatus("已推送");
			}
			
			if("3".equals(state)){
			m.put("type", "news");
			//m.put("type","notice");
			m.put("info",news);
			jpushSender.send(m, ALL);
			news.setPushStatus("已推送");
			}
			
			if("4".equals(state)){
			m.put("type","notice");
			//m.put("type","notice");
			m.put("info",news);
			jpushSender.send(m, POLICE);
			news.setPushStatus("已推送");
			}
			
			if("5".equals(state)){
			m.put("type","notice");
			//m.put("type","notice");
			m.put("info",news);
			jpushSender.send(m, PEOPLE);
			news.setPushStatus("已推送");
			}
			
			if("6".equals(state)){
			m.put("type","notice");
			//m.put("type","notice");
			m.put("info",news);
			jpushSender.send(m, ALL);
			news.setPushStatus("已推送");
			}
			
		} catch (Exception e) {
         MessageUtil.error("MSG108",108);
 		}	
		return MessageUtil.success("MSG1",200);
	}
	
	
	
	/**
	 * 上传图片2用于普通的上传
	 * @Title: checkFile1 
	 * @Description: TODO
	 * @param request
	 * @param file
	 * @param session
	 * @return
	 * @return: Map<String,Object>
	 */
	@RequestMapping(params = "p=checkFile2")
	public @ResponseBody Map<String, Object> checkFile2(
			HttpServletRequest request, //@RequestParam MultipartFile file,
			HttpSession session) {
//		BufferedReader reader;
//		try {
//			reader = request.getReader();
//			System.out.println(reader.readLine());
//		} catch (IOException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		}
		MultipartHttpServletRequest mureq = (MultipartHttpServletRequest) request;

		Map<String, MultipartFile> files = mureq.getFileMap(); 
/*		if (files == null || files.size() == 0) { 
			return MessageUtil.error(6, 6);
		} */
		Map.Entry<String, MultipartFile> e = files.entrySet().iterator().next(); 
		MultipartFile file = e.getValue(); 
		// 图片上传失败
		boolean isLegal = false;
		for (String type : IMAGE_TYPE) {
			if (StringUtils
					.endsWithIgnoreCase(file.getOriginalFilename(), type)) {
				isLegal = true;
				break;
			}
		}
		if (isLegal == false) {
			return MessageUtil.error("MSG9",410);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
//			Member user = (Member) session.getAttribute(Const.SESSION_HAS_MEMBER);
			String fileTureName = file.getOriginalFilename();
			String fileName = UUIDUtil.get32UUID();
			// filePath
			String filePath = this.getImgPath(fileName, request);
			String extName = "."
					+ StringUtils.substringAfterLast(fileTureName, ".");// 生成新的文件名			
			FileUpload.fileUp(file, systemInfo.absoluteFile+filePath, fileName);
		     //CompressPicDemo mypic = new CompressPicDemo();   
			compressPicService.compressPic(systemInfo.absoluteFile+filePath, systemInfo.absoluteFile+filePath, fileName+extName, fileName+extName, 1200, 1200, true);  
	            
			map.put("filePath", systemInfo.DIDIMOULD+filePath + fileName + extName);
		} catch (Exception e1) {
			e1.printStackTrace();
			return MessageUtil.error(6, 6);
		}
		return MessageUtil.success(map);
	}

	
	/**
	 * 富文本的上传图片
	 * @Title: checkFile1 
	 * @Description: TODO
	 * @param request
	 * @param file
	 * @param session
	 * @return
	 * @return: Map<String,Object>
	 */
	@RequestMapping(params = "p=checkFile")
	public @ResponseBody PicUploadResult checkFile(
			HttpServletRequest request, //@RequestParam MultipartFile file,
			HttpSession session) {
//		BufferedReader reader;
//		try {
//			reader = request.getReader();
//			System.out.println(reader.readLine());
//		} catch (IOException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		}
		MultipartHttpServletRequest mureq = (MultipartHttpServletRequest) request;
        PicUploadResult picUploadResult = new PicUploadResult();
		Map<String, MultipartFile> files = mureq.getFileMap(); 
		/*if (files == null || files.size() == 0) { 
			return MessageUtil.error(6, 6);
		} */
		Map.Entry<String, MultipartFile> e = files.entrySet().iterator().next(); 
		MultipartFile file = e.getValue(); 
		// 图片上传失败
		boolean isLegal = false;
		for (String type : IMAGE_TYPE) {
			if (StringUtils
					.endsWithIgnoreCase(file.getOriginalFilename(), type)) {
				isLegal = true;
				break;
			}
		}
		
		if (isLegal == false) {
			picUploadResult.setError(1);
			picUploadResult.setMessage("上传图片的图片格式不对");
		    return picUploadResult;
		}
		
		//Map<String, Object> map = new HashMap<String, Object>();
		try {
//			Member user = (Member) session.getAttribute(Const.SESSION_HAS_MEMBER);
			String fileTureName = file.getOriginalFilename();
			String fileName = UUIDUtil.get32UUID();
			// filePath
			String filePath = this.getImgPath(fileName, request);
			String extName = "."
					+ StringUtils.substringAfterLast(fileTureName, ".");// 生成新的文件名
			//String realPath = request.getServletContext().getRealPath("/");
			FileUpload.fileUp(file,systemInfo.absoluteFile+filePath, fileName);
			//map.put("filePath", filePath + fileName + extName);
			compressPicService.compressPic(systemInfo.absoluteFile+filePath, systemInfo.absoluteFile+filePath, fileName+extName, fileName+extName, 1200, 1200, true);  
			picUploadResult.setUrl(systemInfo.upload+filePath + fileName + extName);
			picUploadResult.setError(0);
		} catch (Exception e1) {
			picUploadResult.setError(1);
			picUploadResult.setMessage("服务器无响应!");
		    return picUploadResult;
		
}
	    return picUploadResult;
	}
	
	
	
	
}
