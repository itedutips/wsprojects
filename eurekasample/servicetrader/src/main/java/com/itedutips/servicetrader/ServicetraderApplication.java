package com.itedutips.servicetrader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class ServicetraderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicetraderApplication.class, args);
	}
}
