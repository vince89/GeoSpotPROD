package com.geospot.algorithm;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.DocsAndPositionsEnum;
import org.apache.lucene.index.DocsEnum;
import org.apache.lucene.index.FilteredTermsEnum;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexReaderContext;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.automaton.Automaton;
import org.apache.lucene.util.automaton.CompiledAutomaton;
import org.apache.lucene.index.TermsEnum;


public class RowSearchQuery extends org.apache.lucene.search.Query {
    
	private List<String> searchWords;
	private List<String> lvl2Categories;
	List<MatchedDocument> documents = new ArrayList<MatchedDocument>();
	Map<Integer,Double> spacingScore = new HashMap<Integer,Double>();
	Map<String, Float> categoryHitCount = new HashMap<String, Float>();
	
	public Map<String, Float> getCategoryHitsCount() {
		return categoryHitCount;
	}

	public void setCategoryHitsCount(Map<String, Float> categoryHitsCount) {
		this.categoryHitCount = categoryHitCount;
	}

	Double defaultSpacingScore = 0.10;

	public RowSearchQuery(List<String> sWords, List<String> lvl2Cat) {
		this.searchWords = sWords;
		this.lvl2Categories = lvl2Cat;
		
		spacingScore.put(0,1.0);
		spacingScore.put(1,1.0);
		spacingScore.put(2,0.95);
		spacingScore.put(3,0.50);
		spacingScore.put(4,0.40);
		spacingScore.put(5,0.30);
		spacingScore.put(6,0.20);
	}

	@Override
	public Weight createWeight(IndexSearcher searcher) throws IOException {
		return new RowSearchWeight(searcher);
	}

	public String toString(String arg0) {
		return null;
	}

	public class RowSearchWeight extends Weight {

		public RowSearchWeight(IndexSearcher searcher) {
			// TODO Auto-generated constructor stub
		}

		@Override
		public Explanation explain(AtomicReaderContext arg0, int docId)
				throws IOException {
			// TODO Auto-generated method stub
			MatchedDocument md = findDocumentByDocId(docId);

	        if(md!=null)	
	        {	
	        	return new Explanation(0.0f,md.getExplanation());
			}
			return new Explanation(0.0f,"No match terms found.");
		}

		@Override
		public Query getQuery() {
			// TODO Auto-generated method stub
			return RowSearchQuery.this;
		}

		@Override
		public float getValueForNormalization() throws IOException {
			// TODO Auto-generated method stub
			return 0.0f;
		}

