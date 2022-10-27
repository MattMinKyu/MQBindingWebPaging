package com.ytn.mq.project.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	@GetMapping("/")
	public String redirectMain(Model model, HttpServletRequest request) {
    	logger.info("======= [IndexController] [IndexPage] [Init] =======");
    	
        return "redirect:/ytn/captions/realTimeView";
    }
	
}
