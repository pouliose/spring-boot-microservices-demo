package com.runner.statistics.services;

import com.runner.statistics.domain.Ranking;

import java.util.List;

public interface StatisticsService  {
    List<Ranking> findAll();

    Ranking findByUserId(Integer id);
}
