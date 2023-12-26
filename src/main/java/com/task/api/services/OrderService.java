package com.task.api.services;

import com.task.api.dto.BaseDTO;
import io.restassured.http.Header;
import io.restassured.response.Response;

public class OrderService extends BaseService {
    private static final String CHECKOUT_PATH = "/api/CheckOut/{userId}";
    private static final String GET_ALL_ORDERS_PATH = "/api/Order/{userId}";

    public Response checkout(Header header, BaseDTO body, Object... vars) {
        return getRequestSpecification().header(header).body(body).post(CHECKOUT_PATH, vars);
    }

    public Response getAllOrders(Header header, Object... vars) {
        return getRequestSpecification().header(header).get(GET_ALL_ORDERS_PATH, vars);
    }
}
