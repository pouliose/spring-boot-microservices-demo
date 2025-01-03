package com.runner.statistics.services;

import com.runner.statistics.domain.PointsStatistics;

import java.util.List;

public interface PointsStatisticsService {
    List<PointsStatistics> findAll();

    PointsStatistics findByUserId(Integer id);
}
