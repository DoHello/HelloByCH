package com.derbysoft.util;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 通过短信接口发送短信
 */
@SuppressWarnings("all")
public class SmsUtil {

	private static boolean a;
	public static void main(String[] args) {
try {
	System.out.println(true&&a);
	
	System.out.println(URLEncoder.encode("我是", "UTF-8"));
} catch (UnsupportedEncodingException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
//		sendSms2("13511111111", "您的验证码是：1111。请不要把验证码泄露给其他人。", "", "");
		// sendSmsAll(List<PageData> list)

		// sendSms1();
	}

	// 短信商 一 http://www.dxton.com/
	// =====================================================================================
	/**
	 * 给一个人发送单条短信
	 * 
	 * @param mobile
	 *            手机号
	 * @param code
	 *            短信内容
	 */
	public static void sendSms1(String mobile, String code, String account,
			String password) {

		// String account = "", password = "";
		// String strSMS1 = Tools.readTxtFile(Const.SMS1); //读取短信1配置
		// if(null != strSMS1 && !"".equals(strSMS1)){
		// String strS1[] = strSMS1.split(",fh,");
		// if(strS1.length == 2){
		// account = strS1[0];
		// password = strS1[1];
		// }
		// }
		String PostData = "";
		try {
			PostData = "account=" + account + "&password=" + password
					+ "&mobile=" + mobile + "&content="
					+ URLEncoder.encode(code, "utf-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("短信提交失败");
		}
		// System.out.println(PostData);
		String ret = SMS(PostData, "http://sms.106jiekou.com/utf8/sms.aspx");
		System.out.println(ret);
		/*
		 * 100 发送成功 101 验证失败 102 手机号码格式不正确 103 会员级别不够 104 内容未审核 105 内容过多 106
		 * 账户余额不足 107 Ip受限 108 手机号码发送太频繁，请换号或隔天再发 109 帐号被锁定 110 发送通道不正确 111
		 * 当前时间段禁止短信发送 120 系统升级
		 */

	}

	public static String SMS(String postData, String postUrl) {
		try {
			// 发送POST请求
			URL url = new URL(postUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setUseCaches(false);
			conn.setDoOutput(true);

			conn.setRequestProperty("Content-Length", "" + postData.length());
			OutputStreamWriter out = new OutputStreamWriter(
					conn.getOutputStream(), "UTF-8");
			out.write(postData);
			out.flush();
			out.close();

			// 获取响应状态
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				System.out.println("connect failed!");
				return "";
			}
			// 获取响应内容体
			String line, result = "";
			BufferedReader in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "utf-8"));
			while ((line = in.readLine()) != null) {
				result += line + "\n";
			}
			in.close();
			return result;
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
		return "";
	}

	// ===================================================================================================================

	/**
	 * 
	 * 短信商 二 http://www.ihuyi.com/
	 * ==============================================
	 * =======================================
	 * 
	 */
	private static String Url = "http://106.ihuyi.com/webservice/sms.php?method=Submit";

	/**
	 * 给一个人发送单条短信
	 * 
	 * @param mobile
	 *            手机号
	 * @param code
	 *            短信内容
	 */
	public static void sendSms2(String mobile, String code, String account,
			String password) {
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(Url);

		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType",
				"application/x-www-form-urlencoded;charset=UTF-8");

		String content = new String(code);

		// String account = "", password = "";
		// String strSMS2 = Tools.readTxtFile(Const.SMS2); //读取短信2配置
		// if(null != strSMS2 && !"".equals(strSMS2)){
		// String strS2[] = strSMS2.split(",fh,");
		// if(strS2.length == 2){
		// account = strS2[0];
		// password = strS2[1];
		// }
		// }

		NameValuePair[] data = {// 提交短信
				new NameValuePair("account", account),
				new NameValuePair("password", password), // 密码可以使用明文密码或使用32位MD5加密
				new NameValuePair("mobile", mobile),
				new NameValuePair("content", content), };

		method.setRequestBody(data);

		try {
			client.executeMethod(method);

			String SubmitResult = method.getResponseBodyAsString();

			Document doc = DocumentHelper.parseText(SubmitResult);
			Element root = doc.getRootElement();

			code = root.elementText("code");
			String msg = root.elementText("msg");
			String smsid = root.elementText("smsid");

			System.out.println(code);
			System.out.println(msg);
			System.out.println(smsid);

			if (code == "2") {
				System.out.println("短信提交成功");
			}

		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 给多个人发送单条短信
	 * 
	 * @param list
	 *            手机号验证码
	 */
//	public static void sendSmsAll(List<PageData> list) {
//		String code;
//		String mobile;
//		for (int i = 0; i < list.size(); i++) {
//			code = list.get(i).get("code").toString();
//			mobile = list.get(i).get("mobile").toString();
//			sendSms2(mobile, code, "", "");
//		}
//	}

	// ================================http://sms.1xinxi.cn/=================================================================

	public static boolean sendSms3(String mobile, String content, String name,
			String pwd) {

		// 发送内容
		// String content = "第一信息 JAVA示例测试";
		String sign = "上海网恒";

		// 创建StringBuffer对象用来操作字符串
		StringBuffer sb = new StringBuffer(
				"http://web.1xinxi.cn/asmx/smsservice.aspx?");

		// 向StringBuffer追加用户名
		sb.append("name=" + name);

		// 向StringBuffer追加密码（登陆网页版，在管理中心--基本资料--接口密码，是28位的）fa246d0262c3925617b0c72bb20eeb
		sb.append("&pwd=" + pwd);

		// 向StringBuffer追加手机号码
		sb.append("&mobile=" + mobile);

		// 向StringBuffer追加消息内容转URL标准码
		try {
			sb.append("&content=" + URLEncoder.encode(content, "utf-8"));

			// 追加发送时间，可为空，为空为及时发送
			sb.append("&stime=");

			// 加签名 URLEncoder.encode(sign)
			sb.append("&sign=" + URLEncoder.encode(sign,"utf-8"));

			// type为固定值pt extno为扩展码，必须为数字 可为空
			sb.append("&type=pt");
			// 创建url对象
			// 返回发送结果
			String inputline;
			URL url = new URL(sb.toString());

			// 打开url连接
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
//			System.Net.HttpWebRequest webRequest = (System.Net.HttpWebRequest)WebRequest.Create(new Uri(url));
			// 设置url请求方式 ‘get’ 或者 ‘post’
			connection.setRequestMethod("POST");

			// 发送
			BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream()));

			inputline = in.readLine();
			// 返回结果为‘0，20140009090990,1，提交成功’ 发送成功 具体见说明文档
			System.out.println(inputline);
			return inputline.startsWith("0");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		

	}
	public static boolean sendSms4(String mobile, String content1, String name,
			String pwd) {
		
		 // Send the request
        try {
			URL postUrl = new URL("http://union.nmenu.cn/DataVerification/GetSmsForPublic.ashx");
    // Post请求的url，与get不同的是不需要带参数
//        URL postUrl = new URL("http://www.wangzhiqiang87.cn");
			// 打开连接
			HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
     
			// 设置是否向connection输出，因为这个是post请求，参数要放在
			// http正文内，因此需要设为true
			connection.setDoOutput(true);
			// Read from the connection. Default is true.
			connection.setDoInput(true);
			// 默认是 GET方式
			connection.setRequestMethod("POST");
      
			// Post 请求不能使用缓存
			connection.setUseCaches(false);
      
			connection.setInstanceFollowRedirects(true);
      
			// 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
			// 意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode
			// 进行编码
			connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			// 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
			// 要注意的是connection.getOutputStream会隐含的进行connect。
			connection.connect();
			DataOutputStream out = new DataOutputStream(connection
			        .getOutputStream());
			// The URL-encoded contend
			// 正文，正文内容其实跟get的URL中 '? '后的参数字符串一致
			String content = "msg=" + URLEncoder.encode(content1, "UTF-8");
			content +="&mobile="+mobile;
			// DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写到流里面
			out.writeBytes(content);
 
			out.flush();
			out.close(); 
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			
			while ((line = reader.readLine()) != null){
			    System.out.println(line);
			}
     
			reader.close();
			connection.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
		
	}

}
