package com.task.frontend.page;

import com.task.dto.LoginDto;
import com.task.dto.UserMaster;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private static final By USERNAME_FIELD_BY = By.id("mat-input-0");
    private static final By PASSWORD_FIELD_BY = By.id("mat-input-1");

    public LoginPage(WebDriver driver) {
        super(driver);
        waitUntil(USERNAME_FIELD_BY);
    }

    public CheckoutPage login(String username) {
        UserMaster user = registerUser(username);

        findElement(USERNAME_FIELD_BY).sendKeys(user.getUsername());
        findElement(PASSWORD_FIELD_BY).sendKeys(user.getPassword(), Keys.RETURN);

        LoginDto loginDto = callLoginUser(user); //parallel login to get Token+UserId for future use.
        Assertions.assertNotNull(loginDto);

        driver.manage().addCookie(new Cookie(TEST_USERID, String.valueOf(loginDto.getUserDetails().getUserId())));
        driver.manage().addCookie(new Cookie(TEST_TOKEN, loginDto.getToken()));

        return new CheckoutPage(driver);
    }

    private UserMaster registerUser(String username) {
        UserMaster user = createUser(username);
        callRegisterUser(user);
        return user;
    }

    private UserMaster createUser(String username) {
        UserMaster user = new UserMaster();
        user.setFirstName("mariia");
        user.setLastName("gladiy");
        user.setUsername(username);
        user.setPassword("123");
        user.setGender("female");
        return user;
    }
}
