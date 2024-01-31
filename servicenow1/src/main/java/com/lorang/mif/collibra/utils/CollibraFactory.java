package com.lorang.mif.collibra.utils;

import java.nio.charset.Charset;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.lorang.mif.servicenow.properties.CollibraProperties;

@Configuration
public class CollibraFactory {


	String standardCharsets = "ISO-8859-1";
	String authentic = "Basic ";
	int statuscode;

	private CollibraProperties collibraProperties;


	@Autowired
	public CollibraFactory(CollibraProperties collibraProperties) {
		this.collibraProperties = collibraProperties;
	}

	public ResponseEntity<String> httpRequests(String serverUrl, String params, HttpMethod methodTpe) {

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = null;
		String auth = collibraProperties.getUser() + ":" + collibraProperties.getPassword();
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName(standardCharsets)));
		String authHeader = authentic + new String(encodedAuth);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(HttpHeaders.AUTHORIZATION, authHeader);
		HttpEntity<String> entity = new HttpEntity<>(params, headers);
		response = restTemplate.exchange(serverUrl, methodTpe, entity, String.class);
		statuscode = response.getStatusCodeValue();
		if (statuscode == 200 || statuscode == 201) {
			return response;
		} else {
			return ResponseEntity.status(statuscode).body("Failed !! CollibraClientFactory Due to "+response.getBody());
		}
	}

	public String getResourceUri() {

		return collibraProperties.getUrl() + ":" + collibraProperties.getPort() + collibraProperties.getApipath();
	}

}
