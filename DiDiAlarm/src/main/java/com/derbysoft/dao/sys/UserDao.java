package com.derbysoft.dao.sys;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.derbysoft.entity.sys.SYS_Module;
import com.derbysoft.entity.sys.SYS_ModuleButt;
import com.derbysoft.entity.sys.SYS_User;
import com.derbysoft.form.UserForm;

import dy.hrtworkframe.dao.BaseDaoImpl;
import dy.hrtworkframe.db.SQLUtil;
import dy.hrtworkframe.exception.CustomerException;
import dy.hrtworkframe.util.UUIDUtil;

@Transactional
@Repository
public class UserDao extends BaseDaoImpl {

	@Resource(name = "jdbcTemplate")
	public JdbcTemplate jdbcT;

	/**
	 * (这个方法是注册用户使用的) 如果存在将注册失败！
	 */
	public String regUser(UserForm form) throws SQLException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		SYS_User user = new SYS_User();
		user.setUserID(UUIDUtil.get32UUID());
		user.setUserName(form.getUser().getUserName());
		user.setRealName(form.getUser().getRealName());
		user.setPassword(form.getUser().getPassword());
		user.setCompanyID(form.getUser().getCompanyID());
		user.setCompanyName(form.getUser().getCompanyName());
		user.setPhone(form.getUser().getPhone());
		user.setQq(form.getUser().getQq());
		user.setInputDate(formatter.format(new Date()));
		user.setInputID(form.getUser().getInputID());
		user.setInputName(form.getUser().getInputName());
		user.setEmail(form.getUser().getEmail());
		user.setIdCard(form.getUser().getIdCard());

		/**
		String sql = "insert into "
				+ SQLUtil.getTableName(form.getUser())
				+ " (UserID,UserName,RealName,UserPasword,CompanyID,CompanyName,"
				+ "Phone,Qq,InputDate,Email,IDCard,roleID,roleName,DataStatusEnum,"
				+ "DataStatusEnumName,DataStatusEnumValue,CreateUserID,CreateUserName,UserTypeEnum) values ('"
				+ form.getUser().getUserID() + "','"
				+ form.getUser().getRealName() + "'," + "'"
				+ form.getUser().getUserName() + "','"
				+ form.getUser().getUserPasword() + "','"
				+ form.getUser().getCompanyID() + "','" + ""
				+ form.getUser().getCompanyName() + "','"
				+ form.getUser().getPhone() + "','" + form.getUser().getQq()
				+ "','" + form.getUser().getInputDate() + "','"
				+ form.getUser().getEmail() + "','"
				+ form.getUser().getiDCard() + "','boss','老板','"
				+ DataStatusEnum.TAKE_EFFECT.SUBMIT + "','"
				+DataStatusEnum.TAKE_EFFECT.SUBMIT.getName()+"','"
				+DataStatusEnum.TAKE_EFFECT.SUBMIT.getValue()+"','onnwer','系统','"+UserTypeEnum.ADMIN+"')";
		jdbcT.execute(sql);
		*/
		
		insert(user);
		
		return "";

	}

	/**
	 * 修改注册用户信息
	 */
