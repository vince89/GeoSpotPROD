package com.geospot.models;

import java.util.ArrayList;
import java.util.List;

public class PSSearchPageModel {
	
	private int totalCount;
	private int searchDistance;
	
	 public static class BizInfoModel {
	 private int noOfItems;
	 private String shopName;
	 private String address;
	 private String phoneNos;
	 private double distanceFromYou;
	 
	 public int getNoOfItems() {
		return noOfItems;
	}
	public void setNoOfItems(int noOfItems) {
		this.noOfItems = noOfItems;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNos() {
		return phoneNos;
	}
	public void setPhoneNos(String phoneNos) {
		this.phoneNos = phoneNos;
	}
	public double getDistanceFromYou() {
		return distanceFromYou;
	}
	public void setDistanceFromYou(double distanceFromYou) {
		this.distanceFromYou = distanceFromYou;
	}

	}
	
	private List<BizInfoModel> bizInfos = new ArrayList<BizInfoModel>();

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<BizInfoModel> getBizInfos() {
		return bizInfos;
	}

	public void setBizInfos(List<BizInfoModel> bizInfos) {
		this.bizInfos = bizInfos;
	}

}
