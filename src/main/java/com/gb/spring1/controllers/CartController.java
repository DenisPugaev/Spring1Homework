package com.gb.spring1.controllers;


import com.gb.spring1.converters.ProductConverter;
import com.gb.spring1.dto.Cart;
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
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;



    @GetMapping()
    public Cart getCurrentCart() {
        return cartService.getCurrentCart();
    }

    @GetMapping("/add/{id}")
    public void addProductToCart(@PathVariable Long id) {
        cartService.addProductByIdToCart(id);
    }

    @GetMapping("/delete/{id}")
    public void deleteProductFromCart(@PathVariable Long id) {
       cartService.deleteProductByIdFromCart(id);
    }
    @GetMapping("/clear")
    public void clearCart() {
        cartService.getCurrentCart().clear();
    }
}
