package com.task.frontend.page;

import com.task.dto.Book;
import com.task.dto.CartItemDto;
import com.task.dto.OrdersDto;
import com.task.frontend.Util;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class CheckoutPage extends BasePage {
    private static final String PLACE_ORDER = "Place Order";

    private static final By NAME_FIELD_BY = By.id("mat-input-0");
    private static final By ADDRESS_LINE1_FIELD_BY = By.id("mat-input-1");
    private static final By ADDRESS_LINE2_FIELD_BY = By.id("mat-input-2");
    private static final By PINCODE_FIELD_BY = By.id("mat-input-3");
    private static final By STATE_FIELD_BY = By.id("mat-input-4");

    private static final By TABLE_ROWS_BY = By.xpath("//table/tr[@class='ng-star-inserted']");
    private static final By TABLE_COLUMNS_BY = By.xpath(".//td");
    private static final By TABLE_LINK_BY = By.xpath(".//a");
    private static final By TABLE_LAST_CELL_BY = By.xpath("//tfoot/tr/th[3]");

    public CheckoutPage(WebDriver driver) {
        super(driver);
        driver.navigate().refresh(); //by some reason the table is not populated initially, so force refresh.
        waitUntil(TABLE_ROWS_BY);
    }

    public OrdersPage placeOrder() {
        validateOrder(getOrder());

        findElement(NAME_FIELD_BY).sendKeys("abc");
        findElement(ADDRESS_LINE1_FIELD_BY).sendKeys("def");
        findElement(ADDRESS_LINE2_FIELD_BY).sendKeys("ghi");
        findElement(PINCODE_FIELD_BY).sendKeys("123456");
        findElement(STATE_FIELD_BY).sendKeys("654321");

        findButtonByText(PLACE_ORDER).click();

        return new OrdersPage(driver);
    }

    private OrdersDto getOrder() {
        OrdersDto order = new OrdersDto();
        order.setOrderDetails(getCartItems());
        order.setCartTotal(getCartTotal());
        return order;
    }

    private List<CartItemDto> getCartItems() {
        List<CartItemDto> cartItems = new ArrayList<>();
        for (WebElement tr : findElements(TABLE_ROWS_BY)) {
            cartItems.add(getCartItem(findElements(tr, TABLE_COLUMNS_BY)));
        }
        return cartItems;
    }

    private CartItemDto getCartItem(List<WebElement> tdElements) {
        CartItemDto cartItem = new CartItemDto();
        cartItem.setQuantity(NumberUtils.toInt(tdElements.get(1).getText()));
        cartItem.setBook(new Book());

        WebElement aElement = findElement(tdElements.get(0), TABLE_LINK_BY);
        cartItem.getBook().setTitle(aElement.getText());
        cartItem.getBook().setBookId(NumberUtils.toInt(StringUtils.substringAfterLast(aElement.getAttribute("href"), "/")));

        cartItem.getBook().setPrice(Util.convertPrice(tdElements.get(2).getText()));
        return cartItem;
    }

    private double getCartTotal() {
        return Util.convertPrice(findElement(TABLE_LAST_CELL_BY).getText());
    }

    private void validateOrder(OrdersDto order) {
        int userId = NumberUtils.toInt(driver.manage().getCookieNamed(TEST_USERID).getValue());
        CartItemDto[] expectedCartItems = callGetShoppingCartItems(userId);
        List<CartItemDto> actualCartItems = order.getOrderDetails();

        Assertions.assertEquals(expectedCartItems.length, actualCartItems.size());

        double cartTotalExpected = 0;
        for (int i = 0; i < expectedCartItems.length; i++) {
            Assertions.assertEquals(expectedCartItems[i].getBook().getBookId(), actualCartItems.get(i).getBook().getBookId());
            Assertions.assertEquals(expectedCartItems[i].getBook().getTitle(), actualCartItems.get(i).getBook().getTitle());
            Assertions.assertEquals(expectedCartItems[i].getBook().getPrice(), actualCartItems.get(i).getBook().getPrice());
            Assertions.assertEquals(expectedCartItems[i].getQuantity(), actualCartItems.get(i).getQuantity());
            cartTotalExpected += actualCartItems.get(i).getBook().getPrice() * actualCartItems.get(i).getQuantity();
        }
        Assertions.assertEquals(cartTotalExpected, order.getCartTotal());
    }
}
