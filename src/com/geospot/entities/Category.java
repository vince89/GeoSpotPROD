package com.geospot.entities;

import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;


@Document
public class Category {
	
	@Id 
	private long categoryId;
	
	@Indexed(unique = true)
	private String categoryName;
	
	private String categoryImgURL; 
	
	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	private long parentCategoryId = 0;
	
	@Transient
	private List<Category> subCategories;

	public Category() {
		// TODO Auto-generated constructor stub
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryImgURL() {
		return categoryImgURL;
	}

	public void setCategoryImgURL(String categoryImgURL) {
		this.categoryImgURL = categoryImgURL;
	}

	public List<Category> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(List<Category> subCategories) {
		this.subCategories = subCategories;
	}

	public long getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(long parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}

}
