package com.task.frontend;

import com.task.frontend.page.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

@TestInstance(TestInstance.Lifecycle.PER_METHOD) //optional. PER_METHOD is a default value.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //for @Order annotation to be working.
public class FrontendTest {


    @Test
    @Order(1)
    @DisplayName("E2E UI test")
    public void testUi() {
        WebDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get(BasePage.HOME_PAGE_URL);

        HomePage homePage = new HomePage(driver);
        BookDetailsPage bookDetailsPage = homePage.findBook("HP", 7);
        ShoppingCartPage shoppingCartPage = bookDetailsPage.addToCart();
        LoginPage loginPage = shoppingCartPage.checkout();
        CheckoutPage checkoutPage = loginPage.login("zx10");
        OrdersPage ordersPage = checkoutPage.placeOrder();
        ordersPage.validateOrder();
        driver.quit();
    }

}
