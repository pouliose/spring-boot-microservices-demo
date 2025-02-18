package com.runner.users.utils;

import com.runner.users.domain.User;

public class CreateTestData {

    public static User createTestUser() {
        return User.builder()
                .firstName("Anna")
                .lastName("Marra")
                .email("annamarra@example.com")
                .build();
    }
}
