package com.application.planetnow.reply;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.planetnow.user.LevelDAO;
import com.application.planetnow.user.PointDAO;
import com.application.planetnow.user.PointDTO;
import com.application.planetnow.user.UserDAO;
import com.application.planetnow.user.UserDTO;
import com.application.planetnow.user.UserPointDAO;
import com.application.planetnow.user.UserPointDTO;

@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private ReplyDAO replyDAO;
    
    @Autowired
    private PointDAO pointDAO;
    
    @Autowired
    private UserPointDAO userPointDAO;
    
    @Autowired
    private LevelDAO levelDAO;
    
    @Autowired
    private UserDAO userDAO;

    @Override
    public List<Map<String, Object>> getReplyList(Long mainTaskId) {
        List<Map<String, Object>> replyDTO= replyDAO.getReplyList(mainTaskId);

        return replyDTO;
    }



    @Override
    public ReplyDTO getReplyDetail(Long replyId) {
        ReplyDTO replyDTO = replyDAO.getReplyDetail(replyId);
        return replyDTO;
    }

    @Override
    public int getReplyCnt(Long mainTaskId) {
        int replyCnt =replyDAO.getReplyCnt(mainTaskId);
        return replyCnt;
    }


    @Override
    public void createReply(ReplyDTO replyDTO) {
        replyDAO.createReply(replyDTO);
        
        Long userId = replyDTO.getUserId();
        
        PointDTO pointDTO = pointDAO.getReplyPoint();
        Long pointId = pointDTO.getPointId();
        UserPointDTO userPointDTO = UserPointDTO.of(userId, pointId);
        userPointDAO.userPointSave(userPointDTO);

        // userId 유저 포인트 합
        Long totalPoint = userPointDAO.getUserTotalPoint(userId);
        System.out.println("총 포인트 : " + totalPoint);

        // 레벨 계산
        Long levelId;
        if (totalPoint >= 0 && totalPoint <= 99) { // 0이상 99이하 
            levelId = 1L; // 레벨 1
        } else if (totalPoint >= 100 && totalPoint <= 199) { // 100이상 199이하
            levelId = 2L; // 레벨 2
        } else if (totalPoint >= 200 && totalPoint <= 299) { // 200이상 299이하
            levelId = 3L; // 레벨 3 
        } else if (totalPoint >= 300 && totalPoint <= 499) { // 300이상 499이하
            levelId = 4L; // 레벨 4 
        } else {
            levelId = 5L; // 포인트 500이상은 레벨 5
        }

        System.out.println("레벨 : " + levelId);

        // 유저 정보
        UserDTO userDTO = userDAO.getUserDetailById(userId);
        
        // levelId, totalPoin 업데이트
        userDTO.setLevelId(levelId);
        userDTO.setTotalPoint(totalPoint);
        
        // 업데이트한 유저 정보 저장
        userDAO.updateUser(userDTO);

        System.out.println("업데이트 : " + userDTO);
        
    }




    @Override
    public void deleteReply(Long replyId) {
        replyDAO.deleteReply(replyId);
    }



    @Override
    public void updateReply(ReplyDTO replyDTO) {
        replyDAO.updateReply(replyDTO);
    }


}
