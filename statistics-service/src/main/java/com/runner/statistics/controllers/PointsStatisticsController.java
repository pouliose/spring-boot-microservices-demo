package com.runner.statistics.controllers;

import com.runner.statistics.domain.PointsStatistics;
import com.runner.statistics.services.PointsStatisticsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RequestMapping("api/v1/statistics")
@RestController
public class PointsStatisticsController {

    private final PointsStatisticsService statisticsService;

    @GetMapping("")
    List<PointsStatistics> findAll() {
        return statisticsService.findAll();
    }

    @GetMapping("/users/{userId}")
    PointsStatistics findRankingByUserId(@PathVariable Integer userId) {
        return statisticsService.findByUserId(userId);
    }
}
