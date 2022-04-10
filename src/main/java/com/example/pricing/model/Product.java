package com.example.pricing.model;

import com.example.pricing.model.enums.DiscountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Product implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "has_discount")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean hasDiscount;

    @Column(name = "discount_rate")
    private Float discountRate;

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    @Column(name = "price")
    private Float price;
}
