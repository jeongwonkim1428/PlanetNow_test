package com.application.planetnow;

import com.application.planetnow.follow.FollowDAO;
import com.application.planetnow.follow.FollowDTO;
import com.application.planetnow.mainTask.CategoryDTO;
import com.application.planetnow.mainTask.MainTaskDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
class PlanetNowApplicationTests {

//    @Autowired
//    MainTaskDAO mainTaskDAO;
//    
//    @Autowired
//    FollowDAO followDAO;
//
//    @Test
//    void contextLoads() {
//    }
//
//    @Test
//    void searchMainTask() {
//
//        List<Map<String, Object>> getMainTaskList = mainTaskDAO.getMainTaskList();
//        for (Map<String, Object> map : getMainTaskList) {
//            System.out.println(map);
//        }
//    }
//
//    @Test
//    void getCategoryList() {
//
//        List<CategoryDTO> getCategoryList = mainTaskDAO.getCategoryList();
//        for (CategoryDTO map : getCategoryList) {
//            System.out.println(map);
//        }
//
//    }
//
//    @Test
//    void getMainTaskListById() {
//        List<Map<String, Object>> getMAinTaskListById = mainTaskDAO.getMainTaskListById(1L);
//        for (Map<String, Object> map : getMAinTaskListById) {
//            System.out.println(map);
//        }
//    }
//    
//    @Test
//    void check() {
//    	FollowDTO followDTO = new FollowDTO();
//    	followDTO.setFollowerId(4L);
//    	followDTO.setFolloweeId(5L);
//    	System.out.println(followDAO.check(followDTO));
//    }



}
