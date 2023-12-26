package com.task.ui.pages;

import com.task.api.dto.LoginDTO;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private static final By USERNAME_FIELD_BY = By.xpath("//input[@formcontrolname='username']");
    private static final By PASSWORD_FIELD_BY = By.xpath("//input[@formcontrolname='password']");

    public LoginPage(WebDriver driver) {
        super(driver);
        waitUntilVisible(USERNAME_FIELD_BY);
    }

    public CheckoutPage login(LoginDTO loginDto) {
        findElement(USERNAME_FIELD_BY).sendKeys(loginDto.getUserDetails().getUsername());
        findElement(PASSWORD_FIELD_BY).sendKeys(loginDto.getUserDetails().getPassword(), Keys.RETURN);
        return new CheckoutPage(driver);
    }
}
