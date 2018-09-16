package com.itedutips.oauth2provider.model;

import java.util.Date;

public class OpenIDSession {
	
	private String client_id;
	private String username;
	private String email;
	private String preferred_name;
	private String id_token;
	private long token_expiry_time; //custom implementation- in millisecond(to verify)
	private long session_creationTime;
	
	
	public OpenIDSession()
	{
		
	}
	
	public long getToken_expiry_time() {
		return token_expiry_time;
	}
	public void setToken_expiry_time(long token_expiry_time) { //in milliseconds 
		
		this.token_expiry_time = token_expiry_time;
	}
	public long getSession_creationTime() {
		return session_creationTime;
	}
	public void setSession_creationTime() {
		Date creationTime = new Date();
		this.session_creationTime = creationTime.getTime();
		
	}
	public boolean isExpired()
	{
		long currentTime =new Date().getTime() - getSession_creationTime();
		
		if(currentTime > token_expiry_time)
		{
			return true;
		}
		return false;
	}
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPreferred_name() {
		return preferred_name;
	}
	public void setPreferred_name(String preferred_name) {
		this.preferred_name = preferred_name;
	}
	public String getId_token() {
		return id_token;
	}
	public void setId_token(String id_token) {
		this.id_token = id_token;
	}
	
	
	

}
