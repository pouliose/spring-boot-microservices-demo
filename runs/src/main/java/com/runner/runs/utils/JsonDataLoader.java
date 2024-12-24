package com.runner.runs.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.runner.runs.domain.Runs;
import com.runner.runs.repositories.RunRepository;
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
    private final RunRepository runRepository;

    public JsonDataLoader(ObjectMapper objectMapper, RunRepository runRepository) {
        this.objectMapper = objectMapper;
        this.runRepository = runRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(runRepository.count() == 0) {
            try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/runs.json")) {
                Runs allRuns = objectMapper.readValue(inputStream, Runs.class);
                log.info("Reading {} runs from JSON data and saving to in-memory collection.", allRuns.runs().size());
                runRepository.saveAll(allRuns.runs());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
        } else {
            log.info("Not loading Runs from JSON data because the collection contains data.");
        }
    }

}
