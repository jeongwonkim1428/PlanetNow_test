package com.application.planetnow.mainTask;

import com.application.planetnow.subTask.SubTaskDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

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
    private Integer taskStatusId;
    private Long viewCnt;
    private List<SubTaskDTO> subTaskDtoList;
    private Long likeCnt;



}
