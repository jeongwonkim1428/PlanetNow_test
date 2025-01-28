package com.application.planetnow.subTask;


import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SubTaskDAO {
    public void createSubTask(SubTaskDTO subTaskDTO);
}
