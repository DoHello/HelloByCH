package com.derbysoft.util;

import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.StringUtils;

//import common.Logger;

public class EmailUtil {


		//private static final Logger logger = Logf.Logger(EmailUtil.class);

		// 定义发件人、收件人、主题等

		private String to = null;// 收件人邮箱地址

		private String from = null;// 发件人邮箱地址

		private String password = null;//发件人密码

		private String host = null;//邮件服务器

		private String filename = null;

		// 用于保存发送附件的文件路径名的集合

		private Vector<String> fileList = new Vector<String>();

		/*

		* 可以传发件人等参数的构造

		*/

		public EmailUtil(String to, String from, String password,

		String smtpServer) {

		// 初始化发件人、收件人、主题等

		this.to = to;

		this.from = from;

		this.password = password;

		this.host = smtpServer;

		}

		/*

		* 该方法用于收集附件名

		*/

		public void attachfile(String fname) {

		fileList.addElement(fname);

		}

		/*

		* 开始发送信件的方法

		*/

		public boolean startSend(String emailTitle, String emailContent) {

		if (StringUtils.isBlank(emailContent)) {

		//logger.error("邮件内容不能为空！");

		return false;

		}

		try {

		if (StringUtils.isBlank(emailContent)) {

		throw new NullPointerException("发送的内容不能为空！");

		}

		// 创建Properties对象

		Properties props = System.getProperties();

		// 创建信件服务器

		props.put("mail.smtp.auth", "true");

		props.put("mail.smtp.host", host);

		props.put("mail.smtp.password", "0");

		// 得到默认的对话对象

		Session session = Session.getInstance(props,

		new PopupAuthenticator(this.from, this.password));

		// 创建一个消息，并初始化该消息的各项元素

		MimeMessage msg = new MimeMessage(session);
		
		

		msg.setFrom(new InternetAddress(from));

		InternetAddress[] address = { new InternetAddress(to) };

		msg.setRecipients(Message.RecipientType.TO, address);

		// getBytes("ISO-8859-1")

		// String title=new String(emailTitle.getBytes("ISO-8859-1"),"GBK");

		msg.setSubject(emailTitle);

		// 后面的BodyPart将加入到此处创建的Multipart中

		Multipart mp = new MimeMultipart("subtype");

		// 添加HTML正文

		BodyPart htmlBody = new MimeBodyPart();

		MimeMultipart htmlContent = new MimeMultipart("related");

		BodyPart msgContent = new MimeBodyPart();

		htmlContent.addBodyPart(msgContent);

		msgContent.setContent(emailContent, "text/html;charset=utf-8");

		htmlBody.setContent(htmlContent);

		mp.addBodyPart(htmlBody);

		// 利用枚举器方便的遍历集合

		Enumeration efile = fileList.elements();

		// 检查序列中是否还有更多的对象

		while (efile.hasMoreElements()) {

		MimeBodyPart mbp = new MimeBodyPart();

		// 选择出每一个附件名

		filename = efile.nextElement().toString();

		// 得到数据源

		FileDataSource fds = new FileDataSource(filename);

		// 得到附件本身并至入BodyPart

		mbp.setDataHandler(new DataHandler(fds));

		// 得到文件名同样至入BodyPart

		mbp.setFileName(fds.getName());

		mp.addBodyPart(mbp);

		}

		// 移走集合中的所有元素

		fileList.removeAllElements();

		// Multipart加入到信件

		msg.setContent(mp);

		// 设置信件头的发送日期

		msg.setSentDate(new Date());

		// 发送信件

		Transport.send(msg);

		} catch (Exception e) {

		e.printStackTrace();

		return false;

		}

		return true;

		}

		// 认证类

		class PopupAuthenticator extends Authenticator {

		private String username ;

		private String password ;

		public PopupAuthenticator(String user, String pass) {

		this.username = user;

		this.password = pass;

		}

		protected PasswordAuthentication getPasswordAuthentication() {

		return new PasswordAuthentication(username, password);

		}

		}

}
