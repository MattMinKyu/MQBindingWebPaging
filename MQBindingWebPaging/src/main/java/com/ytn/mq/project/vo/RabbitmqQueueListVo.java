package com.ytn.mq.project.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Captions Data Vo
 * 
 * @author mattmk
 */
@Data
public class RabbitmqQueueListVo {
	
	/**
	 * messages_ready
	 */
	@JsonProperty("messages_ready")
	private int messagesReady;
	
	/**
	 * name
	 */
	private String name;
}
