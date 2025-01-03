package com.runner.statistics.repositories;

import com.runner.statistics.domain.PointsStatistics;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PointsStatisticsRepository extends ListCrudRepository<PointsStatistics, Integer> {
    Optional<PointsStatistics> findByUserId(Integer userId);
}