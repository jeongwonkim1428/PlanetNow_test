package com.application.planetnow.mainTask;

import com.application.planetnow.subTask.SubTaskDAO;
import com.application.planetnow.user.*;

import com.application.planetnow.user.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
@Slf4j
public class MainTaskServiceImpl implements MainTaskService {

    @Autowired
    MainTaskDAO mainTaskDAO;

    @Autowired
    SubTaskDAO subTaskDAO;

    @Autowired
    UserPointDAO userPointDAO;

    @Autowired
    LevelDAO levelDAO;

    @Autowired
    PointDAO pointDAO;

    @Autowired
    UserDAO userDAO;

    @Override
    public List<Map<String, Object>> getMainTaskList(Integer size, Integer page) {
        Integer offset = page * size - 5;
        Map<String, Object> map = new HashMap<>();
        map.put("size", size);
        map.put("offset", offset);
        return mainTaskDAO.getMainTaskList(map);
    }

    @Override
    public List<Map<String, Object>> getMainTaskList(String keyword, Long categoryId, Integer size, Integer page) {
        Integer offset = page * size - 5;
        return mainTaskDAO.getMainTaskList(keyword, categoryId, size, offset);
    }

    @Override
    public List<Map<String, Object>> getMainTaskListById(Integer size, Integer page, Long userId) {
        Integer offset = page * size - 5;
        return mainTaskDAO.getMainTaskListById(size, offset, userId);
    }


    @Override
    public List<CategoryDTO> getCategoryList() {
        return mainTaskDAO.getCategoryList();
    }

    @Override
    public void createMainTask(MainTaskDTO mainTaskDTO) {
        mainTaskDAO.createMainTask(mainTaskDTO);


        //포인트 리스트 조회
        List<PointDTO> pointList = pointDAO.getPointList();
        PointDTO addPoint = pointList.stream()
                .filter((p)->p.getAction().equals("게시글 등록"))
                .findFirst()
                .orElseThrow(()-> new NotFoundException("해당 포인트를 찾을 수 없습니다."));

        //UserPointDTO 생성
        UserPointDTO userPointDTO = UserPointDTO.of(mainTaskDTO.getUserId(), addPoint.getPointId());
        log.info("유저 포인트 DTO 객체 : " +userPointDTO);

        //UserPoint 객체 DB에 저장
        userPointDAO.userPointSave(userPointDTO);

        //유저가 가지고 있는 포인트 조회
        Long userTotalPoint = userPointDAO.getUserTotalPoint(mainTaskDTO.getUserId());

        //레벨 리스트 조회
        List<LevelDTO> levelDTOList = levelDAO.getLevelList();
        //레벨 지정
        Long userLevel = levelDTOList.stream()
                .filter(level -> userTotalPoint >= level.getLevelValue())  // 포인트가 levelValue보다 크거나 같은 레벨만 필터링
                .max(Comparator.comparingLong(LevelDTO::getLevelId))  // levelId가 가장 큰 값을 선택
                .map(LevelDTO::getLevelId)  // 해당 LevelDTO의 levelId만 추출
                .orElse(1L);

        //변경사항 저장
        UserDTO userDTO = userDAO.getUserDetailById(mainTaskDTO.getUserId());
        userDTO.setLevelId(userLevel);
        userDTO.setTotalPoint(userTotalPoint);
        userDAO.updateUser(userDTO);

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
    
      // home (top3, best5)
    @Override
    public List<MainTaskDTO> getTopViewCnt() {
      return mainTaskDAO.getTopViewCnt();
    }

    @Override
    public List<MainTaskDTO> getTopReplyCnt() {
      return mainTaskDAO.getTopReplyCnt();
    }

    @Override
    public List<MainTaskDTO> getTopLikeCnt() {
      return mainTaskDAO.getTopLikeCnt();
    }

    @Override
    public List<MainTaskDTO> getBestUserCnt() {
      return mainTaskDAO.getBestUserCnt();
    }

    @Override
    public List<MainTaskDTO> getBestCnt() {
      return mainTaskDAO.getBestCnt();
    }


    @Override
    public int getTotalOfMainTaskByUserId(Long userId) {
        int totalOfMainTask = mainTaskDAO.getTotalOfMainTaskByUserId(userId);
        int nOfPages = (int) Math.ceil((double) totalOfMainTask / 5);
        return nOfPages;
    }

    @Override
    public int getTotalOfMainTask() {
        int totalOfMainTask = mainTaskDAO.getTotalOfMainTask();
        int nOfPages = (int) Math.ceil((double) totalOfMainTask / 5);
        return nOfPages;
    }

    @Override
    public int getTotalOfMainTaskBySearch(String keyword, Long categoryId) {
        int totalOfMainTask = mainTaskDAO.getTotalOfMainTaskBySearch(keyword, categoryId);
        int nOfPages = (int) Math.ceil((double) totalOfMainTask / 5);
        return nOfPages;
    }


    @Scheduled(cron = "0 0 0 * * *")
    public void updateExpiredMainTaskStatus() {
        List<MainTaskDTO> expiredMainTasks = mainTaskDAO.getExpiredMainTasks(new Date());
        for (MainTaskDTO mainTaskDTO : expiredMainTasks) {
            mainTaskDTO.setTaskStatusId(4);
        }
        mainTaskDAO.updateExpiredMainTaskStatus(expiredMainTasks);
    }







}
