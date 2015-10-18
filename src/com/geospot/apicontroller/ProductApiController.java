package com.geospot.apicontroller;

import java.net.UnknownHostException;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.geospot.entities.Product;
import com.geospot.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductApiController {
	
	ProductService productService;
	
	public ProductApiController() throws UnknownHostException {
		productService = new ProductService();
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/saveProducts"
			+ "")
	@ResponseBody
	public String saveProducts(List<Product> prodList) {
		return productService.saveProducts(prodList);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/saveProduct")
	@ResponseBody
	public String saveProduct(Product pd) {
		return productService.saveProduct(pd);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/getProducts")
	@ResponseBody
	public List<Product> getProducts() {
		return productService.getProducts();
	}
}
