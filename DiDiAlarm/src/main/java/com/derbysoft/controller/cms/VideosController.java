package com.derbysoft.controller.cms;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.derbysoft.dao.cms.NewsDao;
import com.derbysoft.entity.SystemInfo;
import com.derbysoft.entity.cms.Article;
import com.derbysoft.entity.cms.Slide;
import com.derbysoft.entity.cms.UserOrder;

import dy.hrtworkframe.controller.base.BaseController;
import dy.hrtworkframe.db.SQLUtil;
import dy.hrtworkframe.entity.Pager;
import dy.hrtworkframe.entity.User;
import dy.hrtworkframe.util.Const;
import dy.hrtworkframe.util.DateUtil;
import dy.hrtworkframe.util.ExportUtils;
import dy.hrtworkframe.util.FileUpload;
import dy.hrtworkframe.util.Logger;
import dy.hrtworkframe.util.MessageUtil;
import dy.hrtworkframe.util.SortUtil;
import dy.hrtworkframe.util.UUIDUtil;

@Transactional
@Controller("videos")
@RequestMapping("videos.do")

public class VideosController extends BaseController {
	


	@Resource(name="newsDao")
	private NewsDao newsDao;
	@Autowired
	private SystemInfo systemInfo;
	
    private static final String[] IMAGE_TYPE = new String[] { ".bmp", ".jpg", ".jpeg", ".gif", ".png" };
    
	@RequestMapping(params="p=view")
	public ModelAndView showListView(HttpSession session) throws Exception {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd = getPageData();
		try {
			mv.addObject("model", pd);
			mv.setViewName("/cms/videos/videos_view");
		} catch (Exception e) {
			return MessageUtil.exception(user, mv, e);
		}
		
		return MessageUtil.success(mv);
	}
	
	
	@RequestMapping(params = "p=add")
	public @ResponseBody Map<String,Object> add(@ModelAttribute  Slide entity, HttpSession session) {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		try {
			entity.setCreateTime(DateUtil.getDateTimeString());
			entity.setLocalType("en_video");
			entity.setSortId("0");
			entity.setSlideID(UUIDUtil.get32UUID());
			newsDao.insert(entity);
		} catch (Exception e) {
			return MessageUtil.exception(user, e);
		}
		
		return MessageUtil.success();
	}
	
	
	
	@RequestMapping(params = "p=eyeView")
	public ModelAndView showEyeView(Article entity, HttpSession session) {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd = getPageData();
		try {
		
			mv.addObject("model", pd);
			mv.setViewName("/cms/videos/videos_eye");
		} catch (Exception e) {
			return MessageUtil.exception(user, mv, e);
		}
		return MessageUtil.success(mv);
	}
	
	@RequestMapping(params = "p=showAddView")
	public ModelAndView showAddView(@ModelAttribute Slide entity, HttpSession session) {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		try {
			String sql = SQLUtil.getQuerySQL(Slide.class) ;
			List<Slide> range = newsDao.query(Slide.class, sql);
			mv.addObject("model", getPageData().put("range", range));
			mv.setViewName("/cms/videos/videos_add");
		} catch (Exception e) {
			return MessageUtil.exception(user, mv, e);
		}
		
		return MessageUtil.success(mv);
	}
	
	
	
   //这个可以上传单个文件
	@RequestMapping(params="p=checkFile")
	public @ResponseBody  Map<String,Object> checkFile1(HttpServletRequest request, 
			@RequestParam MultipartFile file  , HttpSession session) {
	       
        	//图片上传失败
        	boolean isLegal = false;
            for (String type : IMAGE_TYPE) {
                if (StringUtils.endsWithIgnoreCase(file.getOriginalFilename(), type)) {
                    isLegal = true;
                    break;
                }
            }
        if(isLegal==false){
        	return MessageUtil.error();
        }
		User user = (User) session.getAttribute(Const.SESSION_USER);
		Map<String,Object> map = new HashMap<String, Object>();
		try {		
			String fileTureName = file.getOriginalFilename();
			String fileName = UUIDUtil.get32UUID();	
			//filePath
			String filePath  = this.getFilePath(fileName,request);
				 
			String extName =  "." + StringUtils.substringAfterLast(fileTureName, ".");// 生成新的文件名
			FileUpload.fileUp(file,request.getServletContext().getRealPath("/")+filePath, fileName);
			map.put("filePath", filePath+fileName+extName);
		} catch (Exception e) {
			return MessageUtil.exception(user, e);
		}
		return MessageUtil.success(map);	
		/*
		User user = (User) session.getAttribute(Const.SESSION_USER);
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			String fileName = UUID.randomUUID().toString();//uuid后的名字
			String filePath  = request.getServletContext().getRealPath("/")+systemInfo.getUoloadPath();//userfile全路径
			String extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			String url =  systemInfo.getUoloadPath();// userfile/
			
			FileUpload.fileUp(file, filePath, fileName);//文件上传
			map.put("filePath", url+"/"+fileName+extName);
		} catch (Exception e) {
			return MessageUtil.exception(user, e);
		}
		return MessageUtil.success(map);
	*/}
	
