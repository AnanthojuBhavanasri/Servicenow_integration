package com.lorang.mif.servicenow.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "workflowDefinitionId", "businessItemIds", "businessItemType", "formProperties", "guestUserId",
		"sendNotification" })

public class WorkFlowInstances {

	//	@JsonProperty("workflowDefinitionId")
	private String workflowDefinitionId;

	//	@JsonProperty("businessItemIds")
	private List<String> businessItemIds = null;

	//	@JsonProperty("businessItemType")
	private String businessItemType;

	//	@JsonProperty("formProperties")
	private Map<String,String> formProperties;

	//	@JsonProperty("guestUserId")
	private String guestUserId;

	//	@JsonProperty("sendNotification")
	private Boolean sendNotification;
	@JsonIgnore
	
	private Map<String, Object> additionalProperties = new HashMap<>();

	@JsonProperty("workflowDefinitionId")
	public String getWorkflowDefinitionId() {
		return workflowDefinitionId;
	}

	@JsonProperty("workflowDefinitionId")
	public void setWorkflowDefinitionId(String workflowDefinitionId) {
		this.workflowDefinitionId = workflowDefinitionId;
	}

	@JsonProperty("businessItemIds")
	public List<String> getBusinessItemIds() {
		return businessItemIds;
	}

	@JsonProperty("businessItemIds")
	public void setBusinessItemIds(List<String> businessItemIds) {
		this.businessItemIds = businessItemIds;
	}

	@JsonProperty("businessItemType")
	public String getBusinessItemType() {
		return businessItemType;
	}

	@JsonProperty("businessItemType")
	public void setBusinessItemType(String businessItemType) {
		this.businessItemType = businessItemType;
	}

	@JsonProperty("formProperties")
	public Map<String, String> getFormProperties() {
		return formProperties;
	}

	@JsonProperty("formProperties")
	public void setFormProperties(Map<String, String> formProperties) {
		this.formProperties = formProperties;
	}

	@JsonProperty("guestUserId")
	public String getGuestUserId() {
		return guestUserId;
	}

	@JsonProperty("guestUserId")
	public void setGuestUserId(String guestUserId) {
		this.guestUserId = guestUserId;
	}

	@JsonProperty("sendNotification")
	public Boolean getSendNotification() {
		return sendNotification;
	}

	@JsonProperty("sendNotification")
	public void setSendNotification(Boolean sendNotification) {
		this.sendNotification = sendNotification;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}
}
