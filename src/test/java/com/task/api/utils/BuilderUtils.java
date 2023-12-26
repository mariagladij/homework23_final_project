package com.task.api.utils;

import com.task.api.dto.CartItemDTO;
import com.task.api.dto.OrdersDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class BuilderUtils {

    public static OrdersDTO buildOrder(String orderId, CartItemDTO[] cartItemsArray) {
        OrdersDTO order = new OrdersDTO();
        order.setOrderId(orderId);
        order.setOrderDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")));
        for (CartItemDTO cartItem : cartItemsArray) {
            if (cartItem.getBook() != null) {
                double itemPrice = cartItem.getBook().getPrice() * cartItem.getQuantity();
                order.setCartTotal(order.getCartTotal() + itemPrice);
            }
        }
        order.setOrderDetails(Arrays.asList(cartItemsArray));
        return order;
    }
}
