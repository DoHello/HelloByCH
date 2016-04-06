package com.derbysoft.dao.sys;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.derbysoft.entity.cms.Article;

import dy.hrtworkframe.dao.BaseDaoImpl;
import dy.hrtworkframe.entity.Pager;

/**
 * 获取关于新闻的数据操作
*    
* 项目名称：Derby   
* 类名称：NewsDao   
* 类描述：   
* 创建人：LD   
* 创建时间：2015年11月2日 下午3:21:29   
* 修改人：LD   
* 修改时间：2015年11月2日 下午3:21:29   
* 修改备注：   
* @version    
*
 */
@Service
public class CityDao extends BaseDaoImpl {
	
	
	public CityDao (){}
	/**
	 * 获取关于 新闻信息的操作DAO类
	
	* @Title: queryArticles 
	
	* @Description: TODO(关于数据操作的工作) 
	
	* @param @param pager
	* @param @param parms
	* @param @return    设定文件 
	
	* @return Pager    返回类型 
	
	* @throws
	 */
	public Pager queryArticles(Pager pager,Map<String,Object> parms){
		
		return  super.queryPager(pager, Article.class, parms);
		
	}
	
	/**
	 * 查询关于article
	
	* @Title: findByID 
	
	* @Description: TODO(关于数据操作部分的获取 ) 
	
	* @param @param articleID
	* @param @return    设定文件 
	
	* @return Article    返回类型 
	
	* @throws
	 */
	public Article findByID(String articleID) {
		Article dic = new Article();
		dic.setArticleID(articleID);
		return super.queryByPrimary(dic);
	}
}
