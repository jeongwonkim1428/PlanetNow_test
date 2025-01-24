package com.application.planetnow.mainTask;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MainTaskDTO {
    private Long mainTaskId;
    private Long userId;
    private Long categoryId;
    private String mainSubject;
    private LocalDate startTimestamp;
    private LocalDate endTimestamp;
    private String memo;
    private LocalDate enrolledAt;
    private LocalDate modifiedAt;
    private Long taskStatusId;
    private Long viewCnt;

}
