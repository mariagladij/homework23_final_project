package com.task.api.tests;

import com.task.api.dto.LoginDTO;
import com.task.api.dto.UserMasterDTO;
import com.task.common.TestData;
import com.task.listeners.LoggingRule;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.Rule;

public class AddToCartTest extends BaseApiTest {

    @Rule
    public LoggingRule loggingRule = new LoggingRule();
    @Test
    @DisplayName("add to cart_1")
    public void testAddToCart() {
        UserMasterDTO user = TestData.createUser();
        userService.register(user).then().statusCode(HttpStatus.SC_OK);
        LoginDTO loginDto = userService.login(user)
                .then().statusCode(HttpStatus.SC_OK).extract().response().as(LoginDTO.class);

        int userId = loginDto.getUserDetails().getUserId();
        int bookId = 45; //any existing book

        Integer i = shoppingCartService.addToCart(userId, bookId)
                .then().statusCode(HttpStatus.SC_OK).extract().response().as(Integer.class);

        Assertions.assertTrue(i > 0);
    }

    @Test
    @DisplayName("add to cart_2")
    public void testAddToCartWithEmptyBookId() {
        shoppingCartService.addToCart("", "").then().statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
    }
}
