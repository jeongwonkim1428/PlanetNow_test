package com.application.planetnow.recommendedTask;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RecommendedTaskDTO {
    private Long recommendedTaskId;
    private Long mainTaskId;
    private Long userId;
    private String subSubject;
    private LocalDate enrolledAt;

}
