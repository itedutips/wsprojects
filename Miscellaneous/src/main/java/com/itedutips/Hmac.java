package com.itedutips;

import java.security.SecureRandom;
import java.text.ParseException;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;

public class Hmac {

	public static void main(String[] args) throws JOSEException, ParseException {
		// Generate random 256-bit (32-byte) shared secret
		SecureRandom random = new SecureRandom();
		byte[] sharedSecret = new byte[32];	
	 
		random.nextBytes(sharedSecret);
		/*
		 * confidential value..
		 */
		System.out.println("Text [Byte Format] : " + sharedSecret.toString());
		

		// Create HMAC signer
		JWSSigner signer = new MACSigner(sharedSecret);
		String payLoad ="{\r\n" + 
				"  \"sub\"      : \"user-12345\",\r\n" + 
				"  \"email\"    : \"alice@wonderland.net\",\r\n" + 
				"  \"login_ip\" : \"172.16.254.1\", \r\n" + 
				"  \"exp\"      : 1471102267\r\n" + 
				"}";

		// Prepare JWS object wit the payload
		JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload(payLoad));

		// Apply the HMAC
		jwsObject.sign(signer);

		// To serialize to compact form, produces something like
		// eyJhbGciOiJIUzI1NiJ9.SGVsbG8sIHdvcmxkIQ.onO9Ihudz3WkiauDO2Uhyuz0Y18UASXlSc1eS0NkWyA
		String s = jwsObject.serialize();
		
		System.out.println(s);
		// To parse the JWS and verify it, e.g. on client-side
		jwsObject = JWSObject.parse(s);

		JWSVerifier verifier = new MACVerifier(sharedSecret);

		if(jwsObject.verify(verifier))
		{
			System.out.println(jwsObject.getPayload().toString());
			
		}

	}

}

