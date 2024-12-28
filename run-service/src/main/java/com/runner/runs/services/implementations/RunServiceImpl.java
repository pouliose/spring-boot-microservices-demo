package com.runner.runs.services.implementations;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.runner.runs.domain.Run;
import com.runner.runs.exceptions.RunNotFoundException;
import com.runner.runs.repositories.RunRepository;
import com.runner.runs.services.RunService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RunServiceImpl implements RunService {

    private static final Logger LOG = LoggerFactory.getLogger(RunServiceImpl.class);

    private final RunRepository runRepository;
    private final RunKafkaProducerServiceImpl runKafkaProducerService;

    @Override
    public List<Run> findAll() {
        return runRepository.findAll();
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
        Run savedRun = runRepository.save(run);
        LOG.debug("Run saved: {}", savedRun);
        runKafkaProducerService.sendRunToKafka(savedRun);
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
