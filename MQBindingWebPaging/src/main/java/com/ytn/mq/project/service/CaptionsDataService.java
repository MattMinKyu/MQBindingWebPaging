package com.ytn.mq.project.service;

import java.sql.SQLException;

import com.ytn.mq.project.vo.CaptionsDataVo;
import com.ytn.mq.project.vo.CaptionsRbMqVo;
import com.ytn.mq.project.vo.PageingVo;

/**
 * FirstDataService Interface
 * 
 * @author mattmk
 *
 */
public interface CaptionsDataService {
	
	/**
	 * Captions List Call Data.
	 * 
	 * @author mattmk
	 * @param PageingVo pageingVo
	 * @return String
	 */
	public String getCaptionsDataJson(PageingVo pageingVo);
	
	/**
	 * Caption Edit Save Data.
	 * 
	 * @author mattmk
	 * @param CaptionsDataVo captionsDataVo
	 * @return int
	 */
	public int saveCaptionEditData(CaptionsDataVo captionsDataVo);
	
	/**
	 * Caption Edit Get Data.
	 * 
	 * @author mattmk
	 * @param String
	 * @return String
	 */
	public String getCaptionEditDataJson(String captionsIdx);
	
	/**
	 * Caption Save Data.
	 * 
	 * @author mattmk
	 * @param CaptionsRbMqVo captionsRbMqVo
	 * @return int
	 * @throws Exception 
	 */
	public int saveCaptionData(CaptionsRbMqVo captionsRbMqVo) throws SQLException;
	
}
