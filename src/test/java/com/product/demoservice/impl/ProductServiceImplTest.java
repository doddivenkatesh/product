package com.product.demoservice.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.product.demo.dao.ProductDao;
import com.product.demo.entity.ProductEntity;
import com.product.demo.exception.handler.ProductNotFoundException;
import com.product.demo.service.impl.ProductServiceImpl;

public class ProductServiceImplTest {

	@InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductDao productDao;

    private ProductEntity product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        product = new ProductEntity();
        product.setId(1L);
        product.setName("Test Product");
        product.setCategory("Electronics");
        product.setPrice(99.99);
        product.setAvailable(true);
        product.setReleaseDate(LocalDate.now());
    }

    @Test
    void testSave() {
        when(productDao.save(product)).thenReturn(product);

        ProductEntity savedProduct = productService.save(product);

        assertNotNull(savedProduct);
        assertEquals("Test Product", savedProduct.getName());
        verify(productDao, times(1)).save(product);
    }

    @Test
    void testGetByProductId() {
        when(productDao.findById(1L)).thenReturn(Optional.of(product));

        ProductEntity foundProduct = productService.getByProductId(1L);

        assertNotNull(foundProduct);
        assertEquals(1L, foundProduct.getId());
        verify(productDao, times(1)).findById(1L);
    }

    @Test
    void testGetByProductId_NotFound() {
        when(productDao.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getByProductId(2L));
        verify(productDao, times(1)).findById(2L);
    }

    @Test
    void testGetAllProducts() {
        when(productDao.findAll()).thenReturn(Collections.singletonList(product));

        List<ProductEntity> products = productService.getAllProducts();

        assertNotNull(products);
        assertEquals(1, products.size());
        verify(productDao, times(1)).findAll();
    }

    @Test
    void testUpdateProduct() {
        ProductEntity updatedProduct = new ProductEntity();
        updatedProduct.setName("Updated Product");
        updatedProduct.setCategory("Home Appliances");
        updatedProduct.setPrice(199.99);
        updatedProduct.setAvailable(false);
        updatedProduct.setReleaseDate(LocalDate.now().minusDays(1));

        when(productDao.findById(1L)).thenReturn(Optional.of(product));
        when(productDao.save(any(ProductEntity.class))).thenReturn(updatedProduct);

        ProductEntity result = productService.updateProduct(1L, updatedProduct);

        assertNotNull(result);
        assertEquals("Updated Product", result.getName());
        verify(productDao, times(1)).findById(1L);
        verify(productDao, times(1)).save(any(ProductEntity.class));
    }

    @Test
    void testDeleteProduct() {
        when(productDao.findById(1L)).thenReturn(Optional.of(product));

        String result = productService.deleteProduct(1L);

        assertEquals("Product successfully deleted", result);
        verify(productDao, times(1)).findById(1L);
        verify(productDao, times(1)).deleteById(1L);
    }

    @Test
    void testFindByCategory() {
        when(productDao.findByCategory("Electronics")).thenReturn(Collections.singletonList(product));

        List<ProductEntity> products = productService.findByCategory("Electronics");

        assertNotNull(products);
        assertEquals(1, products.size());
        verify(productDao, times(1)).findByCategory("Electronics");
    }

    @Test
    void testGetAvailableProductsSortedByPrice() {
        when(productDao.findByAvailableTrue(Sort.by("price"))).thenReturn(Collections.singletonList(product));

        List<ProductEntity> products = productService.getAvailableProductsSortedByPrice();

        assertNotNull(products);
        assertEquals(1, products.size());
        verify(productDao, times(1)).findByAvailableTrue(Sort.by("price"));
    }

    @Test
    void testGetProductsReleasedAfter() {
        LocalDate date = LocalDate.now().minusDays(5);
        when(productDao.findByReleaseDateAfter(date)).thenReturn(Collections.singletonList(product));

        List<ProductEntity> products = productService.getProductsReleasedAfter(date);

        assertNotNull(products);
        assertEquals(1, products.size());
        verify(productDao, times(1)).findByReleaseDateAfter(date);
    }

    @Test
    void testGetAllProductsWithPagination() {
        Page<ProductEntity> page = new PageImpl<>(Collections.singletonList(product));
        when(productDao.findAll(any(PageRequest.class))).thenReturn(page);

        List<ProductEntity> products = productService.getAllProducts(0, 1, "name");

        assertNotNull(products);
        assertEquals(1, products.size());
        verify(productDao, times(1)).findAll(any(PageRequest.class));
    }
}
