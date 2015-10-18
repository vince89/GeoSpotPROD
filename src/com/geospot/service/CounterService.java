package com.geospot.service;

import static org.springframework.data.mongodb.core.query.Query.*;
import static org.springframework.data.mongodb.core.query.Criteria.*;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.*;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.geospot.entities.Counter;
import com.mongodb.MongoClient;
 
@Service
public class CounterService {
	
  public long getNextSequence(String collectionName){
	  try {
	  MongoOperations mongoOps = new MongoTemplate(new SimpleMongoDbFactory(new MongoClient("ec2-52-74-100-117.ap-southeast-1.compute.amazonaws.com"), "geospot"));
      Counter counter = mongoOps.findAndModify(
      query(where("name").is(collectionName)), 
      new Update().inc("sequence", 1),
      options().returnNew(true),
      Counter.class);
      if(counter == null)
      {
    	  Counter c = new Counter();
    	  c.setName(collectionName);
    	  c.setSequence(1000);
    	  if(mongoOps != null){
    		  mongoOps.save(c);
    	  }
    	  return 1000;
      }
     return counter.getSequence();
	  }catch(Exception ex){
		  ex.printStackTrace();
		  return 0;
	  }
  }
}