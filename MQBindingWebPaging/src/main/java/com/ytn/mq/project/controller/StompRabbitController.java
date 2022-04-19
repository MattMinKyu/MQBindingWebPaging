package com.ytn.mq.project.controller;

import java.time.LocalDateTime;

import javax.websocket.OnMessage;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.ytn.mq.project.dto.ChatDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class StompRabbitController {

    private final RabbitTemplate template;

    private final static String CHAT_EXCHANGE_NAME = "chat.exchange";
    private final static String CHAT_QUEUE_NAME = "chat.queue";
    
    @MessageMapping("chat.enter")
    public void enter(ChatDto chatDto) {
        chatDto.setMessage("�����ϼ̽��ϴ�.");
        chatDto.setRegDate(LocalDateTime.now());

        // exchange
        template.convertAndSend(CHAT_EXCHANGE_NAME, "mattmk.routing.key", chatDto);
        // template.convertAndSend("room." + chatRoomId, chat); //queue
        // template.convertAndSend("amq.topic", "room." + chatRoomId, chat); //topic
    }


    @MessageMapping("chat.message")
    public void send(ChatDto chatDto) {
        chatDto.setRegDate(LocalDateTime.now());

        template.convertAndSend(CHAT_EXCHANGE_NAME, "mattmk.routing.key", chatDto);
        //template.convertAndSend( "room." + chatRoomId, chat);
        //template.convertAndSend("amq.topic", "room." + chatRoomId, chat);
    }

    // receiver()�� �ܼ��� ť�� ���� �޼����� �Һ� �Ѵ�. (����� ����� �뵵)
   // @RabbitListener(queues = CHAT_QUEUE_NAME)
    //public void receive(ChatDto chatDto) {
    	//log.info("chatDto.getMessage() = {}",chatDto.getMessage());
    //}
    
}
