package com.ytn.mq.project.rest.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ytn.mq.project.dto.ListMessageDto;
import com.ytn.mq.project.dto.WrapperDto;

import ch.qos.logback.classic.Logger;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/mq")
public class MainRestController {
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	private static final String topicExchange = "sample.exchange2";
	
	@PostMapping(value = "/getList")
	public String getList(HttpServletRequest request) throws JsonProcessingException {
		/*
		System.out.println("Sending message...");
	    MqMessageVo mqMessageVo = new MqMessageVo("Hello Message!", "mattMk");
	    rabbitTemplate.convertAndSend(topicExchange, "foo.bar.baz", mqMessageVo);
	    */
		
		log.info("[request] ====> {}", request);
		
		Integer pageCnt = Integer.parseInt(request.getParameter("iDisplayStart"));
		Integer pageLen = Integer.parseInt(request.getParameter("iDisplayLength"));
        String sEcho = request.getParameter("sEcho");
        String sCol = request.getParameter("iSortCol_0");
        String sdir = request.getParameter("sSortDir_0");
        
        log.info("[pageCnt] ====> {}", pageCnt);
        log.info("[pageLen] ====> {}", pageLen);
        log.info("[sEcho] ====> {}", sEcho);
        log.info("[sCol] ====> {}", sCol);
        log.info("[sdir] ====> {}", sdir);
        
        Integer page = pageCnt/pageLen +1;
        
        log.info("[page] ====> {}", page);
        log.info("[pageLen] ====> {}", pageLen);
        
		List mkTestList = new ArrayList();
		int recordsCnt = 0;
		int recordsFilterCnt = 70;
		
		for(int cnt=0;cnt<100;cnt++) {
			ListMessageDto listMessageDto = new ListMessageDto();
			int tempCnt = 1;
			tempCnt = tempCnt+cnt;
			
			listMessageDto.setNo(tempCnt);
			listMessageDto.setTitle("SDF"+tempCnt);
			
			mkTestList.add(listMessageDto);
		}
		
		
		
		ObjectMapper mapper = new ObjectMapper();

		//String jsonList = mapper.writeValueAsString(mktest);
		
		//log.info("[mktest] ====> {}", jsonList);
		
		
		//?????????????????? ??? ????????? ?????? WrapperDto??? ?????????
		WrapperDto wrapperDto = new WrapperDto();
		wrapperDto.setAaData(mkTestList);
		//?????? ?????? ?????????
		wrapperDto.setITotalRecords(recordsCnt);
		//????????? ??? ?????? ????????? - ????????? ?????? ???????????? ?????? ????????? ?????? ?????? ???????????? ????????????.
		wrapperDto.setITotalDisplayRecords(recordsFilterCnt);
		
		log.info("[wrapperDto] ====> {}", wrapperDto);
		
		String jsonList = mapper.writeValueAsString(wrapperDto);
		
		log.info("[mktest] ====> {}", jsonList);
		
		return jsonList;
	}
}
