package com.geospot.entities;

public class Product {
	
	private String ProductId;
	
	private String ProductName;
	
	private String ProductDesc;
	
	private String BrandName;
	
	private String CatLvl3;
	
	private String CatLvl2;
	
	private String CatLvl1;
	
	public Product(String productId, String productName, String productDesc, String brandName,
			String catLvl3, String catLvl2, String catLvl1) {
		super();
		ProductId = productId;
		ProductName = productName;
		ProductDesc = productDesc;
		BrandName = brandName;
		CatLvl3 = catLvl3;
		CatLvl2 = catLvl2;
		CatLvl1 = catLvl1;
	}
	
	public String getProductId() {
		return ProductId;
	}

	public void setProductId(String productId) {
		ProductId = productId;
	}


	public String getProductName() {
		return ProductName;
	}

	public void setProductName(String productName) {
		ProductName = productName;
	}

	public String getProductDesc() {
		return ProductDesc;
	}

	public void setProductDesc(String productDesc) {
		ProductDesc = productDesc;
	}

	public String getBrandName() {
		return BrandName;
	}

	public void setBrandName(String brandName) {
		BrandName = brandName;
	}

	public String getCatLvl3() {
		return CatLvl3;
	}

	public void setCatLvl3(String catLvl3) {
		CatLvl3 = catLvl3;
	}

	public String getCatLvl2() {
		return CatLvl2;
	}

	public void setCatLvl2(String catLvl2) {
		CatLvl2 = catLvl2;
	}

	public String getCatLvl1() {
		return CatLvl1;
	}

	public void setCatLvl1(String catLvl1) {
		CatLvl1 = catLvl1;
	}

}
