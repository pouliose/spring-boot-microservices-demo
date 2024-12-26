package com.runner.users.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.runner.users.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class JsonDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(JsonDataLoader.class);
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    public JsonDataLoader(ObjectMapper objectMapper, UserRepository userRepository) {
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        if(userRepository.count() == 0) {
            try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/users.json")) {
                com.runner.users.domain.Users users = objectMapper.readValue(inputStream, com.runner.users.domain.Users.class);
                log.info("Reading {} users from JSON data and saving to in-memory collection.", users.users().size());
                userRepository.saveAll(users.users());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
        } else {
            log.info("Not loading Runs from JSON data because the collection contains data.");
        }
    }

}
