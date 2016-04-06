



package com.derbysoft.controller.cms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.derbysoft.controller.service.BaseDaoController;
import com.derbysoft.dao.cms.NewsDao;
import com.derbysoft.dao.sys.DicDao;
import com.derbysoft.entity.cms.Article;
import com.derbysoft.entity.cms.CaseTime;
import com.derbysoft.entity.cms.GmsAlarmCall;
import com.derbysoft.entity.cms.GmsAlarmCheack;
import com.derbysoft.entity.cms.GmsAlarmReceive;
import com.derbysoft.entity.cms.JPush;
import com.derbysoft.entity.cms.Member;
import com.derbysoft.entity.cms.Message;
import com.derbysoft.entity.cms.TimeAndContext;
import com.derbysoft.entity.sys.SYS_Dic;
import com.derbysoft.entity.sys.SYS_RoleButton;
import com.derbysoft.entity.sys.SYS_User;
import com.derbysoft.jms.activemq.JpushSender;
import com.derbysoft.redis.service.RedisService;

import dy.hrtworkframe.db.SQLUtil;
import dy.hrtworkframe.entity.Pager;
import dy.hrtworkframe.entity.User;
import dy.hrtworkframe.exception.CustomerException;
import dy.hrtworkframe.util.Const;
import dy.hrtworkframe.util.DateUtil;
import dy.hrtworkframe.util.ExportUtils;
import dy.hrtworkframe.util.MessageUtil;
import dy.hrtworkframe.util.SortUtil;
import dy.hrtworkframe.util.UUIDUtil;

@Transactional
@Controller("case")
@RequestMapping("case.do")
public class CaseCollection extends BaseDaoController {

	private static final String TOKEN_USER_ADDRESS = "TOKEN_USER_ADDRESS:";
	@Autowired
	public RedisService redisService;
	
	@Resource(name = "newsDao")
	private NewsDao newsDao;

	@Resource(name = "systemDicDao")
	private DicDao systemDicDao;

	@Autowired
	private JpushSender jpushSender;

	// @Resource(name="messageDao")
	// private MessageDao messageDao;
	// 这是保存反馈
	@RequestMapping(params = "p=saveMessage")
	public @ResponseBody Map<String, Object> saveMessage(HttpSession session,
			@ModelAttribute Message message) {
		try {
			String context = message.getContext();
			SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
			pd = getPageData();
			message.setPhone(user.getPhone());
			message.setCreateTime(DateUtil.getDateString());
			message.setUserID(user.getUserID());
			if (context.length() < 11) {
				message.setTitle(context);
			} else {
				message.setTitle(getTitle(context, 10));
			}
			message.setUserName(user.getUserName());
			message.setMessageID(UUIDUtil.get32UUID());

			newsDao.insert(message);

			/* pd.put("newsList", newsList); */
			return MessageUtil.success();
		} catch (Exception e) {
			return MessageUtil.error();
		}
	}

	public String getTitle(String context, int i) {
		String str = context.substring(0, i);
		return str;
	}

