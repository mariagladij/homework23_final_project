package com.task.ui.pages;

import com.task.api.dto.BookDTO;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends BasePage {
    private static final By SEARCH_FIELD_BY = By.className("mat-autocomplete-trigger");
    private static final By BOOKS_IMG_BY = By.xpath("//img[@class='mat-card-image preview-image']");

    public HomePage(WebDriver driver) {
        super(driver);
        waitUntilVisible(SEARCH_FIELD_BY);
    }

    public List<BookDTO> findBooks(String searchValue) {
        WebElement searchField = findElement(SEARCH_FIELD_BY);
        searchField.sendKeys(searchValue, Keys.RETURN);
        searchField.sendKeys(Keys.TAB);

        List<BookDTO> books = new ArrayList<>();
        List<WebElement> bookElements = findElements(BOOKS_IMG_BY);

        for (WebElement e : bookElements) {
            BookDTO book = new BookDTO();
            book.setCoverFileName(e.getAttribute("src"));
            books.add(book);
        }

        return books;
    }

    public BookDetailsPage chooseBook(BookDTO book) {
        String xpath = String.format("//img[contains(@src, '%s')]",
                StringUtils.substringAfterLast(book.getCoverFileName(), "/"));
        clickWhenClickable(findElement(By.xpath(xpath)));
        return new BookDetailsPage(driver);
    }
}
