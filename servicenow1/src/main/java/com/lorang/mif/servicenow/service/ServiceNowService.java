package com.lorang.mif.servicenow.service;

/*
 * Author : Gowtham Vidiyala			
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lorang.mif.collibra.restclient.CollibraRest;
import com.lorang.mif.servicenow.constants.ServiceNowConstants;
import com.lorang.mif.servicenow.models.ServicenowFields;
import com.lorang.mif.servicenow.properties.ServicenowProperties;
import com.lorang.mif.servicenow.restclient.ServiceNowRestClient;

@Service
public class ServiceNowService {

	Logger log = LoggerFactory.getLogger(ServiceNowService.class);

	private  CollibraRest collibraRestClient;
	private  ServicenowProperties servicenowProperties;
	private  ServiceNowRestClient serviceNowRestClient;

	@Autowired
	public ServiceNowService(ServicenowProperties servicenowProperties, ServiceNowRestClient serviceNowRestClient,
			CollibraRest collibraRestClient) {
		this.servicenowProperties = servicenowProperties;
		this.serviceNowRestClient = serviceNowRestClient;
		this.collibraRestClient = collibraRestClient;
	}


	/*
	 * Creating Incident in Servicenow
	 */
	public String createIncident(ServicenowFields servicenowFields)  {

		String incidentResponse = null;
		JSONObject sendJson = new JSONObject();

		// Service Now Create Ticket URL


		sendJson.put("short_description", servicenowFields.getDataUsageId() );

		Map<String,String> description=new HashMap<>();
		description.put("Gcp Project Name", servicenowFields.getGcpProjectName());
		description.put("Gcp Role Type", servicenowFields.getGcpRoleType());
		description.put("Schema Name", servicenowFields.getSchemaName());
		description.put("DataUsage_Url", servicenowFields.getDataUsageURL());
		description.put("Requester Name", servicenowFields.getRequesterNames());
		description.put("Requester_Mail_Id", servicenowFields.getRequesterMailID());
		description.put("Data Element", servicenowFields.getDataElement());
		description.put("Catalog Name", servicenowFields.getCatalogName());
		description.put("Table Name", servicenowFields.getTableName());

		if(servicenowFields.getPrivileges()!=null) {
			description.put("Privileges", servicenowFields.getPrivileges().toString());
		}
		if(servicenowFields.getPrivileges()!=null) {
			description.put("Users", servicenowFields.getUsers().toString());
		}

		sendJson.put("description", description);
		sendJson.put("urgency", servicenowFields.getUrgencyValue());
		sendJson.put("impact", servicenowFields.getImpactValue());
		sendJson.put("caller_id", servicenowFields.getCallerName());

		String postIncidentUrl = servicenowProperties.getUrl() + "/api/now/table/incident";
		ResponseEntity<String> responses = serviceNowRestClient.sendData(postIncidentUrl, sendJson.toString());


		String responseBody = responses.getBody();
		log.info("response:{}", responseBody);

		JSONObject responseJsonBody;
		String dataUsageAssetId = servicenowFields.getDataUsageId();
		String incidentNumber = "";
		int incidentState = 1;
		try {
			responseJsonBody = new JSONObject(responseBody);

			// Asset Id
			dataUsageAssetId = servicenowFields.getDataUsageId();

			// Attribute Values
			incidentState = responseJsonBody.getJSONObject(ServiceNowConstants.RESULT)
					.getInt(ServiceNowConstants.INCIDENTSTATE);
			incidentNumber = responseJsonBody.getJSONObject(ServiceNowConstants.RESULT)
					.getString(ServiceNowConstants.INCIDENTNUMBER);

		} catch (final JSONException e) {
			log.error(String.format("JSONException Caught %s", e.toString()));
		}

		log.info("Incident Created with Number : {}", incidentNumber);

		String incidentStateValue = "";
		switch (incidentState) {
		case 1:
			incidentStateValue = ServiceNowConstants.NEW;
			break;
			// IN PROGRESS
		case 2:
			incidentStateValue = ServiceNowConstants.INPROGRESS;
			break;
			// ON HOLD
		case 3:
			incidentStateValue = ServiceNowConstants.ONHOLD;
			break;
			// RESOLVED
		case 6:
			incidentStateValue = ServiceNowConstants.RESOLVED;
			break;
			// CLOSED
		case 7:
			incidentStateValue = ServiceNowConstants.CLOSED;
			break;
			// CANCELED
		case 8:
			incidentStateValue = ServiceNowConstants.CANCELLED;
			break;
		default:
			log.info(incidentStateValue);
		}

		incidentResponse = dataUsageAssetId + ',' + incidentNumber + ',' + incidentStateValue;
		return incidentResponse;

	}



	/*
	 * Uploading Incident Details
	 */
	public String uploadIncidentDetails(String dataUsageAssetId, String incidentNumber,int incidentState,String impact,
			String urgency,String condition) throws IOException {
		String incidentStateValue = "";
		String rp = null;

		switch (incidentState) {
		case 1:
			incidentStateValue = ServiceNowConstants.NEW;
			break;
		case 2:
			incidentStateValue = ServiceNowConstants.INPROGRESS;
			break;
		case 3:
			incidentStateValue = ServiceNowConstants.ONHOLD;
			break;
		case 6:
			incidentStateValue = ServiceNowConstants.RESOLVED;
			break;
		case 7:
			incidentStateValue = ServiceNowConstants.CLOSED;
			break;
		case 8:
			incidentStateValue = ServiceNowConstants.CANCELLED;
			break;
		default:
			log.info(incidentStateValue);
		}

		if (condition.equals(ServiceNowConstants.INCIDENTCREATE)) {
			dataUsageAttributeCreate(incidentNumber, servicenowProperties.getNumber(), incidentStateValue,servicenowProperties.getStatus(), dataUsageAssetId);
		} else {
			rp = startWorkFlowInstances(dataUsageAssetId, incidentStateValue,impact,urgency, servicenowProperties.getWorkflow());
		}

		return rp;
	}



	/*
	 * Adding Attributes in Collbra
	 */
	private void dataUsageAttributeCreate(String incidentNumberValue, String incidentNumberId,
			String incidentStateValue, String incidentStateId, String dataUsageAssetId) throws IOException {

		try {
			// execute the request
			final ResponseEntity<String> response = collibraRestClient.sendDataCollibra(incidentNumberValue,
					incidentNumberId, incidentStateValue, incidentStateId, dataUsageAssetId);

			final int statusCode = response.getStatusCodeValue();

			if (statusCode == 201) {
				log.info("Successfully added Attributes :{}  & {} ,to AssetId :{}", incidentNumberValue,
						incidentStateValue, dataUsageAssetId);
			} else {
				log.info("Attribute : {} & {} , in AssetId : {} , Not Added", incidentNumberValue, incidentStateValue,
						dataUsageAssetId);

				throw new IOException("Upload failed to create Incident Details to Collibra due to" + statusCode);
			}
		} catch (final Exception e) {
			log.error(e.toString());
		}
	}


	/*
	 * Invokes Workflow with Status Change
	 */
	public String startWorkFlowInstances(String workflowAssetLevelId, String incidentStatus,String impact,String urgency,
			String workflowname) {

		String response = null;

		final List<String> businessTermList = new ArrayList<>();
		businessTermList.add(workflowAssetLevelId);

		final Map<String, String> formProperties = new HashMap<>();
		formProperties.put("datausageid", workflowAssetLevelId);
		formProperties.put("status", incidentStatus);
		formProperties.put("impact", impact);
		formProperties.put("urgency", urgency);

		try {
			response = collibraRestClient.startWorkFlowInstances(workflowname, ServiceNowConstants.ASSET,
					businessTermList, formProperties);
		} catch (final Exception e) {
			log.error("IOException Caught {}", e.toString());
		}
		return response;
	}
}
