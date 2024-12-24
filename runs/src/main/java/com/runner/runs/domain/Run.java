package com.runner.runs.domain;

import com.runner.runs.enums.Location;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "runs")
public class Run {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    private String title;

    private LocalDateTime startedOn;

    private LocalDateTime completedOn;

    @Positive
    private Integer miles;

    @Enumerated(EnumType.STRING)
    private Location location;

    @NotNull
    private Integer userId;

   /* @Version
    private Integer version;*/

}