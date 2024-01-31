package com.lorang.mif.servicenow.models;

import java.util.List;

public class ServicenowFields {

	String dataUsageId;
	String gcpProjectName;
	String gcpRoleType;
	String requesterNames;
	String requesterMailID;
	String dataUsageURL;
	String impactValue;
	String urgencyValue;
	String callerName;
	String description;
	String dataElement;
	String catalogName;
	String schemaName;
	String tableName;
	List<String> privileges;
	List<String> users;
	
	public String getDataUsageId() {
		return dataUsageId;
	}
	public void setDataUsageId(String dataUsageId) {
		this.dataUsageId = dataUsageId;
	}
	public String getGcpProjectName() {
		return gcpProjectName;
	}
	public void setGcpProjectName(String gcpProjectName) {
		this.gcpProjectName = gcpProjectName;
	}
	public String getGcpRoleType() {
		return gcpRoleType;
	}
	public void setGcpRoleType(String gcpRoleType) {
		this.gcpRoleType = gcpRoleType;
	}
	public String getSchemaName() {
		return schemaName;
	}
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	public String getRequesterNames() {
		return requesterNames;
	}
	public void setRequesterNames(String requesterNames) {
		this.requesterNames = requesterNames;
	}
	public String getRequesterMailID() {
		return requesterMailID;
	}
	public void setRequesterMailID(String requesterMailID) {
		this.requesterMailID = requesterMailID;
	}
	public String getDataUsageURL() {
		return dataUsageURL;
	}
	public void setDataUsageURL(String dataUsageURL) {
		this.dataUsageURL = dataUsageURL;
	}
	public String getImpactValue() {
		return impactValue;
	}
	public void setImpactValue(String impactValue) {
		this.impactValue = impactValue;
	}
	public String getUrgencyValue() {
		return urgencyValue;
	}
	public void setUrgencyValue(String urgencyValue) {
		this.urgencyValue = urgencyValue;
	}
	public String getCallerName() {
		return callerName;
	}
	public void setCallerName(String callerName) {
		this.callerName = callerName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDataElement() {
		return dataElement;
	}
	public void setDataElement(String dataElement) {
		this.dataElement = dataElement;
	}
	public String getCatalogName() {
		return catalogName;
	}
	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<String> getPrivileges() {
		return privileges;
	}
	public void setPrivileges(List<String> privileges) {
		this.privileges = privileges;
	}
	public List<String> getUsers() {
		return users;
	}
	public void setUsers(List<String> users) {
		this.users = users;
	}
	


	
}
