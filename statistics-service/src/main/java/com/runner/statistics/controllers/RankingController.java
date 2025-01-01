package com.runner.statistics.controllers;

import com.runner.statistics.domain.Ranking;
import com.runner.statistics.services.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("api/v1/statistics")
@RestController
public class RankingController {

    private final StatisticsService statisticsService;

    public RankingController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("")
    List<Ranking> findAll() {
        return statisticsService.findAll();
    }

    @GetMapping("/users/{userId}")
    Ranking findRankingByUserId(@PathVariable Integer userId) {
        return statisticsService.findByUserId(userId);
    }
}
