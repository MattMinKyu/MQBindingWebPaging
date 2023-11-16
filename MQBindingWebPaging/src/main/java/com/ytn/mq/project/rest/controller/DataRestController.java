package com.ytn.mq.project.rest.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ytn.mq.project.service.CaptionsDataService;
import com.ytn.mq.project.serviceImpl.YtnUtilServiceImpl;
import com.ytn.mq.project.util.AuthInfo;
import com.ytn.mq.project.vo.CaptionsDataVo;
import com.ytn.mq.project.vo.PageingVo;

/**
 * Data Rest Controller
 * @author mattmk
 *
 */
	
@RestController
@RequestMapping("/ytn/captions/data/")
public class DataRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(DataRestController.class);
	
	private final CaptionsDataService captionsDataService;
	private final YtnUtilServiceImpl ytnUtilServiceImpl;
	
	public DataRestController(CaptionsDataService captionsDataService, YtnUtilServiceImpl ytnUtilServiceImpl) {
		this.captionsDataService = captionsDataService;
		this.ytnUtilServiceImpl = ytnUtilServiceImpl;
	}
	
	/**
	 * Captions rest api
	 * 
	 * @author mattmk
	 * @param Integer, Integer, String, String, String, Integer
	 * @return String
	 */
	@SuppressWarnings("deprecation")
	@PostMapping("getList")
	public String getList(@RequestParam(value = "start")Integer start, @RequestParam(value = "pageSize")Integer pageSize,
							@RequestParam(value = "searchText")String searchText, @RequestParam(value = "strDateTime")String strDateTime,
							@RequestParam(value = "endDateTime")String endDateTime, @RequestParam(value = "page")Integer page) {
		
		String jsonList="";
		
		if(StringUtils.isEmpty(strDateTime) || StringUtils.isEmpty(endDateTime)
				|| strDateTime.length() != 17 || endDateTime.length() != 17) {
			
			logger.info("[DataRestController] [getList] [paramDatas] [strDateTime] [NULL] ====> {}", strDateTime);
			logger.info("[DataRestController] [getList] [paramDatas] [endDateTime] [NULL] ====> {}", endDateTime);
			return jsonList;
		}
		
        PageingVo pageingVo = new PageingVo();
        pageingVo.setStartPageIdx(start+1);
        pageingVo.setEndPageIdx(page*pageSize);
        pageingVo.setPageCnt(page);
        pageingVo.setSearchText(searchText);
        pageingVo.setStrDate(strDateTime);
        pageingVo.setEndDate(endDateTime);
        
        logger.info("[DataRestController] [getList] [pageingVo] ====> {}", pageingVo.toString());
        
        jsonList = captionsDataService.getCaptionsDataJson(pageingVo);
        
		return jsonList;
	}
	
	/**
	 * Caption Edit Get Data.
	 * 
	 * @author mattmk
	 * @param BigInteger
	 * @return String
	 */
	@PostMapping("getEditData")
	public String getEditData(@RequestParam(value = "captionIdx")String captionIdx) {
		
		String jsonResult="";
		
		logger.info("[DataRestController] [getEditData] [paramDatas] [captionsIdx] ====> {}", captionIdx);
		
		jsonResult = captionsDataService.getCaptionEditDataJson(captionIdx);
		
		return jsonResult;
	}
	
	
	/**
	 * Caption Edit save Data.
	 * 
	 * @author mattmk
	 * @param BigIntegeraSD
	 * @return String
	 */
	@SuppressWarnings("deprecation")
	@PostMapping("saveEditData")
	public int saveEditData(@RequestParam(value = "captionIdx")String captionIdx, @RequestParam(value = "context")String context
							,HttpSession session) {
		
		int resultInt=0;
		
		if(session.getAttribute("authInfo") == null || StringUtils.isEmpty(context)) {
			return resultInt;
		}
		
		AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
		
		if(authInfo == null || StringUtils.isEmpty(authInfo.getId())) {
			return resultInt;
		}
		
		CaptionsDataVo captionsDataVo = new CaptionsDataVo();
		captionsDataVo.setCaptionIdx(captionIdx);
		captionsDataVo.setContext(context);
		captionsDataVo.setUpdUserid(authInfo.getId());
		
		logger.info("[DataRestController] [saveEditData] [captionsDataVo] ====> {}", captionsDataVo.toString());
		
		resultInt = captionsDataService.saveCaptionEditData(captionsDataVo);
		
		return resultInt;
	}
	
	/**
	 * Caption Edit save Data.
	 * 
	 * @author mattmk
	 * @param BigInteger
	 * @return String
	 */
	@SuppressWarnings("deprecation")
	@PostMapping("getExcelData")
	public String getExcelData(@RequestParam(value = "strDateTime")String strDateTime, @RequestParam(value = "endDateTime")String endDateTime
									,@RequestParam(value = "searchText")String searchText) {
		
		String result = "";
		
		if(StringUtils.isEmpty(strDateTime) || StringUtils.isEmpty(endDateTime)
				|| strDateTime.length() != 17 || endDateTime.length() != 17) {
			
			logger.info("[DataRestController] [getExcelData] [paramDatas] [strDateTime] [NULL] ====> {}", strDateTime);
			logger.info("[DataRestController] [getExcelData] [paramDatas] [endDateTime] [NULL] ====> {}", endDateTime);
			return result;
		}
		
		PageingVo pageingVo = new PageingVo();
        pageingVo.setStrDate(strDateTime);
        pageingVo.setEndDate(endDateTime);
        pageingVo.setSearchText(searchText);
        result = ytnUtilServiceImpl.getCaptionsExcelData(pageingVo);
        
		return result;
	}
	
	/**
	 * Caption Preview Data.
	 * 
	 * @author mattmk
	 * @param BigInteger
	 * @return String
	 */
	@SuppressWarnings("deprecation")
	@PostMapping("getPreviewData")
	public String getPreviewData(@RequestParam(value = "searchStrDate")String searchStrDate, @RequestParam(value = "searchEndDate")String searchEndDate
									,@RequestParam(value = "searchText")String searchText) {
		
		String jsonResult="";
		
		if(StringUtils.isEmpty(searchStrDate) || StringUtils.isEmpty(searchEndDate)
				|| searchStrDate.length() != 17 || searchEndDate.length() != 17) {
			
			logger.info("[DataRestController] [getPreviewData] [paramDatas] [searchStrDate] [NULL] ====> {}", searchStrDate);
			logger.info("[DataRestController] [getPreviewData] [paramDatas] [searchEndDate] [NULL] ====> {}", searchEndDate);
			return jsonResult;
		}
		
		PageingVo pageingVo = new PageingVo();
        pageingVo.setStrDate(searchStrDate);
        pageingVo.setEndDate(searchEndDate);
        pageingVo.setSearchText(searchText);
        
        logger.info("[DataRestController] [getPreviewData] [pageingVo] ====> {}", pageingVo.toString());
        
        jsonResult = ytnUtilServiceImpl.getCaptionsExcelData(pageingVo);
		
		return jsonResult;
	}
	
	
	/**
	 * dataListMoveCnt rest api
	 * 
	 * @author mattmk
	 * @param 
	 * @return Boolean
	 */
	@PostMapping("dataListMoveCnt")
	public String dataListMoveCnt(HttpSession session) {
		
		String resultStr = "";
		String paramAuthData = "";
		
		AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
		
		if(authInfo != null) {
			paramAuthData = authInfo.getType();
		}
		
		resultStr = captionsDataService.newMoveDataTableBeforeCnt(paramAuthData);
        
		return resultStr;
	}
	
	
	/**
	 * dataListMove rest api
	 * 
	 * @author mattmk
	 * @param 
	 * @return Boolean
	 */
	@PostMapping("dataListMove")
	public String dataListMove(HttpSession session) {
		
		String resultStr = "";
		String paramAuthData = "";
		
		AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
		
		if(authInfo != null) {
			paramAuthData = authInfo.getType();
		}
		
		resultStr = captionsDataService.newMoveDataTable(paramAuthData);
        
		return resultStr;
	}
	
	/**
	 * createRabbitmqQueue.
	 * 
	 * @author mattmks
	 * @param String
	 * @return Boolean
	 */
	@PostMapping("createRabbitmqQueue")
	public Boolean createRabbitmqQueue(@RequestParam(value = "randomTargetName")String randomTargetName) {
		
		Boolean result = Boolean.FALSE;
		
		logger.info("[DataRestController] [getQueueName] [randomTargetName] ====> {}", randomTargetName);
		
		result = ytnUtilServiceImpl.createRabbitmqQueue(randomTargetName);
		logger.info("[DataRestController] [getQueueName] [result] ====> {}", result);
		
		return result;
	}
	
	
	/**
	 * deleteTargetQueue.
	 * 
	 * @author mattmk
	 * @param String
	 * @return
	 */
	@PostMapping("deleteTargetQueue")
	public void deleteTargetQueue(@RequestParam(value = "queueName")String queueName) {
		
		Boolean result = Boolean.FALSE;
		
		
		result = ytnUtilServiceImpl.deleteTargetQueue(queueName);
		
		if(result) {
			logger.info("[DataRestController] [deleteTargetQueue] [SUCCESS] ====> {}", queueName);
		}else {
			logger.error("[DataRestController] [deleteTargetQueue] [FAIL] ====> {}", queueName);
		}
	}
	
	/**
	 * beforeCaptionDataInsert.
	 * 
	 * @author mattmk
	 * @param MultipartHttpServletRequest, String
	 * @return
	 */
	@PostMapping("beforeCaptionDataInsert")
	public void beforeCaptionDataInsert(@RequestParam(value = "targetFile") MultipartFile targetFile, @RequestParam(value = "targetDate")String targetDate) {
		
		// @RequestParam(value = "targetFile") MultipartFile targetFile
		// @RequestParam(value = "targetDate")String targetDate
		logger.info("[DataRestController] [beforeCaptionDataInsert]  ====> OKAY");
		
		System.out.println("MKTEST"); // 파일의 파라미터 이름
		System.out.println(targetDate); // 파일의 파라미터 이름
		
		Boolean result = Boolean.FALSE;
		
		System.out.println(targetFile); // 파일의 파라미터 이름
		System.out.println(targetFile.getName()); // 파일의 파라미터 이름
		System.out.println(targetFile.getSize()); // 파일의 사이즈
		System.out.println(targetFile.getOriginalFilename()); // 파일의 실제 이름
		
		
		
		String line;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(targetFile.getInputStream(), "UTF-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while((line=br.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		result = ytnUtilServiceImpl.deleteTargetQueue(queueName);
		
		if(result) {
			logger.info("[DataRestController] [deleteTargetQueue] [SUCCESS] ====> {}", queueName);
		}else {
			logger.error("[DataRestController] [deleteTargetQueue] [FAIL] ====> {}", queueName);
		}
		*/
	}
	
}
