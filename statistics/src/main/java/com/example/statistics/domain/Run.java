package com.example.statistics.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Run {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("startedOn")
    private LocalDateTime startedOn;

    @JsonProperty("completedOn")
    private LocalDateTime completedOn;

    @JsonProperty("miles")
    private Integer miles;

    @JsonProperty("location")
    private String location;

    @JsonProperty("userId")
    private Integer userId;

}