package com.ytn.mq.project.util;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    private static final String topicExchange = "sample.exchange2";

    private final RabbitTemplate rabbitTemplate;

    public Runner(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
	}
    
    /*
    @Override
    public void run(String... args) {
        System.out.println("Sending message...");
        //rabbitTemplate.convertAndSend(topicExchange, "foo.bar.baz", "Hello Message!");
    }
	*/
}