package com.runner.runs.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.runner.runs.domain.Run;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RunService {
    List<Run> findAll();

    Page<Run> findAll(Pageable pageable);

    Run find(Integer id);

    void create(Run run) throws JsonProcessingException;

    void update(Run run, Integer id);

    void delete(Integer id);

    List<Run> getRunsByUserId(Integer userId);
}