package com.geospot.entities;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Document
public class Feed {
	
	@Id
	private long feedId;
	
	private long bizId;
	
	private String userId;
	
	private long globalFeedNo;
	
	private String title;

	private double[] gpsLocation = new double[2];
	
	private String description;
	
	private String[] linkedImages;
	
	private String[] linkedAudios;
	
	private String[] linkedVideos;
	
	private String[] linkedURLs;
	
	private int[] categoryIDs;
	
	private Tag[] tags;
	
	private boolean isDeal;
	
	private String status;
	
	private String sourceDevice;
	
	private long numReportedAbuse;
	
	private String validityOfPost;
	
	private long numOfUpvotes;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getBizId() {
		return bizId;
	}

	public void setBizId(long bizId) {
		this.bizId = bizId;
	}
	
	public Tag[] getTags() {
		return tags;
	}

	public void setTags(Tag[] tags) {
		this.tags = tags;
	}

	public boolean isDeal() {
		return isDeal;
	}

	public void setDeal(boolean isDeal) {
		this.isDeal = isDeal;
	}

	public long getNumOfUpvotes() {
		return numOfUpvotes;
	}

	public void setNumOfUpvotes(long numOfUpvotes) {
		this.numOfUpvotes = numOfUpvotes;
	}

	@DateTimeFormat(iso=ISO.DATE_TIME)
	private Date dateOfPost;
	
	@DateTimeFormat(iso=ISO.DATE_TIME)
	private Date expiryDate;
	
	public long getGlobalFeedNo() {
		return globalFeedNo;
	}

	public void setGlobalFeedNo(long globalFeedNo) {
		this.globalFeedNo = globalFeedNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String[] getLinkedImages() {
		return linkedImages;
	}

	public void setLinkedImages(String[] linkedImages) {
		this.linkedImages = linkedImages;
	}

	public String[] getLinkedAudios() {
		return linkedAudios;
	}

	public void setLinkedAudios(String[] linkedAudios) {
		this.linkedAudios = linkedAudios;
	}

	public String[] getLinkedVideos() {
		return linkedVideos;
	}

	public void setLinkedVideos(String[] linkedVideos) {
		this.linkedVideos = linkedVideos;
	}

	public String[] getLinkedURLs() {
		return linkedURLs;
	}

	public void setLinkedURLs(String[] linkedURLs) {
		this.linkedURLs = linkedURLs;
	}

	public String getValidityOfPost() {
		return validityOfPost;
	}

	public void setValidityOfPost(String validityOfPost) {
		this.validityOfPost = validityOfPost;
	}

	public long getFeedId() {
		return feedId;
	}

	public void setFeedId(long feedId) {
		this.feedId = feedId;
	}

	public double getGpsLatitude() {
		return gpsLocation[0];
	}

	public void setGpsLatitude(double gpsLatitude) {
		gpsLocation[0] = gpsLatitude;
	}

	public double getGpsLongitude() {
		return gpsLocation[1];
	}

	public void setGpsLongitude(double gpsLongitude) {
		gpsLocation[1] = gpsLongitude;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String textContent) {
		this.description = textContent;
	}

	public long getNumOfUpVotes() {
		return numOfUpvotes;
	}

	public void setNumOfUpVotes(long numOfLikes) {
		this.numOfUpvotes = numOfLikes;
	}

	public Date getDateOfPost() {
		return dateOfPost;
	}

	public void setDateOfPost(Date dateOfPost) {
		this.dateOfPost = dateOfPost;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getSourceDevice() {
		return sourceDevice;
	}

	public void setSourceDevice(String sourceDevice) {
		this.sourceDevice = sourceDevice;
	}

	public long getNumReportedAbuse() {
		return numReportedAbuse;
	}

	public void setNumReportedAbuse(long reportedAbusedByNum) {
		this.numReportedAbuse = reportedAbusedByNum;
	}
	
	public int[] getCategoryIDs() {
		return categoryIDs;
	}

	public void setCategoryIDs(int[] categoryIDs) {
		this.categoryIDs = categoryIDs;
	}

}
