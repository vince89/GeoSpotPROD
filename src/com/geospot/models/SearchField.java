package com.geospot.models;

import java.util.ArrayList;
import java.util.List;


public class SearchField {
	
	private String fieldName;
	private int fieldWeight;
	private List<TermFound> termsFound;
	
	public SearchField(String fName, int fWeight){
		this.fieldName = fName;
		this.fieldWeight = fWeight;
		termsFound = new ArrayList<TermFound>();
	}
	
	public String getFieldName() {
		return fieldName;
	}
	
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public List<TermFound> getTermsFound() {
		return termsFound;
	}
	
	public void setTermsFound(List<TermFound> termsFound) {
		this.termsFound = termsFound;
	}
	
	public int getFieldWeight() {
		return fieldWeight;
	}
	
	public void setFieldWeight(int fieldWeight) {
		this.fieldWeight = fieldWeight;
	}
}
