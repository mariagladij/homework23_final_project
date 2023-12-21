package com.task.frontend.page;

import com.task.frontend.Util;
import org.openqa.selenium.WebDriver;

public class ShoppingCartPage extends BasePage {
    private static final String CHECKOUT = "CheckOut";

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
        Util.delay(2);
    }

    public LoginPage checkout() {
        findButtonByText(CHECKOUT).click();
        return new LoginPage(driver);
    }
}
