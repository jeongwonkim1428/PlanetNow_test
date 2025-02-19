package com.application.planetnow.task;

import com.application.planetnow.mainTask.MainTaskDAO;
import com.application.planetnow.mainTask.MainTaskDTO;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Transactional
public class TaskDaoTests {

    @Autowired
    private MainTaskDAO mainTaskDAO;


    private MainTaskDTO mainTaskDTO;

    @BeforeEach
    public void setUp() {
        mainTaskDTO = new MainTaskDTO();
        mainTaskDTO.setUserId(1L);
        mainTaskDTO.setCategoryId(1L);
        mainTaskDTO.setMainSubject("단위테스트");
        mainTaskDTO.setStartTimestamp(LocalDate.now());
        mainTaskDTO.setEndTimestamp(LocalDate.now().plusDays(1));
        mainTaskDTO.setMemo("test");
        mainTaskDTO.setTaskStatusId(1);
        mainTaskDTO.setViewCnt(0L);

        mainTaskDAO.createMainTask(mainTaskDTO);
    }

    @Test
    void getMainTaskList() {

        //Given
        Map<String, Object> searchKeywordAndCategory = new HashMap<>();
        searchKeywordAndCategory.put("keyword", "단");
        searchKeywordAndCategory.put("categoryId", 1); // 자격증
        searchKeywordAndCategory.put("size", 10000);
        searchKeywordAndCategory.put("offset", 0);

        //When
        List<Map<String, Object>> mainTaskList = mainTaskDAO.getMainTaskList(searchKeywordAndCategory);
        for (Map<String, Object> mainTask : mainTaskList) {
            System.out.println(mainTask);
            System.out.println(mainTask.get("categoryNm"));
        }
        //Then
        assertThat(mainTaskList).isNotNull()
                .isNotEmpty();
        assertThat(mainTaskList.stream()
                .allMatch(task -> task.get("mainSubject").toString().equals("단위테스트")))
                .isTrue();
        assertThat(mainTaskList.stream()
                        .allMatch(task -> (task.get("categoryNm").toString()).equals("자격증")))
                        .isTrue();
    }

    @Test
    void createMainTask() {
        //Given in setup

        //When
        mainTaskDAO.createMainTask(mainTaskDTO);
        Long mainTaskId = mainTaskDAO.getMainTaskId();

        //Then
        assertThat(mainTaskId).isNotNull();

    }

    @Test
    void updateMainTask() {
        //Given
        Long mainTaskId = mainTaskDAO.getMainTaskId();

        //When
        mainTaskDTO.setCategoryId(2L);
        mainTaskDTO.setMainSubject("바뀐제목");
        mainTaskDTO.setStartTimestamp(LocalDate.now());
        mainTaskDTO.setEndTimestamp(LocalDate.now().plusDays(1));
        mainTaskDTO.setMemo("바뀐메모");

        mainTaskDAO.updateMainTask(mainTaskDTO);

        //Then
        mainTaskDAO.getMainTaskDTO(mainTaskId);
        assertThat(mainTaskDAO.getMainTaskDTO(mainTaskId)).isNotNull()
                .extracting(MainTaskDTO::getCategoryId,
                        MainTaskDTO::getMainSubject,
                        MainTaskDTO::getStartTimestamp,
                        MainTaskDTO::getEndTimestamp,
                        MainTaskDTO::getMemo)
                .contains(2L, "바뀐제목", LocalDate.now(), LocalDate.now().plusDays(1), "바뀐메모");


    }

    @Test
    void deleteMainTask() {
        //Given
        Long mainTaskId = mainTaskDAO.getMainTaskId();

        //When
        mainTaskDAO.deleteMainTask(mainTaskId);

        //Then
        assertThat(mainTaskDAO.getMainTaskDTO(mainTaskId)).isNull();

    }


}
