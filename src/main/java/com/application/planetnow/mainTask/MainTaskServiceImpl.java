package com.application.planetnow.mainTask;

import com.application.planetnow.subTask.SubTaskDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MainTaskServiceImpl implements MainTaskService {

    @Autowired
    MainTaskDAO mainTaskDAO;

    @Autowired
    SubTaskDAO subTaskDAO;



    @Override
    public List<Map<String, Object>> getMainTaskList() {
        return mainTaskDAO.getMainTaskList();
    }

    @Override
    public List<Map<String, Object>> getMainTaskList(String keyword, Long categoryId) {
        return mainTaskDAO.getMainTaskList(keyword, categoryId);
    }

    @Override
    public List<CategoryDTO> getCategoryList() {
        return mainTaskDAO.getCategoryList();
    }

    @Override
    public void createMainTask(MainTaskDTO mainTaskDTO) {
        mainTaskDAO.createMainTask(mainTaskDTO);
    }

    @Override
    public Map<String, Object> getMainTaskDetail(Long mainTaskId) {

        List<Map<String, Object>> getSubTaskList = subTaskDAO.getSubTaskList(mainTaskId);
        MainTaskDTO mainTaskDTO = mainTaskDAO.getMainTaskDTO(mainTaskId);

        int nOfTotalSubTask = getSubTaskList.size();
        int nOfNotInProgressSubTask = 0;
        int nOfInProgressSubTask = 0;
        int nOfCompletedSubTask = 0;
        int nOfFailedSubTask = 0;

        for (Map<String, Object> subTask : getSubTaskList) {
            Integer taskStatusId = (Integer) subTask.get("taskStatusId");
            if (taskStatusId == 1) {
                nOfNotInProgressSubTask++;
            }
            else if (taskStatusId == 2) {
                nOfInProgressSubTask++;
            }
            else if (taskStatusId == 3) {
                nOfCompletedSubTask++;
            }
            else if (taskStatusId == 4) {
                nOfFailedSubTask++;
            }
        }
        if (nOfInProgressSubTask != 0 ) {
            mainTaskDTO.setTaskStatusId(2);
        }
        if (nOfNotInProgressSubTask != 0 ) {
            mainTaskDTO.setTaskStatusId(2);
        }
        if (nOfCompletedSubTask == nOfTotalSubTask ||((nOfFailedSubTask + nOfCompletedSubTask) == nOfTotalSubTask)) {
            mainTaskDTO.setTaskStatusId(3);
        }
        if (nOfFailedSubTask == nOfTotalSubTask) {
            mainTaskDTO.setTaskStatusId(4);
        }
        if (nOfNotInProgressSubTask == nOfTotalSubTask) {
            mainTaskDTO.setTaskStatusId(1);
        }

        mainTaskDAO.updateMainTaskStatus(mainTaskDTO);
        mainTaskDAO.increaseViewCnt(mainTaskId);

        return mainTaskDAO.getMainTaskDetail(mainTaskId);
    }

    @Override
    public MainTaskDTO getMainTaskDTO(Long mainTaskId) {
        return mainTaskDAO.getMainTaskDTO(mainTaskId);
    }

    @Override
    public void updateMainTask(MainTaskDTO mainTaskDTO) {
        mainTaskDAO.updateMainTask(mainTaskDTO);
    }

    @Override
    public void deleteMainTask(Long mainTaskId) {
        mainTaskDAO.deleteMainTask(mainTaskId);
    }


}
