package com.itedutips.serverapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/serverep")
public class Handler {
	
	@GetMapping
	public String helloSrv()
	{
		return "Hello there!";
	}

}
