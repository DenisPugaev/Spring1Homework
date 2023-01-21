package com.gb.spring1.repository;


import com.gb.spring1.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@NoArgsConstructor
@AllArgsConstructor
public class SimpleCartRepository {
    private List<ProductDto> productListInCart = new ArrayList<>();

    public List<ProductDto> getProductListInCart() {
        return productListInCart;
    }

    public void add(ProductDto productDto) {
        productListInCart.add(productDto);
    }


    public void remove(ProductDto productDto) {
        productListInCart.remove(productDto);
    }

}
