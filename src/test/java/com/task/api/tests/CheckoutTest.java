package com.task.api.tests;

import com.task.api.dto.CartItemDTO;
import com.task.api.dto.LoginDTO;
import com.task.api.dto.OrdersDTO;
import com.task.api.dto.UserMasterDTO;
import com.task.api.utils.BuilderUtils;
import com.task.common.TestData;
import io.restassured.http.Header;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CheckoutTest extends BaseApiTest {

    @Test
    @DisplayName("checkout_1")
    public void testCheckout() {
        UserMasterDTO user = TestData.createUser();
        userService.register(user).then().statusCode(HttpStatus.SC_OK);
        LoginDTO loginDto = userService.login(user)
                .then().statusCode(HttpStatus.SC_OK).extract().response().as(LoginDTO.class);

        int userId = loginDto.getUserDetails().getUserId();
        int bookId = 45; //any existing book

        Integer i = shoppingCartService.addToCart(userId, bookId)
                .then().statusCode(HttpStatus.SC_OK).extract().response().as(Integer.class);
        Assertions.assertTrue(i > 0);

        CartItemDTO[] cartItems = shoppingCartService.getCartItems(userId)
                .then().statusCode(HttpStatus.SC_OK).extract().response().as(CartItemDTO[].class);

        String token = loginDto.getToken();
        String orderId = "order#123";
        OrdersDTO order = BuilderUtils.buildOrder(orderId, cartItems);

        Integer j = orderService.checkout(new Header("Authorization", ("Bearer " + token)), order, userId)
                .then().statusCode(HttpStatus.SC_OK).extract().response().as(Integer.class);

        Assertions.assertEquals(0, j);
    }

    @Test
    @DisplayName("checkout_2")
    public void testCheckoutNotAuthorized() {
        String token = "undefined";
        orderService.checkout(new Header("Authorization", ("Bearer " + token)), new OrdersDTO(), 0)
                .then().statusCode(HttpStatus.SC_UNAUTHORIZED);
    }
}
