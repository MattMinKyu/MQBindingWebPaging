package com.ytn.mq.project.service;

import com.ytn.mq.project.dto.UserInfoDataDto;
import com.ytn.mq.project.util.AuthInfo;
import com.ytn.mq.project.vo.UserDataVo;

/**
 * LoginUserService Interface
 * 
 * @author mattmk
 *
 */
public interface UserService {
	
	/**
	 * loginAuth Data.
	 * 
	 * @author mattmk
	 * @param UserDataVo
	 * @return AuthInfo
	 */
	public AuthInfo loginAuth(UserDataVo loginUserDataVo);
	
	/**
	 * loginUserIdChk.
	 * 
	 * @author mattmk
	 * @param String
	 * @return UserInfoDataDto
	 */
	public UserInfoDataDto loginUserIdChk(String userId);
	
	/**
	 * loginUserPasswordChk.
	 * 
	 * @author mattmk
	 * @param String, String
	 * @return Boolean
	 */
	public Boolean loginUserPasswordChk(String passwordInput, String password);
	
	/**
	 * Register User.
	 * 
	 * @author mattmk
	 * @param UserDataVo
	 * @return int
	 */
	public int registerUser(UserDataVo loginUserDataVo);
}
