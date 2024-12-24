package com.runner.runs.repositories;

import com.runner.runs.domain.Run;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RunRepository extends ListCrudRepository<Run, Integer> {

    List<Run> findAllByTitle(String title);

    List<Run> findByUserId(Integer userId);
}
