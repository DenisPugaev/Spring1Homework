package com.gb.spring1.entities;

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

    @Column(name = "manufacturer")
    private String manufacturer;

    public Product(Long id, String title, BigDecimal price, String manufacturer) {
        this.title = title;
        this.price = price;
        this.manufacturer = manufacturer;
    }

    public Product() {
    }
}
