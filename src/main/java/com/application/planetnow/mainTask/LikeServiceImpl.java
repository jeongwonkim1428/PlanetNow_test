package com.application.planetnow.mainTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.planetnow.user.PointDAO;
import com.application.planetnow.user.PointDTO;
import com.application.planetnow.user.UserDAO;
import com.application.planetnow.user.UserDTO;
import com.application.planetnow.user.UserPointDAO;
import com.application.planetnow.user.UserPointDTO;

@Service
public class LikeServiceImpl implements LikeService {
	
	@Autowired
	private LikeDAO likeDAO;
	
	@Autowired
	private PointDAO pointDAO;

	@Autowired
	private UserPointDAO userPointDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Override
	public boolean getLikeStatus(Long mainTaskId, Long userId) {
		
		LikeDTO like = new LikeDTO();
		like.setMainTaskId(mainTaskId);
		like.setUserId(userId);
		LikeDTO likeDTO = likeDAO.getLikeStatus(like);
		if (likeDTO == null) {
			return false;
		}
		else {
			return true;
			
		}
		
	}

	@Override
	public void createHeart(LikeDTO likeDTO) {
		likeDAO.createHeart(likeDTO);
		
		Long userId = likeDTO.getUserId();
		PointDTO pointDTO = pointDAO.getLikePoint(); 
		Long pointId = pointDTO.getPointId();
		System.out.println(pointDTO);
		
		UserPointDTO userPointDTO = UserPointDTO.of(userId, pointId);
		
		// userPointSave user_point에 userId, pointId 값 넣음
		userPointDAO.userPointSave(userPointDTO);
		
		// getUserTotalPoint userId의 총 포인트 값
		Long point = userPointDAO.getUserTotalPoint(userId);
		System.out.println("총 포인트 : " + point);
		
		// 1-5레벨 포인트
        Long levelId;
        if (point >= 0 && point <= 99) {
            levelId = 1L;
        } else if (point >= 100 && point <= 199) {
            levelId = 2L;
        } else if (point >= 200 && point <= 299) {
            levelId = 3L;
        } else if (point >= 300 && point <= 499) {
            levelId = 4L;
        } else {
            levelId = 5L; 
        }
        //System.out.println(levelId);
        
        
        UserDTO userDTO = userDAO.getUserDetailById(userId);
        userDTO.setLevelId(levelId);
        userDTO.setTotalPoint(point);
        userDAO.updateUser(userDTO);

        System.out.println("유저 정보 : " + userDTO);
		
		
	}

	@Override
	public void deleteHeart(LikeDTO likeDTO) {
		likeDAO.deleteHeart(likeDTO);
	}

	@Override
	public int getLikeCnt(Long mainTaskId) {
		int likeCnt = likeDAO.getLikeCnt(mainTaskId);
		return likeCnt;
	}
}
