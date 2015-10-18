package com.geospot.models;

import com.fasterxml.jackson.databind.JsonNode;

public class UserInfo {
	private String userId;
	
	private long dynamicFeedId;
	
	private long staticFeedId;
	
	private String topicAtLastSync;
	
	private String topicNow;
	
	private double gpsLatitudeAtLastSync;
	
	private double gpsLongitudeAtLastSync;
	
	private double gpsLatitudeNow;
	
	private double gpsLongitudeNow;
	
	private String sourceDevice;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public long getDynamicFeedId() {
		return dynamicFeedId;
	}

	public void setDynamicFeedId(long dynamicFeedId) {
		this.dynamicFeedId = dynamicFeedId;
	}

	public long getStaticFeedId() {
		return staticFeedId;
	}

	public void setStaticFeedId(long staticFeedId) {
		this.staticFeedId = staticFeedId;
	}

	public String getTopicAtLastSync() {
		return topicAtLastSync;
	}

	public void setTopicAtLastSync(String topicAtLastSync) {
		this.topicAtLastSync = topicAtLastSync;
	}

	public String getTopicNow() {
		return topicNow;
	}

	public void setTopicNow(String topicNow) {
		this.topicNow = topicNow;
	}

	public double getGpsLatitudeAtLastSync() {
		return gpsLatitudeAtLastSync;
	}

	public void setGpsLatitudeAtLastSync(double gpsLatitudeAtLastSync) {
		this.gpsLatitudeAtLastSync = gpsLatitudeAtLastSync;
	}

	public double getGpsLongitudeAtLastSync() {
		return gpsLongitudeAtLastSync;
	}

	public void setGpsLongitudeAtLastSync(double gpsLongitudeAtLastSync) {
		this.gpsLongitudeAtLastSync = gpsLongitudeAtLastSync;
	}

	public double getGpsLatitudeNow() {
		return gpsLatitudeNow;
	}

	public void setGpsLatitudeNow(double gpsLatitudeNow) {
		this.gpsLatitudeNow = gpsLatitudeNow;
	}

	public double getGpsLongitudeNow() {
		return gpsLongitudeNow;
	}

	public void setGpsLongitudeNow(double gpsLongitudeNow) {
		this.gpsLongitudeNow = gpsLongitudeNow;
	}

	public String getSourceDevice() {
		return sourceDevice;
	}

	public void setSourceDevice(String sourceDevice) {
		this.sourceDevice = sourceDevice;
	}
	
	public static UserInfo getUserInfo(JsonNode userInfoJSON) throws Exception{
		if(userInfoJSON.get("userId") == null || userInfoJSON.get("staticFeedId") == null || userInfoJSON.get("dynamicFeedId") == null ||
				userInfoJSON.get("topicNow") == null || userInfoJSON.get("topicAtLastSync") == null || userInfoJSON.get("gpsLatitudeNow") == null ||
				userInfoJSON.get("gpsLongitudeNow") == null || userInfoJSON.get("gpsLatitudeAtLastSync") == null || userInfoJSON.get("gpsLongitudeAtLastSync") == null ||userInfoJSON.get("sourceDevice") == null )
			throw new Exception("Invalid Arguments");
		UserInfo obj = new UserInfo();
		obj.setUserId(userInfoJSON.get("userId").asText(""));
		obj.setStaticFeedId(userInfoJSON.get("staticFeedId").asLong(0));
		obj.setStaticFeedId(userInfoJSON.get("dynamicFeedId").asLong(0));
		obj.setTopicNow(userInfoJSON.get("topicNow").asText(""));
		obj.setTopicAtLastSync(userInfoJSON.get("topicAtLastSync").asText(""));
		obj.setGpsLatitudeNow(userInfoJSON.get("gpsLatitudeNow").asDouble(0.0));
		obj.setGpsLongitudeNow(userInfoJSON.get("gpsLongitudeNow").asDouble(0.0));
		obj.setGpsLatitudeAtLastSync(userInfoJSON.get("gpsLatitudeAtLastSync").asDouble(0.0));
		obj.setGpsLongitudeAtLastSync(userInfoJSON.get("gpsLongitudeAtLastSync").asDouble(0.0));
		obj.setSourceDevice(userInfoJSON.get("sourceDevice").asText(""));
		return obj;
	}
}
