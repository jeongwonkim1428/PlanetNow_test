package com.application.planetnow.mainTask;


import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MainTaskDAO {
   public List<Map<String, Object>> getMainTaskList();
   public List<Map<String, Object>> getMainTaskList(String keyword, Long categoryId);

   public List<CategoryDTO> getCategoryList();

   public void createMainTask(MainTaskDTO mainTaskDTO);

   public Map<String, Object> getMainTaskDetail(Long mainTaskId);

   public MainTaskDTO getMainTaskDTO(Long mainTaskId);

   public void updateMainTaskStatus(MainTaskDTO mainTaskDTO);

   public void increaseViewCnt(Long mainTaskId);

   public void updateMainTask(MainTaskDTO mainTaskDTO);

   public void deleteMainTask(Long mainTaskId);
}
