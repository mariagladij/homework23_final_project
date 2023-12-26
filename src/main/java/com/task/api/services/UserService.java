package com.task.api.services;

import com.task.api.dto.UserMasterDTO;
import io.restassured.response.Response;

public class UserService extends BaseService {
    private static final String REGISTER_USER_PATH = "/api/User";
    private static final String LOGIN_USER_PATH = "/api/Login";

    public Response register(UserMasterDTO user) {
        return getRequestSpecification().body(user).post(REGISTER_USER_PATH);
    }

    public Response login(UserMasterDTO user) {
        return getRequestSpecification().body(user).post(LOGIN_USER_PATH);
    }
}
