package com.derbysoft.redis.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.derbysoft.dao.cms.MemberAndNews;
import com.derbysoft.dao.cms.NewsDao;
import com.derbysoft.entity.cms.GmsAlarmReceive;
import com.derbysoft.entity.cms.Message;
import com.derbysoft.entity.cms.News;
import com.derbysoft.entity.sys.SYS_User;
@Service
public class NewsService {
	@Autowired
	private NewsDao newsDao;
    public  boolean JspToHtmlFile(String filePath, String HtmlFile,News news) {
        String str = "";
        //long beginDate = (new Date()).getTime();
        try {
                String tempStr = "";
          FileInputStream is = new FileInputStream(filePath);//读取模块文件
                BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
                while ((tempStr = br.readLine()) != null)
                str = str + tempStr ;
                is.close();
        } catch (IOException e) {
                e.printStackTrace();
                return false;
        }
        try {
    if(null==news.getTitle()){
    	str = str.replaceAll("title1","");
    	}
    else{   	
    str = str.replaceAll("title1",
    		news.getTitle());
    }
    if(null==news.getSummary()){
    	str = str.replaceAll("summary1","");
    	}
    else{   	
        str = str.replaceAll("summary1",
        		news.getSummary());
    }
    if(null==news.getPublishTime()){
    	str = str.replaceAll("time1","");
    	}
    else{   	
    	str = str.replaceAll("time1",
        		news.getPublishTime());//替换掉模块中相应的地方
    }
    if(null==news.getContext()){
    	str = str.replaceAll("zhenwen","");
    	}
    else{   	
        str = str.replaceAll("zhenwen",
        		news.getContext());
    }
    if(null==news.getFilePath()){
    	str = str.replaceAll("picket","");
    	}
    else{   	
        str = str.replaceAll("picket",
        		news.getSavepath());
    }
                File f = new File(HtmlFile);
                OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f),"UTF-8"); 
                BufferedWriter o=new BufferedWriter(write);   
               // BufferedWriter o = new BufferedWriter(new FileWriter(f));
                o.write(str);
                o.close();
               // System.out.println("共用时：" + ((new Date()).getTime() - beginDate) + "ms");
        } catch (IOException e) {
                e.printStackTrace();
                return false;
        }
        return true;
}

/**
 * 根据url生成静态页面
 *
 * @param u        动态文件路经 如：http://www.163.com/x.jsp

 * @param path 文件存放路经如：x:\\abc\bbb.html
 * @return
 */
public  boolean JspToHtmlByURL(String u, String path) {
        //从utl中读取html存为str
        String str = "";
        try {
                URL url = new URL(u);
                URLConnection uc = url.openConnection();
                InputStream is = uc.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
                while (br.ready()) {
                        str += br.readLine() + "\n";
                        
                }
                is.close();
                //写入文件
                File f = new File(path);
                BufferedWriter o = new BufferedWriter(new FileWriter(f));
                o.write(str);
                o.close();
                str = "";
                return true;
        } catch (Exception e) {
                e.printStackTrace();
                return false;
        }
}

/**
 * 根据url生成静态页面
 *
 * @param url 动态文件路经 如：http://www.163.com/x.jsp

 * @return d
 */
public  StringBuffer getHtmlTextByURL(String url) {
        //从utl中读取html存为str
        StringBuffer sb = new StringBuffer();
        try {
                URL u = new URL(url);
                URLConnection uc = u.openConnection();
                InputStream is = uc.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
                while (br.ready()) {
                        sb.append(br.readLine() + "\n");
                }
                is.close();
                return sb;
        } catch (Exception e) {
                e.printStackTrace();
                return sb;
        }
}
/**
 * 
     * @discription 新增
     * @author Knight      
     * @created 2016年2月19日 上午11:42:24     
     * @param entity
 */

public void add(News  entity){
	newsDao.insert(entity);
}
/**
 * 
     * @discription 根据id查新闻
     * @author Knight      
     * @created 2016年2月19日 上午11:42:24     
     * @param entity
 */
