package com.derbysoft.jms.queue;


import javax.jms.Destination;

import org.springframework.jms.core.JmsTemplate;

import com.derbysoft.entity.jms.PhoneNoticeInfo;
import com.derbysoft.jms.converter.NotifyMessageConverter;

/**
 * 消息生产者服务类
 */
public class QueueMessageProducer {
 private JmsTemplate jmsTemplate;
 private Destination notifyQueue;
 private NotifyMessageConverter messageConverter;
 public void sendQueue(PhoneNoticeInfo noticeInfo){
  sendMessage(noticeInfo);
 }
 private void sendMessage(PhoneNoticeInfo noticeInfo) {
  // TODO Auto-generated method stub
  jmsTemplate.setMessageConverter(messageConverter);
  jmsTemplate.setPubSubDomain(false);
  jmsTemplate.convertAndSend(notifyQueue,noticeInfo);
 }
 public JmsTemplate getJmsTemplate() {
  return jmsTemplate;
 }
 public void setJmsTemplate(JmsTemplate jmsTemplate) {
  this.jmsTemplate = jmsTemplate;
 }
 public Destination getNotifyQueue() {
  return notifyQueue;
 }
 public void setNotifyQueue(Destination notifyQueue) {
  this.notifyQueue = notifyQueue;
 }
 public NotifyMessageConverter getMessageConverter() {
  return messageConverter;
 }
 public void setMessageConverter(NotifyMessageConverter messageConverter) {
  this.messageConverter = messageConverter;
 }
}