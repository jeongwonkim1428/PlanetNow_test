package com.application.planetnow.mainTask;

import java.util.List;
import java.util.Map;

public interface MainTaskService {

    public List<Map<String,Object>> getMainTaskList();
    public List<Map<String,Object>> getMainTaskList(String keyword, Long categoryId);
    public List<CategoryDTO> getCategoryList();

    public void createMainTask(MainTaskDTO mainTaskDTO);

    public Map<String, Object> getMainTaskDetail(Long mainTaskId);

}
