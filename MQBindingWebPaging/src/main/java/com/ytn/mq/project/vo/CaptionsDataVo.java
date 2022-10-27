package com.ytn.mq.project.vo;

import lombok.Data;

/**
 * Captions Data Vo
 * 
 * @author mattmk
 */
@Data
public class CaptionsDataVo {
	
	/**
	 * rowIdx
	 */
	private Integer rowidx;
	
	/**
	 * captionIdx
	 */
	private String captionIdx;
	
	/**
	 * context
	 */
	private String context;
	
	/**
	 * ins_date
	 */
	private String insDate;
	
	/**
	 * upd_userid
	 */
	private String updUserid;
	
	/**
	 * upd_date
	 */
	private String updDate;
	
	/**
	 * mqInsDate
	 */
	private String mqInsDate;
}
