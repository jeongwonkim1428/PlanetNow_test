package com.application.planetnow.recommendedTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RecommendedTaskServiceImpl implements RecommendedTaskService {

    @Autowired
    RecommendedTaskDAO recommendedTaskDAO;

    @Override
    public List<RecommendedTaskDTO> getRecommendedTaskList(Long mainTaskId) {
        return recommendedTaskDAO.getRecommendedTaskList(mainTaskId);
    }

    @Override
    public void createRecommendedTask(RecommendedTaskDTO recommendedTaskDTO) {
        recommendedTaskDAO.createRecommendedTask(recommendedTaskDTO);
    }

    @Override
    public RecommendedTaskDTO getRecommendedTaskDTO(Long recommendedTaskId) {
        return recommendedTaskDAO.getRecommendedTaskDTO(recommendedTaskId);
    }

    @Override
    public void declineRecommendedTask(Long recommendedTaskId) {
        recommendedTaskDAO.declineRecommendedTask(recommendedTaskId);
    }
}
