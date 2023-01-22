package com.gb.spring1.controllers;


import com.gb.spring1.converters.ProductConverter;
import com.gb.spring1.dto.ProductDto;
import com.gb.spring1.dto.ProfileDto;
import com.gb.spring1.entities.Product;
import com.gb.spring1.services.ProductService;
import com.gb.spring1.validators.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProfileController {

    private final ProductService productService;
    private final ProductConverter productConverter;
    private final ProductValidator productValidator;

    @GetMapping("/profile")
    public ProfileDto getCurrentUserInfo(Principal principal) {
        return new ProfileDto(principal.getName());
    }


    @PostMapping("/admin/product")
    public ProductDto saveNewProduct(@RequestBody ProductDto productDto) {
        productValidator.validate(productDto);
        Product product = productConverter.dtoInEntity(productDto);
        product = productService.save(product);
        return productConverter.entityInDto(product);
    }

    @DeleteMapping("/admin/product/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteById(id);
    }
}