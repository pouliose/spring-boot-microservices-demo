package com.runner.users.domain.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RunDTO {

    private Integer id;

    private String title;

    private LocalDateTime startedOn;

    private LocalDateTime completedOn;

    private Integer miles;

    private String location;
}
