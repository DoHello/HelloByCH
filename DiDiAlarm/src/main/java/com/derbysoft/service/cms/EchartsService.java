package com.derbysoft.service.cms;




import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.derbysoft.dao.sys.UserDao;
import com.derbysoft.entity.echarts.AreaCount;

@Service
public class EchartsService {
  @Autowired
 private UserDao userdao;
	
	

@SuppressWarnings("all")
public List getAreaCount(){
	List<String> params = new ArrayList<String>();
	params.add("0");
	params.add("0");
	params.add("0");
	params.add("1");
	List call = userdao.call(AreaCount.class, params, "areaCount");
	return call;
} 	
	
}
