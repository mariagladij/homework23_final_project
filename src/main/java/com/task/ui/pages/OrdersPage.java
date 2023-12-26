package com.task.ui.pages;

import com.task.api.dto.OrdersDTO;
import com.task.ui.utils.UiUtils;
import org.openqa.selenium.By;
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
        waitUntilVisible(TABLE_ROWS_BY); //slow process: The more orders, the longer a time of page loading!
    }

    public List<OrdersDTO> getAllOrders() {
        List<OrdersDTO> orders = new ArrayList<>();
        inspectOrdersTable(orders);
        return orders;
    }

    private void inspectOrdersTable(List<OrdersDTO> orders) {
        orders.addAll(inspectOrdersTable());
        WebElement nextButton = findElement(driver, NEXT_BUTTON_BY);
        if (nextButton.isEnabled()) {
            nextButton.click();
            inspectOrdersTable(orders); //recursion
        }
    }

    private List<OrdersDTO> inspectOrdersTable() {
        List<OrdersDTO> orders = new ArrayList<>();
        for (WebElement tr : findElements(driver, TABLE_ROWS_BY)) {
            List<WebElement> tdElements = findElements(tr, TABLE_COLUMNS_BY);
            if (tdElements.size() == 3) {
                OrdersDTO order = new OrdersDTO();
                order.setOrderId(tdElements.get(0).getText());
                order.setOrderDate(tdElements.get(1).getText());
                order.setCartTotal(UiUtils.convertPrice(tdElements.get(2).getText()));
                orders.add(order);
            }
        }
        return orders;
    }
}
