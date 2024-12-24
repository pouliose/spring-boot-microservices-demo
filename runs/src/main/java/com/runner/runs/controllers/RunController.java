package com.runner.runs.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.runner.runs.domain.Run;
import com.runner.runs.repositories.RunRepository;
import com.runner.runs.exceptions.RunNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.runner.runs.service.RunKafkaProducerService;

import java.util.List;
import java.util.Optional;

@RequestMapping("api/v1/runs")
@RestController
@AllArgsConstructor
public class RunController {

    private static final Logger LOG = LoggerFactory.getLogger(RunController.class);


    private final RunRepository runRepository;
    private final RunKafkaProducerService runKafkaProducerService;

    @GetMapping("")
    List<Run> findAll() {
        return runRepository.findAll();
    }

    @GetMapping("/{id}")
    Run find(@PathVariable Integer id){
        Optional<Run> run = runRepository.findById(id);
        if(run.isEmpty()){
            throw new RunNotFoundException();
        }
        return run.get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@Valid @RequestBody Run run) throws JsonProcessingException {

        Run savedRun = runRepository.save(run);
        LOG.debug("Run saved: {}", savedRun);

        runKafkaProducerService.sendRunToKafka(savedRun);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@Valid @RequestBody Run run, @PathVariable Integer id){
        runRepository.save(run);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id){
        runRepository.deleteById(runRepository.findById(id).orElseThrow(RunNotFoundException::new).getId());
    }

    @GetMapping("/user/{userId}")
    public List<Run> getRunsByUserId(@PathVariable Integer userId) {
        return runRepository.findByUserId(userId);
    }
}
