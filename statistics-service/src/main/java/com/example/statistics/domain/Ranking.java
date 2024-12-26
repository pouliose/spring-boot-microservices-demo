package com.example.statistics.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

/*@Getter
@Setter*/
@NoArgsConstructor
@ToString
@Entity
@Table(name = "rankings")
public class Ranking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;

    private Integer points;

    private LocalDate lastRunDateForActiveCalculation;

    private Integer counterOf5ActiveDaysWithoutDayOff;

    private Integer counterOf15ActiveDaysWithoutDayOff;

    private Integer totalRuns;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public LocalDate getLastRunDateForActiveCalculation() {
        return lastRunDateForActiveCalculation;
    }

    public void setLastRunDateForActiveCalculation(LocalDate lastRunDateForActiveCalculation) {
        this.lastRunDateForActiveCalculation = lastRunDateForActiveCalculation;
    }

    public Integer getCounterOf5ActiveDaysWithoutDayOff() {
        return counterOf5ActiveDaysWithoutDayOff;
    }

    public void setCounterOf5ActiveDaysWithoutDayOff(Integer counterOf5ActiveDaysWithoutDayOff) {
        this.counterOf5ActiveDaysWithoutDayOff = counterOf5ActiveDaysWithoutDayOff;
    }

    public Integer getCounterOf15ActiveDaysWithoutDayOff() {
        return counterOf15ActiveDaysWithoutDayOff;
    }

    public void setCounterOf15ActiveDaysWithoutDayOff(Integer counterOf15ActiveDaysWithoutDayOff) {
        this.counterOf15ActiveDaysWithoutDayOff = counterOf15ActiveDaysWithoutDayOff;
    }

    public Integer getTotalRuns() {
        return totalRuns;
    }

    public void setTotalRuns(Integer totalRuns) {
        this.totalRuns = totalRuns;
    }
}