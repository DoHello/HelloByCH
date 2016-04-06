package com.derbysoft.controller.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.derbysoft.controller.service.BaseDaoController;
import com.derbysoft.jms.activemq.JpushSender;

@Transactional
@Controller("pushBySys")
@RequestMapping("pushBySys.do")
public class JpushBySysController extends BaseDaoController{

	@Autowired
	private JpushSender jpushSender;

}
