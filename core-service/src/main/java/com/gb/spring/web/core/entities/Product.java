package com.gb.spring.web.core.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private BigDecimal price;


    public Product(Long id, String title, BigDecimal price) {
        this.title = title;
        this.price = price;

    }

    public Product() {
    }
}
