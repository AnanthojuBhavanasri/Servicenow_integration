package com.lorang.mif.servicenow.utils;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.lorang.mif.servicenow.properties.ServicenowProperties;

@Configuration
public class ServicenowClientFactory {

	private final ServicenowProperties servicenowProperties;

	@Autowired
	public ServicenowClientFactory(ServicenowProperties servicenowProperties) {
		this.servicenowProperties = servicenowProperties;
	}

	
	public ResponseEntity<String> httpRequests(String url, String reqJson) {
		int statuscode;
		final RestTemplate restTemplate = new RestTemplate();
		final String auth = servicenowProperties.getUsername() + ":" + servicenowProperties.getPassword();
		final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("ISO-8859-1")));
		final String authHeader = "Basic " + new String(encodedAuth);
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set(HttpHeaders.AUTHORIZATION, authHeader);
		final HttpEntity<String> entity = new HttpEntity<>(reqJson, headers);
		final ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		statuscode = response.getStatusCodeValue();
		if (statuscode == 201 || statuscode == 200) {
			return response;
		} else {
			return ResponseEntity.status(statuscode)
					.body("Failed !! ServicenowClientFactory Due to " + response.getBody());
		}

	}
}
