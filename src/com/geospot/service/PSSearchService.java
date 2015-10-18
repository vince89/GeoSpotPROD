//package com.geospot.service;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Comparator;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.TreeMap;
//
//import org.apache.lucene.document.Document;
//import org.apache.lucene.index.DirectoryReader;
//import org.apache.lucene.index.IndexReader;
//import org.apache.lucene.index.Term;
//import org.apache.lucene.search.BooleanQuery;
//import org.apache.lucene.search.IndexSearcher;
//import org.apache.lucene.search.PhraseQuery;
//import org.apache.lucene.search.ScoreDoc;
//import org.apache.lucene.search.TermQuery;
//import org.apache.lucene.search.TopScoreDocCollector;
//import org.apache.lucene.search.BooleanClause.Occur;
//import org.apache.lucene.store.Directory;
//import org.apache.lucene.store.FSDirectory;
//
//import com.geospot.algorithm.MatchedDocument;
//import com.geospot.algorithm.NameSearchQuery;
//import com.geospot.algorithm.RowSearchQuery;
//import com.geospot.entities.Product;
//import com.geospot.models.ResultDocument;
//
//public class PSSearchService {
//	// Searcher
//
//	public HashMap<String, List<ResultDocument>> searcher(String searchWord,
//			String parentDir) throws IOException {
//
//		String IndexDir = parentDir + File.separator + "Data" + File.separator + "index";
//
//		String indexFilePath = IndexDir + File.separator + "GeoSpotData";
//		String indexFilePathL1 = IndexDir + File.separator + "ProductTreeGuide";
//		String indexFilePathL2 = IndexDir + File.separator + "SpecificGeneric";
//
//		File indexFile = new File(indexFilePath);
//		File indexFileL1 = new File(indexFilePathL1);
//		File indexFileL2 = new File(indexFilePathL2);
//
//		FSDirectory index = FSDirectory.open(indexFile);
//		FSDirectory Catindex = FSDirectory.open(indexFileL1);
//		FSDirectory CatL2index = FSDirectory.open(indexFileL2);
//
//		List<String> searchWords = new ArrayList<String>();
//		List<String> filterCategories = new ArrayList<String>();
//		
//		System.out.println("Search Word " + searchWord);
//
//		if (searchWord != null && !searchWord.isEmpty()) {
//			searchWords = Arrays.asList(searchWord.split(" "));
//		List<String> filterCategoriesTemp = new ArrayList<String>();
//
//			for (String s : searchWords) {
//				filterCategoriesTemp = searchL2(s, CatL2index, Catindex);
//				filterCategories.addAll(filterCategoriesTemp);
//			}
//
//			for (String s : filterCategories) {
//				System.out.println("Filter Categories " + s);
//			}
//
//			/* NameSearchQuery nSearchQuery = new NameSearchQuery(searchWords,
//					filterCategories);
//			IndexReader nameReader = null;
//			nameReader = DirectoryReader.open(index);
//			IndexSearcher nameSearcher = new IndexSearcher(nameReader);
//			nSearchQuery.search(nameSearcher);
//			List<com.geospot.models.ResultDocument> nResultDocuments = new ArrayList<com.geospot.models.ResultDocument>();
//
//			request.setAttribute("nCategoryHits",
//			// nSearchQuery.getCategoryHitsCount());
//			// request.setAttribute("nProductCodeHits",
//			// nSearchQuery.getProdCodeHitCount());
//
//			for (MatchedDocument mr : nSearchQuery.getDocuments()) {
//				ResultDocument rd = new ResultDocument();
//				rd.setDocument(nameSearcher.doc(mr.getDocId()));
//				rd.setExplanation(mr.getExplanation());
//				rd.setScore(mr.getScore());
//				nResultDocuments.add(rd);
//			}
//
//			// request.setAttribute("nSearchHits",nResultDocuments); */
//
//			RowSearchQuery rSearchQuery = new RowSearchQuery(searchWords, filterCategories);
//			IndexReader reader = null;
//			reader = DirectoryReader.open(index);
//			IndexSearcher searcher = new IndexSearcher(reader);
//			//rSearchQuery.search(searcher);
//
//			HashMap<String, List<ResultDocument>> resultDocuments = new HashMap<String, List<ResultDocument>>();
//			Map<String, Float> categoryHitCount = rSearchQuery
//					.getCategoryHitsCount();
//			TreeMap<String, Float> sortedcategoryHitCount = SortByValue(categoryHitCount);
//			// request.setAttribute("categoryHits", sortedcategoryHitCount);
//
//			
//			for (MatchedDocument mr : rSearchQuery.getDocuments()) {
//				String category;
//				category = searcher.doc(mr.getDocId()).get("CatLvl2");
//				if (sortedcategoryHitCount.size() > 1 && !sortedcategoryHitCount.firstKey().equals(category))
//					continue;
//				if (mr.getScore() <= 8)
//					continue;
//				ResultDocument rd = new ResultDocument();
//				Product p = new Product();
//				p.setProductName(searcher.doc(mr.getDocId()).get("ProductName"));
//				p.setProductDesc(searcher.doc(mr.getDocId()).get("ProductDesc"));
//				p.setPrice(searcher.doc(mr.getDocId()).get("Price"));
//				rd.setProduct(p);
//				String storeIds = searcher.doc(mr.getDocId()).get("StoreIds");
//				List<String> storeIdList = new ArrayList<String>(Arrays.asList(storeIds.split(",")));
//				rd.setExplanation(mr.getExplanation());
//				rd.setScore(mr.getScore());
//				
//				for (String storeId : storeIdList) {
//					if (resultDocuments.containsKey(storeId)) {
//						List<ResultDocument> resultDocs = resultDocuments.get(storeId);
//						resultDocs.add(rd);
//						resultDocuments.put(storeId, resultDocs);
//					} else {
//						List<ResultDocument> resultDocs = new ArrayList<ResultDocument>();
//						resultDocs.add(rd);
//						resultDocuments.put(storeId, resultDocs);
//					}
//				}
//			}
//			return resultDocuments;
//		}
//		return null;
//	}
//
//	private List<String> searchL2(String querystr, Directory CatL2index,Directory Catindex) throws IOException {
//		BooleanQuery bq = new BooleanQuery();
//		String[] phrases = querystr.split(" ");
//		PhraseQuery pq1 = new PhraseQuery();
//		PhraseQuery pq2 = new PhraseQuery();
//
//		for (String each : phrases) {
//
//			TermQuery tq1 = new TermQuery(new Term("Word", each.toLowerCase()));
//			tq1.setBoost(98f);
//			bq.add(tq1, Occur.SHOULD);
//		}
//
//		// 3. search
//		int hitsPerPage = 10;
//		IndexReader reader = DirectoryReader.open(CatL2index);
//		IndexSearcher searcher = new IndexSearcher(reader);
//		TopScoreDocCollector collector = TopScoreDocCollector.create(
//				hitsPerPage, true);
//		searcher.search(bq, collector);
//		ScoreDoc[] hits = collector.topDocs().scoreDocs;
//		List<String> specificCatList = new ArrayList<String>();
//		List<String> genericCatList = new ArrayList<String>();
//		List<String> L1List = new ArrayList<String>();
//		double docStoreLimit = 0.0;
//
//		// 4. display results
//		System.out.println("Found " + hits.length + " hits.");
//		for (int i = 0; i < hits.length; i++) {
//			int docId = hits[i].doc;
//			if (i == 0)
//				docStoreLimit = hits[i].score * 0.2;
//			Document d = new Document();
//			// if(hits[i].score > docStoreLimit) {
//			d = searcher.doc(docId);
//			System.out.println((i + 1) + ". " + d.get("Word") + "\t"
//					+ d.get("Keyword") + " " + hits[i].score);
//			// }
//
//			if (d.get("Keyword").equalsIgnoreCase("Specific")) {
//				specificCatList.add(d.get("Category"));
//				System.out.println("Specific" + d.get("Category"));
//			}
//			if (d.get("Keyword").equalsIgnoreCase("Generic")) {
//				genericCatList.add(d.get("Category"));
//				System.out.println("Generic" + d.get("Category"));
//			}
//			if (d.get("Keyword").equalsIgnoreCase("L1")) {
//				L1List.add(d.get("Category"));
//				System.out.println("L1" + d.get("Category"));
//
//			}
//		}
//		List<String> catList = new ArrayList<String>();
//		if (specificCatList.isEmpty() && genericCatList.isEmpty()) {
//			for (String l1List : L1List) {
//				catList = searchL1(l1List, Catindex);
//			}
//			// genericCatList.addAll(searchL1(querystr,genericCatList));
//			// return null;
//		} else {
//			catList.addAll(specificCatList);
//			catList.addAll(genericCatList);
//		}
//		System.out.println("Generic Size " + genericCatList.size());
//		System.out.println("Specific Size " + specificCatList.size());
//		System.out.println("Category Level1 Size " + L1List.size());
//		reader.close();
//		return catList;
//	}
//
//	public static TreeMap<String, Float> SortByValue(Map<String, Float> map) {
//		ValueComparator vc = new ValueComparator(map);
//		TreeMap<String, Float> sortedMap = new TreeMap<String, Float>(vc);
//		sortedMap.putAll(map);
//		return sortedMap;
//	}
//
//	private List<String> searchL1(String queryStr, Directory Catindex)
//			throws IOException {
//
//		System.out.println("Query String " + queryStr);
//		BooleanQuery bq = new BooleanQuery();
//		String[] phrases = queryStr.split(" ");
//		PhraseQuery pq1 = new PhraseQuery();
//		PhraseQuery pq2 = new PhraseQuery();
//
//		for (String each : phrases) {
//			System.out.println(each);
//			TermQuery tq1 = new TermQuery(new Term("Word", each.toLowerCase()));
//			tq1.setBoost(98f);
//			bq.add(tq1, Occur.SHOULD);
//		}
//
//		List<String> genericCatL2List = new ArrayList<String>();
//		int hitsPerPage = 10;
//		IndexReader reader = DirectoryReader.open(Catindex);
//		IndexSearcher searcher = new IndexSearcher(reader);
//		TopScoreDocCollector collector = TopScoreDocCollector.create(
//				hitsPerPage, true);
//		// searcher.search(rSearchQuery , filter, collector);
//		searcher.search(bq, collector);
//		ScoreDoc[] hits = collector.topDocs().scoreDocs;
//
//		double docStoreLimit = 0.0;
//
//		// 4. display results
//		System.out.println("Found " + hits.length + " hits.");
//		for (int i = 0; i < hits.length; ++i) {
//			int docId = hits[i].doc;
//			if (i == 0)
//				docStoreLimit = hits[i].score * 0.2;
//			if (hits[i].score > docStoreLimit) {
//				Document d = searcher.doc(docId);
//				System.out.println((i + 1) + ". " + d.get("CategoryL1") + "\t"
//						+ d.get("CategoryL2") + " " + hits[i].score);
//				System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
//				System.out.println("L2" + d.get("CategoryL2"));
//				genericCatL2List.add(d.get("CategoryL2"));
//			}
//		}
//		reader.close();
//		System.out.println(genericCatL2List.size());
//		return genericCatL2List;
//	}
//
//}
//
//class ValueComparator implements Comparator<String> {
//
//	Map<String, Float> map;
//
//	public ValueComparator(Map<String, Float> base) {
//		this.map = base;
//	}
//
//	public int compare(String a, String b) {
//		if (map.get(a) >= map.get(b)) {
//			return -1;
//		} else {
//			return 1;
//		} // returning 0 would merge keys
//	}
//
//}
