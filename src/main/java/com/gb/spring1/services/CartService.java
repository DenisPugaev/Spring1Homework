package com.gb.spring1.services;


import com.gb.spring1.dto.ProductDto;
import com.gb.spring1.repository.ProductRepository;
import com.gb.spring1.repository.SimpleCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final SimpleCartRepository simpleCartRepository;
    private final ProductRepository productRepository;


    public List<ProductDto> getProductListInCart() {
        return simpleCartRepository.getProductListInCart();
    }

    public void add(ProductDto productDto) {
        if (simpleCartRepository.getProductListInCart().contains(productDto)) return;
        simpleCartRepository.add(productDto);
    }


    public void remove(ProductDto productDto) {
        simpleCartRepository.remove(productDto);
    }


}
