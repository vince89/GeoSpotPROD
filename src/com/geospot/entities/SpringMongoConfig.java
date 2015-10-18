package com.geospot.entities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
 
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
 
@Configuration
public class SpringMongoConfig extends AbstractMongoConfiguration{
 
	@Bean
	public GridFsTemplate gridFsTemplate() throws Exception {
		return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
	}
 
	@Override
	protected String getDatabaseName() {
		return "geospot";
	}
 
	@Override
	@Bean
	public Mongo mongo() throws Exception {
		return new MongoClient("ec2-52-74-100-117.ap-southeast-1.compute.amazonaws.com");
	}
 
}