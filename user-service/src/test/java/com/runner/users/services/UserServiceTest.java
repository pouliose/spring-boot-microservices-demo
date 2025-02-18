package com.runner.users.services;

import com.runner.users.domain.User;
import com.runner.users.exceptions.UserNotFoundException;
import com.runner.users.services.implementations.UserServiceImpl;
import com.runner.users.utils.CreateTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
@TestPropertySource(properties = {
        "eureka.client.enabled=false",
        "spring.jpa.hibernate.ddl-auto=create-drop"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserServiceTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer("postgres:14.15-alpine3.21");

    private final UserServiceImpl userService;

    @Autowired
    public UserServiceTest(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Test
    void connectionEstablished() {
        assertTrue(postgres.isCreated());
        assertTrue(postgres.isRunning());
    }

    @Test
    void findAll() {
        assertEquals(4, countUsers());
    }

    private long countUsers() {
        Iterator<User> iterator = userService.findAll().iterator();
        long count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        return count;
    }

    @Test
    void findAllPageable() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> page = userService.findAll(pageable);

        assertEquals(4, page.getTotalElements());
        assertEquals(1, page.getTotalPages());
    }

    @Test
    void findById() {
        User user = userService.find(1);
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("john.doe@example.com", user.getEmail());
    }

    @Test
    void testFindUserThatDoesNotExistThrowsException() {
        assertThrowsExactly(UserNotFoundException.class, () -> userService.find(5));
    }

    @Test
    void testCreateUser() {
        User user = CreateTestData.createTestUserB();
        userService.create(user);

        User savedUser = userService.find(5);

        assertEquals(user.getFirstName(), savedUser.getFirstName());
        assertEquals(user.getLastName(), savedUser.getLastName());
        assertEquals(user.getEmail(), savedUser.getEmail());
    }

    @Test
    void testUserGetsUpdated() {
        User user = userService.find(1);
        user.setFirstName("updatedFirstName");

        userService.update(user, user.getId());
        User updatedUserRetrieved = userService.find(1);

        assertEquals(user.getFirstName(), updatedUserRetrieved.getFirstName());
    }

    @Test
    void testUpdatingUserWithDifferentIdsThrowsException() {
        User user = userService.find(1);
        assertThrowsExactly(IllegalArgumentException.class, () -> userService.update(user, 99));
    }

    @Test
    void testUpdatingNonExistentUserThrowsException() {
        User user = CreateTestData.createTestUserA();
        assertThrowsExactly(UserNotFoundException.class, () -> userService.update(user, user.getId()));
    }

    @Test
    void testDeleteExistingUser() {
        userService.delete(1);
        assertThrowsExactly(UserNotFoundException.class, () -> userService.find(1));
    }

    @Test
    void testDeleteNonExistingUserThrowsException() {
        userService.delete(1);
        assertThrowsExactly(UserNotFoundException.class, () -> userService.find(1));
    }

}