package com.application.planetnow.mainTask;


import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MainTaskDAO {
   public List<Map<String, Object>> getMainTaskList();
   public List<Map<String, Object>> getMainTaskList(String keyword);

   public List<CategoryDTO> getCategoryList();


}
