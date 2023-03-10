package com.gb.spring.web.cart;

import com.gb.spring.web.cart.configs.RedisConfig;
import com.gb.spring.web.cart.integrations.ProductsServiceIntegration;
import com.gb.spring.web.cart.services.CartService;

import com.gb.web.api.core.ProductDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest(classes = {CartService.class, RedisConfig.class})
public class CartTest {

    @Autowired
    private  CartService cartService;

    @MockBean
    private ProductsServiceIntegration productService;
   private final ProductDto product = new ProductDto();
   private final ProductDto product2 = new ProductDto();
   private final ProductDto product3 = new ProductDto();

    @BeforeEach
    public void initCart() {
        cartService.clearCart("test_cart1");
        cartService.clearCart("test_cart2");

    }
    @BeforeEach
    public void initProducts() {
        product.setId(1L);
        product.setTitle("phone");
        product.setPrice(BigDecimal.valueOf(100.0));
        product2.setId(2L);
        product2.setTitle("TV");
        product2.setPrice(BigDecimal.valueOf(300.0));
        product3.setId(3L);
        product3.setTitle("clock");
        product3.setPrice(BigDecimal.valueOf(50.0));
    }


    public void addProductToCart() {
        Mockito.doReturn(Optional.of(product)).when(productService).findById(1L);
        Mockito.doReturn(Optional.of(product2)).when(productService).findById(2L);
        Mockito.doReturn(Optional.of(product3)).when(productService).findById(3L);
        cartService.addToCart("test_cart1", 1L);
        cartService.addToCart("test_cart1", 1L);
        cartService.addToCart("test_cart1", 2L);
        cartService.addToCart("test_cart2", 3L);
    }

    @Test
    public void addToCartTest() {
        addProductToCart();
        Mockito.verify(productService, Mockito.times(2)).findById(ArgumentMatchers.eq(1L));
        Mockito.verify(productService, Mockito.times(1)).findById(ArgumentMatchers.eq(2L));
        Assertions.assertEquals(2, cartService.getCurrentCart("test_cart1").getItems().size());
        Assertions.assertEquals(new BigDecimal("500.0"), cartService.getCurrentCart("test_cart1").getTotalPrice());
    }
    @Test
    public void mergeCartTest() {
        addProductToCart();
        cartService.merge("test_cart1","test_cart2");
        Assertions.assertEquals(3, cartService.getCurrentCart("test_cart1").getItems().size());
    }

    @Test
    public void clearCartTest() {
        addProductToCart();
        cartService.clearCart("test_cart1");
        cartService.clearCart("test_cart2");
        Assertions.assertEquals(0, cartService.getCurrentCart("test_cart1").getItems().size());
        Assertions.assertEquals(0, cartService.getCurrentCart("test_cart2").getItems().size());
    }

}
