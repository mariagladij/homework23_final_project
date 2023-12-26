package com.task.ui.tests;

import com.task.api.services.BaseService;
import com.task.api.services.OrderService;
import com.task.api.services.ShoppingCartService;
import com.task.api.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class BaseUiTest {
    protected ShoppingCartService shoppingCartService = new ShoppingCartService();
    protected OrderService orderService = new OrderService();
    protected UserService userService = new UserService();

    protected WebDriver driver;

    @BeforeEach
    public void beforeEach() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.get(BaseService.BASE_URI);
    }

    @AfterEach
    public void afterEach() {
        if (driver != null) {
            driver.quit();
        }
    }
}
