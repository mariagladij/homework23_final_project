package com.task.frontend.page;

import com.task.frontend.Util;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HomePage extends BasePage {
    private static final By SEARCH_FIELD_BY = By.className("mat-autocomplete-trigger");
    private static final By BOOKS_IMG_BY = By.xpath("//img[@class='mat-card-image preview-image']");

    public HomePage(WebDriver driver) {
        super(driver);
        waitUntil(SEARCH_FIELD_BY);
    }

    public BookDetailsPage findBook(String searchValue, int expectedBooksCount) {
        WebElement searchField = findElement(SEARCH_FIELD_BY);
        searchField.sendKeys(searchValue, Keys.RETURN);
        searchField.sendKeys(Keys.TAB);
        Util.delay(1); //let page to show search results

        List<WebElement> books = findElements(BOOKS_IMG_BY);

        int actualBooksCount = books.size();
        Assertions.assertEquals(expectedBooksCount, actualBooksCount);
        return chooseBook(books);
    }

    private BookDetailsPage chooseBook(List<WebElement> books) {
        books.get(0).click(); //choose 1st book
        return new BookDetailsPage(driver);
    }
}
