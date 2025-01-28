package com.application.planetnow.subTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubTaskServiceImpl implements SubTaskService {

    @Autowired
    SubTaskDAO subTaskDAO;

    @Override
    public void createSubTask(SubTaskDTO subTaskDTO) {
        subTaskDAO.createSubTask(subTaskDTO);
    }
}
