package com.ytn.mq.project.serviceImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ytn.mq.project.dao.CaptionsDataDao;
import com.ytn.mq.project.dto.CaptionsDataDto;
import com.ytn.mq.project.dto.WrapperDto;
import com.ytn.mq.project.service.CaptionsDataService;
import com.ytn.mq.project.vo.CaptionsDataVo;
import com.ytn.mq.project.vo.CaptionsRbMqVo;
import com.ytn.mq.project.vo.PageingVo;

@Service
public class CaptionsDataServiceImpl implements CaptionsDataService{
	
	private static final Logger logger = LoggerFactory.getLogger(CaptionsDataServiceImpl.class);
	
	private CaptionsDataDao captionsDataDao;
	
	public CaptionsDataServiceImpl(CaptionsDataDao captionsDataDao) {
		this.captionsDataDao = captionsDataDao;
	}
	
	@Override
	public String getCaptionsDataJson(PageingVo pageingVo) {
		WrapperDto wrapperDto = new WrapperDto();
		ObjectMapper mapper = new ObjectMapper();
		List<CaptionsDataDto> captionsDataList = new ArrayList<CaptionsDataDto>();
		String returnData="";
		
		/*
		 *  초기화.
		 */
		wrapperDto.setAaData(captionsDataList);
		
		try {
			returnData = mapper.writeValueAsString(wrapperDto);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			logger.error("[CaptionsDataServiceImpl] [getCaptionsDataJson] [Try Catch returnData] [Exception] ====> {}", e);
		};
		
		logger.info("[CaptionsDataServiceImpl] [getCaptionsDataJson] [pageingVo] ====> {}", pageingVo);
		
		if(pageingVo == null) {
			logger.error("[CaptionsDataServiceImpl] [getCaptionsDataJson] [pageingVo] [NULL]");
			return returnData;
		}
		
		/*
		String targetOrder;
        
        switch (pageingVo.getSCol()) {
        	case "1" : 
        		targetOrder = "area";
        		break;
        	case "2" : 
        		targetOrder = "subSgId";
        		break;
        	case "3" : 
        		targetOrder = "sdid";
        		break;
        	case "4" : 
        		targetOrder = "wiwid";
        		break;
        	case "5" : 
        		targetOrder = "sggid";
        		break;
        	case "6" : 
        		targetOrder = "cnt";
        		break;
        	default:
        		targetOrder = "area";
        		break;
        }
        
        pageingVo.setTargetOrder(targetOrder);
		*/
		
        int recordsCnt;
		
		recordsCnt = captionsDataDao.selectCaptionsInfoListCnt(pageingVo);
		
		logger.info("[CaptionsDataServiceImpl] [getCaptionsDataJson] [recordsCnt] ====> {}", recordsCnt);
		
		/*
		 * count가 0이면 바로 Return
		 */
		if(recordsCnt==0) {
			logger.info("[CaptionsDataServiceImpl] [getCaptionsDataJson] [recordsCnt] [NULL]");
			return returnData;
		}
		
		captionsDataList = captionsDataDao.selectCaptionsInfoList(pageingVo);	
		
		/*
		// @TestData
			recordsCnt = 0;
		*/
		//Ŭ���̾�Ʈ�� �� ������ ���� WrapperDto�� ���α�
		wrapperDto.setAaData(captionsDataList);
		//���� ��ü ������
		wrapperDto.setITotalRecords(recordsCnt);
		//���͸� �� ��ü ������ - ���͸� ��� ������� �ʱ� ������ ���� ��ü �����Ϳ� �����ϴ�.
		wrapperDto.setITotalDisplayRecords(recordsCnt);
		
		try {
			returnData = mapper.writeValueAsString(wrapperDto);
		}catch (JsonProcessingException e) {
			try {
				returnData = mapper.writeValueAsString("");
			} catch (JsonProcessingException e1) {
				logger.error("[CaptionsDataServiceImpl] [getCaptionsDataJson] [selectCaptionsInfoList] [JsonProcessingException] ====> {}", e1);
				e1.printStackTrace();
			}
			
			e.printStackTrace();
			logger.error("[CaptionsDataServiceImpl] [getCaptionsDataJson] [Exception] ====> {}", e);
		}
		
		//logger.info("[CaptionsDataServiceImpl] [getCaptionsDataJson] [returnData] ====> {}", returnData);
		
		
		return returnData;
	}
	
	
	/**
	 * Caption Edit Save Data.
	 * 
	 * @author mattmk
	 * @param CaptionsRbMqVo captionsRbMqVo
	 * @return int
	 */
	@Override
	@Transactional(rollbackFor = {RuntimeException.class, Exception.class})
	public int saveCaptionData(CaptionsRbMqVo captionsRbMqVo) throws SQLException{
		int resultCnt = 0;
		String dateTimeTemp = captionsRbMqVo.getDateTime();
		String resultDateTime = "";
		
		CaptionsDataVo captionsDataVo = new CaptionsDataVo();
		captionsDataVo.setContext(captionsRbMqVo.getData());
		captionsDataVo.setMqInsDate(dateTimeTemp);
		captionsDataVo.setUpdUserid("SYSTEM");
		
		/*
		 * 정규 표현식
		 * 숫자 빼고 전부 제거, 띄워쓰기 공백 제거.
		 */
		resultDateTime = dateTimeTemp.replaceAll("[^0-9]", "").replaceAll("[\\\\s]", "");
		
		captionsDataVo.setCaptionIdx(resultDateTime);
		
		resultCnt = captionsDataDao.saveCaptionData(captionsDataVo);
		
		return resultCnt;
	}

	
	
