package com.gb.spring1.dto;

import com.gb.spring1.entities.Product;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
public class Cart {

    private List<OrderItemDto> items;
    private BigDecimal totalPrice;

    public Cart(){
        this.items = new ArrayList<>();
    }

    public void clear() {
        items.clear();
        totalPrice = BigDecimal.ZERO;
    }

    private void recalculate() {
        totalPrice = BigDecimal.ZERO;
        for (OrderItemDto o : items) {
            totalPrice = totalPrice.add(o.getPrice());
        }
    }

    public  void addProduct(Product product){
        if(addProduct(product.getId())){
            return;
        }
        items.add(new OrderItemDto(product));
        recalculate();
    }

    public  boolean addProduct(Long id){
        for (OrderItemDto o : items){
            if(o.getProductId().equals(id)){
                o.changeQuantity(1);
                recalculate();
                return true;
            }
        }
        return false;
    }
    public void decreaseProduct(Long id) {
        Iterator<OrderItemDto> iter = items.iterator();
        while (iter.hasNext()) {
            OrderItemDto o = iter.next();
            if (o.getProductId().equals(id)) {
                o.changeQuantity(-1);
                if (o.getQuantity() <= 0) {
                    iter.remove();
                }
                recalculate();
                return;
            }
        }
    }

    public void removeProduct(Long id) {
        items.removeIf(o -> o.getProductId().equals(id));
        recalculate();
    }



}
