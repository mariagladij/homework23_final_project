package com.task.frontend.page;

import com.task.dto.CartItemDto;
import com.task.dto.LoginDto;
import com.task.dto.OrdersDto;
import com.task.dto.UserMaster;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage {
    public static final String HOME_PAGE_URL = "https://bookcart.azurewebsites.net/";
    public static final String TEST_USERID = "test_UserId"; //cookie
    public static final String TEST_TOKEN = "test_Token"; //cookie
    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    private RequestSpecification getRequestSpecification() {
        return RestAssured.given().baseUri(HOME_PAGE_URL).contentType(ContentType.JSON).log().uri();
    }

    //If the element becomes visible before the timeout, the script will continue execution.
    protected void waitUntil(By by) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    //--- finders ---
    protected WebElement findElement(SearchContext rootElement, By by) {
        WebElement element = rootElement.findElement(by);
        Assertions.assertNotNull(element, "No element found " + by);
        return element;
    }

    protected List<WebElement> findElements(SearchContext rootElement, By by) {
        List<WebElement> elements = rootElement.findElements(by);
        Assertions.assertNotEquals(0, elements.size(), "No elements found " + by);
        return elements;
    }

    protected WebElement findElement(By by) {
        return findElement(driver, by);
    }

    protected List<WebElement> findElements(By by) {
        return findElements(driver, by);
    }

    protected WebElement findButtonByText(String text) {
        List<WebElement> elements = findElements(By.tagName("button"));
        for (WebElement element : elements) {
            if (StringUtils.startsWith(element.getText(), text)) {
                Assertions.assertNotNull(element, "No button found with text: " + text);
                return element;
            }
        }
        return null;
    }

    //--- api calls---
    protected OrdersDto[] callGetOrders(String token, int userId) {
        return getRequestSpecification()
                .header("Authorization", ("Bearer " + token))
                .get("/api/Order/{userId}", userId)
                .then().statusCode(HttpStatus.SC_OK).extract().response().as(OrdersDto[].class);
    }

    protected CartItemDto[] callGetShoppingCartItems(int userId) {
        return getRequestSpecification().get("/api/ShoppingCart/{userId}", userId)
                .then().statusCode(HttpStatus.SC_OK).extract().response().as(CartItemDto[].class);
    }

    protected LoginDto callLoginUser(UserMaster user) {
        return getRequestSpecification().body(user).post("/api/Login")
                .then().statusCode(HttpStatus.SC_OK).extract().response().as(LoginDto.class);
    }

    protected void callRegisterUser(UserMaster user) {
        getRequestSpecification().body(user).post("/api/User").then().statusCode(HttpStatus.SC_OK);
    }
}
