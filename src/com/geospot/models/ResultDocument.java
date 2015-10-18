package com.geospot.models;

import org.apache.lucene.document.Document;

import com.geospot.entities.Product;

public class ResultDocument implements Comparable {
	
	private Product product;
	private float score;
	private String explanation;
	
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public String getExplanation() {
		return explanation;
	}
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	
	 @Override
	 public int compareTo(Object comparestu) {
	        Float compareScore=((ResultDocument)comparestu).getScore();
	        /* For Ascending order*/
	        Float thisScore = this.getScore();
	        return compareScore.compareTo(thisScore);

	        /* For Descending order do like this */
	        //return compareage-this.studentage;
	    }
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
}