public News queryByPrimary(News entity) throws Exception{

	return newsDao.queryByPrimary(entity);
	
}
/**
 * 
     * @discription 根据sql查新闻
     * @author Knight      
     * @created 2016年2月19日 上午11:42:24     
     * @param entity
 */
public List<News> queryBySql(String sql) throws Exception{

	return newsDao.query(News.class, sql);
	
}



/**
 * 
     * @discription 更新新闻
     * @author Knight      
     * @created 2016年2月19日 上午11:42:24     
     * @param entity
 */
public void updateNews(News entity) throws Exception{
	newsDao.update(entity);	
}
/**
 * 
     * @discription 删除新闻
     * @author Knight      
     * @created 2016年2月19日 上午11:42:24     
     * @param entity
 */
public void delNews(News entity) throws Exception{
	newsDao.delete(entity);
}

/**
 * 
     * @discription 查询新闻
     * @author Knight      
     * @created 2016年2月19日 下午2:04:04     
     * @param entity
 * @return 
 */
public List<News> queryNews(News entity) throws Exception{
	List<News> query = newsDao.query(entity);
    return query;
}
/**
 * 
     * @discription 删除某个新闻的所有收藏
     * @author Knight      
     * @created 2016年2月19日 下午2:17:57     
     * @param entity
     * @return
 */
public void delConllect(News entity) throws Exception{
	String w="DELETE from cms_user_news where  newsID='"+entity.getNewsID()+"'";
	newsDao.jdbcTemplate.execute(w);
}
/**
 * 
     * @discription 删除个人收藏
     * @author Knight      
     * @created 2016年2月19日 下午2:17:57     
     * @param entity
     * @return
 */
public void delPeopleConllect(News entity) throws Exception{
	String w="DELETE from cms_user_news where newsID='"+entity.getNewsID()+"'and userid='"+entity.getUserID()+"'";
	newsDao.jdbcTemplate.execute(w);
}
/**
 * 
     * @discription 查询某个人的收藏新闻(只有个数)  
     * @author Knight      
     * @created 2016年2月19日 下午2:46:53     
     * @param entity
 * @return 
     * @throws Exception
 */

public List<MemberAndNews> queryPeopleConllect(MemberAndNews memberAndNews) throws Exception{
	return  newsDao.query(memberAndNews);
}

/**
 * 
     * @discription 查询某个人的收藏新闻 (主要是新闻) 
     * @author Knight      
     * @created 2016年2月19日 下午2:46:53     
     * @param entity
 * @return 
     * @throws Exception
 */
public List<MemberAndNews> queryAllConllect(MemberAndNews memberAndNews) throws Exception{
	return  newsDao.query(memberAndNews);
}

/**
 * 
     * @discription 查询警察的所有可以查看的新闻
     * @author Knight      
     * @created 2016年2月19日 下午2:46:53     
     * @param entity
 * @return 
     * @throws Exception
 */
