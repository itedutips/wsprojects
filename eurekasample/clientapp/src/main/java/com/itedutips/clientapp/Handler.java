package com.itedutips.clientapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/rest/clientep")
public class Handler {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping
	public String hello()
	{
		String url="http://serverapp/rest/serverep";
		System.out.println("URL "+ url); 
		return restTemplate.getForObject(url, String.class);
	}
}
