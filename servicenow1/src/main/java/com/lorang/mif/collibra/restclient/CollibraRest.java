package com.lorang.mif.collibra.restclient;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lorang.mif.collibra.utils.CollibraFactory;
import com.lorang.mif.servicenow.models.WorkFlowInstances;
import com.lorang.mif.servicenow.properties.CollibraProperties;

@Service
public class CollibraRest {

	Logger log = LoggerFactory.getLogger(CollibraRest.class);

	private final CollibraProperties collibraProperties;
	private final CollibraFactory collibraClientFactory;

	@Autowired
	public CollibraRest(CollibraProperties collibraProperties, CollibraFactory collibraClientFactory) {
		this.collibraProperties = collibraProperties;
		this.collibraClientFactory = collibraClientFactory;
	}

	public ResponseEntity<String> sendDataCollibra(String incidentNumberValue, String incidentNumberId,
			String incidentStateValue, String incidentStateId, String dataUsageAssetId) {

		final String dataUsageAttributeUrl = collibraProperties.getUrl() + ":" + collibraProperties.getPort()
		+ collibraProperties.getApipath() + "attributes/bulk";

		JSONArray jsonArray=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("assetId",dataUsageAssetId);
		jsonObject.put("typeId",incidentNumberId);
		jsonObject.put("value",incidentNumberValue);

		JSONObject jsonObject1=new JSONObject();
		jsonObject1.put("assetId",dataUsageAssetId);
		jsonObject1.put("typeId",incidentStateId);
		jsonObject1.put("value",incidentStateValue);

		jsonArray.put(0,jsonObject);
		jsonArray.put(1,jsonObject1);

		final String requestBody = jsonArray.toString();

		return collibraClientFactory.httpRequests(dataUsageAttributeUrl, requestBody, HttpMethod.POST);
	}

	/*
	 * Invokes the Workflow
	 */
	public String startWorkFlowInstances(String workflowname, String worflowLevel,
			List<String> workflowLevelId, Map<String, String> formProperties) {

		ResponseEntity<String> response = null;
		String wrkFlowId = null;
		try {

			final String workflowUrl = collibraClientFactory.getResourceUri() + "workflowInstances";

			final WorkFlowInstances workFlowInstances = new WorkFlowInstances();

			wrkFlowId = workflowDefinitionsByName(workflowname);
			workFlowInstances.setWorkflowDefinitionId(wrkFlowId);
			workFlowInstances.setBusinessItemIds(workflowLevelId);
			workFlowInstances.setBusinessItemType(worflowLevel);

			workFlowInstances.setFormProperties(formProperties);

			final ObjectMapper mapper = new ObjectMapper();
			final String resources = mapper.writeValueAsString(workFlowInstances);

			response = collibraClientFactory.httpRequests(workflowUrl, resources, HttpMethod.POST);

			 int statusCode = response.getStatusCodeValue();

			if (statusCode == 201) {
				log.info("Workflow Sucessfully Triggered");
			} else {
				log.error("Workflow Triggered with Error with status code :{}", statusCode);
			}

		} catch ( Exception e) {
			log.error(String.format("Caught with IOException:%s", e.toString()));
		}
		return "Workflow Sucessfully Triggered";
	}

	/*
	 * Gets Workflow Name
	 */
	private String workflowDefinitionsByName(String workflowName) {
		JSONObject wrkFlowObject = new JSONObject();
		String responseString = null;
		String wrkFlowId = null;
		int statuscode;

		try {
			final String wrkFlowUrl = collibraClientFactory.getResourceUri() + "workflowDefinitions?name="
					+ workflowName;

			final ResponseEntity<String> response = collibraClientFactory.httpRequests(wrkFlowUrl, null,
					HttpMethod.GET);
			statuscode = response.getStatusCodeValue();
			if (statuscode == 200) {
				responseString = response.getBody();
				wrkFlowObject = new JSONObject(responseString);
				final int total = wrkFlowObject.getInt("total");
				if (total == 0) {
					log.error("There is no workflow exists with this name:{}", workflowName);
				}
				final JSONArray resultsArray = (JSONArray) wrkFlowObject.get("results");
				final JSONObject serversObject = (JSONObject) resultsArray.get(0);
				wrkFlowId = serversObject.getString("id");
			} else {
				log.error("Failed in retriving workflow id for :{}", workflowName);
			}
		} catch (final HttpStatusCodeException e) {
			log.error(String.format("Exception caught due to:%s", e.toString()));

		}

		return wrkFlowId;
	}

//	public void attributeUpdate(String id, String assetId) {
//
//		try {
//			final String serverUrl = collibraProperties.getUrl() + ":" + collibraProperties.getPort()
//			+ collibraProperties.getApipath() + "/attributes/bulk";
//
//			final String inputjson = "[\r\n" + "  {\r\n" + "    \"assetId\": \"" + assetId + "\",\r\n"
//					+ "    \"typeId\": \"236a8485-2ae7-4d75-928e-bc7802f8bb5b\",\r\n" + "    \"value\": \"" + id
//					+ "\"\r\n" + "  }\r\n" + "]";
//
//			final ResponseEntity<String> response = collibraClientFactory.httpRequests(serverUrl, inputjson,
//					HttpMethod.POST);
//			final int statusCode = response.getStatusCodeValue();
//			if (statusCode == 201) {
//				log.info("id:{}", id);
//			} else {
//				log.warn("Id Already Existed in Collibra");
//			}
//		} catch (final CollibraRestClientException e) {
//			throw new CollibraRestClientException("updated attribute not found in collibra");
//		}
//	}
//



}
