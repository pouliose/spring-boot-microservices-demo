package com.runner.statistics.services.implementations;

import com.runner.statistics.domain.Ranking;
import com.runner.statistics.exceptions.RankingNotFoundForUserIdException;
import com.runner.statistics.repositories.StatisticsRepository;
import com.runner.statistics.services.StatisticsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticsRepository statisticsRepository;

    @Override
    public List<Ranking> findAll() {
        return statisticsRepository.findAll();
    }

    @Override
    public Ranking findByUserId(Integer id) {
        Optional<Ranking> ranking = statisticsRepository.findByUserId(id);
        if(ranking.isEmpty()) {
            throw new RankingNotFoundForUserIdException();
        }
        return ranking.get();
    }
}
