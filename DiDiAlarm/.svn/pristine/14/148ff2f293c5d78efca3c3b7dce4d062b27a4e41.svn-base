package com.derbysoft.jms.activemq;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import net.sf.json.JSONObject;

import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import com.derbysoft.redis.service.UserService;

import dy.hrtworkframe.dao.BaseDaoImpl;

@Component("jpushSender")
public class JpushSender {

	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Resource(name = "baseDaoImpl")
	private BaseDaoImpl baseDaoImpl;
	@Autowired
	public UserService userService;
	
	public static void main(String[] args) {
		Map<String , Object> m = new HashMap<String , Object>();
		People p =new People();
		p.setName("laowang");
		p.setPwd("123");
		m.put("n", "ios");
		m.put("data", p);
		JSONObject jsonObject = JSONObject.fromObject(m);
		System.out.println(jsonObject.toString());
	}
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public ActiveMQQueue getQueue() {
		return queue;
	}

	public void setQueue(ActiveMQQueue queue) {
		this.queue = queue;
	}

	private ActiveMQQueue queue;

	private static final Logger logger = LoggerFactory
			.getLogger(JpushSender.class);

//	public void send(final String message) {
//		this.jmsTemplate.send(queue, new MessageCreator() {
//			@Override
//			public Message createMessage(Session session) throws JMSException {
//				return session.createTextMessage(message);
//			}
//		});
//		logger.info("-------发送消息到消息服务器通知队列成功--" + message + "-------");
//	}
	
	
	/**
	 * 传数据与类型
	 * 
	 * @Title: send 
	 * @Description: TODO
	 * @param message
	 * @param type
	 * @return: void
	 */

	
//	public void send(Object message,String type,int code){
//		Map m = new HashMap();
//		m.put("type", type);
//		m.put("data", message);
//		m.put("Code", code);
//		String sm =  JSONObject.fromObject(m).toString();
//		send(sm);
//	}
	


	public void send(Object message,String type){
		Map<String , Object> m = new HashMap<String , Object>();
		m.put("type", type);
		m.put("data", message);
		m.put("pushTime", userService.getJpushTime());
		String sm =  JSONObject.fromObject(m).toString();
		send(sm);
	}
	public void send(final String m) {
		this.jmsTemplate.send(queue, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(m);
			}
		});
		logger.info("-------发送消息到消息服务器通知队列成功--" + m + "-------");
	}
}
