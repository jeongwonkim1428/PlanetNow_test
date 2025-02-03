package com.application.planetnow.subTask;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class SubTaskDTO {
    private Long subTaskId;
    private Long mainTaskId;
    private String subSubject;
    private LocalDate enrolledAt;
    private LocalDate modifiedAt;
    private Integer taskStatusId;
    private List<SubTaskDTO> subTaskDtoList;

}
