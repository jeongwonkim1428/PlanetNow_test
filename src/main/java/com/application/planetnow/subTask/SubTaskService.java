package com.application.planetnow.subTask;

import java.util.List;
import java.util.Map;

public interface SubTaskService {

    public void createSubTask(SubTaskDTO subTaskDTO);

    public List<Map<String, Object>> getSubTaskList(Long mainTaskId);

    public void changeStatus(SubTaskDTO subTaskDTO);
}
