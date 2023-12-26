package com.task.api.tests;

import com.task.api.dto.LoginDTO;
import com.task.api.dto.UserMasterDTO;
import com.task.common.TestData;
import com.task.listeners.LoggingRule;
import org.apache.http.HttpStatus;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoginUserTest extends BaseApiTest {

    @Rule
    public LoggingRule loggingRule = new LoggingRule();

    @Test
    @DisplayName("login user_1")
    public void testLogin() {
        UserMasterDTO user = TestData.createUser();
        userService.register(user).then().statusCode(HttpStatus.SC_OK);
        LoginDTO loginDto = userService.login(user)
                .then().statusCode(HttpStatus.SC_OK).extract().response().as(LoginDTO.class);

        Assertions.assertNotNull(loginDto.getToken());
        Assertions.assertNotNull(loginDto.getUserDetails());
        Assertions.assertTrue(loginDto.getUserDetails().getUserId() > 0);
    }

    @Test
    @DisplayName("login user_2")
    public void testLoginNullUsername() {
        userService.login(TestData.createUser(null)).then().statusCode(HttpStatus.SC_UNAUTHORIZED);
    }
}
