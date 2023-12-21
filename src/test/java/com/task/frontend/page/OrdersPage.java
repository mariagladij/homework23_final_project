package com.task.frontend.page;

import com.task.dto.OrdersDto;
import com.task.frontend.Util;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class OrdersPage extends BasePage {
    private static final By NEXT_BUTTON_BY = By.className("mat-paginator-navigation-next");
    private static final By TABLE_ROWS_BY = By.xpath("//tbody[@role='rowgroup']/tr[contains(@class, 'example-element-row')]");
    private static final By TABLE_COLUMNS_BY = By.xpath(".//td");

    public OrdersPage(WebDriver driver) {
        super(driver);
        waitUntil(TABLE_ROWS_BY); //slow process: The more orders, the longer a time of page loading!
    }

    public void validateOrder() {
        List<OrdersDto> orders = new ArrayList<>();
        inspectOrdersTable(orders);
        validateOrders(orders);
    }

    private void inspectOrdersTable(List<OrdersDto> orders) {
        orders.addAll(inspectOrdersTable());
        WebElement nextButton = findElement(driver, NEXT_BUTTON_BY);
        if (nextButton.isEnabled()) {
            nextButton.click();
            Util.delay(2);
            inspectOrdersTable(orders); //recursion
        }
    }

    private List<OrdersDto> inspectOrdersTable() {
        List<OrdersDto> orders = new ArrayList<>();
        for (WebElement tr : findElements(driver, TABLE_ROWS_BY)) {
            List<WebElement> tdElements = findElements(tr, TABLE_COLUMNS_BY);
            if (tdElements.size() == 3) {
                OrdersDto order = new OrdersDto();
                order.setOrderId(tdElements.get(0).getText());
                order.setOrderDate(tdElements.get(1).getText());
                order.setCartTotal(Util.convertPrice(tdElements.get(2).getText()));
                orders.add(order);
            }
        }
        return orders;
    }

    private void validateOrders(List<OrdersDto> orders) {
        Cookie tokenCookie = driver.manage().getCookieNamed(TEST_TOKEN);
        Cookie userIdCookie = driver.manage().getCookieNamed(TEST_USERID);

        OrdersDto[] ordersArray = callGetOrders(tokenCookie.getValue(), NumberUtils.toInt(userIdCookie.getValue()));

        Assertions.assertEquals(ordersArray.length, orders.size());

        for (int i = 0; i < ordersArray.length; i++) {
            Assertions.assertEquals(ordersArray[i].getOrderId(), orders.get(i).getOrderId());
            Assertions.assertEquals(ordersArray[i].getOrderId(), orders.get(i).getOrderId());
            Assertions.assertEquals(ordersArray[i].getCartTotal(), orders.get(i).getCartTotal());
        }
    }
}
