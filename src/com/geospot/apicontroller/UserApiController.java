package com.geospot.apicontroller;

import java.net.UnknownHostException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geospot.entities.User;
import com.geospot.service.UserService;

@RestController
@RequestMapping("/api")
public class UserApiController {
	
	private UserService fs;
	
	public UserApiController() throws UnknownHostException {
	   fs = new UserService();
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/getUser")
	@ResponseBody
	public List<User> getStaticFeeds(HttpServletRequest request) {
		try {
			return fs.getUsers();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage()); 
			return null;
		} 
	}
	
	@RequestMapping(headers = {"content-type=application/json"},method=RequestMethod.POST, value="/insertUser")
	@ResponseBody
	public long insertUser(@RequestBody String json, HttpServletRequest request)  {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode feedParams;
		try {
			feedParams = mapper.readTree(json);
			return fs.insertUser(feedParams);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return 408;
		} 
	}
	
	
}
