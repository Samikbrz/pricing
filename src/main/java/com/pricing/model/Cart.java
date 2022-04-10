package com.pricing.model;

import com.pricing.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cart")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@NamedNativeQuery(
        name = "find_product_with_dto",
        query =
                " SELECT p.name as productName, p.price, c.total_count as productCount,"
                        + " p.discount_type discountType, c.total_count*p.price as totalPrice "
                        + " FROM product p "
                        + " RIGHT JOIN cart c on p.id = c.product_id",
        resultSetMapping = "get_products_with_count"
)
@SqlResultSetMapping(
        name = "get_products_with_count",
        classes = @ConstructorResult(
                targetClass = ProductDto.class,
                columns = {
                        @ColumnResult(name = "productName", type = String.class),
                        @ColumnResult(name = "price", type = Float.class),
                        @ColumnResult(name = "productCount", type = Integer.class),
                        @ColumnResult(name = "totalPrice", type = Float.class),
                        @ColumnResult(name = "discountType", type = String.class)
                }
        )
)
public class Cart implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @OneToMany
    @JoinColumn(name = "id")
    private List<Product> products;

    @Column(name = "total_count")
    private int totalCount;

}
