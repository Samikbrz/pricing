package com.example.pricing.repository;

import com.example.pricing.dto.ProductDto;
import com.example.pricing.model.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {

    @Query(nativeQuery = true, name = "find_product_with_dto")
    List<ProductDto> getProductsWithCount();
}
