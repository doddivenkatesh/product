package com.product.demo.controller;

import java.time.LocalDate;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.product.demo.entity.ProductEntity;
import com.product.demo.service.ProductService;

import jakarta.annotation.Resource;

@RestController
@RequestMapping("/products")
public class ProductController  {

	@Resource(name = "productService")
	private ProductService productService;

	@GetMapping
	public List<ProductEntity> getAllProducts() {
		return productService.getAllProducts();
	}

	@GetMapping("/{id}")
	public ProductEntity findById(@PathVariable("id") Long id) {
		return productService.getByProductId(id);
	}

	@PostMapping
	public ProductEntity save(@RequestBody ProductEntity productEntity) {
		return productService.save(productEntity);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductEntity> updateProduct(@PathVariable Long id,
			@RequestBody ProductEntity updatedProduct) {
		ProductEntity updatedEntity = productService.updateProduct(id, updatedProduct);
	    return ResponseEntity.ok(updatedEntity);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/category/{category}")
	public List<ProductEntity> getProductsByCategory(@PathVariable String category) {
		return productService.findByCategory(category);
	}

	@GetMapping("/available")
	public List<ProductEntity> getAvailableProductsSortedByPrice() {
		return productService.getAvailableProductsSortedByPrice();
	}

	@GetMapping("/released-after/{date}")
	public List<ProductEntity> getProductsReleasedAfter(@PathVariable String date) {
		LocalDate parsedDate = LocalDate.parse(date);
		return productService.getProductsReleasedAfter(parsedDate);
	}

	@GetMapping("/list")
	public List<ProductEntity> getAllProducts(@RequestParam Integer pageNo, @RequestParam Integer pageSize,
			@RequestParam String sortBy) {
			return productService.getAllProducts(pageNo, pageSize, sortBy);
	}
}
