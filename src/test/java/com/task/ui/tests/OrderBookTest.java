package com.task.ui.tests;

import com.task.api.dto.*;
import com.task.common.TestData;
import com.task.listeners.LoggingRule;
import com.task.ui.pages.*;
import io.restassured.http.Header;
import org.apache.http.HttpStatus;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class OrderBookTest extends BaseUiTest {

    @Rule
    public LoggingRule loggingRule = new LoggingRule();
    @Test
    @DisplayName("E2E UI test")
    public void testOrderBook() {
        HomePage homePage = new HomePage(driver);
        List<BookDTO> books = homePage.findBooks("HP");
        Assertions.assertEquals(7, books.size());
        books.forEach(b -> Assertions.assertNotNull(b.getCoverFileName()));

        BookDetailsPage bookDetailsPage = homePage.chooseBook(books.get(0));

        ShoppingCartPage shoppingCartPage = bookDetailsPage.addToCart();

        LoginPage loginPage = shoppingCartPage.checkout();

        UserMasterDTO user = TestData.createUser();
        userService.register(user).then().statusCode(HttpStatus.SC_OK);
        LoginDTO loginDto = userService.login(user)
                .then().statusCode(HttpStatus.SC_OK).extract().response().as(LoginDTO.class);
        Assertions.assertNotNull(loginDto.getToken());
        Assertions.assertNotNull(loginDto.getUserDetails());
        Assertions.assertTrue(loginDto.getUserDetails().getUserId() > 0);
        loginDto.getUserDetails().setUsername(user.getUsername());
        loginDto.getUserDetails().setPassword(user.getPassword());

        CheckoutPage checkoutPage = loginPage.login(loginDto);

        OrdersDTO order = checkoutPage.getOrder();

        int userId = loginDto.getUserDetails().getUserId();
        CartItemDTO[] expectedCartItems = shoppingCartService.getCartItems(userId)
                .then().statusCode(HttpStatus.SC_OK).extract().response().as(CartItemDTO[].class);
        List<CartItemDTO> actualCartItems = order.getOrderDetails();
        Assertions.assertEquals(expectedCartItems.length, actualCartItems.size());
        double cartTotalExpected = 0;
        for (int i = 0; i < expectedCartItems.length; i++) {
            Assertions.assertEquals(expectedCartItems[i].getBook().getBookId(), actualCartItems.get(i).getBook().getBookId());
            Assertions.assertEquals(expectedCartItems[i].getBook().getTitle(), actualCartItems.get(i).getBook().getTitle());
            Assertions.assertEquals(expectedCartItems[i].getBook().getPrice(), actualCartItems.get(i).getBook().getPrice());
            Assertions.assertEquals(expectedCartItems[i].getQuantity(), actualCartItems.get(i).getQuantity());
            cartTotalExpected += actualCartItems.get(i).getBook().getPrice() * actualCartItems.get(i).getQuantity();
        }
        Assertions.assertEquals(cartTotalExpected, order.getCartTotal());

        OrdersPage ordersPage = checkoutPage.placeOrder();

        List<OrdersDTO> orders = ordersPage.getAllOrders();

        OrdersDTO[] ordersArray = orderService.getAllOrders(new Header("Authorization", ("Bearer " + loginDto.getToken())), userId)
                .then().statusCode(HttpStatus.SC_OK).extract().response().as(OrdersDTO[].class);
        Assertions.assertEquals(ordersArray.length, orders.size());
        for (int i = 0; i < ordersArray.length; i++) {
            Assertions.assertEquals(ordersArray[i].getOrderId(), orders.get(i).getOrderId());
            Assertions.assertEquals(ordersArray[i].getOrderId(), orders.get(i).getOrderId());
            Assertions.assertEquals(ordersArray[i].getCartTotal(), orders.get(i).getCartTotal());
        }
    }
}
