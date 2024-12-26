package com.runner.runs.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.runner.runs.domain.Run;

import java.util.List;

public interface RunService {
    List<Run> findAll();

    Run find(Integer id);

    void create(Run run) throws JsonProcessingException;

    void update(Run run, Integer id);

    void delete(Integer id);

    List<Run> getRunsByUserId(Integer userId);
}