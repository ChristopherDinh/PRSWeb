package com.prs.business.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prs.business.product.Product;
import com.prs.business.product.ProductRepository;
import com.prs.util.PRSMaintenanceReturn;


@Controller   
@RequestMapping(path="/Products")
public class ProductController {
	@Autowired 
	private ProductRepository productRepository;

	@GetMapping(path="/List")
	public @ResponseBody Iterable<Product> getAllProduct() {
		return productRepository.findAll();
	}
	@GetMapping(path="/Get")
	public @ResponseBody Product getProduct(@RequestParam int id) {
		Optional<Product> v= productRepository.findById(id);
		if (v.isPresent())
			return v.get();
		else
			return null;
	}
	@PostMapping(path="/Add")
	public @ResponseBody PRSMaintenanceReturn addNewProduct (@RequestBody Product product) {
		try {
			productRepository.save(product);
		}
		catch (Exception e) {
			product = null;
		}
		return PRSMaintenanceReturn.getMaintReturn(product);
	}
	@GetMapping(path="/Remove")
	public @ResponseBody PRSMaintenanceReturn deleteProduct (@RequestParam int id) {
		Optional<Product> product = productRepository.findById(id);
		try {
			productRepository.delete(product.get());
		}
		catch (Exception e) {
			product = null;
		}
		return PRSMaintenanceReturn.getMaintReturn(product.get());
	}
	@PostMapping(path="/Change")
	public @ResponseBody PRSMaintenanceReturn updateProduct (@RequestBody Product product) {
		try {
			productRepository.save(product);
		}
		catch (Exception e) {
			product = null;
		}
		return PRSMaintenanceReturn.getMaintReturn(product);
	}
}