		@Override
		public void normalize(float arg0, float arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public Scorer scorer(AtomicReaderContext context, Bits acceptDocs)
				throws IOException {
			documents.clear();
			categoryHitCount.clear();
            IndexReaderContext readerContext = context.parent;
            IndexReader reader = readerContext.reader();
			List<Integer> filteredDocuments = new ArrayList<Integer>();
			Map<String, Integer> cHits = new HashMap<String, Integer>();

			List<Term> productNameTerms = new ArrayList<Term>();
			List<Term> productDescTerms = new ArrayList<Term>();
			List<Term> catlvl1Terms = new ArrayList<Term>();
			List<Term> catlvl2Terms = new ArrayList<Term>();

			for (String eachCategory : lvl2Categories) {
				System.out.println(eachCategory+"Prechana");
				//eachCategory="Mobile";
				Term filterCategory = new Term("CatLvl2",
						eachCategory.toLowerCase());
				DocsEnum docs = context.reader().termDocsEnum(filterCategory);
				
				if (docs != null) {
					int i = 0;
					while (docs.nextDoc() != Scorer.NO_MORE_DOCS) {
						
						int docId = docs.docID();
						if (!filteredDocuments.contains(docId))
							
							filteredDocuments.add(docId);
					}
					
					
				}
			}
		
			for (String eachWord : searchWords) {
				productNameTerms.add(new Term("ProductName", eachWord
						.toLowerCase()));
				
			/*	Term wildCardPNTerm = new Term("ProductName",eachWord
						.toLowerCase() + "*");
				Automaton automaton1 = WildcardQuery.toAutomaton(wildCardPNTerm); 
				CompiledAutomaton compiled1 = new CompiledAutomaton(automaton1); 
				TermsEnum pnWCterms = compiled1.getTermsEnum(context.reader().terms("ProductName"));
				
				do {
					productNameTerms.add(new Term("ProductName", pnWCterms.term().utf8ToString()));
				} while (pnWCterms.next() != null); */
				
				productDescTerms.add(new Term("ProductDesc", eachWord
						.toLowerCase()));
				
			/*	Term wildCardPDTerm = new Term("ProductDesc",eachWord
						.toLowerCase() + "*");
				Automaton automaton2 = WildcardQuery.toAutomaton(wildCardPDTerm); 
				CompiledAutomaton compiled2 = new CompiledAutomaton(automaton2); 
				TermsEnum pdWCterms = compiled2.getTermsEnum(context.reader().terms("ProductName"));
				
				do {
					productDescTerms.add(new Term("ProductDesc", pnWCterms.term().utf8ToString()));
				} while (pdWCterms.next() != null); */
				
				
				catlvl1Terms.add(new Term("CatLvl1", eachWord.toLowerCase()));
				catlvl2Terms.add(new Term("CatLvl2", eachWord.toLowerCase()));
			}

			//name search 

			for (Term pnTerm : productNameTerms) {
				DocsAndPositionsEnum docs = context.reader().termPositionsEnum(pnTerm);
				if (docs != null) {
					while (docs.nextDoc() != Scorer.NO_MORE_DOCS) {
						int docId = docs.docID();
					
						if (filteredDocuments.contains(docId)) {
						
							MatchedDocument existingDocument = findDocumentByDocId(docId);
							if (existingDocument == null) {
								System.out.println("Double Check ");
								MatchedDocument newDocument = new MatchedDocument();
								newDocument.setDocId(docId);
								TermFound tf = new TermFound();
								tf.setTerm(pnTerm.text());
								tf.setFrequency(docs.freq());
								for(int i=0; i< docs.freq(); i++){
									tf.getPositions().add(docs.nextPosition());
								}
								newDocument.getProductName().getTermsFound()
										.add(tf);
								documents.add(newDocument);
							} else {
								
								TermFound tf = new TermFound();
								tf.setTerm(pnTerm.text());
								tf.getPositions().add(docs.nextPosition());
								tf.setFrequency(docs.freq());
								for(int i=0; i< docs.freq(); i++){
									tf.getPositions().add(docs.nextPosition());
								}
								existingDocument.getProductName()
										.getTermsFound().add(tf);
							}
						
						}else 
						{
							
							System.out.println("Double Check 123 ");
							
							
							MatchedDocument existingDocument = findDocumentByDocId(docId);
							if (existingDocument == null) {
								System.out.println("Double Check 2");
								MatchedDocument newDocument = new MatchedDocument();
								newDocument.setDocId(docId);
								TermFound tf = new TermFound();
								tf.setTerm(pnTerm.text());
								tf.setFrequency(docs.freq());
								for(int i=0; i< docs.freq(); i++){
									tf.getPositions().add(docs.nextPosition());
								}
								newDocument.getProductName().getTermsFound()
										.add(tf);
								documents.add(newDocument);
							}
							
							
							
							
							
						}
						
					}
				}
			}

			// Search in the Product Description

			for (Term pnTerm : productDescTerms) {
				DocsAndPositionsEnum docs = context.reader().termPositionsEnum(
						pnTerm);
				if (docs != null) {
					while (docs.nextDoc() != Scorer.NO_MORE_DOCS) {
						int docId = docs.docID();
						if (filteredDocuments.contains(docId)) {
							MatchedDocument existingDocument = findDocumentByDocId(docId);
							if (existingDocument == null) {
								MatchedDocument newDocument = new MatchedDocument();
								newDocument.setDocId(docId);
								TermFound tf = new TermFound();
								tf.setTerm(pnTerm.text());
								tf.setFrequency(docs.freq());
								for(int i=0; i< docs.freq(); i++){
									tf.getPositions().add(docs.nextPosition());
								}
								newDocument.getProductDesc().getTermsFound()
										.add(tf);
								documents.add(newDocument);
							} else {
								TermFound tf = new TermFound();
								tf.setTerm(pnTerm.text());
								tf.setFrequency(docs.freq());
								for(int i=0; i< docs.freq(); i++){
									tf.getPositions().add(docs.nextPosition());
								}
								existingDocument.getProductDesc()
										.getTermsFound().add(tf);
							}
						}
						else 
						{
							System.out.println("Double Check 1234 ");
							MatchedDocument existingDocument = findDocumentByDocId(docId);
							if (existingDocument == null) {
								MatchedDocument newDocument = new MatchedDocument();
								newDocument.setDocId(docId);
								TermFound tf = new TermFound();
								tf.setTerm(pnTerm.text());
								tf.setFrequency(docs.freq());
								for(int i=0; i< docs.freq(); i++){
									tf.getPositions().add(docs.nextPosition());
								}
								newDocument.getProductDesc().getTermsFound()
										.add(tf);
								documents.add(newDocument);
							}
						}
					}
					
					
				}								
			}
			
			
			
			for(MatchedDocument md: documents) {	
				
				List<Integer> pnSpacing = new ArrayList<Integer>();
				List<Integer> pdSpacing = new ArrayList<Integer>();
				List<String> uniqueKeywords = new ArrayList<String>();
				double pnSpacingScore = 1, pdSpacingScore = 1;
				
				StringBuffer sf = new StringBuffer();
				sf.append("Document Id " + md.getDocId() + "<br/>");
				sf.append("Product Name" + "<br/>");
				double d = (double) md.getProductName().getTermsFound().size()/searchWords.size();
				sf.append("Percentage of Keywords : " + md.getProductName().getTermsFound().size() + "/" + searchWords.size() + " = " + d + "<br/>");
				if(!md.getProductName().getTermsFound().isEmpty()) {
				for(TermFound tf : md.getProductName().getTermsFound()){
					sf.append("Keyword: " + tf.getTerm() + ", " + tf.getFrequency() + " times found [");
					if(!uniqueKeywords.contains(tf.getTerm()))uniqueKeywords.add(tf.getTerm());
					for(int count =0 ; count< tf.getPositions().size(); count++){
						sf.append(tf.getPositions().get(count));
						pnSpacing.add(tf.getPositions().get(count));
						if(count< tf.getPositions().size()-1) sf.append(",");
					}
					sf.append("]<br/>");
				  }
				}
				else sf.append("No Keywords Found <br/>");
				
				if(pnSpacing.size()>0){
				sf.append("Position List " + pnSpacing + "<br/>");
				if(searchWords.size() == 1 && pnSpacing.size() == 1) pnSpacingScore = 1.0;
				else if (pnSpacing.size() == 1) pnSpacingScore = defaultSpacingScore;
				else{
					Collections.sort(pnSpacing, Collections.reverseOrder());
				    for(int i=0;i<pnSpacing.size()-1; i++){
				    	int difference = pnSpacing.get(i) - pnSpacing.get(i + 1);
				    	if(spacingScore.containsKey(difference)) {
				    		sf.append("Difference: " + difference + " Spacing Score " + spacingScore.get(difference) + "<br/>");
				    	    pnSpacingScore = pnSpacingScore * spacingScore.get(difference);
				    	} else {
				    		sf.append("Difference: " + difference + " Default Spacing Score " + defaultSpacingScore + "<br/>");
				    	    pnSpacingScore = pnSpacingScore * defaultSpacingScore;
				    	}
				    }
				}
				sf.append("Spacing Score " + pnSpacingScore + "<br/>");
				}
				float productNameScore = (float)(md.getProductName().getFieldWeight() * d * pnSpacingScore);
				sf.append("Product Name Score : " + productNameScore + "<br/><br/>");
				
				
				sf.append("Product Desc" + "<br/>");
				double d2 = (double) md.getProductDesc().getTermsFound().size()/searchWords.size();
				sf.append("Percentage of Keywords : " + md.getProductDesc().getTermsFound().size() + "/" + searchWords.size() + " = " + d2 + "<br/>");
				if(!md.getProductDesc().getTermsFound().isEmpty()) {
				for(TermFound tf : md.getProductDesc().getTermsFound()){
					sf.append("Keyword: " + tf.getTerm() + ", " + tf.getFrequency() + " times found [");
					if(!uniqueKeywords.contains(tf.getTerm()))uniqueKeywords.add(tf.getTerm());
					for(int count =0 ; count< tf.getPositions().size(); count++){
						sf.append(tf.getPositions().get(count));
						pdSpacing.add(tf.getPositions().get(count));
						if(count< tf.getPositions().size()-1) sf.append(",");
					}
					sf.append("]<br/>");
				  }
				}
				else sf.append("No Keywords Found <br/>");
				
				if(pdSpacing.size()>0){
				sf.append("Position List " + pdSpacing + "<br/>");
				if(searchWords.size() == 1 && pdSpacing.size() == 1) pdSpacingScore = 1.0;
				else if (pdSpacing.size() == 1) pdSpacingScore = defaultSpacingScore;
				else{
					Collections.sort(pdSpacing, Collections.reverseOrder());
					sf.append("After Sorting " + pdSpacing + "<br/>");
				    for(int i=0;i<pdSpacing.size()-1; i++){
				    	int difference =  pdSpacing.get(i) - pdSpacing.get(i + 1);
				    	if(spacingScore.containsKey(difference)) {
				    		sf.append("Difference: " + difference + " Spacing Score " + spacingScore.get(difference) + "<br/>");
				    	    pdSpacingScore = pdSpacingScore * spacingScore.get(difference);
				    	} else {
				    		sf.append("Difference: " + difference + " Default Spacing Score " + defaultSpacingScore + "<br/>");
				    	    pdSpacingScore = pdSpacingScore * defaultSpacingScore;
				    	}
				    }
				}				
				
				sf.append("Spacing Score " + pdSpacingScore + "<br/>");
				}
				float productDescScore = (float)(md.getProductDesc().getFieldWeight() * d2 * pdSpacingScore);
				sf.append("Product Desc Score : " + productDescScore + "<br/>");
				
				float overallKeywordMatchScore = (float)uniqueKeywords.size()/searchWords.size();
				sf.append("Overall Keywords Matched Score " + uniqueKeywords.size() + "/"+ searchWords.size() + " = "+ overallKeywordMatchScore + "<br/>");
				
				float overallScore = (productNameScore + productDescScore) * overallKeywordMatchScore; 
				sf.append("Overall Score : " + overallScore + "<br/><br/>" );
				md.setScore(overallScore);
				md.setExplanation(sf.toString());
				
			}
			
			for(MatchedDocument md: documents) {
				String category = reader.document(md.getDocId()).get("CatLvl2");
				if(categoryHitCount.containsKey(category)){
					float currentScore = categoryHitCount.get(category)+md.getScore();
					categoryHitCount.put(category,currentScore);
					int currentCount = cHits.get(category) + 1;
					cHits.put(category,currentCount);
				}
				else {
					categoryHitCount.put(category,md.getScore());
					cHits.put(category,0);
				}
			}
			return new RowSearchScorer(this, documents);
		}

		private MatchedDocument findDocumentByDocId(int docId) {
			for (MatchedDocument md : documents) {
				if (md.getDocId() == docId)
					return md;
			}
			return null;
		}
	}

	public class RowSearchScorer extends Scorer {

		int currentDocument = 0;

		private MatchedDocument findDocumentByDocId(int docId) {
			for (MatchedDocument md : documents) {
				if (md.getDocId() == docId)
					return md;
			}
			return null;
		}

		public RowSearchScorer(RowSearchWeight rowSearchWeight,
				List<MatchedDocument> docs) {
			super(rowSearchWeight);
		}

		@Override
		public float score() throws IOException {
			return documents.get(currentDocument-1).getScore();
		}

		@Override
		public int freq() throws IOException {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int advance(int target) throws IOException {
			// TODO Auto-generated method stub
			MatchedDocument matchedDocument = findDocumentByDocId(target);
			if (matchedDocument != null) {
				return target;
			} else
				return NO_MORE_DOCS;
		}

		@Override
		public long cost() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int docID() {
			if (currentDocument < documents.size()) {
				return documents.get(currentDocument).getDocId();
			}else {
				return NO_MORE_DOCS;
			}
		}

		@Override
		public int nextDoc() throws IOException {
			// TODO Auto-generated method stub
			if (currentDocument < documents.size()) {
				return documents.get(currentDocument++).getDocId();
			}
			else { 
				return NO_MORE_DOCS;
			}
		}
	}

}
