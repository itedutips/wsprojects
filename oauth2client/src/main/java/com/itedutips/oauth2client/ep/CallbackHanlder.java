package com.itedutips.oauth2client.ep;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.nimbusds.jwt.JWT;
import com.nimbusds.oauth2.sdk.*;
import com.nimbusds.oauth2.sdk.auth.ClientAuthentication;
import com.nimbusds.oauth2.sdk.auth.ClientSecretBasic;
import com.nimbusds.oauth2.sdk.auth.Secret;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.oauth2.sdk.token.BearerAccessToken;
import com.nimbusds.oauth2.sdk.token.RefreshToken;
import com.nimbusds.openid.connect.sdk.OIDCTokenResponse;
import com.nimbusds.openid.connect.sdk.OIDCTokenResponseParser;
import com.nimbusds.openid.connect.sdk.UserInfoRequest;
import com.nimbusds.openid.connect.sdk.UserInfoResponse;
import com.nimbusds.openid.connect.sdk.claims.UserInfo;

/*
 * Token request from this callback!
 * Demo code.. Need to add more logic
 */
@Path("clientcallback") 
public class CallbackHanlder {
	
	private final static Logger LOGGER = Logger.getLogger(CallbackHanlder.class.getName());
	String redirect_uri = "http://localhost:8080/oauth2client/oauth2/clientcallback";
	@GET
    @Produces(MediaType.TEXT_PLAIN)
    public String callbackHandler(@Context HttpServletRequest req,@Context HttpServletResponse res,@QueryParam("code") String authCode,@QueryParam("state") String state) {
		
		HttpSession session = req.getSession(false);
		if(session == null)
		{
			return "Error!";
		}
		
		String stateOrg = (String) session.getAttribute("state");
		if(stateOrg == null || stateOrg.isEmpty())
		{
			return "Error - original state not found!";
		}
		if(!stateOrg.equals(state))
		{
			return "Error - state varies!";
		}
		LOGGER.info("Custom: AuthCode is " + authCode);
		
		
		try {
			// Construct the code grant from the code obtained from the authz endpoint
			// and the original callback URI used at the authz endpoint
			AuthorizationCode code = new AuthorizationCode(authCode);
			URI callback_uri = new URI(redirect_uri);
			AuthorizationGrant codeGrant = new AuthorizationCodeGrant(code, callback_uri);

			// The credentials to authenticate the client at the token endpoint
			ClientID clientID = new ClientID("client_id_value");
			Secret clientSecret = new Secret("clientsecret_value");
			ClientAuthentication clientAuth = new ClientSecretBasic(clientID, clientSecret);

			// The token endpoint
			URI tokenEndpoint;
			
			tokenEndpoint = new URI("https://oauth2.googleapis.com/token");
			

			// Make the token request
			TokenRequest request = new TokenRequest(tokenEndpoint, clientAuth, codeGrant);

			TokenResponse tokenResponse;
			tokenResponse = OIDCTokenResponseParser.parse(request.toHTTPRequest().send());
			if (! tokenResponse.indicatesSuccess()) {
			    // We got an error response...
			    TokenErrorResponse errorResponse = tokenResponse.toErrorResponse();
			    return "Error in getting token!";
			}

			OIDCTokenResponse successResponse = (OIDCTokenResponse)tokenResponse.toSuccessResponse();

			// Get the ID and access token, the server may also return a refresh token
			JWT idToken = successResponse.getOIDCTokens().getIDToken();
			LOGGER.info("ID Token " + idToken.getParsedString()); 
			AccessToken accessToken = successResponse.getOIDCTokens().getAccessToken();
			LOGGER.info("Access Token " + accessToken.toString()); 
			RefreshToken refreshToken = successResponse.getOIDCTokens().getRefreshToken();
			LOGGER.info("Refresh Token " + refreshToken); 
			
			URI userInfoEndpoint;    // The UserInfoEndpoint of the OpenID provider
			/*
			 * To get user credentials with respect to access toek
			 */
			String userinfo_ep="https://www.googleapis.com/oauth2/v3/userinfo";
			BearerAccessToken token = (BearerAccessToken) accessToken; // The access token
			URI userInfoEndpt= new URI(userinfo_ep);
			// Make the request
			HTTPResponse httpResponse = new UserInfoRequest(userInfoEndpt, token)
			    .toHTTPRequest()
			    .send();

			// Parse the response
			UserInfoResponse userInfoResponse = UserInfoResponse.parse(httpResponse);

			if (! userInfoResponse.indicatesSuccess()) {
			    // The request failed, e.g. due to invalid or expired token
			    LOGGER.info(userInfoResponse.toErrorResponse().getErrorObject().getCode());
			    LOGGER.info(userInfoResponse.toErrorResponse().getErrorObject().getDescription());
			    return "Error in getting UserInfo!";
			}

			// Extract the claims
			UserInfo userInfo = userInfoResponse.toSuccessResponse().getUserInfo();
			LOGGER.info("Subject: " + userInfo.getSubject());
			LOGGER.info("Email: " + userInfo.getEmailAddress());
			LOGGER.info("Name: " + userInfo.getName());
		}  catch (URISyntaxException e) {
			
			e.printStackTrace();
		}  catch (ParseException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		
		return "Success!";
	}

}
