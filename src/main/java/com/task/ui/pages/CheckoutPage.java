package com.task.ui.pages;

import com.task.api.dto.BookDTO;
import com.task.api.dto.CartItemDTO;
import com.task.api.dto.OrdersDTO;
import com.task.ui.utils.UiUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class CheckoutPage extends BasePage {
    private static final String PLACE_ORDER = "Place Order";

    private static final By NAME_FIELD_BY = By.xpath("//input[@formcontrolname='name']");
    private static final By ADDRESS_LINE1_FIELD_BY = By.xpath("//input[@formcontrolname='addressLine1']");
    private static final By ADDRESS_LINE2_FIELD_BY = By.xpath("//input[@formcontrolname='addressLine2']");
    private static final By PINCODE_FIELD_BY = By.xpath("//input[@formcontrolname='pincode']");
    private static final By STATE_FIELD_BY = By.xpath("//input[@formcontrolname='state']");

    private static final By TABLE_ROWS_BY = By.xpath("//table/tr[@class='ng-star-inserted']");
    private static final By TABLE_COLUMNS_BY = By.xpath(".//td");
    private static final By TABLE_LINK_BY = By.xpath(".//a");
    private static final By TABLE_LAST_CELL_BY = By.xpath("//tfoot/tr/th[3]");

    public CheckoutPage(WebDriver driver) {
        super(driver);
        waitUntilVisible(NAME_FIELD_BY);
        driver.navigate().refresh(); //by some reason the table is not populated initially, so force refresh.
    }

    public OrdersDTO getOrder() {
        OrdersDTO order = new OrdersDTO();
        order.setOrderDetails(getCartItems());
        order.setCartTotal(getCartTotal());
        return order;
    }

    public OrdersPage placeOrder() {
        findElement(NAME_FIELD_BY).sendKeys("abc");
        findElement(ADDRESS_LINE1_FIELD_BY).sendKeys("def");
        findElement(ADDRESS_LINE2_FIELD_BY).sendKeys("ghi");
        findElement(PINCODE_FIELD_BY).sendKeys("123456");
        findElement(STATE_FIELD_BY).sendKeys("654321");

        clickWhenClickable(findButtonByText(PLACE_ORDER));

        return new OrdersPage(driver);
    }

    private List<CartItemDTO> getCartItems() {
        List<CartItemDTO> cartItems = new ArrayList<>();
        for (WebElement tr : findElements(TABLE_ROWS_BY)) {
            cartItems.add(getCartItem(findElements(tr, TABLE_COLUMNS_BY)));
        }
        return cartItems;
    }

    private CartItemDTO getCartItem(List<WebElement> tdElements) {
        CartItemDTO cartItem = new CartItemDTO();
        cartItem.setQuantity(NumberUtils.toInt(tdElements.get(1).getText()));
        cartItem.setBook(new BookDTO());

        WebElement aElement = findElement(tdElements.get(0), TABLE_LINK_BY);
        cartItem.getBook().setTitle(aElement.getText());
        cartItem.getBook().setBookId(NumberUtils.toInt(StringUtils.substringAfterLast(aElement.getAttribute("href"), "/")));

        cartItem.getBook().setPrice(UiUtils.convertPrice(tdElements.get(2).getText()));
        return cartItem;
    }

    private double getCartTotal() {
        return UiUtils.convertPrice(findElement(TABLE_LAST_CELL_BY).getText());
    }
}
