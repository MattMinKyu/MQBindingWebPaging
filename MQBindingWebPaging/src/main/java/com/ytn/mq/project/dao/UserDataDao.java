package com.ytn.mq.project.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

import com.ytn.mq.project.dto.UserInfoDataDto;
import com.ytn.mq.project.vo.UserDataVo;


/**
 * LoginUser DAO
 * 
 * @author mattmk
 */

@Mapper
public interface UserDataDao {
	
	/**
	 * select UserInfoData.
	 * 
	 * @author mattmk
	 * @param String
	 * @return UserInfoDataDto
	 */
	UserInfoDataDto selectUserInfoData(@RequestParam("userId")String userId);
	
	/**
	 * insert Before select UserInfoData.
	 * 
	 * @author mattmk
	 * @param UserDataVo loginUserDataVo
	 * @return int
	 */
	int selectUserInfoDataCnt(@RequestParam("loginUserDataVo")UserDataVo loginUserDataVo);
	
	/**
	 * insert UserInfoData.
	 * 
	 * @author mattmk
	 * @param UserDataVo loginUserDataVo
	 * @return int
	 */
	int insertUserInfoData(@RequestParam("loginUserDataVo")UserDataVo loginUserDataVo);
		
}
