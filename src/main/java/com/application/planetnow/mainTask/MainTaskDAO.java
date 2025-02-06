package com.application.planetnow.mainTask;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface MainTaskDAO {
   public List<Map<String, Object>> getMainTaskList();
   public List<Map<String, Object>> getMainTaskList(@Param("keyword") String keyword, @Param("categoryId") Long categoryId);


   public List<CategoryDTO> getCategoryList();

   public void createMainTask(MainTaskDTO mainTaskDTO);

   public Map<String, Object> getMainTaskDetail(Long mainTaskId);

   public MainTaskDTO getMainTaskDTO(Long mainTaskId);

   public void updateMainTaskStatus(MainTaskDTO mainTaskDTO);

   public void increaseViewCnt(Long mainTaskId);

   public void updateMainTask(MainTaskDTO mainTaskDTO);

   public void deleteMainTask(Long mainTaskId);

   public List<Map<String, Object>> getMainTaskListById(Long userId);
}
