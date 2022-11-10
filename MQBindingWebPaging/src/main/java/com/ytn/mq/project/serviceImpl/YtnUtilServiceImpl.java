package com.ytn.mq.project.serviceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.ytn.mq.project.dao.CaptionsDataDao;
import com.ytn.mq.project.dto.CaptionsDataDto;
import com.ytn.mq.project.vo.PageingVo;
import com.ytn.mq.project.vo.RabbitmqQueueListVo;

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
	
	private static final String CHAT_EXCHANGE_NAME = "DTVCC";
    
    private static final String ROUTING_KEY = "ytn.dtvcc.data";
    
	@Value("${spring.rabbitmq.host}")
    private String host;
	
	@Value("${spring.rabbitmq.port}")
    private String port;
    
    @Value("${spring.rabbitmq.username}")
    private String username;
    
    @Value("${spring.rabbitmq.password}")
    private String password;
    
    private final String apiQueueListPatten = "/api/queues";
    
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
	
	/**
	 * createRabbitmqQueue.
	 * 
	 * @author mattmk
	 * @param String
	 * @return Boolean
	 */
	@SuppressWarnings("deprecation")
	public Boolean createRabbitmqQueue(String randomTargetNameParam) {
		Boolean returnResult = Boolean.FALSE;
		
		if(StringUtils.isEmpty(randomTargetNameParam)) {
			return returnResult;
		}
		
		Connection connection = null;
		Channel channel = null;
		connection = this.createConnectionRabbitmq(connection);
		
		if(connection == null) {
			return returnResult;
		}
		
		channel = this.createQueueRabbitmq(connection, channel, randomTargetNameParam);
		
		if(channel == null) {
			return returnResult;
		}
		
		this.deleteChannel(channel);
		this.deleteConnection(connection);
		
		returnResult = Boolean.TRUE;
		
        return returnResult;
	}
	
	
	/**
	 * deleteQueueListExcute.
	 * 
	 * @author mattmk
	 * @param
	 * @return
	 */
	public void deleteQueueListExcute() {
		RestTemplate restTemplate = new RestTemplate();
		
		String apiCallUrl = "http://"+host+":"+port+apiQueueListPatten; 

		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth(username, password);

		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		
		List<RabbitmqQueueListVo> rabbitmqQueueList = new ArrayList<RabbitmqQueueListVo>();
		
		try {
			ResponseEntity<List<RabbitmqQueueListVo>> responseEntity = restTemplate.exchange(apiCallUrl, HttpMethod.GET, request,  new ParameterizedTypeReference<List<RabbitmqQueueListVo>>() {});
			rabbitmqQueueList = responseEntity.getBody();
		}catch (Exception e) {
			logger.error("[YtnUtilServiceImpl] [deleteQueueListExcute] [Exception] ====> {}", e);
			e.printStackTrace();
			return;
		}
		
		Connection connection = null;
		connection = this.createConnectionRabbitmq(connection);
		
		if(connection == null) {
			return;
		}
		
		for(RabbitmqQueueListVo rabbitmqQueueListVo : rabbitmqQueueList) {
			
			if(rabbitmqQueueListVo.getName().indexOf("ytn_stomp_connection") == 0
					&& rabbitmqQueueListVo.getMessagesReady() > 50) {
				logger.info("[YtnUtilServiceImpl][deleteQueueListExcute][rabbitmqQueueListVo] =====> {}", rabbitmqQueueListVo.toString());
				
				Channel channel = null;
				this.deleteQueueRabbitmq(connection, channel, rabbitmqQueueListVo.getName());
			}
		}
		
		this.deleteConnection(connection);
	}
	
	
	/**
	 * createConnectionRabbitmq.
	 * 
	 * @author mattmk
	 * @param Connection
	 * @return Connection
	 */
	public Connection createConnectionRabbitmq(Connection connection) {
		ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(5672);
        factory.setUsername(username);
        factory.setPassword(password);
        
		try {
			connection = factory.newConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("[YtnUtilServiceImpl] [createConnectionRabbitmq] [IOException] ====> {}", e);
			e.printStackTrace();
			return null;
		} catch (TimeoutException e) {
			logger.error("[YtnUtilServiceImpl] [createConnectionRabbitmq] [TimeoutException] ====> {}", e);
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
			
		return connection;
	}
	
	/**
	 * createQueueRabbitmq.
	 * 
	 * @author mattmk
	 * @param Connection, Channel, String
	 * @return Connection
	 */
	@SuppressWarnings("deprecation")
	public Channel createQueueRabbitmq(Connection connection, Channel channel, String randomTargetNameParam) {
		
		if(StringUtils.isEmpty(randomTargetNameParam)) {
			return null;
		}
		
		try {
			channel = connection.createChannel();
			
			// Exchange 선언 
            channel.exchangeDeclare(CHAT_EXCHANGE_NAME, BuiltinExchangeType.TOPIC, true);
            
			channel.queueDeclare(randomTargetNameParam,true,false,false,null);
			channel.queueBind(randomTargetNameParam, CHAT_EXCHANGE_NAME, ROUTING_KEY);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("[YtnUtilServiceImpl] [createQueueRabbitmq] [IOException] ====> {}", e);
			e.printStackTrace();
			return null;
		}
		
		return channel;
	}
	
	
	/**
	 * deleteQueueRabbitmq.
	 * 
	 * @author mattmk
	 * @param Connection, Channel, String
	 * @return Boolean
	 */
	@SuppressWarnings("deprecation")
	public Boolean deleteTargetQueue(String queueNameParam) {
		Boolean result = Boolean.FALSE;
		
		if(StringUtils.isEmpty(queueNameParam)) {
			return result;
		}
		
		Connection connection = null;
		Channel channel = null;
		
		connection = this.createConnectionRabbitmq(connection);
		
		if(connection == null) {
			return result;
		}
		
		result = this.deleteQueueRabbitmq(connection, channel, queueNameParam);
		
		this.deleteConnection(connection);
		
		return result;
	}
	
	/**
	 * deleteQueueRabbitmq.
	 * 
	 * @author mattmk
	 * @param Connection, Channel, String
	 * @return Boolean
	 */
	@SuppressWarnings("deprecation")
	public Boolean deleteQueueRabbitmq(Connection connection, Channel channel, String queueName) {
		Boolean result = Boolean.FALSE;
		
		if(StringUtils.isEmpty(queueName)) {
			return result;
		}
		
		try {
			channel = connection.createChannel();
			
			// Exchange 선언 
			channel.queueDelete(queueName);
			result = Boolean.TRUE;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("[YtnUtilServiceImpl] [deleteQueueRabbitmq] [IOException] ====> {}", e);
			e.printStackTrace();
		}
		
		this.deleteChannel(channel);
		
		return result;
	}
	
	/**
	 * deleteConnection.
	 * 
	 * @author mattmk
	 * @param Connection
	 * @return
	 */
	public void deleteConnection(Connection connection) {
		 try {
			connection.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * deleteChannel.
	 * 
	 * @author mattmk
	 * @param Channel
	 * @return
	 */
	public void deleteChannel(Channel channel) {
		try {
			channel.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("[YtnUtilServiceImpl] [deleteChannel] [IOException] ====> {}", e);
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			logger.error("[YtnUtilServiceImpl] [deleteChannel] [TimeoutException] ====> {}", e);
			e.printStackTrace();
		}
	}
}
