package com.geospot.service;

import java.net.UnknownHostException;
import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.geospot.entities.MasterBizInfo;
import com.geospot.entities.SubBizInfo;
import com.mongodb.MongoClient;

@Service
public class BizInfoService {
	
	MongoOperations mongoOps = null;
	 
	public BizInfoService() throws UnknownHostException {
    	mongoOps = new MongoTemplate(new SimpleMongoDbFactory(new MongoClient("ec2-52-74-100-117.ap-southeast-1.compute.amazonaws.com"), "geospot"));
	}
	
	public List<SubBizInfo> getBizInfos() {
		List<SubBizInfo> subBizInfos = mongoOps.findAll(SubBizInfo.class);
		return subBizInfos;
	}
	
	public long createBizInfo(JsonNode bizInfo) {
		SubBizInfo subBizInfo = new SubBizInfo();
		MasterBizInfo masterBizInfo = new MasterBizInfo();  
		CounterService cs = new CounterService();
		long seqId = cs.getNextSequence(SubBizInfo.class.getName());
		long seqMasterId = cs.getNextSequence(SubBizInfo.class.getName());
		subBizInfo.setBizId(seqId);
		masterBizInfo.setMasterBizId(seqMasterId);
		//logic for save business
		
		
		
		
		
		if(bizInfo.get("mBizName")!=null)
		{
			masterBizInfo.setMasterBizName(bizInfo.get("mBizName").asText(""));
		}
		if(bizInfo.get("isRegisteredBusiness")!=null)
		{
			masterBizInfo.setIsRegisteredBusiness(bizInfo.get("isRegisteredBusiness").asText(""));
		}
		if(bizInfo.get("tin")!=null)
		{
			masterBizInfo.setTin(bizInfo.get("tin").asText(""));
		}
		if(bizInfo.get("strn")!=null)
		{
			masterBizInfo.setStrn(bizInfo.get("strn").asText(""));
		}
		if(bizInfo.get("aaadharnum")!=null)
		{
			masterBizInfo.setStrn(bizInfo.get("aaadharnum").asText(""));
		}
		if(bizInfo.get("pan")!=null)
		{
			masterBizInfo.setPan(bizInfo.get("pan").asText(""));
		}
		if(bizInfo.get("ownername")!=null)
		{
			masterBizInfo.setOwnerName(bizInfo.get("ownername").asText(""));
		}
		if(bizInfo.get("password")!=null)
		{
			masterBizInfo.setGsBizPassword(bizInfo.get("password").asText(""));
		}
		
		
		
		subBizInfo.setMasterBizInfo(masterBizInfo);
		
		if(bizInfo.get("bizProfilename")!=null)
		{
			subBizInfo.setBizName(bizInfo.get("bizProfilename").asText(""));
		}
		if(bizInfo.get("bizType")!=null)
		{
			subBizInfo.setBizName(bizInfo.get("bizType").asText(""));
		}
		if(bizInfo.get("bizOpenDate")!=null)
		{
			subBizInfo.setBizName(bizInfo.get("bizOpenDate").asText(""));
		}
		
		if(bizInfo.get("bizStatus")!=null)
		{
			subBizInfo.setBizStatus(bizInfo.get("bizStatus").asText(""));
		}
		
		if(bizInfo.get("bizInfo")!=null)
		{
			subBizInfo.setBizDesc(bizInfo.get("bizInfo").asText(""));
		}
		
		
		if(bizInfo.get("address")!=null)
		{
			subBizInfo.setBizStreetAddress(bizInfo.get("address").asText(""));
		}
		if(bizInfo.get("area")!=null)
		{
			subBizInfo.setBizArea(bizInfo.get("area").asText(""));
		}
		if(bizInfo.get("city")!=null)
		{
			subBizInfo.setBizCity(bizInfo.get("city").asText(""));
		}
		
		
		if(bizInfo.get("state")!=null)
		{
			subBizInfo.setBizCity(bizInfo.get("state").asText(""));
		}
		
		if(bizInfo.get("pincode")!=null)
		{
			subBizInfo.setBizPinCode(bizInfo.get("pincode").asText(""));
		}
		if(bizInfo.get("phone1")!=null)
		{
			subBizInfo.setBizPhone1(bizInfo.get("phone1").asText(""));
		}
		if(bizInfo.get("phone2")!=null)
		{
			subBizInfo.setBizPhone2(bizInfo.get("phone2").asText(""));
		}
		
		if(bizInfo.get("website")!=null)
		{
			subBizInfo.setWebsite(bizInfo.get("website").asText(""));
		}
		
		
		if(bizInfo.get("email")!=null)
		{
			subBizInfo.setEmail1(bizInfo.get("email").asText(""));
		}
		
		if(bizInfo.get("24hropen")!=null)
		{
			if((bizInfo.get("24hropen").asText("").equalsIgnoreCase("true")))
					{
				      subBizInfo.setOpen24Hrs(true);
					}
			else {
				subBizInfo.setOpen24Hrs(false);
			}
			
		}
		
		if(bizInfo.get("sundayOpsStart")!=null)
		{
			subBizInfo.setSunOpHourStartTime(bizInfo.get("sundayOpsStart").asText(""));
		}
		
		
		if(bizInfo.get("sundayOpsEnd")!=null)
		{
			subBizInfo.setSunOpHourEndTime(bizInfo.get("sundayOpsEnd").asText(""));
		}
		
		if(bizInfo.get("monOpsStart")!=null)
		{
			subBizInfo.setMonOpHourStartTime(bizInfo.get("monOpsStart").asText(""));
		}
		
		
		if(bizInfo.get("monOpsEnd")!=null)
		{
			subBizInfo.setMonOpHourEndTime(bizInfo.get("monOpsEnd").asText(""));
		}
		
		
		if(bizInfo.get("tueOpsStart")!=null)
		{
			subBizInfo.setTueOpHourStartTime(bizInfo.get("tueOpsStart").asText(""));
		}
		
		
		if(bizInfo.get("tueOpsEnd")!=null)
		{
			subBizInfo.setTueOpHourEndTime(bizInfo.get("tueOpsEnd").asText(""));
		}
		
		
		
		if(bizInfo.get("wedOpsStart")!=null)
		{
			subBizInfo.setWedOpHourStartTime(bizInfo.get("wedOpsStart").asText(""));
		}
		
		
		if(bizInfo.get("wedOpsEnd")!=null)
		{
			subBizInfo.setWedOpHourEndTime(bizInfo.get("wedOpsEnd").asText(""));
		}
		
		
		
		if(bizInfo.get("thusOpsStart")!=null)
		{
			subBizInfo.setTueOpHourStartTime(bizInfo.get("thusOpsStart").asText(""));
		}
		
		
		if(bizInfo.get("thusOpsEnd")!=null)
		{
			subBizInfo.setTueOpHourEndTime(bizInfo.get("thusOpsEnd").asText(""));
		}
		
		
		
		if(bizInfo.get("friOpsStart")!=null)
		{
			subBizInfo.setFriOpHourStartTime(bizInfo.get("friOpsStart").asText(""));
		}
		
		if(bizInfo.get("friOpsEnd")!=null)
		{
			subBizInfo.setFriOpHourEndTime(bizInfo.get("friOpsEnd").asText(""));
		}
		if(bizInfo.get("satOpsStart")!=null)
		{
			subBizInfo.setSatOpHourStartTime(bizInfo.get("satOpsStart").asText(""));
		}
		
		
		if(bizInfo.get("satOpsEnd")!=null)
		{
			subBizInfo.setSatOpHourEndTime(bizInfo.get("satOpsEnd").asText(""));
		}
		
	
		
		if(bizInfo.get("homedelivery")!=null)
		{
			if((bizInfo.get("homedelivery").asText("").equalsIgnoreCase("yes")))
					{
				      subBizInfo.setHomeDeliveryAvlbl(true);
					}
			else {
				subBizInfo.setHomeDeliveryAvlbl(false);
			}
			
		}
		
		if(bizInfo.get("deliveryProximity")!=null)
		{
			subBizInfo.setDeliveryWithinKM(bizInfo.get("deliveryProximity").asText(""));
		}
		
		if(bizInfo.get("latitude")!=null)
		{
			subBizInfo.setBizAddrLatitude(bizInfo.get("latitude").asText(""));
		}
		if(bizInfo.get("longitude")!=null)
		{
			subBizInfo.setBizAddrLongitude(bizInfo.get("longitude").asText(""));
		}
		
		
		mongoOps.save(subBizInfo);
		return seqId;
	}

}
