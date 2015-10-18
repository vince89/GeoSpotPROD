package com.geospot.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public class IndexService {

	public void indexData(String parentDir) throws IOException {
		List<String> searchWords = new ArrayList<String>();
		List<String> filterCategories = new ArrayList<String>();
		
		String CSVDir = parentDir  + "Data" + File.separator + "csv";
		String IndexDir = parentDir  + "Data" + File.separator + "index";

		String csvFilePath =  CSVDir + File.separator + "GeoSpotData.csv";
		String csvFilePathL1 = CSVDir + File.separator + "ProductTreeGuide.csv";
		String csvFilePathL2 = CSVDir + File.separator + "SpecificGeneric.csv";
		
		String indexFilePath =  IndexDir+ File.separator + "GeoSpotData";
		String indexFilePathL1 = IndexDir + File.separator + "ProductTreeGuide";
		String indexFilePathL2 = IndexDir + File.separator + "SpecificGeneric";
		
		System.out.println(indexFilePath);
		File indexFile = new File(indexFilePath);
		File indexFileL1 = new File(indexFilePathL1); 	
		File indexFileL2 = new File(indexFilePathL2); 
		  
		FSDirectory index=FSDirectory.open(indexFile);
		FSDirectory Catindex=FSDirectory.open(indexFileL1);
		FSDirectory CatL2index=FSDirectory.open(indexFileL2);

		try {
			System.out.println(csvFilePath);
			addProductIndex(index, csvFilePath);
			addProductIndexL1(Catindex, csvFilePathL1);
			addProductIndexL2(CatL2index, csvFilePathL2);
			System.out.println("Files Indexed Sucessfully");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void addProductIndex(Directory index, String filePath)
			throws IOException {
		StandardAnalyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(Version.LATEST,
				analyzer);
		IndexWriter w = new IndexWriter(index, config);
		Reader in = new FileReader(filePath);
		w.deleteAll();
		final CSVParser parser = new CSVParser(in, CSVFormat.EXCEL.withHeader());
		;
		for (CSVRecord record : parser) {
			addDoc(w, record);
		}
		parser.close();
		w.close();
	}

	private static void addProductIndexL1(Directory index, String filePath)
			throws IOException {
		StandardAnalyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(Version.LATEST,
				analyzer);
		IndexWriter w = new IndexWriter(index, config);
		w.deleteAll();
		Reader in = new FileReader(filePath);
		final CSVParser parser = new CSVParser(in, CSVFormat.EXCEL.withHeader());
		for (CSVRecord record : parser) {
			addDocL1(w, record);
		}
        parser.close();
		w.close();
	}

	private static void addProductIndexL2(Directory index, String filePath)
			throws IOException {
		StandardAnalyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(Version.LATEST,
				analyzer);
		IndexWriter w = new IndexWriter(index, config);
		w.deleteAll();
		Reader in = new FileReader(filePath);
		final CSVParser parser = new CSVParser(in, CSVFormat.EXCEL.withHeader());
		;
		for (CSVRecord record : parser) {
			addDocL2(w, record);
		}
		parser.close();
		w.close();
	}

	private void addDoc(IndexWriter w, CSVRecord record) throws IOException {
		Document doc = new Document();
		doc.add(new StringField("ProductId", record.get("Product ID"),
				Field.Store.YES));
		doc.add(new TextField("ProductName", record.get("Product Name"),
				Field.Store.YES));
		doc.add(new TextField("ProductDesc", record.get("Product Description"),
				Field.Store.YES));
		doc.add(new TextField("ProductCode", record.get("Prod ID Code"),
				Field.Store.YES));
		// doc.add(new TextField("BrandName", record.get("Brand Name"),
		// Field.Store.YES));
		// doc.add(new TextField("CatLvl1", record.get("Category Level 1"),
		// Field.Store.YES));
		doc.add(new TextField("CatLvl2", record.get("Category Level 2"),
				Field.Store.YES));
		doc.add(new StringField("StoreIds", record.get("Store IDs"),
				Field.Store.YES));
		doc.add(new TextField("Price", record.get("Price"), Field.Store.YES));
		// doc.add(new TextField("Latitude", record.get("Store Latitude"),
		// Field.Store.YES));
		// doc.add(new TextField("Longitude", record.get("Store Longitude"),
		// Field.Store.YES));
		w.addDocument(doc);
	}

	// indexing for L2 Category

	private static void addDocL1(IndexWriter w, CSVRecord record)
			throws IOException {
		Document doc = new Document();
		doc.add(new StringField("Word", record.get("Word"), Field.Store.YES));
		doc.add(new TextField("CategoryL2", record.get("CategoryL2"),
				Field.Store.YES));
		w.addDocument(doc);
	}

	//

	private static void addDocL2(IndexWriter w, CSVRecord record)
			throws IOException {
		Document doc = new Document();
		doc.add(new StringField("Word", record.get("Word"), Field.Store.YES));
		doc.add(new TextField("Keyword", record.get("Keyword"), Field.Store.YES));
		doc.add(new TextField("Category", record.get("CategoryL1"),
				Field.Store.YES));
		w.addDocument(doc);
	}

}
