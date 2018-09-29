package com.itedutips;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyPair;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.Date;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.SignedJWT;


public class Rsa256NimbusJWT {


		public static void main(String[] args) throws IOException, JOSEException, ParseException {
			
			System.out.println("RSA JWT generator..");

			// Load BouncyCastle as JCA provider
			Security.addProvider(new BouncyCastleProvider());

			// Parse the RSA key pair
			PEMParser pemParser = new PEMParser(new InputStreamReader(new FileInputStream("C:\\pemfiles\\privatekeynimbus.pem")));
			PEMKeyPair pemKeyPair = (PEMKeyPair)pemParser.readObject();

			
			// Convert to Java (JCA) format
			JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
			KeyPair keyPair = converter.getKeyPair(pemKeyPair);
			pemParser.close();
			
			// Get private + public EC key
			RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
			RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
			

			// Create RSA-signer with the private key
			JWSSigner signer = new RSASSASigner(privateKey);
		
			
			long iat = new Date().getTime() / 1000;
			long exp =iat + 60 * 60;
			String payLoad ="{\r\n" + 
					"  \"iss\"      : \"http://acme.com\",\r\n" + 
					"  \"sub\"      : \"Alice\",\r\n" + 
					"  \"email\"    : \"alice@wonderland.net\",\r\n" + 
					"  \"login_ip\" : \"172.16.254.1\", \r\n" + 
					"  \"exp\"      : 1471102267\r\n" + 
					"}";
			
			
			// Sign test
			JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.RS256), new Payload(payLoad));
			jwsObject.sign(new RSASSASigner(privateKey));
			// Serialise
			String compactJWS = jwsObject.serialize();
			System.out.println(compactJWS); 
					

			// On the consumer side, parse the JWS and verify its RSA signature
			SignedJWT signedJWT = SignedJWT.parse(compactJWS);
			JWSVerifier verifier = new RSASSAVerifier(publicKey);
			if(signedJWT.verify(verifier)){
				System.out.println(signedJWT.getJWTClaimsSet().getSubject());
				System.out.println(signedJWT.getJWTClaimsSet().getIssuer());
				System.out.println(signedJWT.getJWTClaimsSet().getClaim("email"));
				System.out.println(signedJWT.getJWTClaimsSet().getClaim("login_ip"));
				if(new Date().before(signedJWT.getJWTClaimsSet().getExpirationTime()))
				{
					System.out.println("Date before..");
				}
				else
				{
					System.out.println("Date after..");
				}
			}
			
		}

	}

