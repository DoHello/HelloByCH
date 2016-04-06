package com.derbysoft.controller.sys;

import java.util.HashMap;

import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.derbysoft.controller.service.BaseDaoController;
import com.derbysoft.entity.cms.GmsAlarmCall;
import com.derbysoft.entity.cms.Member;
import com.derbysoft.entity.sys.SYS_User;
import com.derbysoft.jms.activemq.JpushSender;
import com.derbysoft.redis.service.UserService;

import dy.hrtworkframe.util.MessageUtil;
@Transactional
@Controller("alarmByJpush")
@RequestMapping("alarmByJpush.do")
public class AlarmByJpush extends BaseDaoController{

	@Autowired
	private JpushSender jpushSender;

	@Autowired
	private UserService userService;
	
	@RequestMapping(params = "p=alarmByWeb")
	public @ResponseBody Map<String , Object> alarmByWeb(@RequestBody JSONObject Body ,HttpSession session){
		try{
			JSONObject fromObject = Body;
			String alias = (String) fromObject.get("alias");
			String tag = (String) fromObject.get("tag");
			Member user = new Member();
//			List<SYS_User> list = getPolice(alarmCall);
			user.setAlias(alias);
			user.setTag(tag);
			user.setTelephone("11223344556");
			GmsAlarmCall alarmCall = new GmsAlarmCall();
			alarmCall.setPhone("1122334456");
			alarmCall.setMessage("http://pic14.nipic.com/20110522/7411759_164157418126_2.jpg");
			alarmCall.setMessageText("报警文字描述");
			SYS_User s= new SYS_User();
			s.setAlias(alias);
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("who", "people");
			m.put("peopleCall", null);
			m.put("poliseFirst", s);
			m.put("userAlias", user);
			jpushSender.send(m, "POLICE");
			return MessageUtil.success("MSG1",200);
		}catch(Exception e){
			e.printStackTrace();
		}
		return MessageUtil.error("MSG6",500);
	}
}
