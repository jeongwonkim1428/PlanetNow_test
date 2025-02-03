package com.application.planetnow.recommendedTask;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface RecommendedTaskDAO {
    public List<RecommendedTaskDTO> getRecommendedTaskList(Long mainTaskId);

    public void createRecommendedTask(RecommendedTaskDTO recommendedTaskDTO);

    public RecommendedTaskDTO getRecommendedTaskDTO(Long recommendedTaskId);

    public void declineRecommendedTask(Long recommendedTaskId);
}
