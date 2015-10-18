package com.geospot.apicontroller;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geospot.entities.BadRequestException;
import com.geospot.entities.Feed;
import com.geospot.entities.ValueObject;
import com.geospot.service.FeedService;

@RestController
@RequestMapping("/api")
public class FeedsApiController {
	
	private FeedService fs;
	
	public FeedsApiController() throws UnknownHostException {
	   fs = new FeedService();
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/getFeeds")
	@ResponseBody
	public List<Feed> getFeeds(HttpServletRequest request) {
		try {
			return fs.getFeeds();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(headers = {"content-type=application/json"},method=RequestMethod.POST, value="/getDynamicFeeds")
	@ResponseBody
	public JsonNode getDynamicFeeds(@RequestBody String json, HttpServletRequest request) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode feedParams;
		try {
			feedParams = mapper.readTree(json);
			return fs.getDynamicFeeds(feedParams);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	@RequestMapping(headers = {"content-type=application/json"},method=RequestMethod.POST, value="/insertFeed")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public long insertFeed(HttpServletRequest request) throws Exception{
			String jsonBody = IOUtils.toString( request.getInputStream());
			ObjectMapper mapper = new ObjectMapper();
			JsonNode feed = null;
			feed = mapper.readTree(jsonBody);
			return fs.insertFeed(feed);
	 }

}
