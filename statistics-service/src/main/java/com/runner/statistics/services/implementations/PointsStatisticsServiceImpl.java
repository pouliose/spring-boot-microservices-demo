package com.runner.statistics.services.implementations;

import com.runner.statistics.domain.PointsStatistics;
import com.runner.statistics.exceptions.PointsStatisticsNotFoundForUserIdException;
import com.runner.statistics.repositories.PointsStatisticsRepository;
import com.runner.statistics.services.PointsStatisticsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PointsStatisticsServiceImpl implements PointsStatisticsService {

    private final PointsStatisticsRepository statisticsRepository;

    @Override
    public List<PointsStatistics> findAll() {
        return statisticsRepository.findAll();
    }

    @Override
    public PointsStatistics findByUserId(Integer id) {
        Optional<PointsStatistics> ranking = statisticsRepository.findByUserId(id);
        if(ranking.isEmpty()) {
            throw new PointsStatisticsNotFoundForUserIdException();
        }
        return ranking.get();
    }
}
