package com.itedutips.oauth2client.ep;

import java.io.IOException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.nimbusds.oauth2.sdk.ResponseType;
import com.nimbusds.oauth2.sdk.Scope;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.id.State;
import com.nimbusds.openid.connect.sdk.AuthenticationRequest;
import com.nimbusds.openid.connect.sdk.Nonce;

@Path("clientlogin")
public class MainHandler {
	
	private String authorization_uri ="https://accounts.google.com/o/oauth2/v2/auth";
	String redirect_uri ="http://localhost:8080/oauth2client/oauth2/clientcallback";
	
	/*
	 * Call for Authorization code!
	 */
	
	@GET
    @Produces(MediaType.TEXT_PLAIN)
    public String clientLogin(@Context HttpServletRequest req,@Context HttpServletResponse res,@QueryParam("client_id") String client_id) {
		
		
		String scope= "openid email profile";
		HttpSession session = req.getSession(true);
		
		// Generate random state string for pairing the response to the request
		State state = new State();
		session.setAttribute("state", state.toString()); 

		
		try {
			res.sendRedirect("https://accounts.google.com/o/oauth2/v2/auth?response_type=code&scope=" + scope + "&client_id=" + client_id + "&redirect_uri=" + redirect_uri + "&state=" + state.toString());
		} catch (IOException e) {
			
			e.printStackTrace();
		}

        return "Got it!";
    }
}
