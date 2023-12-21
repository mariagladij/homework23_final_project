package com.task.dto;

public class LoginDto {
    private String token;
    private UserMaster userDetails;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserMaster getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserMaster userDetails) {
        this.userDetails = userDetails;
    }
}
