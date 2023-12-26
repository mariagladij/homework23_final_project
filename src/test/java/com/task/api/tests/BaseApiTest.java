package com.task.api.tests;

import com.task.api.services.OrderService;
import com.task.api.services.ShoppingCartService;
import com.task.api.services.UserService;

public class BaseApiTest {
    protected ShoppingCartService shoppingCartService = new ShoppingCartService();
    protected OrderService orderService = new OrderService();
    protected UserService userService = new UserService();

}
