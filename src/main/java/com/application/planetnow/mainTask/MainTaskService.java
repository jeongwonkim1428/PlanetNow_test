package com.application.planetnow.mainTask;

import java.util.List;
import java.util.Map;

import com.application.planetnow.user.UserDTO;


public interface MainTaskService {
	

    public List<Map<String,Object>> getMainTaskList(Integer size, Integer page);
    public List<Map<String,Object>> getMainTaskList(String keyword, Long categoryId, Integer size, Integer page);

    public List<Map<String,Object>> getMainTaskListById(Integer size, Integer page, Long userId);

    public List<CategoryDTO> getCategoryList();

    public void createMainTask(MainTaskDTO mainTaskDTO);

    public Map<String, Object> getMainTaskDetail(Long mainTaskId);

    public MainTaskDTO getMainTaskDTO(Long mainTaskId);

    public void updateMainTask(MainTaskDTO mainTaskDTO);

    public void deleteMainTask(Long mainTaskId);

    
    // home (top3, best5)
    public List<MainTaskDTO> getTopViewCnt();
    public List<MainTaskDTO> getTopReplyCnt();
    public List<MainTaskDTO> getTopLikeCnt();
    public List<MainTaskDTO> getBestUserCnt();
    public List<MainTaskDTO> getBestCnt();
    

    int getTotalOfMainTaskByUserId(Long userId);

    int getTotalOfMainTask();

    int getTotalOfMainTaskBySearch(String keyword, Long categoryId);



}
