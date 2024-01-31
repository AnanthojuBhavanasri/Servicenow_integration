package com.lorang.mif.servicenow.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@ConfigurationProperties("collibra")
public class CollibraProperties {
	
	  private String url;
	  private String port;
	  private String user;
	  private String password;
	  private String apipath;
	  
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getApipath() {
		return apipath;
	}
	public void setApipath(String apipath) {
		this.apipath = apipath;
	}
	  
}