	@RequestMapping(params="p=checkFile1")
	public @ResponseBody  Map<String,Object> checkFile(HttpServletRequest request, 
			 UserOrder userOrder  , HttpSession session) {
		MultipartFile[] files = userOrder.getFile();
		int i=0;
		String fileNames="";//这是个文件的文件位置的总和中间用,好分开
		Map<String,Object> map = new HashMap<String, Object>();

		for(;i<files.length;i++){
       
        	//图片上传失败
        	boolean isLegal = false;
            for (String type : IMAGE_TYPE) {
                if (StringUtils.endsWithIgnoreCase(files[i].getOriginalFilename(), type)) {
                    isLegal = true;
                    break;
                }
            }
        if(isLegal==false){
        	return MessageUtil.error();
        }
		User user = (User) session.getAttribute(Const.SESSION_USER);
		try {		
			String fileTureName = files[i].getOriginalFilename();
			String fileName = UUIDUtil.get32UUID();	
			//filePath
			String filePath  = this.getFilePath(fileName,request);
				 
			String extName =  "." + StringUtils.substringAfterLast(fileTureName, ".");// 生成新的文件名
			
			FileUpload.fileUp(files[i], request.getServletContext().getRealPath("/")+filePath, fileName);
			fileNames=fileNames+filePath+fileName+extName+",";
			
			
		} catch (Exception e) {
			return MessageUtil.exception(user, e);
		}
		}
		map.put("filePath", fileNames);
		return MessageUtil.success(map);
		}
	
	
	@RequestMapping(params = "p=edit")
	public @ResponseBody Map<String,Object> edit(@ModelAttribute Slide entity, HttpSession session) throws Exception {
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
	public @ResponseBody Map<String,Object> del(@ModelAttribute Slide entity, HttpSession session) {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		try {
			newsDao.delete(entity);
		} catch (Exception e) {
			return MessageUtil.exception(user, e);
		}
		
		return MessageUtil.success();
	}
	
	
	@SuppressWarnings("deprecation")
	@RequestMapping(params = "p=find")
	public @ResponseBody Pager find( Pager pager, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String w = "Title like '%" + pager.getParameters().get("searchText") + "%'"  + SQLUtil.getWhereClause(pager);

				if (pager.getIsExport()) {
					if (pager.getExportAllData()) {
						pager.setExportDatas(newsDao.query(SQLUtil.getQuerySQL(Slide.class)));
					}
					try {
						ExportUtils.export(request, response, pager);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}
			
				if (pager.getPageSize() == 0) {
					
					String sql = SQLUtil.getQuerySQL(Slide.class) + " where " + w;
					pager.setExhibitDatas(newsDao.query(Slide.class, sql));
					
				} else {
					
					pager.setAdvanceQuerySorts(SortUtil.sortBuild1("CreateTime", "desc"));
					newsDao.queryCount(pager, Slide.class, w);
					newsDao.queryPager(pager, Slide.class, w);
					pager.setIsSuccess(true);
				}
				
				if (pager.getIsExport()) {
					if (pager.getExportAllData()) {
						pager.setExportDatas(newsDao.query(SQLUtil.getQuerySQL(Slide.class)));
					}
					try {
						ExportUtils.export(request, response, pager);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}
			
				if (pager.getPageSize() == 0) {
					
					String sql = SQLUtil.getQuerySQL(Slide.class) + " where " + w;
					pager.setExhibitDatas(newsDao.query(Slide.class, sql));
					
				} else {
					
					pager.setAdvanceQuerySorts(SortUtil.sortBuild1("CreateTime", "desc"));
					newsDao.queryCount(pager, Slide.class, w);
					newsDao.queryPager(pager, Slide.class, w);
					pager.setIsSuccess(true);
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			pager.setIsSuccess(false);
		}
		
		pager.setIsSuccess(true);
		return pager;
	}
	//返回在\\userfile \\2015\\08\\31\
	 private String getFilePath(String sourceFileName,HttpServletRequest request) {
/*	        String baseFolder = systemInfo.getUoloadPath();//userfile
*/	        Date nowDate = new Date();
	        // yyyy/MM/dd
	        String fileFolder = systemInfo.getUoloadPath()  + new DateTime(nowDate).toString("yyyy")
	                + File.separator + new DateTime(nowDate).toString("MM") + File.separator
	                + new DateTime(nowDate).toString("dd") ;
	        File file = new File(request.getServletContext().getRealPath("/")+fileFolder);
	        if (!file.isDirectory()) {
	            // 如果目录不存在，则创建目录
	            file.mkdirs();
	        }
	        
	        return fileFolder+"/" ;
	    }
	 
	//这个可以上传单个文件
		@RequestMapping(params="p=kingCheckFile")
		public @ResponseBody  Map<String,Object> kingCheckFile(HttpServletRequest request, 
				@RequestParam MultipartFile file  , HttpSession session) {
		       
	        	//图片上传失败
	        	boolean isLegal = false;
	            for (String type : IMAGE_TYPE) {
	                if (StringUtils.endsWithIgnoreCase(file.getOriginalFilename(), type)) {
	                    isLegal = true;
	                    break;
	                }
	            }
	        if(isLegal==false){
	        	return MessageUtil.error();
	        }
			User user = (User) session.getAttribute(Const.SESSION_USER);
			Map<String,Object> map = new HashMap<String, Object>();
			try {		
				String fileTureName = file.getOriginalFilename();
				String fileName = UUIDUtil.get32UUID();	
				//filePath
				String filePath  = this.getFilePath(fileName,request);
					 
				String extName =  "." + StringUtils.substringAfterLast(fileTureName, ".");// 生成新的文件名
				FileUpload.fileUp(file, filePath, fileName);
				map.put("filePath", filePath+fileName+extName);
			} catch (Exception e) {
				return MessageUtil.exception(user, e);
			}
			return MessageUtil.success(map);	
			/*
			User user = (User) session.getAttribute(Const.SESSION_USER);
			Map<String,Object> map = new HashMap<String, Object>();
			try {
				String fileName = UUID.randomUUID().toString();//uuid后的名字
				String filePath  = request.getServletContext().getRealPath("/")+systemInfo.getUoloadPath();//userfile全路径
				String extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
				String url =  systemInfo.getUoloadPath();// userfile/
				
				FileUpload.fileUp(file, filePath, fileName);//文件上传
				map.put("filePath", url+"/"+fileName+extName);
			} catch (Exception e) {
				return MessageUtil.exception(user, e);
			}
			return MessageUtil.success(map);
		*/}
		
		private static final Logger LOGGER = Logger.getLogger(VideosController.class);  
	      
		@RequestMapping(params = "p=uploadFile")
	    public void attached(HttpServletRequest request, HttpServletResponse response,   
	            @PathVariable String fileType,  
	            @PathVariable String uploadDate,  
	            @PathVariable String suffix,  
	            @PathVariable String fileName) {  
	        //根据suffix设置响应ContentType  
	        //response.setContentType("text/html; charset=UTF-8");  
	          
	        InputStream is = null;  
	        OutputStream os = null;  
	        try {  
	            File file = new File("d:/attached/" + fileType + "/" + uploadDate + "/" + fileName + "." + suffix);  
	            is = new FileInputStream(file);  
	            byte[] buffer = new byte[is.available()];  
	            is.read(buffer);  
	              
	            os = new BufferedOutputStream(response.getOutputStream());  
	            os.write(buffer);  
	            os.flush();  
	        } catch (Exception e) {  
	            //判断suffix  
	            //图片请求可以在此显示一个默认图片  
	            //file显示文件已损坏等错误提示...  
	            LOGGER.error("读取文件失败", e);  
	        } finally {  
	            if (is != null) {  
	                try {  
	                    is.close();  
	                } catch (IOException e) {  
	                    LOGGER.error("读取文件失败", e);  
	                }  
	                  
	                if (os != null) {  
	                    try {  
	                        os.close();  
	                    } catch (IOException e) {  
	                        LOGGER.error("读取文件失败", e);  
	                    }  
	                }  
	            }  
	        }  
	          
	    }  
		
	

	 
	 
}
