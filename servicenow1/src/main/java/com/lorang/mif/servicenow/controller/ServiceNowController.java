package com.lorang.mif.servicenow.controller;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lorang.mif.servicenow.constants.ServiceNowConstants;
import com.lorang.mif.servicenow.models.IncidentDetails;
import com.lorang.mif.servicenow.models.ServicenowFields;
import com.lorang.mif.servicenow.service.ServiceNowService;

@RestController
public class ServiceNowController {

	Logger log = LoggerFactory.getLogger(ServiceNowController.class);

	private  ServiceNowService serviceNowService;

	@Autowired
	public ServiceNowController(ServiceNowService serviceNowService) {
		this.serviceNowService = serviceNowService;
	}
	
	
	@PostMapping(value = "/createIncident")
	public String createIncident(@RequestBody ServicenowFields servicenowFields) {
		
		String response = null;
		try {
			response=serviceNowService.createIncident(servicenowFields);
			log.info("Returning response:{}",response);
		} catch (Exception e) {
			log.error("Exception due to {}", e.toString());
		}
		return response;
	}

	
	/*
	 * ServiceNow Hits the End Point
	 */
	@PostMapping(value = "/sendIncidentDetails")
	public String sendIncidentDetails(@RequestBody IncidentDetails snowDetails) {
				
		String response = null;
		try {
			log.info("Usageid:{}",snowDetails.getUsageid());
			log.info("state:{}",snowDetails.getState());
			log.info("impact:{}",snowDetails.getImpact());
			log.info("urgency:{}",snowDetails.getUrgency());
			
			response = serviceNowService.uploadIncidentDetails(snowDetails.getUsageid(), snowDetails.getIncident(),
					snowDetails.getState(), snowDetails.getImpact(),snowDetails.getUrgency(),ServiceNowConstants.INCIDENTUPDATE);
		
		} catch (Exception e) {
			log.error("Exception due to {}", e.toString());
		}
		return response;
	}


}
