package com.geospot.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Circle;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.geospot.entities.BizInfo;
import com.geospot.entities.Feed;
import com.geospot.entities.SpringMongoConfig;
import com.geospot.entities.ValueObject;
import com.geospot.models.UserInfo;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.sun.javafx.font.Metrics;

import org.springframework.data.domain.PageRequest;

@Service
public class FeedService {

	MongoOperations mongoOps = null;
	ApplicationContext ctx = null;
	GridFsOperations gridOperations = null;

	public FeedService() throws UnknownHostException {
		mongoOps = new MongoTemplate(new SimpleMongoDbFactory(new MongoClient(
				"ec2-52-74-100-117.ap-southeast-1.compute.amazonaws.com"), "geospot"));
		ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		gridOperations = (GridFsOperations) ctx.getBean("gridFsTemplate");
	}

	public List<Feed> getFeeds() {
		List<Feed> feeds = mongoOps.findAll(Feed.class);
		return feeds;
	}

	@SuppressWarnings("deprecation")
	public JsonNode getDynamicFeeds(JsonNode feedParams) throws Exception {
		final JsonNodeFactory factory = JsonNodeFactory.instance;
		ObjectNode dynamicFeeds = factory.objectNode();
		List<Integer> subCategoryIds = new ArrayList<Integer>();
		double gpsLatitude = feedParams.get("gpsLatitudeAtLastSync").asDouble();
		double gpsLongitude = feedParams.get("gpsLongitudeAtLastSync").asDouble();
		String strSourceDevice = feedParams.get("sourceDevice").asText();
		String feedsTypeToDownload = feedParams.get("feedTypeToDownload").asText();
		String postType = feedParams.get("postType").asText();
		int feedsPerPage = feedParams.get("feedsPerPage").asInt();
		List<String> tagNames = feedParams.findValuesAsText("tagNamesAtLastSync");
		List<String> selectedAreas = feedParams.findValuesAsText("selectedAreas");
		List<JsonNode> jsSubCategoryIds = feedParams.findValues("subCategoryIdsAtLastSync");
		long lastDynamicFeedId = feedParams.get("lastDynamicFeedId").asLong();
		long lastFavouriteFeedId = feedParams.get("lastDynamicFeedId").asLong();
		for(JsonNode jsCategoryId: jsSubCategoryIds) subCategoryIds.add(jsCategoryId.asInt());
		Circle circle = new Circle(gpsLatitude, gpsLongitude, 4/6371);
		Query locQuery = new Query(Criteria.where("gpsLocation").within(
				circle));
		Pageable request = new PageRequest(0, 50, new Sort(
				Sort.Direction.DESC, "dateOfPost"));
		locQuery.with(request);
		List<Feed> feeds = mongoOps.find(locQuery, Feed.class);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(feeds);
		dynamicFeeds.put("feeds", mapper.readTree(json));
		return dynamicFeeds;
	}

