package com.task.api.tests;

import com.task.api.dto.CartItemDTO;
import com.task.api.dto.LoginDTO;
import com.task.api.dto.UserMasterDTO;
import com.task.common.TestData;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GetCartItemsTest extends BaseApiTest {

    @Test
    @DisplayName("get cart items_1")
    public void testGetCartItems() {
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

        Assertions.assertTrue(cartItems.length > 0);
    }

    @Test
    @DisplayName("get cart items_2")
    public void testGetCartItemsWithEmptyUserId() {
        shoppingCartService.getCartItems("").then().statusCode(HttpStatus.SC_OK); //returns 200 for empty 'userId'
    }
}
