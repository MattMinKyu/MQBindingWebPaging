package com.ytn.mq.project.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ytn.mq.project.util.AuthInfo;

@Controller
//@RequestMapping(value="/ytn/member/" , method = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping("/ytn/member/")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
    @GetMapping("login")
    public String loginPage() {
    	logger.info("[UserController] [loginPage] START");
    	
        return "login";
    }
 
    @GetMapping("userRegister")
    public String userRegisterPage(HttpServletRequest request) {
    	logger.info("[UserController] [userRegisterPage] START");
    	
    	HttpSession session = request.getSession(false);
    	
    	if(session == null) {
    		return "redirect:login";
    	}
    	
    	AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
    	
    	if(authInfo == null) {
    		return "redirect:login";
    	}else if(!authInfo.getType().equals("ADMIN")) {
    		return "redirect:/ytn/captions/list";
    	}
    	
        return "userRegister";
    }
    
}
