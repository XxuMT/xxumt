package com.example.xxumt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class XxumtApplication {

	public static void main(String[] args) {
		SpringApplication.run(XxumtApplication.class, args);
	}

}
