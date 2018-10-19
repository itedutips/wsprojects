package com.itedutips.serverapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ServerappApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerappApplication.class, args);
	}
}
