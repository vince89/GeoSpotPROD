
package com.geospot.service;

import java.net.UnknownHostException;
import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.stereotype.Service;

import com.geospot.entities.BizInfo;
import com.geospot.entities.Product;
import com.mongodb.MongoClient;


@Service
public class ProductService {
	
	MongoOperations mongoOps = null;
	 
	public ProductService() throws UnknownHostException {
    	mongoOps = new MongoTemplate(new SimpleMongoDbFactory(new MongoClient("ec2-52-74-100-117.ap-southeast-1.compute.amazonaws.com"), "geospot"));
	}
	
	public List<Product> getProducts() {
		List<Product> products = mongoOps.findAll(Product.class);
		return products;
	}

	public String saveProducts(List<Product> prodList) {
		// TODO Auto-generated method stub
		
		for(Product pd : prodList)
		{
			mongoOps.save(pd);
		}
		
		return "SUCCESS";
	}

	public String saveProduct(Product pd) {
		// TODO Auto-generated method stub

		mongoOps.save(pd);
		return "SUCCESS";
	}

}

