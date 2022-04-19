package com.ytn.mq.project.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class WrapperDto {
	
	private List<?> aaData;
	@JsonProperty("iTotalRecords")
    private int iTotalRecords;
	@JsonProperty("iTotalDisplayRecords")
    private int iTotalDisplayRecords;
}
