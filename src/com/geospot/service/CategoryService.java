package com.geospot.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.UUID;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.gridfs.GridFsOperations;

import com.fasterxml.jackson.databind.JsonNode;
import com.geospot.entities.BizInfo;
import com.geospot.entities.Category;
import com.geospot.entities.SpringMongoConfig;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class CategoryService {
	
	MongoOperations mongoOps = null;
	ApplicationContext ctx = null;
	GridFsOperations gridOperations = null;
	 
	public CategoryService() throws UnknownHostException {
    	mongoOps = new MongoTemplate(new SimpleMongoDbFactory(new MongoClient("ec2-52-74-100-117.ap-southeast-1.compute.amazonaws.com"), "geospot"));
    	ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		gridOperations = (GridFsOperations) ctx.getBean("gridFsTemplate");
	}
	
	public List<Category> getCategories() {
		BasicQuery query = new BasicQuery("{parentCategoryId: "+ 0 +"}");
		List<Category> categories =  mongoOps.find(query, Category.class);
		for(Category c:categories){
			if(c.getParentCategoryId()==0){
				BasicQuery query1 = new BasicQuery("{parentCategoryId: "+ c.getCategoryId() +"}");
				List<Category> subCategories = mongoOps.find(query1, Category.class);
				c.setSubCategories(subCategories);
			}
		}
		return categories;
	}

	public long createCategory(JsonNode category) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		Category newC = new Category();
		CounterService cs = new CounterService();
		long seqId = cs.getNextSequence(Category.class.getName());
		newC.setCategoryId(seqId);
		if(category.get("categoryName")!=null)
		{
			newC.setCategoryName(category.get("categoryName").asText(""));
		}
		if(category.get("categoryImg")!=null)
		{
			String categoryImgFile = category.get("categoryImg").asText();
			DBObject metaData = new BasicDBObject();
			String categoryImgFileName = category.get("categoryImgFileName").asText();
			// populate metadata
			UUID uniqueId = UUID.randomUUID();
			newC.setCategoryId(seqId);
			InputStream inputStream = new ByteArrayInputStream(
					categoryImgFile.getBytes("utf-8"));
			String modifiedFileName ="/CategoryImages/" + uniqueId + "_" + categoryImgFileName;
			gridOperations.store(inputStream,  modifiedFileName, metaData);		
	        newC.setCategoryImgURL(modifiedFileName);
		}
		
		if(category.get("parentCategoryId")!=null)
		{
			newC.setParentCategoryId(category.get("parentCategoryId").asLong(0));
		}
		mongoOps.save(newC);
		return newC.getCategoryId();
	}
}
