package com.lorang.mif.servicenow.restclient;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import com.lorang.mif.servicenow.utils.ServicenowClientFactory;

@Configuration
public class ServiceNowRestClient {

	Logger log = LoggerFactory.getLogger(ServiceNowRestClient.class);

	private final ServicenowClientFactory servicenowClientFactory;

	@Autowired
	public ServiceNowRestClient(ServicenowClientFactory servicenowClientFactory) {
		this.servicenowClientFactory = servicenowClientFactory;
	}

	public ResponseEntity<String> sendData(String url, String sendJson) {

		ResponseEntity<String> responseBody = null;
		responseBody = servicenowClientFactory.httpRequests(url, sendJson);
		return responseBody;
	}
	
}
