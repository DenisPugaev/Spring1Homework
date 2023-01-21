package com.gb.spring1.controllers;


import com.gb.spring1.converters.ProductConverter;
import com.gb.spring1.dto.ProductDto;
import com.gb.spring1.entities.Product;
import com.gb.spring1.exceptions.ResourceNotFoundException;
import com.gb.spring1.services.CartService;
import com.gb.spring1.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final ProductService productService;
    private final ProductConverter productConverter;


    @GetMapping("/cart")
    public List<ProductDto> getProductsCartList() {
        return cartService.getProductListInCart();
    }

    @GetMapping("/cart/{id}")
    public void addProductInCart(@PathVariable Long id) {
        Product product = productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found, id: " + id));
        log.info(productConverter.entityInDto(product).getTitle());
        cartService.add(productConverter.entityInDto(product));
    }

    @DeleteMapping("/cart/{id}")
    public void deleteProductFromCart(@PathVariable Long id) {
        Product product = productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found, id: " + id));
        cartService.remove(productConverter.entityInDto(product));
    }
}
