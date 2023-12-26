package com.task.ui.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ShoppingCartPage extends BasePage {
    private static final String CHECKOUT = "CheckOut";
    private static final By CHECKOUT_BUTTON =
            By.xpath("//button[contains(@class,'mat-elevation-z4')]/span[@class='mat-button-wrapper']");

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage checkout() {
        clickWhenClickable(findCheckoutButton());
        return new LoginPage(driver);
    }

    private WebElement findCheckoutButton() {
        List<WebElement> elements = findElements(CHECKOUT_BUTTON);
        for (WebElement element : elements) {
            if (CHECKOUT.equals(element.getText())) {
                return element;
            }
        }
        return Assertions.fail();
    }
}
