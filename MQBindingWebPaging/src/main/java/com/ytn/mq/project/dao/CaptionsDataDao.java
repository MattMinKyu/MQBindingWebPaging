package com.ytn.mq.project.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

import com.ytn.mq.project.dto.CaptionsDataDto;
import com.ytn.mq.project.vo.CaptionsDataVo;
import com.ytn.mq.project.vo.PageingVo;


/**
 * Captions DAO
 * 
 * @author mattmk
 */

@Mapper
public interface CaptionsDataDao {
	
	/**
	 * Captions List Cnt.
	 * 
	 * @author mattmk
	 * @param PageingVo pageingVo
	 * @return int
	 */
	int selectCaptionsInfoListCnt(@RequestParam("pageingVo")PageingVo pageingVo);
		
	/**
	 * Captions List Array.
	 * 
	 * @author mattmk
	 * @param PageingVo pageingVo
	 * @return List<CaptionsDataDto>
	 */
	List<CaptionsDataDto> selectCaptionsInfoList(@RequestParam("pageingVo")PageingVo pageingVo);
	
	/**
	 * Captions Data Save
	 * 
	 * @author mattmk
	 * @param CaptionsDataVo captionsDataVo
	 * @return int
	 */
	int saveCaptionData(@RequestParam("CaptionsDataVo")CaptionsDataVo captionsDataVo);
	
	/**
	 * Captions Edit select
	 * 
	 * @author mattmk
	 * @param String
	 * @return CaptionsDataDto
	 */
	CaptionsDataDto selectCaptionEditData(@RequestParam("captionIdx")String captionIdx);
	
	/**
	 * Captions Edit Save
	 * 
	 * @author mattmk
	 * @param CaptionsDataVo captionsDataVo
	 * @return int
	 */
	int saveCaptionEditData(@RequestParam("CaptionsDataVo")CaptionsDataVo captionsDataVo);
	
	/**
	 * Captions List Array For Excel.
	 * 
	 * @author mattmk
	 * @param PageingVo pageingVo
	 * @return List<CaptionsDataDto>
	 */
	List<CaptionsDataDto> selectCaptionsInfoListForExcel(@RequestParam("pageingVo")PageingVo pageingVo);
}
