package com.task.common;

import com.task.api.dto.UserMasterDTO;

public class TestData {
    public static UserMasterDTO createUser() {
        UserMasterDTO user = new UserMasterDTO();
        user.setUsername("zx15");
        user.setPassword("123");
        user.setFirstName("masha");
        user.setLastName("gladii");
        user.setGender("female");
        return user;
    }

    public static UserMasterDTO createUser(String username) {
        UserMasterDTO user = createUser();
        user.setUsername(username);
        return user;
    }
}
