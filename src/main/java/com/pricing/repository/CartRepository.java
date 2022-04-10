package com.pricing.repository;

import com.pricing.dto.ProductDto;
import com.pricing.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query(nativeQuery = true, name = "find_product_with_dto")
    List<ProductDto> getProductsWithCount();

    @Query("select b from Cart b where b.productId = ?1")
    Cart findByProductId(long productId);

    @Transactional
    @Modifying
    @Query("update Cart set totalCount = :newCount where productId = :productId")
    void updateCountByProductId(@Param("newCount") int newCount, @Param("productId") Long productId);

    @Transactional
    @Modifying
    @Query("delete from Cart b where b.productId = ?1")
    void deleteByProductId(long productId);
}