	public long insertFeed(JsonNode feed) throws Exception {
		// TODO Auto-generated method stub
		SimpleDateFormat isoFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		CounterService cs = new CounterService();
		long seqId = cs.getNextSequence(BizInfo.class.getName());
		List<String> ltLinkedURLs = new ArrayList<String>();
		List<Integer> ltCategoryIds = new ArrayList<Integer>();
		Feed d = new Feed();
		String strTitle = feed.get("title").asText();
		String strDescription = feed.get("description").asText("");
		String strUserId = feed.get("userId").asText();
		long bizId = feed.get("bizId").asLong();
		double gpsLatitude = feed.get("gpsLatitude").asDouble();
		double gpsLongitude = feed.get("gpsLongitude").asDouble();
		String strSourceDevice = feed.get("sourceDevice").asText();
		boolean isDeal = feed.get("isDeal").asBoolean();
		
		
		ArrayNode jsonlinkedURLs = (ArrayNode)feed.get("linkedURLs");
		for(JsonNode eachNode:jsonlinkedURLs) ltLinkedURLs.add(eachNode.textValue());
		
		ArrayNode jsoncategoryIds = (ArrayNode)feed.get("subCategoryIds");
		for(JsonNode eachNode:jsonlinkedURLs) ltCategoryIds.add(eachNode.asInt());
		
		JsonNode linkedVideos = feed.get("linkedVideos");
		JsonNode linkedAudios = feed.get("linkedAudios");
		JsonNode linkedImages = feed.get("linkedImages");
		
		Calendar cal = Calendar.getInstance();
		Date dateofPost = new Date();
		cal.add(Calendar.DATE, 10);
		Date expiryDate = cal.getTime();
		d.setTitle(strTitle);
		d.setDescription(strDescription);
		d.setDateOfPost(dateofPost);
		d.setExpiryDate(expiryDate);
		d.setLinkedURLs(ltLinkedURLs.toArray(new String[ltLinkedURLs.size()]));
		d.setGpsLatitude(gpsLatitude);
		d.setGpsLongitude(gpsLongitude);
		d.setSourceDevice(strSourceDevice);
		d.setUserId(strUserId);
		d.setBizId(bizId);
		d.setDeal(isDeal);
		
		/*Matcher matcher = Pattern.compile("#(\\w+)").matcher(strDescription);
		List<String> lstHashTaggedWords = new ArrayList<String>();
		String[] tagNames = {};
		while (matcher.find()) {
			lstHashTaggedWords.add(matcher.group(1).toLowerCase());
		}
		tagNames = lstHashTaggedWords.toArray(tagNames);
		d.setTagNames(tagNames);*/

		if (linkedImages != null && linkedImages.isArray()) {
			JsonNode linkedFile = linkedImages.get(0);
			if (linkedFile != null) {
				DBObject metaData = new BasicDBObject();
				// populate metadata
				String fileName = linkedFile.get("fileName").asText();
				String base64String = linkedFile.get("base64String").asText();
				UUID uniqueId = UUID.randomUUID();
				InputStream inputStream = new ByteArrayInputStream(
						base64String.getBytes("utf-8"));
				String modifiedFileName = uniqueId + "_" + fileName;
				gridOperations.store(inputStream, "/Images/"
						+ modifiedFileName, metaData);
				String[] lFiles = new String[1];
				lFiles[0] = "/Images/" + modifiedFileName;
				d.setLinkedImages(lFiles);
			}
		}
		
		if (linkedVideos != null && linkedVideos.isArray()) {
			JsonNode linkedFile = linkedVideos.get(0);
			if (linkedFile != null) {
				DBObject metaData = new BasicDBObject();
				// populate metadata
				String fileName = linkedFile.get("fileName").asText();
				String base64String = linkedFile.get("base64String").asText();
				UUID uniqueId = UUID.randomUUID();
				InputStream inputStream = new ByteArrayInputStream(
						base64String.getBytes("utf-8"));
				String modifiedFileName = uniqueId + "_" + fileName;
				gridOperations.store(inputStream, "/Videos/"
						+ modifiedFileName, metaData);
				String[] lFiles = new String[1];
				lFiles[0] = "/Videos/" + modifiedFileName;
				d.setLinkedVideos(lFiles);
			}
		}
		
		
		if (linkedAudios != null && linkedAudios.isArray()) {
			JsonNode linkedFile = linkedAudios.get(0);
			if (linkedFile != null) {
				DBObject metaData = new BasicDBObject();
				// populate metadata
				String fileName = linkedFile.get("fileName").asText();
				String base64String = linkedFile.get("base64String").asText();
				UUID uniqueId = UUID.randomUUID();
				InputStream inputStream = new ByteArrayInputStream(
						base64String.getBytes("utf-8"));
				String modifiedFileName = uniqueId + "_" + fileName;
				gridOperations.store(inputStream, "/Audios/"
						+ modifiedFileName, metaData);
				String[] lFiles = new String[1];
				lFiles[0] = "/Audios/" + modifiedFileName;
				d.setLinkedAudios(lFiles);
			}
		}
		d.setFeedId(seqId);
		d.setGlobalFeedNo(seqId);
		mongoOps.insert(d);
		return d.getFeedId();
	}

	public List<ValueObject> getTrendingHashTags(JsonNode feedParams) {
		// TODO Auto-generated method stub
		double gpsLatitudeNow = 0.0, gpsLongitudeNow = 0.0;

		if (feedParams.get("gpsLatitudeNow") != null)
			gpsLatitudeNow = feedParams.get("gpsLatitudeNow").asDouble(0.0);

		if (feedParams.get("gpsLongitudeNow") != null)
			gpsLongitudeNow = feedParams.get("gpsLongitudeNow").asDouble(0.0);

		Circle circle = new Circle(gpsLatitudeNow, gpsLongitudeNow, 0.01);

		Query query = new Query(Criteria.where("gpsLocation").within(circle));

		MapReduceResults<ValueObject> results = mongoOps
				.mapReduce(
						query,
						"feed",
						"function() { this.hashTaggedWords.forEach(function(tag) { emit(tag,  1); }); }",
						"function(key, values) { var totalCount=0; values.forEach(function(value) { totalCount++; }); return totalCount  };",
						ValueObject.class);

		List<ValueObject> trendingHashTaggedWords = new ArrayList<ValueObject>();

		for (ValueObject valueObject : results) {
			trendingHashTaggedWords.add(valueObject);
		}

		Collections.sort(trendingHashTaggedWords,
				new Comparator<ValueObject>() {

					@Override
					public int compare(ValueObject o1, ValueObject o2) {
						// TODO Auto-generated method stub
						return (int) (o2.getValue() - o1.getValue());
					}
				});

		return trendingHashTaggedWords;
	}
}
