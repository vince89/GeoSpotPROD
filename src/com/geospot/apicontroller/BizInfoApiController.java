package com.geospot.apicontroller;

import java.net.UnknownHostException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geospot.entities.SubBizInfo;
import com.geospot.service.BizInfoService;


@RestController
@RequestMapping("/api")
public class BizInfoApiController {
	
	BizInfoService bizInfoService;
	
	public BizInfoApiController() throws UnknownHostException {
		bizInfoService = new BizInfoService();
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/getBizInfos")
	@ResponseBody
	public List<SubBizInfo> getBizInfos() {
		
		return bizInfoService.getBizInfos();
	}
	
	@RequestMapping(headers = {"content-type=application/json"},method=RequestMethod.POST, value="/insertBiz")
	@ResponseBody
	public long insertUser(@RequestBody String json, HttpServletRequest request)  {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode feedParams;
		try {
			feedParams = mapper.readTree(json);
			return bizInfoService.createBizInfo(feedParams);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return 408;
		} 
	}

}
