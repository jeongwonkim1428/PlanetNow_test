package com.application.planetnow.task;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.*;

import com.application.planetnow.mainTask.MainTaskDAO;
import com.application.planetnow.mainTask.MainTaskDTO;
import com.application.planetnow.mainTask.MainTaskServiceImpl;
import com.application.planetnow.subTask.SubTaskDAO;
import com.application.planetnow.user.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskServiceTests {

    @Mock
    MainTaskDAO mainTaskDAO;

    @Mock
    PointDAO pointDAO;

    @Mock
    UserPointDAO userPointDAO;

    @Mock
    LevelDAO levelDAO;

    @Mock
    UserDAO userDAO;

    @Mock
    SubTaskDAO subTaskDAO;

    @InjectMocks
    MainTaskServiceImpl mainTaskService;

    private MainTaskDTO mainTaskDTO;

    @BeforeEach
    void setUp() {
        mainTaskDTO = new MainTaskDTO();
        mainTaskDTO.setUserId(1L);
        mainTaskDTO.setCategoryId(1L);
        mainTaskDTO.setMainSubject("단위테스트");
        mainTaskDTO.setStartTimestamp(LocalDate.now());
        mainTaskDTO.setEndTimestamp(LocalDate.now().plusDays(1));
        mainTaskDTO.setMemo("test");
        mainTaskDTO.setTaskStatusId(1);
        mainTaskDTO.setViewCnt(0L);
    }


    @Test @Order(1) @DisplayName("게시글 작성 테스트")
    void testCreateMainTask() {
        //Given (in beforeEach)
        List<PointDTO> pointList = Arrays.asList(
                new PointDTO(1L, "게시글 등록", 10L),
                new PointDTO(2L, "팔로우", 10L),
                new PointDTO(3L, "댓글", 5L),
                new PointDTO(4L, "좋아요", 5L),
                new PointDTO(5L, "서브태스크추천", 8L),
                new PointDTO(6L, "로그인", 1L)
        );
        when(pointDAO.getPointList()).thenReturn(pointList);

        List<LevelDTO> levelDTOList = Arrays.asList(
                new LevelDTO(1L, 0L),
                new LevelDTO(2L, 100L),
                new LevelDTO(3L, 200L),
                new LevelDTO(4L, 300L),
                new LevelDTO(5L, 500L)
        );
        when(levelDAO.getLevelList()).thenReturn(levelDTOList);

        UserDTO userDTO = new UserDTO();
        when(userDAO.getUserDetailById(mainTaskDTO.getUserId())).thenReturn(userDTO);

        //When
        mainTaskService.createMainTask(mainTaskDTO);

        //Then
        verify(mainTaskDAO, times(1)).createMainTask(mainTaskDTO);
        verify(pointDAO, times(1)).getPointList();
        verify(levelDAO, times(1)).getLevelList();

    }

    @Test @Order(2) @DisplayName("게시글 리스트 조회")
    void testGetMainTaskList() {
        //Given

        //When
        mainTaskService.getMainTaskList(5,1);
        Map<String, Object> map = new HashMap<>();
        map.put("size", 5);
        map.put("offset", 0);

        //Then
        verify(mainTaskDAO, times(1)).getMainTaskList(map);
    }

    @Test @Order(3) @DisplayName("게시글 상세조회")
    void testGetMainTaskDetail() {
        //Given
        Map<String, Object> subTaskMap = new HashMap<>();
        subTaskMap.put("taskStatusId", 1);
        List<Map<String, Object>> getSubTaskList= Arrays.asList(subTaskMap);
        when(subTaskDAO.getSubTaskList(1L)).thenReturn(getSubTaskList);
        when(mainTaskDAO.getMainTaskDTO(1L)).thenReturn(mainTaskDTO);

        //When
        mainTaskService.getMainTaskDetail(1L);

        //Then
        verify(mainTaskDAO, times(1)).getMainTaskDetail(1L);
        verify(subTaskDAO, times(1)).getSubTaskList(1L);
    }

    @Test @Order(4) @DisplayName("게시글 업데이트")
    void testUpdateMainTask() {
        //Given (in beforeEach)

        //When
        mainTaskService.updateMainTask(mainTaskDTO);

        //Then
        verify(mainTaskDAO, times(1)).updateMainTask(mainTaskDTO);
    }

    @Test @Order(5) @DisplayName("게시글 삭제")
    void testDeleteMainTask() {
        //Given (in beforeEach)

        //When
        mainTaskService.deleteMainTask(1L);

        //Then
        verify(mainTaskDAO, times(1)).deleteMainTask(1L);
    }


}
