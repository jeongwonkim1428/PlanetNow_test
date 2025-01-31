package com.application.planetnow.subTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SubTaskServiceImpl implements SubTaskService {

    @Autowired
    SubTaskDAO subTaskDAO;

    @Override
    public void createSubTask(SubTaskDTO subTaskDTO) {
        subTaskDAO.createSubTask(subTaskDTO);
    }

    @Override
    public List<Map<String, Object>> getSubTaskList(Long mainTaskId) {
        return subTaskDAO.getSubTaskList(mainTaskId);
    }

    @Override
    public void changeStatus(SubTaskDTO subTaskDTO) {
        subTaskDAO.changeStatus(subTaskDTO);
    }

    @Override
    public void updateSubTask(SubTaskDTO subTaskDTO) {
        subTaskDAO.updateSubTask(subTaskDTO);
    }
}
