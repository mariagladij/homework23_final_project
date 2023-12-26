package com.task.api.dto;

import java.util.List;

public class OrdersDTO extends BaseDTO {
    private String orderId;
    private List<CartItemDTO> orderDetails;
    private double cartTotal;
    private String orderDate;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<CartItemDTO> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<CartItemDTO> orderDetails) {
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
