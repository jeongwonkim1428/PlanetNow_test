package com.application.planetnow.recommendedTask;

import java.util.List;
import java.util.Map;

public interface RecommendedTaskService {
    public List<RecommendedTaskDTO> getRecommendedTaskList(Long mainTaskId);

    public void createRecommendedTask(RecommendedTaskDTO recommendedTaskDTO);

    public RecommendedTaskDTO getRecommendedTaskDTO(Long recommendedTaskId);

    public void declineRecommendedTask(Long recommendedTaskId);
}
