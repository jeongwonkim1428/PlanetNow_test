package com.application.planetnow.mainTask;

import java.util.List;
import java.util.Map;

public interface MainTaskService {

    public List<Map<String,Object>> getMainTaskList();


    public List<Map<String, Object>> getSearchMainTaskList(String keyword);
}
