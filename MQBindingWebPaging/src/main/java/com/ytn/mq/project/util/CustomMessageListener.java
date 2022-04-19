package com.ytn.mq.project.util;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.ytn.mq.project.dto.ChatDto;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class CustomMessageListener {
	
	/*
    @RabbitListener(queues = "sample.queues")
    public void receiveMessage(final MqMessageVo mqMessageVo) {
    	System.out.println("TESTSETST~~~~");
        System.out.println(mqMessageVo);
    }
	*/
	
	 @RabbitListener(queues = "chat.queue")
	 public void receiveMessage(ChatDto chatDto) {
		 	log.info("chatDto ====> {}", chatDto);
		 
	    	//ModelAndView  moduleAndView = new ModelAndView();
			//moduleAndView.setViewName("/list.html");
			//moduleAndView.addObject("data", mqMessageVo);
			//return moduleAndView;
	  }
	 
}