	/**
	 * Caption Edit Get Data.
	 * 
	 * @author mattmk
	 * @param String
	 * @return String
	 */
	@Override
	public String getCaptionEditDataJson(String captionIdx) {
		String returnData="";
		
		logger.info("[CaptionsDataServiceImpl] [getCaptionEditDataJson] [captionIdx] ====> {}", captionIdx);
		
		ObjectMapper mapper = new ObjectMapper();
		CaptionsDataDto captionsDataDto = new CaptionsDataDto();
		
		captionsDataDto = captionsDataDao.selectCaptionEditData(captionIdx);

		
		try {
			returnData = mapper.writeValueAsString(captionsDataDto);
		}catch (JsonProcessingException e) {
			try {
				returnData = mapper.writeValueAsString("");
			} catch (JsonProcessingException e1) {
				logger.error("[CaptionsDataServiceImpl] [getCaptionEditDataJson] [JsonProcessingException] ====> {}", e1);
				e1.printStackTrace();
			}
			
			e.printStackTrace();
			logger.error("[CaptionsDataServiceImpl] [getCaptionEditDataJson] [Exception] ====> {}", e);
		}
		
		logger.info("[CaptionsDataServiceImpl] [getCaptionEditDataJson] [returnData] ====> {}", returnData);
		
		
		return returnData;
	}
	

	/**
	 * Caption Edit Save Data.
	 * 
	 * @author mattmk
	 * @param CaptionsDataVo captionsDataVo
	 * @return int
	 */
	@Override
	@Transactional
	public int saveCaptionEditData(CaptionsDataVo captionsDataVo) {
		int resultReturn = 0;
		
		try {
			resultReturn = captionsDataDao.saveCaptionEditData(captionsDataVo);
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("[CaptionsDataServiceImpl] [saveCaptionEditData] [Exception] ====> {}", e);
		}
		
		return resultReturn;
	}
	
	@Override
	public String newMoveDataTableBeforeCnt(String paramAuthData) {
		String resultStr = "";
		String tempStr = "0";
		
		ObjectMapper mapper = new ObjectMapper();
		
		if(paramAuthData != "" && paramAuthData.equals("ADMIN")) {
			tempStr = Integer.toString(captionsDataDao.selectProcedureNewMoveDataBeforeCnt());
		}
		
		try {
			resultStr = mapper.writeValueAsString(tempStr);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			logger.error("[CaptionsDataServiceImpl] [newMoveDataTable] [JsonProcessingException] ====> {}", e);
		}
		
		
		
		return resultStr;
	}
	
	
	
	@Override
	@Transactional(rollbackFor = {RuntimeException.class, Exception.class})
	public String newMoveDataTable(String paramAuthData) {
		String resultStr = "";
		ObjectMapper mapper = new ObjectMapper();
		
		if(paramAuthData == "" || !paramAuthData.equals("ADMIN")) {
			try {
				resultStr = mapper.writeValueAsString("");
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				logger.error("[CaptionsDataServiceImpl] [newMoveDataTable] [JsonProcessingException] ====> {}", e);
			}
			
			return resultStr;
		}
		
		Map<String, Object> receiveMap = new HashMap<String, Object>();
		receiveMap.put("pResult", "");
		
		String pResult = "";
		
		try {
			captionsDataDao.procedureNewMoveDataTable(receiveMap);
			
			pResult = (String) receiveMap.get("pResult");
			
			logger.info("[CaptionsDataServiceImpl] [newMoveDataTable] [pResult] ====> {}", pResult);
			
			String[] receiveArray = new String[receiveMap.size()];
			receiveArray = pResult.split("//");
			Map<String, String> resultMap = new HashMap<String, String>();
			
			resultMap.put("resultCode", receiveArray[0]);
			resultMap.put("resultMessage", receiveArray[1]);
			
			resultStr = mapper.writeValueAsString(resultMap);
			
			if(!receiveArray[0].equals("200")) {
				logger.error("[CaptionsDataServiceImpl] [newMoveDataTable] [Exception] [ErrorCode] ====> {}", receiveArray[0]);
				logger.error("[CaptionsDataServiceImpl] [newMoveDataTable] [Exception] [ErrorMessage] ====> {}", receiveArray[1]);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("[CaptionsDataServiceImpl] [newMoveDataTable] [Exception] ====> {}", e);
		}
		
		
		return resultStr;
	}
	
}
