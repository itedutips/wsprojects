package com.itedutips.oauth2provider;

import java.util.Map;

import com.itedutips.oauth2provider.model.InMemoryObject;
import com.itedutips.oauth2provider.model.OpenIDSession;

public class MainClass {

	public static void main(String[] args) {
		
		System.out.println("Initiazing..");
		Map<String,OpenIDSession> obj = InMemoryObject.getMapInstance();
		OpenIDSession openIDSession = new OpenIDSession();
		openIDSession.setClient_id("acme");
		openIDSession.setUsername("George");
		openIDSession.setSession_creationTime();
		openIDSession.setToken_expiry_time(20000); //To verify
		obj.put("acme:George", openIDSession);
		System.out.println("Reading...");
		OpenIDSession session = obj.get("acme:George");
		System.out.println(session.getUsername()); 

	}

}
