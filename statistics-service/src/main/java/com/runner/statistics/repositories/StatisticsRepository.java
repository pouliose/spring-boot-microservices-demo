package com.runner.statistics.repositories;

import com.runner.statistics.domain.Ranking;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StatisticsRepository extends ListCrudRepository<Ranking, Integer> {
    Optional<Ranking> findByUserId(Integer userId);
}