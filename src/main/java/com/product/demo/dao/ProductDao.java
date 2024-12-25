package com.product.demo.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.product.demo.entity.ProductEntity;

@Repository("productDao")
public interface ProductDao extends JpaRepository<ProductEntity, Long> {

	List<ProductEntity> findByCategory(String category);

	List<ProductEntity> findByAvailableTrue(Sort by);

	List<ProductEntity> findByReleaseDateAfter(LocalDate date);

}
