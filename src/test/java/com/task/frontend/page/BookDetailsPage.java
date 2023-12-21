package com.task.frontend.page;

import com.task.frontend.Util;
import org.openqa.selenium.WebDriver;

public class BookDetailsPage extends BasePage {
    private static final String ADD_TO_CART = "shopping_cart Add to Cart";
    private static final String SHOPPING_CART = "shopping_cart" + "\n";

    public BookDetailsPage(WebDriver driver) {
        super(driver);
        Util.delay(2);
    }

    public ShoppingCartPage addToCart() {
        findButtonByText(ADD_TO_CART).click();
        findButtonByText(SHOPPING_CART).click();
        return new ShoppingCartPage(driver);
    }
}
