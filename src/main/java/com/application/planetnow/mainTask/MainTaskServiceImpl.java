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
        int nOfInProgressSubTask = 0;
        int nOfCompletedSubTask = 0;
        int nOfFailedSubTask = 0;

        for (Map<String, Object> subTask : getSubTaskList) {
            Integer taskStatusId = (Integer) subTask.get("taskStatusId"); // Cast to Integer
            System.out.println(taskStatusId);
            if (taskStatusId == 2) {
                nOfInProgressSubTask++;
            }
            if (taskStatusId == 3) {
                nOfCompletedSubTask++;
            }
            if (taskStatusId == 4) {
                nOfFailedSubTask++;
            }
        }

        if (nOfInProgressSubTask != 0) {
            mainTaskDTO.setTaskStatusId(2);
        }
        if (nOfCompletedSubTask == nOfTotalSubTask) {
            mainTaskDTO.setTaskStatusId(3);
        }
        if (nOfFailedSubTask == nOfTotalSubTask) {
            mainTaskDTO.setTaskStatusId(4);
        }

        mainTaskDAO.updateMainTask(mainTaskDTO);


        return mainTaskDAO.getMainTaskDetail(mainTaskId);
    }


}
