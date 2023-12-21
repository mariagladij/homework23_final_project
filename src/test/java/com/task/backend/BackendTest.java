package com.task.backend;

import com.task.dto.CartItemDto;
import com.task.dto.LoginDto;
import com.task.dto.OrdersDto;
import com.task.dto.UserMaster;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@TestInstance(TestInstance.Lifecycle.PER_METHOD) //optional. PER_METHOD is a default value.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //for @Order annotation to be working.
public class BackendTest {
    private static TestStorage storage = new TestStorage();

    @BeforeAll
    public static void beforeAll() {
        RestAssured.defaultParser = Parser.JSON;
        storage.setUsername("zx10");
        //initialize anything else...
    }

    @AfterAll
    public static void afterAll() {
        storage = null;
        //destroy anything else...
    }

    @Test
    @Order(1)
    @DisplayName("create user - positive test")
    public void testPositiveCreateUser() {
        UserMaster user = createUser(storage.getUsername());
        getRequestSpecification().body(user).post("/api/User").then().statusCode(HttpStatus.SC_OK);
    }

    @Test
    @Order(2)
    @DisplayName("create user - negative test")
    public void testNegativeCreateUser() {
        UserMaster user = createUser(null);
        getRequestSpecification().body(user).post("/api/User").then().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    @Order(3)
    @DisplayName("login user - positive test")
    public void testPositiveLoginUser() {
        UserMaster user = createUser(storage.getUsername());
        LoginDto loginDto = getRequestSpecification().body(user).post("/api/Login")
                .then().statusCode(HttpStatus.SC_OK).extract().response().as(LoginDto.class);
        Assertions.assertNotNull(loginDto.getToken());
        Assertions.assertNotNull(loginDto.getUserDetails());
        Assertions.assertTrue(loginDto.getUserDetails().getUserId() > 0);

        storage.setToken(loginDto.getToken());
        storage.setUserId(loginDto.getUserDetails().getUserId());
    }

    @Test
    @Order(4)
    @DisplayName("login user - negative test")
    public void testNegativeLoginUser() {
        UserMaster user = createUser(null);
        getRequestSpecification().body(user).post("/api/Login").then().statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    @Order(5)
    @DisplayName("add to cart - positive test")
    public void testPositiveAddToCart() {
        int userId = storage.getUserId();
        int bookId = 45; //any existing book
        Integer i = getRequestSpecification().post("/api/ShoppingCart/AddToCart/{userId}/{bookId}", userId, bookId).
                then().statusCode(HttpStatus.SC_OK).extract().response().as(Integer.class);
        Assertions.assertEquals(1, i);
        //For newly created user the Assert will always be 1.
        //The cart is going to be automatically released on CheckOut API call.
    }

    @Test
    @Order(6)
    @DisplayName("add to cart - negative test")
    public void testNegativeAddToCart() {
        //NOTE: whatever we send in request, it always returns status 200. So, use wrong method.
        int userId = storage.getUserId();
        int bookId = 45;
        getRequestSpecification().put("/api/ShoppingCart/AddToCart/{userId}/{bookId}", userId, bookId).
                then().statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
    }

    @Test
    @Order(7)
    @DisplayName("get cart items - positive test")
    public void testPositiveGetCartItems() {
        CartItemDto[] cartItems = getCartItems();
        Assertions.assertEquals(1, cartItems.length,
                "User is already having some items in the shopping cart. Please use new user");
    }

    @Test
    @Order(8)
    @DisplayName("get cart items - negative test")
    public void testNegativeGetCartItems() {
        //NOTE: whatever we send in request, it always returns status 200. So, use wrong method.
        int userId = storage.getUserId();
        getRequestSpecification().put("/api/ShoppingCart/{userId}", userId).
                then().statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
    }

    @Test
    @Order(9)
    @DisplayName("checkout - positive test")
    public void testPositiveCheckout() {
        CartItemDto[] cartItems = getCartItems();
        String orderId = "order#123";
        OrdersDto order = getOrder(orderId, cartItems);
        int userId = storage.getUserId();
        String token = storage.getToken();

        Integer i = getRequestSpecification()
                .header("Authorization", ("Bearer " + token)).body(order).post("/api/CheckOut/{userId}", userId)
                .then().statusCode(HttpStatus.SC_OK).extract().response().as(Integer.class);
        Assertions.assertEquals(0, i);
    }

    @Test
    @Order(10)
    @DisplayName("checkout - negative test")
    public void testNegativeCheckout() {
        CartItemDto[] cartItems = getCartItems();
        String orderId = "order#123";
        OrdersDto order = getOrder(orderId, cartItems);
        int userId = storage.getUserId();
        getRequestSpecification().body(order).post("/api/CheckOut/{userId}", userId)
                .then().statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    private RequestSpecification getRequestSpecification() {
        return RestAssured
                .given()
                .baseUri("https://bookcart.azurewebsites.net")
                .contentType(ContentType.JSON).log().uri();
    }

    private CartItemDto[] getCartItems() {
        int userId = storage.getUserId();
        return getRequestSpecification().get("/api/ShoppingCart/{userId}", userId).
                then().statusCode(HttpStatus.SC_OK).extract().response().as(CartItemDto[].class);
    }

    private UserMaster createUser(String username) {
        UserMaster user = new UserMaster();
        user.setFirstName("masha");
        user.setLastName("gladii");
        user.setUsername(username);
        user.setPassword("123");
        user.setGender("female");
        return user;
    }

    private OrdersDto getOrder(String orderId, CartItemDto[] cartItemsArray) {
        OrdersDto order = new OrdersDto();
        order.setOrderId(orderId);
        order.setOrderDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")));
        for (CartItemDto cartItem : cartItemsArray) {
            if (cartItem.getBook() != null) {
                double itemPrice = cartItem.getBook().getPrice() * cartItem.getQuantity();
                order.setCartTotal(order.getCartTotal() + itemPrice);
            }
        }
        order.setOrderDetails(Arrays.asList(cartItemsArray));
        return order;
    }
}
