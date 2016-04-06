package com.derbysoft.dto;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;


public class HrtJobFactory extends AdaptableJobFactory {

	@Autowired
	  private AutowireCapableBeanFactory capableBeanFactory;
	  
	  protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
	    //调用父类的方法
	    Object jobInstance = super.createJobInstance(bundle);
	    capableBeanFactory.autowireBean(jobInstance);
	    return jobInstance;
	  }
}
