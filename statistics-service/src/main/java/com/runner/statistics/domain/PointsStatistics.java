package com.runner.statistics.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "statistics")
public class PointsStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;

    private Integer points;

    private LocalDate lastRunDateForActiveCalculation;

    private Integer counterOf5ActiveDaysWithoutDayOff;

    private Integer counterOf15ActiveDaysWithoutDayOff;

    private Integer totalRuns;
}