package com.application.planetnow.mainTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MainTaskServiceImpl implements MainTaskService {

    @Autowired
    MainTaskDAO mainTaskDAO;



    @Override
    public List<Map<String, Object>> getMainTaskList() {
        return mainTaskDAO.getMainTaskList();
    }

    @Override
    public List<Map<String, Object>> getMainTaskList(String keyword) {
        return mainTaskDAO.getMainTaskList(keyword);
    }

    @Override
    public List<CategoryDTO> getCategoryList() {
        return mainTaskDAO.getCategoryList();
    }


}
