package com.geospot.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.mongodb.gridfs.*;
import com.geospot.entities.SpringMongoConfig;

@Controller
@RequestMapping("/")
public class FileController {
	
	ApplicationContext ctx = null;
	GridFsOperations gridOperations = null;
	
	public FileController() {
    	ctx =  new AnnotationConfigApplicationContext(SpringMongoConfig.class);
    	gridOperations = (GridFsOperations) ctx.getBean("gridFsTemplate");
	}
		
	@RequestMapping(value = "/FeedFiles/{filename}.{ext}", headers = "Accept=image/jpeg, image/jpg, image/png, image/gif", method = RequestMethod.GET, produces = "image/jpg")
	@ResponseBody
	public byte[] getFeedFiles(@PathVariable("filename") String fileName,@PathVariable("ext") String ext,
			  HttpServletResponse response) throws IOException {
			  fileName = "/FeedFiles/" + fileName + "." + ext;
			  List<GridFSDBFile> result = gridOperations.find(new Query(Criteria.where("filename").is(fileName)));
			  if(result.size() > 0) {
			  InputStream is = result.get(0).getInputStream();
			  String imageString = IOUtils.toString(is, "UTF-8");
			  String imageDataBytes = imageString.substring(imageString.indexOf(",")+1);
			  byte[] bytes = Base64.getDecoder().decode(imageDataBytes.getBytes());
		      // copy it to response's OutputStream
			  response.addHeader("content-length", String.valueOf(bytes.length));
			  return bytes;
		  }
	      return null;
	}
	
	@RequestMapping(value = "/CategoryImages/{filename}.{ext}", headers = "Accept=image/jpeg, image/jpg, image/png, image/gif", method = RequestMethod.GET, produces = "image/jpg")
	@ResponseBody
	public byte[] getCategoryImg(@PathVariable("filename") String fileName,@PathVariable("ext") String ext,
			  HttpServletResponse response) throws IOException {
			  fileName = "/CategoryImages/" + fileName + "." + ext;
			  List<GridFSDBFile> result = gridOperations.find(new Query(Criteria.where("filename").is(fileName)));
			  if(result.size() > 0) {
			  InputStream is = result.get(0).getInputStream();
			  String imageString = IOUtils.toString(is, "UTF-8");
			  String imageDataBytes = imageString.substring(imageString.indexOf(",")+1);
			  byte[] bytes = Base64.getDecoder().decode(imageDataBytes.getBytes());
		      // copy it to response's OutputStream
			  response.addHeader("content-length", String.valueOf(bytes.length));
			  return bytes;
		  }
	      return null;
	}
 
}
