package com.gb.spring.web.core.dto;

import com.gb.spring.web.core.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {

    private Long productId;
    private String productTitle;

    private int quantity;
    private BigDecimal pricePerProduct;
    private BigDecimal price;

    public OrderItemDto(Product product){
        this.productId = product.getId();
        this.productTitle = product.getTitle();
        this.quantity = 1;
        this.pricePerProduct = product.getPrice();
        this.price = product.getPrice();
    }

    public void changeQuantity(int delta){
        this.quantity +=delta;
        this.price = this.pricePerProduct.multiply(BigDecimal.valueOf(this.quantity));
    }

}