//	public String update(SYS_User user) throws SQLException {
//		String sql = "update " + SQLUtil.getTableName(user)
//				+ "   set UserName ='" + user.getUserName()
//				+ "' , Password = '" + user.getPassword() 
//				+ "' , Alias = '" + user.getAlias()
//				+ "' , TokenID = '" + user.getTokenID()
//				+ "' , Tag = '" + user.getTag()
//				+ "', CompanyID = '" + user.getCompanyID() + "', CompanyName='"
//				+ user.getCompanyName() + "'," + "Phone =  '" + user.getPhone()
//				+ "', Qq = '" + user.getQq() + "', InputDate = '"
//				+ user.getInputDate() + "',Email = '" + user.getEmail()
//				+ "', IDCard='" + user.getIdCard() + "' where UserID = '"
//				+ user.getUserID() + "'";
//		jdbcT.execute(sql);
//		return "";
//	}
	public String updateNoPwd(SYS_User user) throws SQLException {
		String sql = "update " + SQLUtil.getTableName(user)
				+ "   set UserName ='" + user.getUserName()
				+ "',    RoleID ='" + user.getRoleID()
				+ "',   RoleName ='" + user.getRoleName()
//				+ "' , Password = '" + user.getPassword()
				+ "', CompanyID = '" + user.getCompanyID() + "', CompanyName='"
				+ user.getCompanyName() + "'," + "Phone =  '" + user.getPhone()
				+ "', Qq = '" + user.getQq() + "', InputDate = '"
				+ user.getInputDate() + "',Email = '" + user.getEmail()
				+ "', IDCard='" + user.getIdCard() + "' where UserID = '"
				+ user.getUserID() + "'";
		jdbcT.execute(sql);
		return "";
	}

	/**
	 * 获取查询数据的条目数，为分页功能服务
	public int queryPageCount(UserForm form) throws SQLException {

		String sql = "select ROW_NUMBER() over(order by userID asc)  from BOSS_User_Information where 1=1 ";
		if (form.getUser().getCompanyName() != null) {
			sql += " and CompanyName ='%" + form.getUser().getCompanyName()
					+ "%'";
		}
		if (form.getStarDate() != null) {
			sql += " and InputDate >'" + form.getStarDate() + "'";
		}
		if (form.getEndDate() != null) {
			sql += " and InputDate<" + form.getEndDate() + "'";
		}
		if (form.getUser().getUserName() != null) {
			sql += " and UserName='%" + form.getUser().getUserName() + "%'";
		}
		return jdbcT.queryForObject(sql, Integer.class);
	}
	*/

	/**
	 * 获取根据登陆名的用户信息
	 */
	public SYS_User get(UserForm form) {
		SYS_User user = null;
		String sql = "select * from SYS_User where 1=1 and userName ='" + form.getUser().getUserName() + "'";
		
		List<SYS_User> userList = this.query(SYS_User.class, sql);
		
		if (userList.size() > 0) {
			user = userList.get(0);
		} else {
			throw new CustomerException("用户不存在");
		}
		
		return user;
	}
	
	public SYS_User getUser(String username, String password) {
		SYS_User user = null;
		String sql = "select * from SYS_User where 1=1 and userName ='" + username + "'";
		
		List<SYS_User> userList = this.query(SYS_User.class, sql);
		
		if (userList.size() > 0) {
			user = userList.get(0);
			if (!password.equals(userList.get(0).getPassword())) {
				throw new CustomerException("密码错误");
			}
		} else {
			throw new CustomerException("用户" + username + "不存在");
		}
		
		return user;
	}

	/**
	 * 获取根据条件查询出来的用户信息
	@SuppressWarnings({ "rawtypes" })
	public List select(UserForm form) throws SQLException {

		String sql = "with A (userID)as (select top "
				+ (form.getPager().getPageSize() * form.getPager().getNowPage())
				+ " " + "userID from BOSS_User_Information where 1=1 ";
		if (form.getUser().getCompanyName() != null) {
			sql += " and CompanyName ='%" + form.getUser().getCompanyName() + "%'";
		}
		if (form.getStarDate() != null) {
			sql += " and InputDate >" + form.getStarDate() + "'";
		}
		if (form.getEndDate() != null) {
			sql += " and InputDate<" + form.getEndDate() + "'";
		}
		if (form.getUser().getUserName() != null) {
			sql += " and UserName='%" + form.getUser().getUserName() + "%'";
		}

		sql += " order by UserID DESC ,InputDate asc), "
				+ "B (userID) as (select top "
				+ form.getPager().getPageSize()
				+ " A.userID from A)"
				+ "select * from BOSS_User_Information userz left join B  on userz.UserID = b.userID ";

		List userList = jdbcT.queryForList(sql, form.getUser().getClass());
		return userList;

	}
	*/
	
	
	/**
	 * 获取用户RoleModule
	 */
	public List<SYS_Module> selectUserModule(SYS_User user) throws SQLException {
		String sql = " select c.* "
				+ " from SYS_Module c right join SYS_RoleModule d on d.ModuleID = c.ModuleID "
				+ " right join SYS_Role a on d.RoleID = a.RoleID "
				+ " where a.RoleID='" + user.getRoleID() + "' "
				+ " and lower(c.status) ='valid' order by cast(replace(c.showIndex,null,'0') as char(30)) asc";
		
		/*
		if (user != null
				&& user.getUserTypeEnum().equals(UserTypeEnum.ADMIN.toString())) {
			
			sql = "select  C.moduleID,C.moduleName,C.parentID,C.modulePath,C.isMenu,C.buttonIds,C.buttonName, C.appPlatform,C.iconClass,C.showIndex,C.description,C.url "
					+ "from SYS_User A left join SYS_RoleModule B on A.RoleID=B.RoleID right join SYS_Module C on B.ModuleID=C.moduleID "
					+ "where A.userName='"
					+ user.getAccount()
					+ "' and lower(C.status) ='valid'"
					+ " order by C.showIndex asc";
		} else {
			sql = " select C.moduleID,C.moduleName,C.parentID,C.modulePath,C.isMenu,C.buttonIds,C.buttonName, C.appPlatform,C.iconClass,C.showIndex,C.description,C.url "
					+ " from SYS_Module C right join SYS_UserModule D on D.moduleID = C.ModuleID "
					+ " right join  SYS_Role a on D.RoleID = a.RoleID "
					+ " where a.RoleID='" + user.getRoleID() + "' "
					+ " and lower(C.status) ='valid' order by C.showIndex asc";
		}*/
		
		return super.query(SYS_Module.class, sql);
	}

	public SYS_User queryUserByPhone(String phone) {
		SYS_User user = null;
		String sql = "select * from SYS_User where 1=1 and phone ='" + phone + "'";
		List<SYS_User> userList = this.query(SYS_User.class, sql);
		
		if (userList.size() > 0) {
			user = userList.get(0);
			return user;
		} else {
			return null;
		}
		
	}
	public List<SYS_ModuleButt> selectUserButton(SYS_User user) {
		String sql = " select c.* "
				+ " from SYS_ModuleButt c right join SYS_RoleButton d on d.ModuleID = c.ModuleID AND d.ButtonID = c.ButtonID "
				+ " right join SYS_Role a on d.RoleID = a.RoleID "
				+ " where a.RoleID='" + user.getRoleID() + "' AND c.MobuID IS NOT NULL";
		return super.query(SYS_ModuleButt.class, sql);
	}
	public List<SYS_ModuleButt> selectAllButton() {
		
		return null;
	}
}
