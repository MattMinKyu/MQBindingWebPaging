package com.ytn.mq.project.rest.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ytn.mq.project.service.UserService;
import com.ytn.mq.project.util.AuthInfo;
import com.ytn.mq.project.vo.UserDataVo;

/**
 * Login Rest Controller
 * @author mattmk
 *
 */
@RestController
@RequestMapping("/ytn/member/auth/")
public class UserRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);
	
	private UserService loginUserService;
	
	public UserRestController(UserService loginUserService) {
		this.loginUserService = loginUserService;
	}
	
	/**
	 * Login Check rest api
	 * 
	 * @author mattmk
	 * @param Integer, Integer, String, String, String, Integer
	 * @return String
	 */
	@PostMapping("login")
	public int login(@RequestParam(value = "userId")String userId, @RequestParam(value = "userPwd")String userPwd
						, HttpSession session){
		
		int result = 700;
		
		UserDataVo loginUserDataVo = new UserDataVo();
		loginUserDataVo.setUserId(userId);
		loginUserDataVo.setPassword(userPwd);
		
		AuthInfo authInfo = loginUserService.loginAuth(loginUserDataVo);
		
		logger.info("[LoginRestController] [login] [authInfo] =====> {}", authInfo);
		
		if(authInfo == null) {
			return result;
		}
		
		session.setAttribute("authInfo",authInfo);
		
		result = 200;
		
		return result;
	}
	
	/**
	 * logout rest api
	 * 
	 * @author mattmk
	 * @param HttpServletRequest
	 * @return String
	 */
	@PostMapping("logout")
	public Boolean logout(HttpServletRequest request) {
		Boolean resultBol = Boolean.FALSE;
		
	    HttpSession session = request.getSession(false);
	    if (session != null) {
	        session.invalidate();
	        resultBol = Boolean.TRUE;
	    }

	    return resultBol;
	}
	
	/**
	 * userRegister rest api
	 * 
	 * @author mattmk
	 * @param String, String, String
	 * @return int
	 */
	@PostMapping("register")
	public int register(@RequestParam(value = "userId")String userId, @RequestParam(value = "userPwd")String userPwd,
							@RequestParam(value = "userName")String userName) {
		
		int result;
		
		UserDataVo loginUserDataVo = new UserDataVo();
		loginUserDataVo.setUserId(userId);
		loginUserDataVo.setPassword(userPwd);
		loginUserDataVo.setUserName(userName);
		
		result = loginUserService.registerUser(loginUserDataVo);
		
		logger.info("[LoginRestController] [register] [loginUserDataVo] =====> {}", loginUserDataVo);
		
		return result;
	}
}
