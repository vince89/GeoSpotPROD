package com.geospot.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class BizCategory {

	@Id
	private int bizCategoryId;
	
	private int bizId;
	
	private String lvl2Category;
	
	private String lvl1Category;

	public int getBizCategoryId() {
		return bizCategoryId;
	}

	public void setBizCategoryId(int bizCategoryId) {
		this.bizCategoryId = bizCategoryId;
	}

	public int getBizId() {
		return bizId;
	}

	public void setBizId(int bizId) {
		this.bizId = bizId;
	}

	public String getLvl2Category() {
		return lvl2Category;
	}

	public void setLvl2Category(String lvl2Category) {
		this.lvl2Category = lvl2Category;
	}

	public String getLvl1Category() {
		return lvl1Category;
	}

	public void setLvl1Category(String lvl1Category) {
		this.lvl1Category = lvl1Category;
	}
	
}
