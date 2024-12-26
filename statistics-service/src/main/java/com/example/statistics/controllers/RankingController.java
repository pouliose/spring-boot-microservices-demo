package com.example.statistics.controllers;

import com.example.statistics.domain.Ranking;
import com.example.statistics.repositories.RankingRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("api/v1/statistics")
@RestController
public class RankingController {

    private final RankingRepository rankingRepository;

    public RankingController(RankingRepository rankingRepository) {
        this.rankingRepository = rankingRepository;
    }

    @GetMapping("")
    List<Ranking> findAll() {
        return rankingRepository.findAll();
    }
}
