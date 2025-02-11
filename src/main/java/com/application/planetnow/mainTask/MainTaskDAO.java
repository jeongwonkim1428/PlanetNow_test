package com.application.planetnow.mainTask;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.application.planetnow.user.UserDTO;

import java.util.List;
import java.util.Map;

@Mapper
public interface MainTaskDAO {

   public List<Map<String, Object>> getMainTaskList(Map<String, Object> map);
   public List<Map<String, Object>> getMainTaskList(@Param("keyword") String keyword, @Param("categoryId") Long categoryId, @Param("size") Integer size, @Param("offset") Integer offset);


   public List<CategoryDTO> getCategoryList();

   public void createMainTask(MainTaskDTO mainTaskDTO);

   public Map<String, Object> getMainTaskDetail(Long mainTaskId);

   public MainTaskDTO getMainTaskDTO(Long mainTaskId);

   public void updateMainTaskStatus(MainTaskDTO mainTaskDTO);

   public void increaseViewCnt(Long mainTaskId);

   public void updateMainTask(MainTaskDTO mainTaskDTO);

   public void deleteMainTask(Long mainTaskId);


   public List<Map<String, Object>> getMainTaskListById(Long userId);
   
   // home (top3, best5)
   public List<MainTaskDTO> getTopViewCnt();
   public List<MainTaskDTO> getTopLikeCnt();
   public List<MainTaskDTO> getTopReplyCnt();
   public List<MainTaskDTO> getBestUserCnt();
   public List<MainTaskDTO> getBestCnt();
   

   public List<Map<String, Object>> getMainTaskListById(@Param("size") Integer size,@Param("offset") Integer offset,@Param("userId") Long userId);

   int getTotalOfMainTaskByUserId(Long userId);

   int getTotalOfMainTask();

   int getTotalOfMainTaskBySearch(String keyword, Long categoryId);

}
