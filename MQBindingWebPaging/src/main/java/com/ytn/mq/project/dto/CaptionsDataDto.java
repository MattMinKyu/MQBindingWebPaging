package com.ytn.mq.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Captions Dto
 * @author mattmk
 *
 */
@Data
public class CaptionsDataDto {
	
	/**
	 * rowIdx
	 */
	@JsonProperty("rowIdx")
	private Integer rowidx;
	
	/**
	 * caption_idx
	 */
	@JsonProperty("captionIdx")
	private String caption_idx;
	
	/**
	 * context
	 */
	private String context;
	
	
	/**
	 * ins_date
	 */
	@JsonProperty("insDate")
	private String ins_date;
	
	/**
	 * upd_userid
	 */
	@JsonProperty("updUserid")
	private String upd_userid;
	
	/**
	 * upd_date
	 */
	@JsonProperty("updDate")
	private String upd_date;
	
	/**
	 * mq_ins_date
	 */
	@JsonProperty("mqInsDate")
	private String mq_ins_date;
	
}
