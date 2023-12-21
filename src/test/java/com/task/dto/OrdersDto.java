package com.task.dto;

import java.util.List;

public class OrdersDto {
    private String orderId;
    private List<CartItemDto> orderDetails;
    private double cartTotal;
    private String orderDate;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<CartItemDto> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<CartItemDto> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public double getCartTotal() {
        return cartTotal;
    }

    public void setCartTotal(double cartTotal) {
        this.cartTotal = cartTotal;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
