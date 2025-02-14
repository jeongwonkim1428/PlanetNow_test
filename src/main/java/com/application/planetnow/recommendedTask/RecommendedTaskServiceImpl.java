package com.application.planetnow.recommendedTask;

import com.application.planetnow.user.*;
import com.application.planetnow.user.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class RecommendedTaskServiceImpl implements RecommendedTaskService {

    @Autowired
    RecommendedTaskDAO recommendedTaskDAO;

    @Autowired
    UserPointDAO userPointDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    PointDAO pointDAO;
    @Autowired
    LevelDAO levelDAO;

    @Override
    public List<RecommendedTaskDTO> getRecommendedTaskList(Long mainTaskId) {
        return recommendedTaskDAO.getRecommendedTaskList(mainTaskId);
    }

    @Override
    public void createRecommendedTask(RecommendedTaskDTO recommendedTaskDTO) {
        recommendedTaskDAO.createRecommendedTask(recommendedTaskDTO);

        //포인트 리스트 조회
        List<PointDTO> pointList = pointDAO.getPointList();
        PointDTO addPoint = pointList.stream()
                .filter((p)->p.getAction().equals("서브태스크추천"))
                .findFirst()
                .orElseThrow(()-> new NotFoundException("해당 포인트를 찾을 수 없습니다."));

        //UserPointDTO 생성
        UserPointDTO userPointDTO = UserPointDTO.of(recommendedTaskDTO.getUserId(), addPoint.getPointId());
        log.info("유저 포인트 DTO 객체 : " +userPointDTO);

        //UserPoint 객체 DB에 저장
        userPointDAO.userPointSave(userPointDTO);

        //유저가 가지고 있는 포인트 조회
        Long userTotalPoint = userPointDAO.getUserTotalPoint(recommendedTaskDTO.getUserId());

        //레벨 리스트 조회
        List<LevelDTO> levelDTOList = levelDAO.getLevelList();
        //레벨 지정
        Long userLevel = levelDTOList.stream()
                .filter(level -> userTotalPoint >= level.getLevelValue())  // 포인트가 levelValue보다 크거나 같은 레벨만 필터링
                .max(Comparator.comparingLong(LevelDTO::getLevelId))  // levelId가 가장 큰 값을 선택
                .map(LevelDTO::getLevelId)  // 해당 LevelDTO의 levelId만 추출
                .orElse(1L);

        //변경사항 저장
        UserDTO userDTO = userDAO.getUserDetailById(recommendedTaskDTO.getUserId());
        userDTO.setLevelId(userLevel);
        userDTO.setTotalPoint(userTotalPoint);
        userDAO.updateUser(userDTO);

    }

    @Override
    public RecommendedTaskDTO getRecommendedTaskDTO(Long recommendedTaskId) {
        return recommendedTaskDAO.getRecommendedTaskDTO(recommendedTaskId);
    }

    @Override
    public void declineRecommendedTask(Long recommendedTaskId) {
        recommendedTaskDAO.declineRecommendedTask(recommendedTaskId);
    }
}
