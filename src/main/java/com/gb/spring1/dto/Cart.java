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

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void add(Product product) {
        if (add(product.getId())) {
            return;
        }
        items.add(new OrderItemDto(product));
        recalculate();
    }

    public boolean add(Long id) {
        for (OrderItemDto o : items) {
            if (o.getProductId().equals(id)) {
                o.changeQuantity(1);
                recalculate();
                return true;
            }
        }
        return false;
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

    public void decrement(Long productId) {
        Iterator<OrderItemDto> iter = items.iterator();
        while (iter.hasNext()) {
            OrderItemDto o = iter.next();
            if (o.getProductId().equals(productId)) {
                o.changeQuantity(-1);
                if (o.getQuantity() <= 0) {
                    iter.remove();
                }
                recalculate();
                return;
            }
        }
    }

    public void remove(Long id) {
        items.removeIf(o -> o.getProductId().equals(id));
        recalculate();
    }

    public void merge(Cart another) {
        for (OrderItemDto anotherItem : another.items) {
            boolean merged = false;
            for (OrderItemDto myItem : items) {
                if (myItem.getProductId().equals(anotherItem.getProductId())) {
                    myItem.changeQuantity(anotherItem.getQuantity());
                    merged = true;
                    break;
                }
            }
            if (!merged) {
                items.add(anotherItem);
            }
        }
        recalculate();
        another.clear();
    }


}
