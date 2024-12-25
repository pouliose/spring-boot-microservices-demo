package com.example.statistics.repositories;

import com.example.statistics.domain.Ranking;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RankingRepository extends ListCrudRepository<Ranking, Integer> {
    Optional<Ranking> findByUserId(Integer userId);
}