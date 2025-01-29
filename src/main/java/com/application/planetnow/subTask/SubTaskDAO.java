package com.application.planetnow.subTask;


import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SubTaskDAO {
    public void createSubTask(SubTaskDTO subTaskDTO);

    public List<Map<String, Object>> getSubTaskList(Long mainTaskId);

    public void changeStatus(SubTaskDTO subTaskDTO);

}