	@RequestMapping(params = "p=casejPush")
	public @ResponseBody Map<String, Object> casejPush(HttpSession session,
			@RequestParam("userID") String userID,
			@RequestParam("alarmID") String alarmID) {
		SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
		try {
			List<SYS_User> query = baseDaoImpl.query(SYS_User.class,
					"select * from sys_user where userID = '" + userID + "' ;");
			List<GmsAlarmCall> query2 = baseDaoImpl.query(GmsAlarmCall.class,
					"select * from gms_alarm_call where alarmID = '"
							+ unicode(alarmID) + "' ;");
			if(!"nodealing".equals(query2.get(0).getStatus())
					&&!"noPolice".equals(query2.get(0).getStatus())
					){
				throw new CustomerException("案件状态已改变,无需推送");
			}
			Map<String, Object> m = new HashMap<String, Object>();
			String key = TOKEN_USER_ADDRESS
					+ DigestUtils.md5Hex(query.get(0).getUserName())
							.toUpperCase();
			String jsonData = this.redisService.get(key);
			if(jsonData!=null){
				if("yes".equals(query.get(0).getWorkStauts())){
					throw new CustomerException("该警员目前已经接警,请选择其他警员");
				}
				m.put("who", "people");
				m.put("peopleCall", query2.get(0));
				m.put("poliseFirst", query.get(0));
				m.put("userAlias", user);
				m.put("styleNum", "5");
				String sql="INSERT INTO Police_Alarm  values ('"+userID+"','"+unicode(alarmID)+"')";
				newsDao.jdbcTemplate.update(sql);
				jpushSender.send(m, POLICESIGN);
				userService.setAlarmWithPeople(query2.get(0).getAlarmID(),query.get(0));
				return MessageUtil.success();
			}else{
				throw new CustomerException("警员不在服务器,请选择其他警察推送");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtil.exception(user,  e);
		}
	}

	@RequestMapping(params = "p=casejPushMore")
	public @ResponseBody Map<String, Object> casejPushMore(HttpSession session,
			String jpushstr, @RequestParam("alarmID") String alarmID) {
		SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
		try {
			JSONArray jsonArray = JSONArray.fromObject(unicode(jpushstr));
			List<GmsAlarmCall> query2 = baseDaoImpl.query(GmsAlarmCall.class,
					"select * from gms_alarm_call where alarmID = '"
							+ unicode(alarmID) + "' ;");
			if(!"nodealing".equals(query2.get(0).getStatus())
					&&!"noPolice".equals(query2.get(0).getStatus())
					){
				throw new CustomerException("案件状态已改变,无需推送");
			}
			String username = "" ;
			for (Object name : jsonArray) {
				JSONObject fromObject = JSONObject.fromObject(name);
				SYS_User suser = (SYS_User) fromObject.toBean(fromObject, SYS_User.class);
				List<SYS_User> query = baseDaoImpl.query(SYS_User.class,
						"select * from sys_user where userID = '" + suser.getUserID() + "' ;");
				String key = TOKEN_USER_ADDRESS
						+ DigestUtils.md5Hex(query.get(0).getUserName())
								.toUpperCase();
				String jsonData = this.redisService.get(key);
				if(jsonData!=null){
					if(!"yes".equals(query.get(0).getWorkStauts())){
						Map<String, Object> m = new HashMap<String, Object>();
						m.put("who", "people");
						m.put("peopleCall", query2.get(0));
						m.put("poliseFirst", name);
						m.put("userAlias", user);
						m.put("styleNum", "5");
						jpushSender.send(m, POLICESIGN);
					}else{
						username+=query.get(0).getRealName()+" ";
					}
				}else{
					username+=query.get(0).getRealName()+" ";
				}
			}
			if(!"".equals(username)){
				throw new CustomerException(username+" 这些警员有事在身,消息将会推送给其他警员");
			}
			return MessageUtil.success();
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtil.exception(user,  e);
		}
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(params = "p=findPolice")
	public @ResponseBody Pager findPolice1(Pager pager, HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			SYS_User entity) throws Exception {
		
		
		entity.setAlarmID(new String(entity.getAlarmID().getBytes("iso-8859-1"), "utf-8"));

	/*	try {
			String w = "UserName like '%"
					+ pager.getParameters().get("searchText") + "%'"
					+ SQLUtil.getWhereClause(pager)
					+ " and  isWorking= 'okworking'";

			if (pager.getIsExport()) {
				if (pager.getExportAllData()) {
					pager.setExportDatas(newsDao.query(SQLUtil
							.getQuerySQL(SYS_User.class)));
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
				String sql = SQLUtil.getQuerySQL(SYS_User.class) + " where "
						+ w;

			} else {

				pager.setAdvanceQuerySorts(SortUtil.sortBuild1("InputDate",
						"desc"));
				newsDao.queryCount(pager, SYS_User.class, w);
				newsDao.queryPager(pager, SYS_User.class, w);
				pager.setIsSuccess(true);
			}

			if (pager.getIsExport()) {
				if (pager.getExportAllData()) {
					pager.setExportDatas(newsDao.query(SQLUtil
							.getQuerySQL(SYS_User.class)));
				}
				try {
					ExportUtils.export(request, response, pager);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
			if (pager.getPageSize() == 0) {

				String sql = SQLUtil.getQuerySQL(SYS_User.class) + " where "
						+ w;
				pager.setExhibitDatas(newsDao.query(SYS_User.class, sql));

			} else {

				pager.setAdvanceQuerySorts(SortUtil.sortBuild1("InputDate",
						"desc"));
				newsDao.queryCount(pager, SYS_User.class, w);
				newsDao.queryPager(pager, SYS_User.class, w);
				pager.setIsSuccess(true);
			}

		} catch (Exception e) {
			e.printStackTrace();
			pager.setIsSuccess(false);
		}*/

		/*
		 * SYS_User sys_User2 = new SYS_User();
		 * sys_User2.setIsWorking("okWorking"); List<SYS_User> query =
		 * newsDao.query(sys_User2);
		 * //request.getSession().setAttribute("sys_user", query);
		 * pager.setSys_user(query);
		 */
		GmsAlarmCall gmsAlarmCall = new GmsAlarmCall();
		gmsAlarmCall.setAlarmID(entity.getAlarmID());
		List<GmsAlarmCall> query = newsDao.query(gmsAlarmCall);
		//TODO
		List<SYS_User> police =getPolice1(query.get(0));
		//sql="select * from ";
		List<SYS_User> list = new  ArrayList<SYS_User>();
        
	 Map<String, SYS_User> hashMap = new HashMap<String,SYS_User>();	
     for(int i=0;i<(police==null?0:police.size());i++ ){
    	 hashMap.put(police.get(i).getUserID(), police.get(i));
     }
       Object[] array = hashMap.keySet().toArray();
  for(int j=0;j<array.length;j++){
	  list.add(hashMap.get((String)array[j]));
  }   

    /*    for(int z=0;z<list.size();z++){
        	int i=1;
        	police.remove(list.get(z));
        }*/
       pager.setExhibitDatas(list);
       pager.setIsSuccess(true);
        return pager;
	}


	
	
	// 参看推送的设置
	@RequestMapping(params = "p=jPush")
	public ModelAndView jPush(HttpSession session) throws Exception {

		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd = getPageData();
		try {
			// mv.addObject("model", pd);
			List<JPush> query = newsDao.query(JPush.class);
			JPush jPush = query.get(0);
			mv.setViewName("/cms/case/case_view");
			pd.put("jPush", jPush);
			mv.addObject("model", pd);
		} catch (Exception e) {
			return MessageUtil.exception(user, mv, e);
		}
		return MessageUtil.success(mv);

	}

	@RequestMapping(params = "p=policeView")
	public ModelAndView policeView(@RequestParam("moduleID") String moduleID,
			HttpSession session) throws Exception {

		SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
		pd = getPageData();
		try {
			// mv.addObject("model", pd);
			// 获取用户权限按钮
			String sql = "select * " + " from SYS_RoleButton "
					+ " where ModuleID= '" + moduleID + "' and roleID = '"
					+ user.getRoleID() + "' ;";
			pd.put("userbutton", newsDao.query(SYS_RoleButton.class, sql));
			List<JPush> query = newsDao.query(JPush.class);
			JPush jPush = query.get(0);
			mv.setViewName("/cms/case/case_policeview");
			pd.put("jPush", jPush);
			mv.addObject("model", pd);
		} catch (Exception e) {
			return MessageUtil.exception(user, mv, e);
		}
		return MessageUtil.success(mv);

	}

	@RequestMapping(params = "p=jPush1")
	public ModelAndView UpdateJPush(HttpSession session, JPush entity)
			throws Exception {

		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd = getPageData();
		try {
			// mv.addObject("model", pd);
			List<JPush> query = newsDao.query(JPush.class);
			JPush jPush = query.get(0);
/*			String num = entity.getPolicejPushPeople();
			String num1 = entity.getPolicejPushTime();*/

/*			String rex = "/^[1-9]+\\d*$";
*//*			Pattern p = Pattern.compile(rex);
*//*			Matcher m = p.matcher(num);
			Matcher m1 = p.matcher(num1);*/

			pd.put("jPush", jPush);
			mv.addObject("model", pd);
		} catch (Exception e) {
			return MessageUtil.exception(user, mv, e);
		}

		return MessageUtil.success(mv);

	}

	@RequestMapping(params = "p=add")
	public @ResponseBody Map<String, Object> add(
			@ModelAttribute Message entity, HttpSession session) {
		SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
		try {

			entity.setCreateTime(DateUtil.getDateTimeString());

			newsDao.insert(entity);
		} catch (Exception e) {
			return MessageUtil.exception(user, e);
		}

		return MessageUtil.success();
	}

	@RequestMapping(params = "p=eyeCase")
	public @ResponseBody Map<String, Object> eyeCase(
			@ModelAttribute GmsAlarmCall entity, HttpSession session) {
		SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
		try {
			List<GmsAlarmCall> query = newsDao.query(entity);
			GmsAlarmCall call = query.get(0);
			mv.setViewName("/cms/case/case_eye");
			pd.put("call", call);
			mv.addObject("model", pd);

			// entity.setCreateTime(DateUtil.getDateTimeString());
			// newsDao.insert(entity);
		} catch (Exception e) {
			return MessageUtil.exception(user, e);
		}

		return MessageUtil.success();
	}
	/**
	 * 
	 * 
	     * @discription 页面跳转
	     * @author Knight      
	     * @created 2016年3月4日 上午11:16:45     
	     * @param entity
	     * @param session
	     * @return
	 */
	@RequestMapping(params = "p=eyeNoCheack")
	public  ModelAndView eyeNoCheack(
			@ModelAttribute GmsAlarmCall entity, HttpSession session) {
		SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
		try {
		String alarmID = new String(entity.getAlarmID().getBytes("iso-8859-1"),"utf-8");
			
			GmsAlarmCall gmsAlarmCall = new GmsAlarmCall();
			gmsAlarmCall.setAlarmID(alarmID);
			List<GmsAlarmCall> query = newsDao.query(gmsAlarmCall);
			pd.put("call", query.get(0));
			if(query.get(0).getStatus().equals("dealing")||query.get(0).getStatus().equals("okCheack") ){
				

				String sql=" select * from gms_alarm_receive where alarmid='"+gmsAlarmCall.getAlarmID()+"' and AlarmRefuseID is  null ";
			List<GmsAlarmReceive> query2 = newsDao.query(GmsAlarmReceive.class, sql);
			
			try {
				pd.put("receive", query2.get(0));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				GmsAlarmCall gmsAlarmCall2 = query.get(0);
				gmsAlarmCall2.setStatus("stop");
				gmsAlarmCall2.setStopCause("其他原因");
				newsDao.update(gmsAlarmCall2);
				throw new CustomerException("该报警目前已经中断！");
			}
			
			}
			if(query.get(0).getStatus().equals("nodealing")){
				
		        String sql=" select * from gms_alarm_receive where alarmid='"+gmsAlarmCall.getAlarmID()+"' and PresentTime is not null ";
				List<GmsAlarmReceive> query2 = newsDao.query(GmsAlarmReceive.class, sql);
				if(query2.size()>0){
					pd.put("receive", query2.get(0));
				}
				
				
				}
			String sql2="SELECT * from  gms_alarm_receive where AlarmID='"+alarmID+"'  and  AlarmRefuseID   is not NULL ORDER BY InputDate ASC";
			List<GmsAlarmReceive> query3 = newsDao.query(GmsAlarmReceive.class, sql2);
			
			
			if(query3.size()>0){
			for( int i=0;i<query3.size() ;i++){
				GmsAlarmReceive gmsAlarmReceive = query3.get(i);
				String[] split = gmsAlarmReceive.getRefuseContext().split(",");
				   ArrayList<String> arrayList = new ArrayList<String>();
				      for(int j=0;j<split.length;j++){
					   arrayList.add(split[j]);
				   }
				      query3.get(i).setImg(arrayList);
			}	
			}
			pd.put("alarmReceive", query3);
			mv.setViewName("/cms/case/case_eyeNoCheack");
			
			mv.addObject("model", pd);

			// entity.setCreateTime(DateUtil.getDateTimeString());
			// newsDao.insert(entity);
		} catch (Exception e) {
			return MessageUtil.exception(user, mv, e);
		}

		    return MessageUtil.success(mv);
	}

	@RequestMapping(params = "p=eyeView")
	public ModelAndView showEyeView(Message entity, HttpSession session,
			@RequestParam("alarmID") String alarmID) {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd = getPageData();
		// pd.put("NewsState", systemDicDao.findByCategory("infoType"));
		// pd.put("NewsFocus", systemDicDao.findByCategory("NewsFocus"));
		pd.put("entity", alarmID);
		try {
			mv.addObject("model", pd);
			mv.setViewName("/cms/case/case_jPush");

		} catch (Exception e) {
			return MessageUtil.exception(user, mv, e);
		}

		return MessageUtil.success(mv);
	}

	@RequestMapping(params = "p=showAddView")
	public ModelAndView showAddView(@ModelAttribute Message entity,
			HttpSession session) {

		User user = (User) session.getAttribute(Const.SESSION_USER);
		try {
			String sql = SQLUtil.getQuerySQL(Message.class);
			List<Message> range = newsDao.query(Message.class, sql);
			mv.addObject("model", getPageData().put("range", range));
			mv.setViewName("/cms/message/message_add");
		} catch (Exception e) {
			return MessageUtil.exception(user, mv, e);
		}
		return MessageUtil.success(mv);
	}

	@RequestMapping(params = "p=upload")
	public @ResponseBody Map<String, Object> edit(@ModelAttribute JPush entity,
			HttpSession session) throws Exception {
		SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
		try {
			/*
			 * String rex = "^[1-9]\d*$"; Pattern p = Pattern.compile(rex);
			 * Matcher m = p.matcher(num); if (m.find()){ sysout("match!"); }
			 */
			List<JPush> query = newsDao.query(JPush.class);
			JPush jPush = query.get(0);
			if (!entity.getPolicejPushPeople().matches("^[1-9]\\d*$")) {
				throw new CustomerException("请输入正整数！");
			}
			if (!entity.getPolicejPushTime().matches("^[1-9]\\d*$")) {
				throw new CustomerException("请输入正整数！");
			}
			String userID = user.getUserID();
			entity.setUserID(userID);
			entity.setInputDate(DateUtil.getDateTimeString());
			entity.setPolicejPushID(jPush.getPolicejPushID());
			newsDao.update(entity);
			userService
					.setJpushTime(Long.parseLong(entity.getPolicejPushTime())
							* 60 + "");
			userService.setJpushNum(entity.getPolicejPushPeople());
			return MessageUtil.success();
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtil.exception(user, e);
		}

	}

	/*
	 * @RequestMapping(params = "p=del") public @ResponseBody Map<String,Object>
	 * del(@ModelAttribute Message entity, HttpSession session) { User user =
	 * (User) session.getAttribute(Const.SESSION_USER); try {
	 * newsDao.delete(entity); } catch (Exception e) { return
	 * MessageUtil.exception(user, e); }
	 * 
	 * return MessageUtil.success(); }
	 */

	@SuppressWarnings("deprecation")
	@RequestMapping(params = "p=find")
	public @ResponseBody Pager find(Pager pager, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
       try {
		String sql = " select * from gms_alarm_call where   Status!= 'ok'  and "
				+ "( name like '%" + pager.getParameters().get("searchText") + "%'"
				+ " or phone like '%" + pager.getParameters().get("searchText") + "%'"
				+ " or alarmid like '%" + pager.getParameters().get("searchText") + "%')"
//				+ " and InputDate>=date(now()) and InputDate<DATE_ADD(date(now()),INTERVAL 1 DAY)"
				+ "  GROUP BY AlarmID ";//ORDER BY inputdate desc 
	
		if(pager.getParameters().get("sortFiled")==null||pager.getParameters().get("sortInt").toString().equals("2")){
			sql+=" ORDER BY inputdate desc ";
		}else{
		if("1".equals(pager.getParameters().get("sortInt").toString())){
			sql+=" ORDER BY "+pager.getParameters().get("sortFiled") + " desc ";
		}else{
			sql+=" ORDER BY "+pager.getParameters().get("sortFiled") + " asc ";
			}
		}
		//int size = query.size();
		List<GmsAlarmCall> query1 = newsDao
				.query(GmsAlarmCall.class, sql);
		pager.setRecordCount(query1.size());
		List<GmsAlarmCall> query = newsDao.query(GmsAlarmCall.class, sql
				+ " limit " + SQLUtil.mySqlPagerStartWith(pager) + ","
				+ SQLUtil.pagerEndedWith(pager) + ";");
		pager.setExhibitDatas(query);
		pager.setIsSuccess(true);
		return pager;
	} catch (Exception e) {
		e.printStackTrace();
		pager.setIsSuccess(false);
	}
	return pager;

	
	}

	@SuppressWarnings({ "deprecation", "all" })
	@RequestMapping(params = "p=policeFind")
	public @ResponseBody Pager policeFind(Pager pager, HttpSession session,
			HttpServletRequest request, HttpServletResponse response ,@RequestParam("alarmID") String alarmID) {
		try {

			String w = "UserName like '%"
					+ pager.getParameters().get("searchText") + "%'"
					+ SQLUtil.getWhereClause(pager);

			if (pager.getIsExport()) {
				if (pager.getExportAllData()) {
					pager.setExportDatas(newsDao.query(SQLUtil
							.getQuerySQL(Message.class)));
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

				pager.setAdvanceQuerySorts(SortUtil.sortBuild1("CreateTime",
						"desc"));
				newsDao.queryCount(pager, Message.class, w);
				newsDao.queryPager(pager, Message.class, w);
				pager.setIsSuccess(true);
			}

			if (pager.getIsExport()) {
				if (pager.getExportAllData()) {
					pager.setExportDatas(newsDao.query(SQLUtil
							.getQuerySQL(Article.class)));
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

				pager.setAdvanceQuerySorts(SortUtil.sortBuild1("CreateTime",
						"desc"));
				newsDao.queryCount(pager, Message.class, w);
				newsDao.queryPager(pager, Message.class, w);
				pager.setIsSuccess(true);
			}

		} catch (Exception e) {
			e.printStackTrace();
			pager.setIsSuccess(false);
		}

		pager.setIsSuccess(true);
		return pager;
	}

	@RequestMapping(params = "p=jPushPolice")
	public ModelAndView jPushPolice(HttpSession session) throws Exception {

		SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
		pd = getPageData();
		try {
			// mv.addObject("model", pd);
			List<JPush> query = newsDao.query(JPush.class);
			JPush jPush = query.get(0);
			mv.setViewName("/cms/case/case_view");
			pd.put("jPush", jPush);
			mv.addObject("model", pd);
		} catch (Exception e) {
			return MessageUtil.exception(user, mv, e);
		}
		return MessageUtil.success(mv);

	}

	// 页面跳转
	@RequestMapping(params = "p=cheackView")
	public ModelAndView cheackView(HttpSession session,
			@RequestParam("moduleID") String moduleID) throws Exception {

		SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
		pd = getPageData();
		try {
			String sql = "select * " + " from SYS_RoleButton "
					+ " where ModuleID= '" + moduleID + "' ";
			pd.put("userbutton", newsDao.query(SYS_RoleButton.class, sql));
			mv.addObject("model", pd);
			// mv.addObject("model", pd);

			// List<JPush> query = newsDao.query(JPush.class);
			// JPush jPush = query.get(0);
			mv.setViewName("/cms/case/case_cheackView");
			// pd.put("jPush", jPush);

			// mv.addObject("model", pd);
		} catch (Exception e) {
			return MessageUtil.exception(user, mv, e);
		}
		return MessageUtil.success(mv);

	}
	//eyeCase

	@RequestMapping(params = "p=findCheack")
	public @ResponseBody Pager findCheack(Pager pager, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		String sql = "select t.*,g.stauts  from gms_alarm_cheack g  , (select a.* , b.InputDate as calltime , b.username as callPhone , b.addressname as callAddress , b.MessageText as callContext   from (select * from gms_alarm_receive  where Ischeack='okcheack' and  AlarmID like '%"
				+ pager.getParameters().get("searchText")
				+ "%'  ) a , gms_alarm_call b where  a.alarmid=b.alarmid order by calltime asc) as t where t.alarmid=g.alarmid  "; //limit "+pager.getPageCount()+","+pager.getPageSize();
//		if(pager.getParameters().get("sortFiled")==null){
//			sql += " order by presenttime desc";
//
//		}else{
//			sql += " order by "+pager.getParameters().get("sortFiled")+" desc";
//			
//		}
		if(pager.getParameters().get("sortFiled")==null||pager.getParameters().get("sortInt").toString().equals("2")){
			sql += " order by presenttime desc";
		}else{
		if("1".equals(pager.getParameters().get("sortInt").toString())){
			sql += " order by "+pager.getParameters().get("sortFiled")+" asc";
		}else{
			sql += " order by "+pager.getParameters().get("sortFiled")+" desc";
		}
		}
       //int size = query.size();
		List<GmsAlarmReceive> query1 = newsDao.query(GmsAlarmReceive.class, sql);
		pager.setRecordCount(query1.size());
       List<GmsAlarmReceive> query = newsDao.query(GmsAlarmReceive.class, sql+" limit "+SQLUtil.mySqlPagerStartWith(pager)+","+
				SQLUtil.pagerEndedWith(pager)+";");
		pager.setExhibitDatas(query);

		pager.setIsSuccess(true);
		return pager;

	}

	// p=cheackEye

	@RequestMapping(params = "p=cheackEye")
	public ModelAndView eyeCheack(@ModelAttribute GmsAlarmReceive entity,
			HttpSession session) {
		SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
		try {
			// entity.setAlarmID(new
			// String(entity.getAlarmReceiveID().getBytes("iso-8859-1"),"utf-8"));

			// String
			// w="select a.* , b.InputTime as cheackTime , b.context as cheackContext from  (select * from  gms_alarm_receive where AlarmID='"+entity.getAlarmID()+"' and `Ischeack`='okCheack') a  , (select * from gms_alarm_cheack where  AlarmID='"+entity.getAlarmID()+"')  b  where  a.alarmid=b.alarmid ";
			// List<GmsAlarmReceive> query2 =
			// newsDao.query(GmsAlarmReceive.class, w);

			// GmsAlarmReceive gmsAlarmReceive = query2.get(0);
			/*
			 * String receiveDate = gmsAlarmReceive.getInputDate();//
			 * GmsAlarmCall gmsAlarmCall = new GmsAlarmCall();
			 * gmsAlarmCall.setAlarmID(entity.getAlarmID()); List<GmsAlarmCall>
			 * query = newsDao.query(gmsAlarmCall); GmsAlarmCall gmsAlarmCall2 =
			 * query. get(0); String callDate = gmsAlarmCall2.getInputDate();
			 * GmsAlarmCheack gmsAlarmCheack = new GmsAlarmCheack();
			 * gmsAlarmCheack.setAlarmID(entity.getInputDate());
			 * List<GmsAlarmCheack> query3 = newsDao.query(gmsAlarmCheack);
			 * GmsAlarmCheack gmsAlarmCheack2 = query3.get(0); String cheackTime
			 * = gmsAlarmCheack2.getInputTime(); List<TimeAndContext> list =new
			 * ArrayList<TimeAndContext>(); //上面是求时间的 String
			 * w2="select * from  caseStatus where alarmid = " +
			 * entity.getAlarmID() + " order by inputdate asc"; List<CaseStatus>
			 * query4 = newsDao.query(CaseStatus.class, w2); list =
			 * this.createList(list, callDate,"报警时间"); list =
			 * this.createList(list,receiveDate ,"接警时间"); list =
			 * this.createList(list, cheackTime, "备案"); for(int i=0;
			 * query4.size()>i ; i++){ CaseStatus caseStatus = query4.get(i);
			 * list = this.createList(list,
			 * query4.get(i).getInputDate(),query4.get(i).getStatus()); }
			 */
			//

			//

			/*
			 * pd.put("call",gmsAlarmCall2); pd.put("time",list);
			 */
			List<GmsAlarmReceive> query = newsDao.query(entity);
			GmsAlarmReceive gmsAlarmReceive = query.get(0);
			
			GmsAlarmCall gmsAlarmCall = new GmsAlarmCall();
	   
	     
	     pd.put("receive", gmsAlarmReceive);
			gmsAlarmCall.setAlarmID(gmsAlarmReceive.getAlarmID());
			List<GmsAlarmCall> query2 = newsDao.query(gmsAlarmCall);
			gmsAlarmCall = query2.get(0);
			  if("imgAlarm".equals(gmsAlarmCall.getMessageType())){
			      String message = gmsAlarmCall.getMessage();
			      String[] split = message.split(",");
			   ArrayList<String> arrayList = new ArrayList<String>();
			      for(int i=0;i<split.length;i++){
				   arrayList.add(split[i]);
			   }
			      gmsAlarmReceive.setImg(arrayList);
		     }
			pd.put("call", gmsAlarmCall);
			GmsAlarmCheack gmsAlarmCheack = new GmsAlarmCheack();
			gmsAlarmCheack.setAlarmReceiveID(gmsAlarmReceive
					.getAlarmReceiveID());
			List<GmsAlarmCheack> query3 = newsDao.query(gmsAlarmCheack);
			gmsAlarmCheack = query3.get(0);
			pd.put("cheack", gmsAlarmCheack);

			List<CaseTime> caseTimeList = new ArrayList<CaseTime>();
			CaseTime caseTime = new CaseTime();
			caseTime.setInputTime(gmsAlarmCall.getInputDate());
			caseTime.setCallStatues("报警");
			CaseTime caseTime2 = new CaseTime();
			caseTime2.setInputTime(gmsAlarmReceive.getInputDate());
			caseTime2.setCallStatues("接警");
			CaseTime caseTime3 = new CaseTime();
			caseTime3.setCallStatues("备案");
			caseTime3.setInputTime(gmsAlarmCheack.getInputTime());
			String w = "select * from caseTime where AlarmID= '"
					+ gmsAlarmReceive.getAlarmID() + "' order by inputTime desc";
			
			List<CaseTime> query4 = newsDao.query(CaseTime.class, w);
     
			//caseTimeList.add(caseTime);
			caseTimeList.add(caseTime2);
			caseTimeList.add(caseTime3);
			if (query4.size() > 0) {
				for (int i = 0; i < query4.size(); i++) {
					caseTimeList.add(query4.get(i));
				}
			}

			//List<SYS_Dic> findByCategory = systemDicDao.findByCategory("CaseType");
			
			pd.put("timeStutes", caseTimeList);
			pd.put("CaseStutes", systemDicDao.findByCategory("CaseStutes"));
			pd.put("caseType", systemDicDao.findByCategory("CaseType"));
            pd.put("first",caseTime);
			mv.setViewName("/cms/case/case_eyeCheack");
			// entity.setCreateTime(DateUtil.getDateTimeString());
			// newsDao.insert(entity);

		} catch (Exception e) {
			return MessageUtil.exception(user, mv, e);
		}

		return MessageUtil.success(mv);
	}

	@RequestMapping(params = "p=updatecCheack")
	public @ResponseBody Map<String, Object> updatecCheack(
			@ModelAttribute GmsAlarmCheack entity, HttpSession session ,@ModelAttribute GmsAlarmReceive receive ) {
		SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
		try {
			// entity.setInputTime(DateUtil.getDateTimeString());
			// newsDao.update(entity);
            String caseTypeValue = receive.getCaseTypeValue();
			/*List<SYS_Dic> findByCategory = systemDicDao.findByCategory("CaseType");
            for(int i=0;i<findByCategory.size(); i++){
            	
            	if(findByCategory.get(i).getDicValue().equals(caseTypeValue)){
            		findByCategory.get(i).getDicName();
            	}
            }*/
            SYS_Dic sys_Dic2 = new SYS_Dic();
			sys_Dic2.setDicValue(caseTypeValue);
            List<SYS_Dic> query3 = newsDao.query(sys_Dic2);
			String dicName = query3.get(0).getDicName();
            String receiveSql="update gms_alarm_receive SET  casetype='"+dicName+"' ,casetypevalue='"+caseTypeValue+"' where alarmrefuseid ='" + receive.getAlarmReceiveID() + "'";
			newsDao.jdbcTemplate.execute(receiveSql);

            String sql;
			GmsAlarmCheack gmsAlarmCheack1 = new GmsAlarmCheack();
			gmsAlarmCheack1.setAlarmReceiveID(entity.getAlarmReceiveID());
			List<GmsAlarmCheack> query = newsDao.query(gmsAlarmCheack1);
			GmsAlarmCheack gmsAlarmCheack = query.get(0);
			GmsAlarmReceive gmsAlarmReceive = new GmsAlarmReceive();
			gmsAlarmReceive.setAlarmReceiveID(gmsAlarmCheack
					.getAlarmReceiveID());
			List<GmsAlarmReceive> query2 = newsDao.query(gmsAlarmReceive);
			String alarmID = query2.get(0).getAlarmID();
			String w = "update gms_alarm_receive SET  casestatus='"
					+ entity.getStauts() + "' where alarmid ='" + alarmID + "'";
			newsDao.jdbcTemplate.execute(w);
			String sql2="update gms_alarm_cheack SET  stauts='"
					+ entity.getStauts() + "' where alarmid ='" + alarmID + "'";
			newsDao.jdbcTemplate.execute(sql2);
            newsDao.update(entity);
            
			if (gmsAlarmCheack.getStauts().equals(entity.getStauts())) {

			} else {
				String callStatues = null;
				List<SYS_Dic> findByCategory = systemDicDao.findByCategory("CaseStutes");
				for(int i=0;i<findByCategory.size();i++){
					SYS_Dic sys_Dic = findByCategory.get(i);
				    if(sys_Dic.getDicValue().equals(entity.getStauts())){
						callStatues = sys_Dic.getDicName();
						break;
				    }
				}
					sql = "INSERT INTO caseTime ( AlarmID , InputTime,CallStatues )VALUES ('"
							+ entity.getAlarmID()
							+ "', '"
							+ DateUtil.getDateTimeString()
							+ "','"
							+ callStatues + "')";
					newsDao.jdbcTemplate.execute(sql);
					
			}

		} catch (Exception e) {
			return MessageUtil.exception(user, e);
		}

		return MessageUtil.success();
	}

	public List<TimeAndContext> createList(List<TimeAndContext> entity,
			String time, String context) {
		TimeAndContext timeAndContext = new TimeAndContext();
		timeAndContext.setContext(context);
		timeAndContext.setTime(time);
		entity.add(timeAndContext);
		return entity;
	}

	@RequestMapping(params = "p=del")
	public @ResponseBody Map<String, Object> del(
			@ModelAttribute GmsAlarmCall entity, HttpSession session)
			throws Exception {
		SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
		try {
			// String alarmID = entity.getAlarmID();
			newsDao.delete(entity);
		} catch (Exception e) {
			return MessageUtil.exception(user, e);
		}
		return MessageUtil.success();
	}
	@RequestMapping(params = "p=stopCall")
	public @ResponseBody Map<String, Object> stopCall(
			@RequestParam("alarmID") String alarmID, 
			@RequestParam("causeStop") String causeStop, 
			@RequestParam("remarkStop") String remarkStop, 
			HttpSession session)
					throws Exception {
		SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
		try {
			if(user == null){
				throw new CustomerException("请登陆后操作");
			}
			String ualarmID = new String(alarmID.getBytes("iso-8859-1"),"utf-8");
			String ucauseStop = new String(causeStop.getBytes("iso-8859-1"),"utf-8");
			String uremarkStop = new String(remarkStop.getBytes("iso-8859-1"),"utf-8");
			GmsAlarmCall alarmCall = new GmsAlarmCall();
			alarmCall.setAlarmID(ualarmID);
			List<GmsAlarmCall> query = newsDao.query(alarmCall);
			
			if(query!=null&&query.size()>0&&"cancleAlarm".equals(query.get(0).getStatus())){
				
				throw new CustomerException("用户已经取消报警");
			}else{
				List<Member> query1 = baseDaoImpl.query(
						Member.class,
						"select * from cms_user where userName = '"
								+ query.get(0).getUserName() + "';");
				alarmCall.setStatus("stop");
				alarmCall.setStopCause(ucauseStop);
				alarmCall.setRemarkStop(uremarkStop);
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("isReceive", "no");
				m.put("peopleCall", alarmCall);
				m.put("userAlias", query1.get(0));
				m.put("msg", "您的案件不属于管辖范围之内");
				m.put("msgCode", "601");
				m.put("styleNum", "3");
				jpushSender.send(m, REFUSE);
				alarmCall.setMessage(null);
			}
			
			
			newsDao.update(alarmCall);
			userService.delRedisAlarmCallTime(ualarmID);
			userService.delRedisAlarmCallTime1(ualarmID);
			//删除报警
			userService.delRedisAlarmCall(alarmCall);
//          String alarmID = entity.getAlarmID();
//			entity.setStatus("stop");
//			newsDao.delete(entity);
		} catch (Exception e) {
			e.printStackTrace();
//			return MessageUtil.exception(user, e);
			return MessageUtil.exception(user, e);
		}
		return MessageUtil.success();
	}
	@RequestMapping(params = "p=editStopCall")
	public @ResponseBody Map<String, Object> editStopCall(
			@RequestParam("alarmID") String alarmID, 
			@RequestParam("causeStops") String causeStops, 
			@RequestParam("remarkStops") String remarkStops, 
			HttpSession session)
					throws Exception {
		SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
		try {
			if(user == null){
				throw new CustomerException("请登陆后操作");
			}
			String ualarmID = new String(alarmID.getBytes("iso-8859-1"),"utf-8");
			String ucauseStop = new String(causeStops.getBytes("iso-8859-1"),"utf-8");
			String uremarkStop = new String(remarkStops.getBytes("iso-8859-1"),"utf-8");
			GmsAlarmCall alarmCall = new GmsAlarmCall();
			alarmCall.setAlarmID(ualarmID);
			alarmCall.setStopCause(ucauseStop);
			alarmCall.setRemarkStop(uremarkStop);
			alarmCall.setStatus("stop");
			
			newsDao.update(alarmCall);
			userService.delRedisAlarmCallTime(ualarmID);
			userService.delRedisAlarmCallTime1(ualarmID);
			//删除报警
			userService.delRedisAlarmCall(alarmCall);
//          String alarmID = entity.getAlarmID();
//			entity.setStatus("stop");
//			newsDao.delete(entity);
		} catch (Exception e) {
			e.printStackTrace();
//			return MessageUtil.exception(user, e);
			throw new CustomerException("服务器异常,请与管理员联系");
		}
		return MessageUtil.success();
	}
	@RequestMapping(params = "p=findCallStop")
	public @ResponseBody Map<String, Object> findCallStop(
			@RequestParam("alarmID") String alarmID, 
			HttpSession session)
					throws Exception {
		SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
		Map result = new HashMap();
		try {
			if(user == null){
				throw new CustomerException("请登陆后操作");
			}
			String ualarmID = new String(alarmID.getBytes("iso-8859-1"),"utf-8");
			GmsAlarmCall alarmCall = new GmsAlarmCall();
			alarmCall.setAlarmID(ualarmID);
			List<GmsAlarmCall> query = newsDao.query(alarmCall);
			if(query!=null||query.size()>0){
				GmsAlarmCall gmsAlarmCall = query.get(0);
				Map m = new HashMap();
				m.put("causeStop", gmsAlarmCall.getStopCause());
				m.put("remarkStop", gmsAlarmCall.getRemarkStop());
				result.put("modelStop", m);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomerException("服务器异常,请与管理员联系");
		}
		return MessageUtil.success(result);
	}
	

	@RequestMapping(params = "p=downExcel")
	public @ResponseBody void downExcel( @ModelAttribute Pager pager, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

	}
}