package com.ytn.mq.project.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ytn.mq.project.service.CaptionsDataService;
import com.ytn.mq.project.serviceImpl.YtnUtilServiceImpl;
import com.ytn.mq.project.vo.CaptionsRbMqVo;


@Component
public class CustomMessageListener {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomMessageListener.class);
	
	private CaptionsDataService captionsDataService;
	
	private YtnUtilServiceImpl ytnUtilServiceImpl;
	
	private static StringBuffer stringBuffer = new StringBuffer();
	
	public CustomMessageListener(CaptionsDataService captionsDataService, YtnUtilServiceImpl ytnUtilServiceImpl) {
		this.captionsDataService = captionsDataService;
		this.ytnUtilServiceImpl = ytnUtilServiceImpl;
	}
	
	@SuppressWarnings("deprecation")
	@RabbitListener(queues = "dtvcc_global3")
	//@RabbitListener(queues = "chat.queue")
    public void processMessage(final CaptionsRbMqVo captionsRbMqVo) {
		
		if(captionsRbMqVo == null) {
			logger.error("[CustomMessageListener] [processMessage] [captionsRbMqVo] [NULL] ====> {}", captionsRbMqVo);
			return;
		}else if(StringUtils.isEmpty(captionsRbMqVo.getData()) || StringUtils.isEmpty(captionsRbMqVo.getDateTime())) {
			logger.error("[CustomMessageListener] [processMessage] [captionsRbMqVo] [isEmpty] ====> {}", captionsRbMqVo);
			return;
		}
		
		stringBuffer.append(captionsRbMqVo.getData());
		
		int resultReturnInt = 0;
		
		if(ytnUtilServiceImpl.getBufferCaptionsData(stringBuffer.toString())) {
			
			String strTemp = stringBuffer.toString();
			strTemp = ytnUtilServiceImpl.AddCrLfAfterDefinedStr(strTemp);
			captionsRbMqVo.setData(strTemp);
			
			try {
				resultReturnInt = captionsDataService.saveCaptionData(captionsRbMqVo);
				//logger.error("[CustomMessageListener] [saveCaptionData] [Exception] [resultReturnInt] [1] ====> {}", resultReturnInt);
			}catch (Exception e) {
				// TODO: handle exception
				//e.printStackTrace();
				//logger.error("[CustomMessageListener] [saveCaptionData] [Exception] ====> {}", e);
				logger.error("[CustomMessageListener] [processMessage] [insert] [Exception] [captionsRbMqVo] ====> {}", captionsRbMqVo);
				//logger.error("[CustomMessageListener] [saveCaptionData] [Exception] [resultReturnInt] [2] ====> {}", resultReturnInt);
				return;
			}
			
			//logger.error("[CustomMessageListener] [saveCaptionData] [Exception] [resultReturnInt] ====> [3] {}", resultReturnInt);
			
			if(resultReturnInt > 0) {
				//logger.error("[CustomMessageListener] [saveCaptionData] [Exception] [resultReturnInt] ====> [4] {}", resultReturnInt);
				stringBuffer.setLength(0);
			}
		}
    }
}
