package com.task.ui.pages;

import org.openqa.selenium.WebDriver;

public class BookDetailsPage extends BasePage {
    private static final String ADD_TO_CART = "shopping_cart Add to Cart";
    private static final String SHOPPING_CART = "shopping_cart" + "\n";

    public BookDetailsPage(WebDriver driver) {
        super(driver);
    }

    public ShoppingCartPage addToCart() {
        clickWhenClickable(findButtonByText(ADD_TO_CART));
        clickWhenClickable(findButtonByText(SHOPPING_CART));
        return new ShoppingCartPage(driver);
    }
}
