package com.ytn.mq.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/views/mq")
public class MainController {
	@RequestMapping("/listPage")
	public ModelAndView viewLst() {
		ModelAndView  moduleAndView = new ModelAndView();
		moduleAndView.setViewName("/list.html");
		moduleAndView.addObject("data", "Thymeleaf TEST ������");
		
		
		
		return moduleAndView;
	}
}
