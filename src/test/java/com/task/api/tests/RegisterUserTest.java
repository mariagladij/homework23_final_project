package com.task.api.tests;

import com.task.common.TestData;
import com.task.listeners.LoggingRule;
import org.apache.http.HttpStatus;
import org.junit.Rule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RegisterUserTest extends BaseApiTest {

    @Rule
    public LoggingRule loggingRule = new LoggingRule();

    @Test
    @DisplayName("create user_1")
    public void testRegister() {
        userService.register(TestData.createUser()).then().statusCode(HttpStatus.SC_OK);
    }

    @Test
    @DisplayName("create user_2")
    public void testRegisterNullUsername() {
        userService.register(TestData.createUser(null)).then().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }
}
