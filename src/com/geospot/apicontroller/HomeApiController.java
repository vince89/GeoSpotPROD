package com.geospot.apicontroller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.geospot.service.IndexService;

@RestController
@RequestMapping("/api")
public class HomeApiController {
	
	//PSSearchService psSearchService = new PSSearchService();
	IndexService indexService = new IndexService();
	
	@SuppressWarnings("deprecation")
	@RequestMapping(method=RequestMethod.GET, value="/search")
	@ResponseBody
	public ObjectNode search(@RequestParam("searchTerm") String searchTerm, HttpServletRequest request) {
	try {
		ServletContext servletContext = request.getSession().getServletContext();
		//indexService.indexData(servletContext.getRealPath("/"));
		//psSearchService.searcher(searchTerm,servletContext.getRealPath("/"));

		ObjectNode searchJSON = JsonNodeFactory.instance.objectNode();
		
		ObjectNode bizInfo =  JsonNodeFactory.instance.objectNode();
		bizInfo.put("address", "No 10, RR Street");
		bizInfo.put("shopName", "Samsung Stores");
		bizInfo.put("phoneNos", "+91 989394 44345, +044 36436434");
		bizInfo.put("distanceFromYou", 3.4);
		ArrayNode bizInfos = JsonNodeFactory.instance.arrayNode();
		bizInfos.add(bizInfo);
        
		searchJSON.put("bizInfos", bizInfos);
		searchJSON.put("totalCount", 10);

		return searchJSON;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
	}
}
