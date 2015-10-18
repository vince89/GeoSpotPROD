package com.geospot.models;

import java.util.ArrayList;
import java.util.List;


public class TermFound {
	private String term;
	private int frequency;
	private List<Integer> positions;
	
	public TermFound() {
		positions = new ArrayList<Integer>();
	}
	
	public String getTerm() {
		return term;
	}
	
	public void setTerm(String term) {
		this.term = term;
	}
	
	public List<Integer> getPositions() {
		return positions;
	}
	
	public void setPositions(List<Integer> position) {
		this.positions = position;
	}
	
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	
	@Override
	public boolean equals(Object arg0) {
		TermFound tf = (TermFound) arg0;
		if(this.getTerm().equals(tf.getTerm()))
			return true;
		return false;
	}
}
