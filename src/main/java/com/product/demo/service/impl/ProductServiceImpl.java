package com.product.demo.service.impl;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.product.demo.dao.ProductDao;
import com.product.demo.entity.ProductEntity;
import com.product.demo.exception.handler.ProductNotFoundException;
import com.product.demo.service.ProductService;

import ch.qos.logback.classic.Logger;
import jakarta.annotation.Resource;

@Service("productService")
public class ProductServiceImpl implements ProductService {

	Logger logger = (Logger) LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Resource(name = "productDao")
	private ProductDao productDao;

	@Override
	public ProductEntity save(ProductEntity productEntity) {
		return productDao.save(productEntity);
	}

	@Override
	@Cacheable(cacheNames = "products", key = "#id")
	public ProductEntity getByProductId(Long id) {
		logger.info("Getting Product by ID from db");
	    return productDao.findById(id)
	                     .orElseThrow(() -> new ProductNotFoundException("Product not found for the given id: " + id));
	}

	@Override
	public List<ProductEntity> getAllProducts() {
		logger.info("Getting all products from db");
		return productDao.findAll();
	}

	@Override
	public ProductEntity updateProduct(Long id, ProductEntity updateProduct) {
		logger.info("Updating Product for id: " + id);

	    return productDao.findById(id).map(product -> {
	        product.setName(updateProduct.getName());
	        product.setCategory(updateProduct.getCategory());
	        product.setPrice(updateProduct.getPrice());
	        product.setAvailable(updateProduct.isAvailable());
	        product.setReleaseDate(updateProduct.getReleaseDate());

	        return productDao.save(product);
	    }).orElseThrow(() -> new ProductNotFoundException("Product not found for the given id: " + id));
	}
	
	
	@Override
	public String deleteProduct(Long id) {
		return productDao.findById(id)
		        .map(product -> {
		            productDao.deleteById(id);
		            return "Product successfully deleted";
		        })
		        .orElseThrow(() -> new ProductNotFoundException("Product ID not found"));
	}

	@Override
	@Cacheable(cacheNames = "products", key = "#category")
	public List<ProductEntity> findByCategory(String category) {

		logger.info("Fetching products by category: " + category);
	    List<ProductEntity> products = productDao.findByCategory(category);

	    return Optional.ofNullable(products)
	                   .filter(list -> !list.isEmpty())
	                   .orElseThrow(() -> new ProductNotFoundException("No products found for category: " + category));
	}

	@Override
	public List<ProductEntity> getAvailableProductsSortedByPrice() {
		return productDao.findByAvailableTrue(Sort.by("price"));
	}

	@Override
	public List<ProductEntity> getProductsReleasedAfter(LocalDate date) {
		return productDao.findByReleaseDateAfter(date);
	}

	@Override
	public List<ProductEntity> getAllProducts(Integer pageNo, Integer pageSize, String sortBy) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		Page<ProductEntity> pagedResult = productDao.findAll(paging);

		if (pagedResult.hasContent()) {
			return pagedResult.getContent();
		} else {
			return new ArrayList<ProductEntity>();
		}
	}
}
