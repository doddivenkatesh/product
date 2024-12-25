package com.product.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.product.demo.entity.ProductEntity;

public interface ProductService {

	public ProductEntity save(ProductEntity productEntity);

	public ProductEntity getByProductId(Long id);

	public List<ProductEntity> getAllProducts();

	ProductEntity updateProduct(Long id, ProductEntity updateProduct);

	public String deleteProduct(Long id);

	public List<ProductEntity> findByCategory(String category);


	public List<ProductEntity> getAvailableProductsSortedByPrice();
	public List<ProductEntity> getProductsReleasedAfter(LocalDate parsedDate);

	public List<ProductEntity> getAllProducts(Integer pageNo, Integer pageSize, String sortBy);
}
