package com.task.api.services;

import io.restassured.response.Response;

public class ShoppingCartService extends BaseService {
    private static final String ADD_TO_CART_PATH = "/api/ShoppingCart/AddToCart/{userId}/{bookId}";
    private static final String GET_CART_ITEMS_PATH = "/api/ShoppingCart/{userId}";

    public Response addToCart(Object... vars) {
        return getRequestSpecification().post(ADD_TO_CART_PATH, vars);
    }

    public Response getCartItems(Object... vars) {
        return getRequestSpecification().get(GET_CART_ITEMS_PATH, vars);
    }
}
