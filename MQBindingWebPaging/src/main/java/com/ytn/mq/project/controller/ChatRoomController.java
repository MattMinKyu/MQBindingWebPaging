package com.ytn.mq.project.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/chat")
@Slf4j
public class ChatRoomController {
	
    @GetMapping("/rooms")
    public String getRooms() {
    	log.info("@ChatRoomController, chat getRooms()");
    	
        return "chat/rooms";
    }

    @GetMapping("/main")
    public String getMain(Model model, HttpServletRequest request) {
    	log.info("@ChatRoomController, chat getRoom()");
    	
    	HttpSession session = request.getSession();

    	session.setAttribute("stompId", "guest");
    	session.setAttribute("stompPwd", "guest");
    	
        model.addAttribute("nickname", "민규테스트");
        return "chat/main";
    }
    
    @GetMapping("/list")
    public String getList(Model model, HttpServletRequest request) {
    	log.info("@ChatRoomController, chat getRoom()");
    	
    	HttpSession session = request.getSession();

    	session.setAttribute("stompId", "guest");
    	session.setAttribute("stompPwd", "guest");
    	
        model.addAttribute("nickname", "민규테스트");
        return "chat/list";
    }
}
