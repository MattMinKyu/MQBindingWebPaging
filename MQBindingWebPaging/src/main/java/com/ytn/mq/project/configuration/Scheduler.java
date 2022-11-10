package com.ytn.mq.project.configuration;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ytn.mq.project.serviceImpl.YtnUtilServiceImpl;

@Component
public class Scheduler {
	
	private static final Logger logger = LoggerFactory.getLogger(Scheduler.class);
	
	private YtnUtilServiceImpl ytnUtilServiceImpl;
	
	public Scheduler(YtnUtilServiceImpl ytnUtilServiceImpl) {
		this.ytnUtilServiceImpl = ytnUtilServiceImpl;
	}
	
	 @Scheduled(cron = "0 */5 * * * *")
	   public void cronJobDeleteQueue() {
		 logger.info("Scheduler [cronJobDeleteQueue] ====> {}", new Date()); 
		 
		 ytnUtilServiceImpl.deleteQueueListExcute();
	      
	      
	   }
}
