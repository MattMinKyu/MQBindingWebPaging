package com.ytn.mq.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MqBindingWebPagingApplication {

	public static void main(String[] args) {
		SpringApplication.run(MqBindingWebPagingApplication.class, args);
	}

}
