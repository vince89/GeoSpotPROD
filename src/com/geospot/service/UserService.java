package com.geospot.service;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.geospot.entities.Feed;
import com.geospot.entities.SpringMongoConfig;
import com.geospot.entities.User;
import com.mongodb.MongoClient;

@Service
public class UserService {

	MongoOperations mongoOps = null;
	ApplicationContext ctx = null;
	GridFsOperations gridOperations = null;

	public UserService() throws UnknownHostException {
		mongoOps = new MongoTemplate(new SimpleMongoDbFactory(new MongoClient(
				"ec2-52-74-100-117.ap-southeast-1.compute.amazonaws.com"), "geospot"));
		ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		gridOperations = (GridFsOperations) ctx.getBean("gridFsTemplate");
	}



	public long insertUser(JsonNode user) throws Exception {
		// TODO Auto-generated method stub 
		SimpleDateFormat isoFormat = new SimpleDateFormat("dd-MM-yyyy");
		isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		//CounterService cs = new CounterService();
		//long seqId = cs.getNextSequence(BizInfo.class.getName());
		//Feed d = new Feed();
		User userObj = new User();


		//JsonNode linkedFiles = user.get("linkedFiles");
		if(user.get("fullName")!=null)
		{
			userObj.setFullname(user.get("fullName").asText(""));
		}

		userObj.setUserId(new Random().nextLong());
		if(user.get("mobile")!=null)
		{
			userObj.setMobile(user.get("mobile").asText(""));
		}
		if(user.get("gender")!=null)
		{
			userObj.setGender(user.get("gender").asText(""));
		}
		if(user.get("dob")!=null)
		{
			userObj.setDob(user.get("dob").asText(""));
		}
		if(user.get("email")!=null)
		{
			userObj.setEmail(user.get("email").asText(""));
		}
		mongoOps.insert(userObj);
		return userObj.getUserId();
	}

	public List<User> getUsers() {
		List<User> user = mongoOps.findAll(User.class);
		return user;
	}


}