public HashMap<String, List<News>> queryAllNewsBySYS_UserID(int  page , String userID) throws Exception{
	 int i= (page-1)*10;
      // User_News user_News = new User_News();
      //String w1="select * from (select b.* , 'okCollect' as collectState from (SELECT * from  cms_user_news where userid="+"'"+userID+"'"+") a LEFT JOIN  (select * from cms_news where State='peopleNews' or State='allNews') b   on b.newsid=a.newsid   UNION select * , 'noCollect' as collectState from cms_news where newsid  not in (select NewsID from cms_user_news where userid="+"'"+userID+"'"+") and State= 'allNews' or State='peopleNews' ) c order BY c.updatetime DESC";
      //这是查新闻不是焦点
      String w2="select * from (select b.* , 'okCollect' as collectState from (SELECT * from  cms_user_news where userid="+"'"+userID+"'"+") a LEFT JOIN  (select * from (select * from cms_news where  PublishState='all' or PublishState= 'police' ) t where    t.FocusStatus='noFocus'   AND t.State= 'news' ) b   on b.newsid=a.newsid   UNION select * , 'noCollect' as collectState from (select * from cms_news where  PublishState='all' or PublishState= 'police' ) t where t.newsid  not in (select NewsID from cms_user_news where userid="+"'"+userID+"'"+") and t.State= 'news' and t.FocusStatus='noFocus' ) c where 1=1 order BY c.updatetime DESC LIMIT "+i+" ,10";

      //这是查公告不是焦点
      // String w3="select * from (select b.* , 'okCollect' as collectState from (SELECT * from  cms_user_news where userid="+"'"+userID+"'"+") a LEFT JOIN  (select * from (select * from cms_news where  PublishState='all' or PublishState= 'police' ) t where    t.FocusStatus='noFocus'   AND t.State= 'notice' ) b   on b.newsid=a.newsid   UNION select * , 'noCollect' as collectState from (select * from cms_news where  PublishState='all' or PublishState= 'police' ) t where t.newsid  not in (select NewsID from cms_user_news where userid="+"'"+userID+"'"+") and t.State= 'notice' and t.FocusStatus='noFocus' ) c order BY c.updatetime DESC";
      //这是查新闻是焦点
      String w4="select * from (select b.* , 'okCollect' as collectState from (SELECT * from  cms_user_news where userid="+"'"+userID+"'"+") a LEFT JOIN  (select * from (select * from cms_news where  PublishState='all' or PublishState= 'police' ) t where    t.FocusStatus='yesFocus'   AND t.State= 'news' ) b   on b.newsid=a.newsid   UNION select * , 'noCollect' as collectState from (select * from cms_news where  PublishState='all' or PublishState= 'police' ) t where t.newsid  not in (select NewsID from cms_user_news where userid="+"'"+userID+"'"+") and t.State= 'news' and t.FocusStatus='yesFocus' ) c order BY c.updatetime DESC ";                    
      //这是查公告是焦点
      //String w5="select * from (select b.* , 'okCollect' as collectState from (SELECT * from  cms_user_news where userid="+"'"+userID+"'"+") a LEFT JOIN  (select * from (select * from cms_news where  PublishState='all' or PublishState= 'police' ) t where    t.FocusStatus='yesFocus'   AND t.State= 'notice' ) b   on b.newsid=a.newsid   UNION select * , 'noCollect' as collectState from (select * from cms_news where  PublishState='all' or PublishState= 'police' ) t where t.newsid  not in (select NewsID from cms_user_news where userid="+"'"+userID+"'"+") and t.State= 'notice' and t.FocusStatus='yesFocus' ) c order BY c.updatetime DESC";	       
     
      List<News> query2 = newsDao.query(News.class, w2);
      List<News> query4 = newsDao.query(News.class, w4);
	  //List<Map<String, Object>> query5 = newsDao.query(w5);
	   if(query4.size()>0){
		   
		   for(int j=0;j<query4.size();j++ ){
			   if(null==query4.get(j).getNewsID()){
				  query4.remove(j);  
			   }
		   }}
	  if(query2.size()>0){
		   for(int j=0;j<query2.size();j++ ){
			   if(null==query2.get(j).getNewsID()){
				  query2.remove(j);  
			   }
		   }
		   }
		HashMap<String,List<News>> hashMap = new HashMap<String, List<News> >();			
      hashMap.put("nofocusAndNews",query2);
     // hashMap.put("nofocusAndNotice", query3);
      hashMap.put("yesFocusAndNews",query4);
      return hashMap;
}


/**
 * 
     * @discription 查询警察的所有可以查看的公告
     * @author Knight      
     * @created 2016年2月19日 下午2:46:53     
     * @param entity
 * @return 
     * @throws Exception
 */
