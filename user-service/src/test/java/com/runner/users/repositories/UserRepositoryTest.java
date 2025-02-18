package com.runner.users.repositories;

import com.runner.users.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Iterator;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest
@TestPropertySource(properties = {
        "eureka.client.enabled=false",
        "spring.jpa.hibernate.ddl-auto=create-drop"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer("postgres:14.15-alpine3.21");

    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(UserRepository runRepository) {
        this.userRepository = runRepository;
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
        Iterator<User> iterator = userRepository.findAll().iterator();
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
        Page<User> page = userRepository.findAll(pageable);

        assertEquals(4, page.getTotalElements());
        assertEquals(1, page.getTotalPages());
    }

    @Test
    void findById() {
        Optional<User> user = userRepository.findById(1);
        assertEquals("John", user.get().getFirstName());
        assertEquals("Doe", user.get().getLastName());
        assertEquals("john.doe@example.com", user.get().getEmail());
    }

    @Test
    void testUserDoesNotExist() {
        Optional<User> user = userRepository.findById(5);
        assertTrue(user.isEmpty());
    }

}