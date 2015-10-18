package com.geospot.entities;

import java.util.Date;

import org.springframework.data.annotation.Id; 
import org.springframework.data.mongodb.core.mapping.Document; 
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;


@Document
public class SubBizInfo{
	
	@Id
	private long bizId;
	
	//For every Sub Biz there is a Master Biz Info
	private MasterBizInfo masterBizInfo;
	
	public MasterBizInfo getMasterBizInfo() {
		return masterBizInfo;
	}

	public void setMasterBizInfo(MasterBizInfo masterBizInfo) {
		this.masterBizInfo = masterBizInfo;
	}

	private String bizType;
	
	private String bizCode;
	
	private String bizName;
	
	@DateTimeFormat(iso=ISO.DATE_TIME)
	private Date bizOpenDate;
	
	private String bizStatus;
	
	private String bizDesc;
	
	private String bizSearchTags;
	
	private String bizStreetAddress;
	
	private String bizArea;
	
	private String bizCity;
	
	private String bizState;
	
	private String bizPinCode;
	
	private String bizPhone1;
	
	private String bizPhone2;
	
	private String bizPhone3;
	
	private String website;
	
	private String email1;
	
	private String email2;
	
	private boolean isOpen24Hrs;
	
	private String sunOpHourStartTime;
	
	private String sunOpHourEndTime;
	
	private String monOpHourStartTime;
	
	private String monOpHourEndTime;
	
	private String tueOpHourStartTime;
	
	private String tueOpHourEndTime;
	
	private String wedOpHourStartTime;
	
	private String wedOpHourEndTime;
	
	private String thuOpHourStartTime;
	
	private String thuOpHourEndTime;
	
	private String friOpHourStartTime;
	
	private String friOpHourEndTime;
	
	private String satOpHourStartTime;
	
	private String satOpHourEndTime;
	
	private boolean isHomeDeliveryAvlbl;
	
	private String deliveryWithinKM;
	
	private String bizAddrLatitude;
	
	private String bizAddrLongitude;

	public long getBizId() {
		return bizId;
	}

