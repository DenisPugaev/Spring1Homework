package com.gb.spring1.services;


import com.gb.spring1.aspect.annotation.Timer;
import com.gb.spring1.dto.Cart;
import com.gb.spring1.dto.OrderDetailsDto;
import com.gb.spring1.entities.Order;
import com.gb.spring1.entities.OrderItem;
import com.gb.spring1.entities.User;
import com.gb.spring1.exceptions.ResourceNotFoundException;
import com.gb.spring1.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Timer
public class OrderService {
   private final OrdersRepository ordersRepository;
   private final CartService cartService;
   private final ProductService productsService;

   @Transactional
   public void createOrder(User user, OrderDetailsDto orderDetailsDto) {
       String cartKey = cartService.getCartUuidFromSuffix(user.getUsername());
      Cart currentCart = cartService.getCurrentCart(cartKey);
      Order order = new Order();
      order.setAddress(orderDetailsDto.getAddress());
      order.setPhone(orderDetailsDto.getPhone());
      order.setUser(user);
      order.setTotalPrice(currentCart.getTotalPrice());
      List<OrderItem> items = currentCart.getItems().stream()
              .map(o -> {
                 OrderItem item = new OrderItem();
                 item.setOrder(order);
                 item.setQuantity(o.getQuantity());
                 item.setPricePerProduct(o.getPricePerProduct());
                 item.setPrice(o.getPrice());
                 item.setProduct(productsService.findById(o.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Продукт не найден!")));
                 return item;
              }).collect(Collectors.toList());
      order.setItems(items);
      ordersRepository.save(order);
       cartService.clearCart(cartKey);
   }

   public List<Order> findOrdersByUsername(String username) {
       return ordersRepository.findAllByUsername(username);
   }
}
