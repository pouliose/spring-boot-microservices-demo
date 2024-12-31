package com.runner.runs.repositories;

import com.runner.runs.domain.Run;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RunRepository extends CrudRepository<Run, Integer>, PagingAndSortingRepository<Run, Integer> {

    List<Run> findAllByTitle(String title);

    List<Run> findByUserId(Integer userId);
}
