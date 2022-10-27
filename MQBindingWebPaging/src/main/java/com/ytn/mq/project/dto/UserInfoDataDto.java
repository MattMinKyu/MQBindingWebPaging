package com.ytn.mq.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * UserInfo Dto
 * @author mattmk
 *
 */
@Data
public class UserInfoDataDto {
	
	/**
	 * userId
	 */
	@JsonProperty("userId")
	private String user_id;
	
	/**
	 * password
	 */
	private String password;
	
	/**
	 * userName
	 */
	@JsonProperty("userName")
	private String user_name;
	
	/**
	 * status
	 */
	private String status;
	
	/**
	 * authType
	 */
	@JsonProperty("authType")
	private String auth_type;
	
	/**
	 * createDate
	 */
	@JsonProperty("createDate")
	private String create_date;
	
}
