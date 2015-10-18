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
import com.geospot.entities.*;
import com.geospot.service.CategoryService;

@RestController
@RequestMapping("/api")
public class CategoryApiController {
	
	CategoryService cs;
	
	public CategoryApiController() throws UnknownHostException {
		cs = new CategoryService();
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/getCategories")
	@ResponseBody
	public List<Category> getCategories(HttpServletRequest request) {
		try {
			return cs.getCategories();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(headers = {"content-type=application/json"},method=RequestMethod.POST, value="/insertCategory")
	@ResponseBody
	public String insertCategory(@RequestBody String json, HttpServletRequest request)  {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode feedParams;
		try {
			feedParams = mapper.readTree(json);
			return String.valueOf(cs.createCategory(feedParams));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return e.getMessage();
		} 
	}

}
