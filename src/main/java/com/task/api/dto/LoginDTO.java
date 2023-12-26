package com.task.api.dto;

public class LoginDTO extends BaseDTO {
    private String token;
    private UserMasterDTO userDetails;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserMasterDTO getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserMasterDTO userDetails) {
        this.userDetails = userDetails;
    }
}
