package com.derbysoft.controller.appgms;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import dy.hrtworkframe.controller.base.BaseController;

@Transactional
@Controller
@RequestMapping("message1.do")
public class FeedbackController extends BaseController {/*
    @Autowired 
    NewsDao newsDao; 
	
    @Autowired
    FeedbackDao feedbackDao;
    
	private static String IS_LOGIN = "isLogin";

	@Autowired
	public UserDao userDao;
	
    这是对反馈的存储
     * 
     * 
	@RequestMapping(params="p=appfeedback")
	public  Map<String, Object> appfeedback  (HttpSession session,@ModelAttribute Feedback feedback )  {
		
		try {
			String context = feedback.getContext();
			SYS_User user = (SYS_User) session.getAttribute(Const.SESSION_USER);
			pd = getPageData();
			feedback.setInputDate(DateUtil.getDateString());
			feedback.setUserID(user.getUserID());
			if(context.length()<11){
				feedback.setInputDate(context);
			}else{feedback.setTitle(getTitle(context, 10));
			}
			feedback.setUserName(user.getUserName());
			feedback.setFeedbackID(UUIDUtil.get32UUID());
			feedbackDao.insert(feedback);
			
			pd.put("newsList", newsList);
           	return MessageUtil.success();			
		} catch (Exception e) {
			return MessageUtil.error();
		}
	}
	public String  getTitle (String context,int i)  {	
	String str = context.substring(0,i);
    return str;
    }
	@RequestMapping(params="p=view1")
	public  ModelAndView queryFeedback  (HttpSession session)  {
		List<Feedback> feedbackList = feedbackDao.query(Feedback.class);
        mv.addObject("mv",feedbackList);
        return MessageUtil.success(mv);
	}
	*/}

