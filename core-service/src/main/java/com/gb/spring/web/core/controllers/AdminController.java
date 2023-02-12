package com.gb.spring.web.core.controllers;


import com.gb.spring.web.core.converters.ProductConverter;
import com.gb.spring.web.core.dto.ProductDto;
import com.gb.spring.web.core.dto.ProfileDto;
import com.gb.spring.web.core.entities.Product;
import com.gb.spring.web.core.services.ProductService;
import com.gb.spring.web.core.validators.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final ProductService productService;
    private final ProductConverter productConverter;
    private final ProductValidator productValidator;

    @GetMapping("/profile")
    public ProfileDto getCurrentUserInfo(Principal principal) {
        return new ProfileDto(principal.getName());
    }


    @PostMapping("/product")
    public ProductDto saveNewProduct(@RequestBody ProductDto productDto) {
        productValidator.validate(productDto);
        Product product = productConverter.dtoInEntity(productDto);
        product = productService.save(product);
        return productConverter.entityInDto(product);
    }

    @DeleteMapping("/product/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteById(id);
    }
}