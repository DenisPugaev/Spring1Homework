package com.gb.spring.web.core.services;



import com.gb.spring.web.core.dto.Cart;
import com.gb.spring.web.core.entities.Product;
import com.gb.web.api.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductService productsService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${utils.cart.prefix}")
    private String cartPrefix;

    public String getCartUuidFromSuffix(String suffix) {
        return cartPrefix + suffix;
    }

    public String generateCartUuid() {
        return UUID.randomUUID().toString();
    }

    public Cart getCurrentCart(String cartKey) {
        if (!redisTemplate.hasKey(cartKey)) {
            redisTemplate.opsForValue().set(cartKey, new Cart());
        }
        return (Cart) redisTemplate.opsForValue().get(cartKey);
    }

    public void addToCart(String cartKey, Long productId) {
        Product product = productsService.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Невозможно добавить продукт в корзину. Продукт не найдет, id: " + productId));
        execute(cartKey, c -> {
            c.add(product);
        });
    }

    public void clearCart(String cartKey) {
        execute(cartKey, Cart::clear);
    }

    public void removeItemFromCart(String cartKey, Long productId) {
        execute(cartKey, c -> c.remove(productId));
    }

    public void decrementItem(String cartKey, Long productId) {
        execute(cartKey, c -> c.decrement(productId));
    }




    /**
     * Метод склеивания двух корзин при авторизации пользователя.
     * @param userCartKey - корзина авторизированого пользователя
     * @param guestCartKey - корзина гостя
     */
    public void merge(String userCartKey, String guestCartKey) {
        Cart guestCart = getCurrentCart(guestCartKey);//получения гостевой корзины
        Cart userCart = getCurrentCart(userCartKey);//получения корзины пользователя
        userCart.merge(guestCart);// добавение в корзину пользователя всех продуктов из гостевой корзины
        updateCart(guestCartKey, guestCart);// обновление корзин
        updateCart(userCartKey, userCart);
    }

    private void execute(String cartKey, Consumer<Cart> action) {
        Cart cart = getCurrentCart(cartKey);// достаем корзину из Redis
        action.accept(cart); // выполняем модифицируеющий метод корзины, например add(),clear(),update() и тд.
        redisTemplate.opsForValue().set(cartKey, cart); //отправка модифицированной корзины в Redis
    }

    public void updateCart(String cartKey, Cart cart) {
        redisTemplate.opsForValue().set(cartKey, cart);
    }
}