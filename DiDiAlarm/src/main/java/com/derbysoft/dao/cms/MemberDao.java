package com.derbysoft.dao.cms;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.derbysoft.entity.cms.Article;
import com.derbysoft.entity.cms.Member;
import com.derbysoft.entity.sys.SYS_Module;
import com.derbysoft.entity.sys.SYS_User;
import com.derbysoft.form.MemberForm;

import dy.hrtworkframe.dao.BaseDaoImpl;
import dy.hrtworkframe.entity.Pager;
import dy.hrtworkframe.exception.CustomerException;

@Service
public class MemberDao extends BaseDaoImpl{

public Pager queryArticles(Pager pager,Map<String,Object> parms){
		
		return  super.queryPager(pager, Article.class, parms);
		
	}
	

	public Article findByID(String articleID) {
		Article dic = new Article();
		dic.setArticleID(articleID);
		return super.queryByPrimary(dic);
	}
	
	public Member get(MemberForm form) {
		Member user = null;
		String sql = "select * from cms_user where 1=1 and userName ='" + form.getUser().getUserName() + "'";
		
		List<Member> userList = this.query(Member.class, sql);
		
		if (userList.size() > 0) {
			user = userList.get(0);
		} else {
			throw new CustomerException("用户不存在");
		}
		
		return user;
	}
	
	/**
	 * 获取用户RoleModule
	 */
	public List<SYS_Module> selectUserModule(SYS_User user) throws SQLException {
		String sql = " select c.* "
				+ " from SYS_Module c right join SYS_RoleModule d on d.ModuleID = c.ModuleID "
				+ " right join SYS_Role a on d.RoleID = a.RoleID "
				+ " where a.RoleID='" + user.getRoleID() + "' "
				+ " and lower(c.status) ='valid' order by cast(replace(c.showIndex,null,'0') as char(30)) asc";
		

		
		return super.query(SYS_Module.class, sql);
	}



	
}
