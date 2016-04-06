package com.derbysoft.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.derbysoft.entity.RegisterPhone;
import com.derbysoft.redis.service.UserService;


@Service
public class SmsRegsiterUtil extends SmsUtil {

	private static String DD_REGISTER_TOKEN = "DD_REGISTER_TOKEN:";
	private static int DD_REGISTER_NUM = 5;

	@Value("${SMS.ACCOUNT}")
	public String ACCOUNT;

	@Value("${SMS.PASSWORD}")
	public String SMSPASSWORD;

	@Autowired
	public UserService userService;

	public boolean sendResgsiterCode(String mobile) {
		// 生成6位随机数
		String code = findCode() + "";
		// 这里我们需要把code放在redis中,这里我们需要做个判断
		code = setRedisRegister(mobile, code);
		if(code==null){
			return false;
		}
		 String sms = "您的验证码为："+code+"，感谢您使用云警平台，请不要把验证码泄露给其他人。";
//		 String sms = "您的订单编码：" + code + "。如需帮助请联系客服。";// sms1 模版
//		 String sms ="您的验证码是：【"+code+"】。请不要把验证码泄露给其他人。";//sms2模版
		// System.out.println(ACCOUNT+SMSPASSWORD+"ssss"+mobile);
		return sendSms4(mobile, sms, ACCOUNT, SMSPASSWORD);
	}

	// 这里是警员端获取帐号密码不需要验证,只需要输入帐号,然后会把密码发送信息给到手机上
	public void sendPasswordByPhone1(String password, String mobile) {
		
		// 获取帐号密码
		String sms = "您的密码为:" + password + " , 感谢您使用云警花溪警务平台,请不要将密码泄露。";
		sendSms4(mobile, sms, ACCOUNT, SMSPASSWORD);
	}
	public void sendPasswordByPhone2(String password, String mobile,String username) {
		
		// 获取帐号密码
		String sms = "您的帐号为:"+username+",您的密码为:" + password + " , 感谢您使用云警花溪,请不要将帐号信息泄露,请牢记该信息内容后删除此信息。";
		sendSms4(mobile, sms, ACCOUNT, SMSPASSWORD);
	}

	/**
	 * 设置验证码到redis中
	 * @Title: setRedisRegister
	 * @Description: TODO
	 * @param mobile
	 * @param code
	 * @return: void
	 */
	public String setRedisRegister(String mobile, String code) {
		// 判断redis中这个验证码是否已经存在失效,没有失效的话就直接拿
		try {
			if (queryCodeByToken(mobile) == null) {
				// 将code写入到redis中,默认是30分钟
				RegisterPhone rp = new RegisterPhone();
				rp.setRegCode(code);
				rp.setCount(1);
				rp.setStatus("reg");
				rp.setTime(System.currentTimeMillis());
				String token = userService.phoneToRedis(DD_REGISTER_TOKEN
						+ mobile, rp);
				 userService.setRedisUserTime(token);
				return code;
			} else {
				// 这里考虑的用户没有收到验证信息二次发送,规定时间里(1分钟),验证码是相同的,时间重新设置
				RegisterPhone rp = queryCodeByToken(mobile);
				if (/*rp.getCount() < DD_REGISTER_NUM && */(System.currentTimeMillis() - rp.getTime()) <= 1 * 60 * 1000) {
					rp.setCount(rp.getCount() + 1);
					rp.setTime(System.currentTimeMillis());
					userService.phoneToRedis(DD_REGISTER_TOKEN
							+ mobile, rp);
					return rp.getRegCode();
				} else if (/*rp.getCount() < DD_REGISTER_NUM && */(System.currentTimeMillis() - rp.getTime())> 1 * 60 * 1000){
					rp.setCount(rp.getCount() + 1);
					rp.setTime(System.currentTimeMillis());
					rp.setRegCode(findCode() + "");
					userService.phoneToRedis(DD_REGISTER_TOKEN
							+ mobile, rp);
					return rp.getRegCode();
				}else{
					return null;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据token查询code
	 * 
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public RegisterPhone queryCodeByToken(String mobile) throws Exception {
		return userService.queryRegisterByToken(DD_REGISTER_TOKEN + mobile);
	}

	public int findCode() {
		int a = (int) (Math.random() * 1000000);
		while (a < 100000 || a >= 1000000) {
			a = (int) (Math.random() * 1000000);
		}
		return a;
	}

}
