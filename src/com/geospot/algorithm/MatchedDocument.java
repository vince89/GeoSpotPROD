package com.geospot.algorithm;


public class MatchedDocument {
	
	private int docId;
	private SearchField ProductName;
	private SearchField ProductDesc;
	private SearchField CatLvl1;
	private SearchField CatLvl2;
	private float score;
	private String lvl2Category;
	private String explanation;
	
	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public MatchedDocument() {
		ProductName = new SearchField("ProductName",8);
		ProductDesc = new SearchField("ProductDesc",4);
		CatLvl1 = new SearchField("CatLvl1",2);
		CatLvl2 = new SearchField("CatLvl2",1);
	}

	public int getDocId() {
		return docId;
	}
	public void setDocId(int docId) {
		this.docId = docId;
	}
	
	public SearchField getProductName() {
		return ProductName;
	}
	public void setProductName(SearchField productName) {
		ProductName = productName;
	}
	
	public SearchField getProductDesc() {
		return ProductDesc;
	}
	
	public void setProductDesc(SearchField productDesc) {
		ProductDesc = productDesc;
	}
	
	public SearchField getCatLvl1() {
		return CatLvl1;
	}
	
	public void setCatLvl1(SearchField catLvl1) {
		CatLvl1 = catLvl1;
	}
	
	public SearchField getCatLvl2() {
		return CatLvl2;
	}
	
	public void setCatLvl2(SearchField catLvl2) {
		CatLvl2 = catLvl2;
	}
	
	public float getScore() {
		return score;
	}
	
	public void setScore(float score) {
		this.score = score;
	}
	
	@Override
	public String toString() {
		StringBuffer sf = new StringBuffer();
		sf.append("Document Id " + docId + "\n");
		sf.append("Product Name" + "\n");
		if(!ProductName.getTermsFound().isEmpty()) {
		for(TermFound tf : ProductName.getTermsFound()){
			sf.append("Keyword: " + tf.getTerm() + ", " + tf.getFrequency() + " times found [");
			for(int count =0 ; count< tf.getPositions().size(); count++){
				sf.append(tf.getPositions().get(count));
				if(count< tf.getPositions().size()-1) sf.append(",");
			}
			sf.append("]\n");
		  }
		}
		else sf.append("No Keywords Found \n");
		
		sf.append("Product Desc" + "\n");
		if(!ProductDesc.getTermsFound().isEmpty()) {
		for(TermFound tf : ProductDesc.getTermsFound()){
			sf.append("Keyword: " + tf.getTerm() + ", " + tf.getFrequency() + " times found [");
			for(int count =0 ; count< tf.getPositions().size(); count++){
				sf.append(tf.getPositions().get(count));
				if(count< tf.getPositions().size()-1) sf.append(",");
			}
			sf.append("]\n");
		  }
		}
		else sf.append("No Keywords Found \n");
		
		return sf.toString();
	}

	public String getLvl2Category() {
		return lvl2Category;
	}

	public void setLvl2Category(String lvl2Category) {
		this.lvl2Category = lvl2Category;
	}

}
