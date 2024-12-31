package com.runner.runs.services.implementations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.runner.runs.domain.Run;
import com.runner.runs.exceptions.RunNotFoundException;
import com.runner.runs.repositories.RunRepository;
import com.runner.runs.services.RunService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RunServiceImpl implements RunService {

    private static final Logger LOG = LoggerFactory.getLogger(RunServiceImpl.class);

    private final RunRepository runRepository;
    private final RunKafkaProducerServiceImpl runKafkaProducerService;
    private final RestClient restClient;

    @Override
    public List<Run> findAll() {
        Iterator<Run> iterator = runRepository.findAll().iterator();
        List<Run> runs = new ArrayList<>();
        iterator.forEachRemaining(runs::add);
        return runs;
    }

    @Override
    public Page<Run> findAll(Pageable pageable) {
        return runRepository.findAll(pageable);
    }

    @Override
    public Run find(Integer id) {
        Optional<Run> run = runRepository.findById(id);
        if (run.isEmpty()) {
            throw new RunNotFoundException();
        }
        return run.get();
    }

    @Override
    public void create(Run run) throws JsonProcessingException {
        userExists(run.getUserId());
        Run savedRun = runRepository.save(run);
        LOG.debug("Run saved: {}", savedRun);
        runKafkaProducerService.sendRunToKafka(savedRun);
    }

    private void userExists(Integer userId) {
        try {
            ResponseEntity<String> result =
                    restClient.get().uri("/" + userId).retrieve().toEntity(String.class);

            LOG.debug("User service response: {}", result);
            if (!result.getStatusCode().equals(HttpStatus.OK)) {
                throw new RuntimeException("User service returned status code: " + result.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void update(Run run, Integer id) {
        if (!run.getId().equals(id)) {
            throw new IllegalArgumentException("ID in the request body does not match the ID in the path parameter");
        }
        if (!runRepository.existsById(id)) {
            throw new RunNotFoundException();
        }
        runRepository.save(run);
    }

    @Override
    public void delete(Integer id) {
        runRepository.deleteById(runRepository.findById(id).orElseThrow(RunNotFoundException::new).getId());
    }

    @Override
    public List<Run> getRunsByUserId(Integer userId) {
        return runRepository.findByUserId(userId);
    }
}
