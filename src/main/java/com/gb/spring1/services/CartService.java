package com.gb.spring1.services;


import com.gb.spring1.dto.Cart;
import com.gb.spring1.entities.Product;
import com.gb.spring1.exceptions.ResourceNotFoundException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private Cart cart;

    private final ProductService productService;

    @PostConstruct
    public  void init(){cart= new Cart();}
    public Cart getCurrentCart(){return  cart;}

    public  void addProductByIdToCart(Long productId){
        if(!getCurrentCart().addProduct(productId)){
            Product product=productService.findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Ошибка добавление продукта в корзину! Продукт не найден. ID продукта: " + productId));
            getCurrentCart().addProduct(product);
        }
    }

    public void clear(){getCurrentCart().clear();}

    public void deleteProductByIdFromCart(Long id) {
        getCurrentCart().decreaseProduct(id);

    }
}
