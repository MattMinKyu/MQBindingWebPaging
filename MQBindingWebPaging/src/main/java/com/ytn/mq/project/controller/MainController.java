package com.ytn.mq.project.controller;

import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value="/ytn/captions/" , method = {RequestMethod.GET, RequestMethod.POST})
//@RequestMapping("/captions")
public class MainController {
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	@Value("${spring.rabbitmq.username}")
    private String userName;
    
    @Value("${spring.rabbitmq.password}")
    private String passWord;
    
	
    @GetMapping("realTimeView")
    public String getMain(Model model) {
    	logger.info("======= [MainController] [getMain] [Init] =======");
    	
    	/*
    	session.setAttribute("stompId", userName);
    	session.setAttribute("stompPwd", passWord);
    	*/
    	//mv.addObject("userName", userName);
    	//mv.addObject("passWord", passWord);
        
        /*
        String test = "{\r\n"
        		+ " \"captionIdx\" : 1,\r\n"
        		+ " \"context\" : \"윤석열 대통령 당선인이 5월 10일 취임식에 맞춰 집무실을 용산 국방부 청사로 이전하겠다고 밝힌 데 대해 청와대가 제동을 걸고 나섰습니다.라랄니아리나어리나어리나어리나어리나어리나어리나어리낭러ㅣ나어리나어리나어리나ㅓㅇ리ㅏ넝리ㅏ너이라ㅓ니아러니ㅏㅇ러ㅣ나얼니ㅏ\",\r\n"
        		+ " \"userId\" : \"mktest\",\r\n"
        		+ " \"insDate\" : 1,\r\n"
        		+ " \"updDate\" :\"\" ,\r\n"s
        		+ " \"status\" : 0\r\n"
        		+ "}\r\n"
        		+ "";
        */
        // exchange, routingkey, message
        
    	/*
        Timer m_timer = new Timer();
        
        count = 0;
        
        
        TimerTask m_task = new TimerTask() {
        	@Override
            public void run() {
        		log.info("-------------");
                ObjectMapper objectMapper = new ObjectMapper();
                CaptionsRbMqVo captionsRbMqVo = new CaptionsRbMqVo();
                LocalDate now = LocalDate.now();
                
                captionsRbMqVo.setData("윤석열 대통령 당선인이 5월 10일 취임식에 맞춰 집무실을 용산 국방부 청사로 이전하겠다고 밝힌 데 대해 청와대가 제동을 걸고 나섰습니다. [COUNT ===>"+count+"]");
                captionsRbMqVo.setDateTime(now.toString());
                
                try {
        			String captionRbMqVoStr = objectMapper.writeValueAsString(captionsRbMqVo);
        			
        			count++;
        			log.debug("[MKTEST ] =====> {}", captionRbMqVoStr);
        			
        			rabbitTemplate.convertAndSend("chat.exchange", "mattmk.routing.key", captionRbMqVoStr);
        		} catch (JsonProcessingException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
                
            }
        };
        m_timer.scheduleAtFixedRate(m_task, 0l, 1000);
        */
        
        
        model.addAttribute("userName", Base64.getEncoder().encodeToString(userName.getBytes()));
        model.addAttribute("passWord", Base64.getEncoder().encodeToString(passWord.getBytes()));
        
        return "main";
    }
    
    @GetMapping("list")
    public String getList(Model model, HttpServletRequest request) {
    	logger.info("======= [MainController] [list] [Init] =======");
    	
        return "list";
    }
    
    @PostMapping("captionEdit")
    public String editView(Model model, @RequestParam(value = "captionIdx")String captionIdx) {
    	logger.info("======= [MainController] [editView] [Init] =======");
    	logger.info("======= [MainController] [editView] [captionIdx] =====> {}", captionIdx);
    	
    	model.addAttribute("captionIdx", captionIdx);
    	
        return "captionEdit";
    }
    
    @GetMapping("captionPreviewList")
    public String captionPreviewList(Model model) {
    	logger.info("======= [MainController] [captionPreviewList] [Init] =======");
    	
        return "captionPreviewList";
    }
}
