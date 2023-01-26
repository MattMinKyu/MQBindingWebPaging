package com.ytn.mq.project.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ytn.mq.project.dao.UserDataDao;
import com.ytn.mq.project.dto.UserInfoDataDto;
import com.ytn.mq.project.service.UserService;
import com.ytn.mq.project.util.AuthInfo;
import com.ytn.mq.project.vo.UserDataVo;

/**
 * LoginUserServiceImpl
 * 
 * @author mattmk
 *
 */
@Service
public class UserServiceImpl implements UserService{
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private UserDataDao loginUserDataDao;
	private PasswordEncoder passwordEncoder;
	
	public UserServiceImpl(UserDataDao loginUserDataDao, PasswordEncoder passwordEncoder) {
		this.loginUserDataDao = loginUserDataDao;
		this.passwordEncoder = passwordEncoder;
	}
	
	
	/**
	 * loginAuth Data.
	 * 
	 * @author mattmk
	 * @param UserDataVo
	 * @return String
	 */
	@SuppressWarnings("deprecation")
	@Override
	public AuthInfo loginAuth(UserDataVo loginUserDataVo) {
		AuthInfo authInfo = new AuthInfo();
		UserInfoDataDto userInfoDataDto = new UserInfoDataDto();
		
		userInfoDataDto = this.loginUserIdChk(loginUserDataVo.getUserId());
		
		logger.info("[LoginUserServiceImpl] [loginAuth] [userInfoDataDto] ====> {}", userInfoDataDto);
		
		if(userInfoDataDto == null || StringUtils.isEmpty(userInfoDataDto.getUser_id())) {
			return null;
		}
		
		if(!loginUserPasswordChk(loginUserDataVo.getPassword(), userInfoDataDto.getPassword())) {
			return null;
		}
		
		logger.info("[LoginUserServiceImpl] [loginAuth] ====> SUCCESS !!!!");
		
		authInfo.setId(userInfoDataDto.getUser_id());
		authInfo.setName(userInfoDataDto.getUser_name());
		authInfo.setType(userInfoDataDto.getAuth_type());
		
		return authInfo;
	}
	
	/**
	 * loginUserIdChk.
	 * 
	 * @author mattmk
	 * @param String
	 * @return UserInfoDataDto
	 */
	@Override
	public UserInfoDataDto loginUserIdChk(String userId) {
		return loginUserDataDao.selectUserInfoData(userId);
	}
	
	/**
	 * loginUserPasswordChk.
	 * 
	 * @author mattmk
	 * @param String, String
	 * @return Boolean
	 */
	@SuppressWarnings("deprecation")
	@Override
	public Boolean loginUserPasswordChk(String passwordInput, String password) {
		Boolean result = Boolean.FALSE;
		
		if(StringUtils.isEmpty(password)) {
			return result;
		}
		
		try {
			result = passwordEncoder.matches(passwordInput, password);
		}catch(Exception e) {
			e.printStackTrace();
			logger.info("[LoginUserServiceImpl] [loginUserPasswordChk] [Exception] ====> {}", e);
		}
		
		return result;
	}
	
	/**
	 * loginUserPasswordChk.
	 * 
	 * @author mattmk
	 * @param String, String
	 * @return Boolean
	 */
	@SuppressWarnings("deprecation")
	@Transactional
	@Override
	public int registerUser(UserDataVo loginUserDataVo) {
		int result = 0;
		
		if(loginUserDataVo == null || StringUtils.isEmpty(loginUserDataVo.getPassword())) {
			return result;
		}
		
		result = loginUserDataDao.selectUserInfoDataCnt(loginUserDataVo);
		
		if(result > 0) {
			return 600;
		}
		
		String currentPassword = new BCryptPasswordEncoder().encode(loginUserDataVo.getPassword());
		loginUserDataVo.setPassword(currentPassword);
		
		return loginUserDataDao.insertUserInfoData(loginUserDataVo);
	}
}
