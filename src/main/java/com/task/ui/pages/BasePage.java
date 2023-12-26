package com.task.ui.pages;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage {
    protected WebDriver driver;
    private WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    }

    protected void clickWhenClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    protected void waitUntilVisible(By by) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    //--- finders ---
    protected WebElement findElement(SearchContext rootElement, By by) {
        WebElement element = rootElement.findElement(by);
        Assertions.assertNotNull(element, "No element found " + by);
        return element;
    }

    protected List<WebElement> findElements(SearchContext searchContext, By by) {
        List<WebElement> elements = searchContext.findElements(by);
        Assertions.assertNotEquals(0, elements.size(), "No elements found " + by);
        return elements;
    }

    protected WebElement findElement(By by) {
        return findElement(driver, by);
    }

    protected List<WebElement> findElements(By by) {
        return findElements(driver, by);
    }

    protected WebElement findButtonByText(String text) {
        List<WebElement> elements = findElements(By.tagName("button"));
        for (WebElement element : elements) {
            if (StringUtils.startsWith(element.getText(), text)) {
                Assertions.assertNotNull(element, "No button found with text: " + text);
                return element;
            }
        }
        return null;
    }
}
