package com.ytn.mq.project.vo;

import lombok.Data;

/**
 * Pageing Vo
 * 
 * @author mattmk
 *https://www.google.com/search?q=SqlSessionFactory%EC%84%A4%EC%A0%95&gbv=1&sxsrf=ALiCzsaN6tgmKpkBV6DdSrX9RSBl2qI3Ag:1657179571224&source=lnms&tbm=isch&sa=X&ved=0ahUKEwiUtq_9oub4AhXlqFYBHUCaDqsQ_AUICCgD
 */
@Data
public class PageingVo {
	
	/**
	 * Pageing Start Idx
	 */
	private Integer startPageIdx;
	
	/**
	 * Pageing End idx
	 */
	private Integer endPageIdx;
	
	/**
	 * Sorting Target
	 */
	private String targetOrder;
	
	/**
	 * Sorting
	 * ex) asc, desc
	 */
	private String order;
	
	/**
	 * Scol
	 */
	private String sCol;
	
	/**
	 * page Cnt
	 */
	private Integer pageCnt;
	
	/**
	 * 검색어
	 */
	private String searchText;
	
	/**
	 * 검색 시작일자
	 */
	private String strDate;
	
	/**
	 * 검색 종료일자
	 */
	private String endDate;
	
}
