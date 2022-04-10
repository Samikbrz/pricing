package com.pricing.service;

import com.pricing.exception.AlreadyExistException;
import com.pricing.exception.NotFoundException;
import com.pricing.model.Product;
import com.pricing.model.enums.DiscountType;
import com.pricing.repository.ProductRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

public class ProductServiceTest {

    private ProductService productService;
    private ProductRepository productRepository;

    @Before
    public void setUp() {
        productRepository = Mockito.mock(ProductRepository.class);
        productService = new ProductService(productRepository);
    }

    @Test(expected = AlreadyExistException.class)
    public void whenSaveProductCalledWithValidRequest_itShouldReturnValidProduct() {

        Product product = Product.builder()
                .id(111L)
                .discountType(DiscountType.DISCOUNTLESS)
                .name("Test Product")
                .price(100F)
                .build();

        Mockito.when(productRepository.findProductByName("Test Product")).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(product)).thenReturn(product);

        productService.saveProduct(product);

        Product product1 = productService.saveProduct(product);

        Assert.assertEquals(product, product1);

        Mockito.verify(productRepository).findProductByName("Test Product");
    }

    @Test(expected = NotFoundException.class)
    public void whenUpdateProductCalledWithValidRequest_itShouldReturnValidProduct() {

        Product product = Product.builder()
                .id(222L)
                .name("Test Product")
                .discountType(DiscountType.BOGO)
                .price(100F)
                .build();

        Mockito.when(productRepository.findProductByName("Test Product")).thenReturn(Optional.ofNullable(product));
        Mockito.when(productRepository.save(product)).thenReturn(product);

        Product updateProduct = Product.builder()
                .id(222L)
                .name("Test Product 2")
                .discountType(DiscountType.BOGO)
                .price(100F)
                .build();

        Mockito.when(productRepository.save(updateProduct)).thenReturn(product);

        Product product1 = productService.updateProduct(product);

        Assert.assertEquals(product, product1);

        Mockito.verify(productRepository).findById(222L);
    }

    @Test
    public void canGetAllProducts() {
        productService.getAllProduct();
        Mockito.verify(productRepository).findAll();
    }


}