	public void setBizId(long seqId) {
		this.bizId = seqId;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getBizName() {
		return bizName;
	}

	public void setBizName(String bizName) {
		this.bizName = bizName;
	}

	public Date getBizOpenDate() {
		return bizOpenDate;
	}

	public void setBizOpenDate(Date bizOpenDate) {
		this.bizOpenDate = bizOpenDate;
	}

	public String getBizStatus() {
		return bizStatus;
	}

	public void setBizStatus(String bizStatus) {
		this.bizStatus = bizStatus;
	}

	public String getBizDesc() {
		return bizDesc;
	}

	public void setBizDesc(String bizDesc) {
		this.bizDesc = bizDesc;
	}

	public String getBizSearchTags() {
		return bizSearchTags;
	}

	public void setBizSearchTags(String bizSearchTags) {
		this.bizSearchTags = bizSearchTags;
	}

	public String getBizStreetAddress() {
		return bizStreetAddress;
	}

	public void setBizStreetAddress(String bizStreetAddress) {
		this.bizStreetAddress = bizStreetAddress;
	}

	public String getBizArea() {
		return bizArea;
	}

	public void setBizArea(String bizArea) {
		this.bizArea = bizArea;
	}

	public String getBizCity() {
		return bizCity;
	}

	public void setBizCity(String bizCity) {
		this.bizCity = bizCity;
	}

	public String getBizState() {
		return bizState;
	}

	public void setBizState(String bizState) {
		this.bizState = bizState;
	}

	public String getBizPinCode() {
		return bizPinCode;
	}

	public void setBizPinCode(String bizPinCode) {
		this.bizPinCode = bizPinCode;
	}

	public String getBizPhone1() {
		return bizPhone1;
	}

	public void setBizPhone1(String bizPhone1) {
		this.bizPhone1 = bizPhone1;
	}

	public String getBizPhone2() {
		return bizPhone2;
	}

	public void setBizPhone2(String bizPhone2) {
		this.bizPhone2 = bizPhone2;
	}

	public String getBizPhone3() {
		return bizPhone3;
	}

	public void setBizPhone3(String bizPhone3) {
		this.bizPhone3 = bizPhone3;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public boolean isOpen24Hrs() {
		return isOpen24Hrs;
	}

	public void setOpen24Hrs(boolean isOpen24Hrs) {
		this.isOpen24Hrs = isOpen24Hrs;
	}

	public String getSunOpHourStartTime() {
		return sunOpHourStartTime;
	}

	public void setSunOpHourStartTime(String sunOpHourStartTime) {
		this.sunOpHourStartTime = sunOpHourStartTime;
	}

	public String getSunOpHourEndTime() {
		return sunOpHourEndTime;
	}

	public void setSunOpHourEndTime(String sunOpHourEndTime) {
		this.sunOpHourEndTime = sunOpHourEndTime;
	}

	public String getMonOpHourStartTime() {
		return monOpHourStartTime;
	}

	public void setMonOpHourStartTime(String monOpHourStartTime) {
		this.monOpHourStartTime = monOpHourStartTime;
	}

	public String getMonOpHourEndTime() {
		return monOpHourEndTime;
	}

	public void setMonOpHourEndTime(String monOpHourEndTime) {
		this.monOpHourEndTime = monOpHourEndTime;
	}

	public String getTueOpHourStartTime() {
		return tueOpHourStartTime;
	}

	public void setTueOpHourStartTime(String tueOpHourStartTime) {
		this.tueOpHourStartTime = tueOpHourStartTime;
	}

	public String getTueOpHourEndTime() {
		return tueOpHourEndTime;
	}

	public void setTueOpHourEndTime(String tueOpHourEndTime) {
		this.tueOpHourEndTime = tueOpHourEndTime;
	}

	public String getWedOpHourStartTime() {
		return wedOpHourStartTime;
	}

	public void setWedOpHourStartTime(String wedOpHourStartTime) {
		this.wedOpHourStartTime = wedOpHourStartTime;
	}

	public String getWedOpHourEndTime() {
		return wedOpHourEndTime;
	}

	public void setWedOpHourEndTime(String wedOpHourEndTime) {
		this.wedOpHourEndTime = wedOpHourEndTime;
	}

	public String getThuOpHourStartTime() {
		return thuOpHourStartTime;
	}

	public void setThuOpHourStartTime(String thuOpHourStartTime) {
		this.thuOpHourStartTime = thuOpHourStartTime;
	}

	public String getThuOpHourEndTime() {
		return thuOpHourEndTime;
	}

	public void setThuOpHourEndTime(String thuOpHourEndTime) {
		this.thuOpHourEndTime = thuOpHourEndTime;
	}

	public String getFriOpHourStartTime() {
		return friOpHourStartTime;
	}

	public void setFriOpHourStartTime(String friOpHourStartTime) {
		this.friOpHourStartTime = friOpHourStartTime;
	}

	public String getFriOpHourEndTime() {
		return friOpHourEndTime;
	}

	public void setFriOpHourEndTime(String friOpHourEndTime) {
		this.friOpHourEndTime = friOpHourEndTime;
	}

	public String getSatOpHourStartTime() {
		return satOpHourStartTime;
	}

	public void setSatOpHourStartTime(String satOpHourStartTime) {
		this.satOpHourStartTime = satOpHourStartTime;
	}

	public String getSatOpHourEndTime() {
		return satOpHourEndTime;
	}

	public void setSatOpHourEndTime(String satOpHourEndTime) {
		this.satOpHourEndTime = satOpHourEndTime;
	}

	public boolean isHomeDeliveryAvlbl() {
		return isHomeDeliveryAvlbl;
	}

	public void setHomeDeliveryAvlbl(boolean isHomeDeliveryAvlbl) {
		this.isHomeDeliveryAvlbl = isHomeDeliveryAvlbl;
	}



	public String getDeliveryWithinKM() {
		return deliveryWithinKM;
	}

	public void setDeliveryWithinKM(String deliveryWithinKM) {
		this.deliveryWithinKM = deliveryWithinKM;
	}

	public String getBizAddrLatitude() {
		return bizAddrLatitude;
	}

	public void setBizAddrLatitude(String bizAddrLatitude) {
		this.bizAddrLatitude = bizAddrLatitude;
	}

	public String getBizAddrLongitude() {
		return bizAddrLongitude;
	}

	public void setBizAddrLongitude(String bizAddrLongitude) {
		this.bizAddrLongitude = bizAddrLongitude;
	}
	
}
