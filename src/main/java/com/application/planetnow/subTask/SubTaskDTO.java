package com.application.planetnow.subTask;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SubTaskDTO {
    private Long subTaskId;
    private Long mainTaskId;
    private String subSubject;
    private LocalDate enrolledAt;
    private LocalDate modifiedAt;
    private Integer taskStatusId;

}
