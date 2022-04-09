package com.example.pricing.model;

import com.example.pricing.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "basket")
@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedNativeQuery(
        name = "find_product_with_dto",
        query =
                " SELECT p.name as productName, p.price, "
                        + " COUNT(p.id) as productCount, "
                        + " COUNT(p.id)*p.price as totalPrice "
                        + " FROM Basket b LEFT JOIN Product p ON b.product_id = p.id "
                        + " GROUP BY p.name, p.price ",
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
                        @ColumnResult(name = "totalPrice", type = Float.class)
                }
        )
)
public class Basket implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @OneToMany
    @JoinColumn(name = "id")
    private List<Product> products;

}
