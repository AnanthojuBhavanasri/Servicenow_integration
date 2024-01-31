package com.lorang.mif.servicenow.models;

public class IncidentDetails {
	
	int state;
	String incident;
	String usageid;
	String impact;
	String urgency;
	
	public String getIncident() {
		return incident;
	}
	public void setIncident(String incident) {
		this.incident = incident;
	}
	public String getUsageid() {
		return usageid;
	}
	public void setUsageid(String usageid) {
		this.usageid = usageid;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getImpact() {
		return impact;
	}
	public void setImpact(String impact) {
		this.impact = impact;
	}
	public String getUrgency() {
		return urgency;
	}
	public void setUrgency(String urgency) {
		this.urgency = urgency;
	}

}
