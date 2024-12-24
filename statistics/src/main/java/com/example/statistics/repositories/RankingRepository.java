package com.example.statistics.repositories;

import com.example.statistics.domain.Ranking;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankingRepository extends ListCrudRepository<Ranking, Integer> {
}