public HashMap<String, List<News>> queryAllNoticeBySYS_UserID(int  page , String userID) throws Exception{
	 int i= (page-1)*10;
      //这是查公告不是焦点
      String w3="select * from (select b.* , 'okCollect' as collectState from (SELECT * from  cms_user_news where userid="+"'"+userID+"'"+") a LEFT JOIN  (select * from (select * from cms_news where  PublishState='all' or PublishState= 'police' ) t where    t.FocusStatus='noFocus'   AND t.State= 'notice' ) b   on b.newsid=a.newsid   UNION select * , 'noCollect' as collectState from (select * from cms_news where  PublishState='all' or PublishState= 'police' ) t where t.newsid  not in (select NewsID from cms_user_news where userid="+"'"+userID+"'"+") and t.State= 'notice' and t.FocusStatus='noFocus' ) c where 1=1 order BY c.updatetime DESC LIMIT "+i+" ,10";
      //这是查新闻是焦点
     // String w4="select * from (select b.* , 'okCollect' as collectState from (SELECT * from  cms_user_news where userid="+"'"+userID+"'"+") a LEFT JOIN  (select * from (select * from cms_news where  PublishState='all' or PublishState= 'member' ) t where    t.FocusStatus='yesFocus'   AND t.State= 'news' ) b   on b.newsid=a.newsid   UNION select * , 'noCollect' as collectState from (select * from cms_news where  PublishState='all' or PublishState= 'member' ) t where t.newsid  not in (select NewsID from cms_user_news where userid="+"'"+userID+"'"+") and t.State= 'news' and t.FocusStatus='yesFocus' ) c order BY c.updatetime DESC";                    
     //这是查公告是焦点
      String w5="select * from (select b.* , 'okCollect' as collectState from (SELECT * from  cms_user_news where userid="+"'"+userID+"'"+") a LEFT JOIN  (select * from (select * from cms_news where  PublishState='all' or PublishState= 'police' ) t where    t.FocusStatus='yesFocus'   AND t.State= 'notice' ) b   on b.newsid=a.newsid   UNION select * , 'noCollect' as collectState from (select * from cms_news where  PublishState='all' or PublishState= 'police' ) t where t.newsid  not in (select NewsID from cms_user_news where userid="+"'"+userID+"'"+") and t.State= 'notice' and t.FocusStatus='yesFocus' ) c order BY c.updatetime DESC";
		//List<Map<String, Object>> query2 = newsDao.query(w2);
	   List<News> query3 = newsDao.query(News.class, w3);
      List<News> query5 = newsDao.query(News.class, w5);
		HashMap<String, List<News>> hashMap = new HashMap<String, List<News>>();			
	   if(query5.size()>0){
		   for(int j=0;j<query5.size();j++ ){
			   if(null==query5.get(j).getNewsID()){
				  query5.remove(j);  
			   }
		   }}
	  if(query3.size()>0){
		   for(int j=0;j<query3.size();j++ ){
			   if(null==query3.get(j).getNewsID()){
				  query3.remove(j);  
			   }
		   }
		   }
		// hashMap.put("nofocusAndNews",query2);
      hashMap.put("noFocusAndNotice", query3);
     // hashMap.put("yesFocusAndNews",query4);
      hashMap.put("yesFocusAndNotice",query5);
     return hashMap;
}
/**
 * 
     * @discription 增加反馈
     * @author Knight      
     * @created 2016年2月19日 下午2:46:53     
     * @param entity
 * @return 
     * @throws Exception
 */
public void addMessage(Message message) throws Exception{
	 newsDao.insert(message);
}
/**
 * 
     * @discription 查询反馈
     * @author Knight      
     * @created 2016年2月19日 下午2:46:53     
     * @param entity
 * @return 
     * @throws Exception
 */
public List<GmsAlarmReceive> addMessage(GmsAlarmReceive gmsAlarmReceive) throws Exception{
	return  newsDao.query(gmsAlarmReceive);
}
/**
 * 
     * @discription 只查看收藏的新闻
     * @author Knight      
     * @created 2016年2月22日 下午2:21:36     
     * @param gmsAlarmReceive
     * @return
     * @throws Exception
 */
public List<News> findCollectionOfPolice(SYS_User user) throws Exception{
	//int i= (page-1)*10;
	String w="SELECT * from cms_news ,cms_user_news where cms_news.NewsID=cms_user_news.NewsID and cms_user_news.UserID= ? order by publishTime desc"; //LIMIT "+i+" ,10";
	List<Object> args = new ArrayList<Object>();
	args.add(user.getUserID());
	List<News> querys = newsDao.query(News.class, w,
			args.toArray());
return querys;
}
}
