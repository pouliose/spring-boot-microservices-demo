package com.runner.users.utils;

import com.runner.users.domain.User;

public class CreateTestData {

    public static User createTestUserA() {
        return User.builder()
                .id(77)
                .firstName("Anna")
                .lastName("Marra")
                .email("annamarra@example.com")
                .version(1)
                .build();
    }

    public static User createTestUserB() {
        return User.builder()
                .firstName("Chris")
                .lastName("Simson")
                .email("chrissimson@example.com")
                .version(1)
                .build();
    }
}
