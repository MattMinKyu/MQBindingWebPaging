package com.ytn.mq.project.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ytn.mq.project.dao.CaptionsDataDao;
import com.ytn.mq.project.dto.CaptionsDataDto;
import com.ytn.mq.project.vo.PageingVo;

/**
 * YtnUtilServiceImpl
 * 
 * @author mattmk
 *
 */

@Service
public class YtnUtilServiceImpl{
	
	private static final Logger logger = LoggerFactory.getLogger(YtnUtilServiceImpl.class);
	
	private CaptionsDataDao captionsDataDao;
	
	private static final String strCrLf = "\r\n";                   // CR/LF 문자열 정의
	
	public YtnUtilServiceImpl(CaptionsDataDao captionsDataDao) {
		this.captionsDataDao = captionsDataDao;
	}
	
	
	/**
	 * getCaptionsExcelData.
	 * 
	 * @author mattmk
	 * @param PageingVo pageingVo
	 * @return String
	 */
	public String getCaptionsExcelData(PageingVo pageingVo) {
		String result = "";
		
		if(pageingVo == null) {
			return result;
		}
		
		ObjectMapper mapper = new ObjectMapper();
		List<CaptionsDataDto> captionsDataList = new ArrayList<CaptionsDataDto>();
		captionsDataList = captionsDataDao.selectCaptionsInfoListForExcel(pageingVo);
		
		if(captionsDataList.size() == 0) {
			try {
				result = mapper.writeValueAsString("");
			}catch(Exception e){
				logger.error("[YtnUtilServiceImpl] [getCaptiasdonsExcelData] [captionsDataList.size()] [Exception] ====> {}", e);
				e.printStackTrace();
			}
			
			return result;
		}
		
		StringBuffer stringBuffer = new StringBuffer();
		
		for(CaptionsDataDto captionsDataDto : captionsDataList) {
			stringBuffer.append(captionsDataDto.getContext());
		}
		
		String temp = stringBuffer.toString();
		
		try {
			result = mapper.writeValueAsString(this.AddCrLfAfterDefinedStr(temp));
		}catch(Exception e){
			logger.error("[YtnUtilServiceImpl] [getCaptionsExcelData] [Exception] ====> {}", e);
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 입력 문자열의 특정 문자열 뒤에 CR/LF를 삽입한다. 단, 이미 있던 CR/LF는 제거한다.
	 * 
	 * @author mattmk
	 * @param String, String[]
	 * @return String
	 */
	@SuppressWarnings("deprecation")
	public String AddCrLfAfterDefinedStr(String strText)
    {
		String[] astrReplaceStr = new String[]{
	            "다.",       // 반갑습니다.
	            "요.",       // 
	            "요!",       // 안녕하세요!
	            "요?",       // 확실해요?
	            "까?",       // 이게 최선입니까?
	            "가?"        // 죽고 난 뒤에 또 다른 삶은 없는가?
		};
		
        if(!StringUtils.isEmpty(strText))
        {
        	strText = this.RemoveCrLf(strText);

            for(String strReplace : astrReplaceStr)
            {
            	strText = strText.replace(strReplace, strReplace + strCrLf);
            }
        }

        return strText;
    }
	
	/**
	 * 문자열에서 CR/LF, CR, LF 문자를 제거한다.
	 * 
	 * @author mattmk
	 * @param String
	 * @return String
	 */
	public String RemoveCrLf(String str)
    {
		//return str.replace("\r\n", "").replace("\r", "").replace("\n", "").replace("\t", "").replace("  ", "");
		//return str.replaceAll("\r\n", "").replace("\\s", " ");
		return str.replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("\n", "").replaceAll("\t", "").replaceAll("  ", " ").replaceAll("\\s+", " ");
    }
	
	
	/**
	 * getCaptionsExcelData.
	 * 
	 * @author mattmk
	 * @param PageingVo pageingVo
	 * @return String
	 */
	public Boolean getBufferCaptionsData(String strData) {
		Boolean result = Boolean.FALSE;
		
		if(strData.contains("다.") || strData.contains("요.")
				|| strData.contains("요!") || strData.contains("요?")
				|| strData.contains("까?") || strData.contains("가?")
				|| strData.length() >= 3900) {
			
			result = Boolean.TRUE;
		}
		return result;
	}
}
