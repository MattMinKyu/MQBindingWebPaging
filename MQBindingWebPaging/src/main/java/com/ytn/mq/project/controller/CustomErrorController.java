package com.ytn.mq.project.controller;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
 
@Controller
@Slf4j
public class CustomErrorController implements ErrorController {
 
    private static final Logger logger = LoggerFactory.getLogger(CustomErrorController.class);
    
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
    	
    	//logger.info("======= [CustomErrorController] [handleError] [Init] =======");
    	
    	/*
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        HttpStatus httpStatus = HttpStatus.valueOf(Integer.valueOf(status.toString()));
        logger.info("httpStatus : "+httpStatus.toString());
        model.addAttribute("code", status.toString());
        model.addAttribute("msg", httpStatus.getReasonPhrase());
        model.addAttribute("timestamp", new Date());
        */
    	
        return "error";
    }
 
}
