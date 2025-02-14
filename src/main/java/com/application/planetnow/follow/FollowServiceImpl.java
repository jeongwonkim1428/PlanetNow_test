package com.application.planetnow.follow;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.planetnow.user.LevelDAO;
import com.application.planetnow.user.LevelDTO;
import com.application.planetnow.user.PointDAO;
import com.application.planetnow.user.PointDTO;
import com.application.planetnow.user.UserDAO;
import com.application.planetnow.user.UserDTO;
import com.application.planetnow.user.UserPointDAO;
import com.application.planetnow.user.UserPointDTO;

@Service
public class FollowServiceImpl implements FollowService {


    @Autowired
    private FollowDAO  followDAO;
    
    @Autowired
    private PointDAO pointDAO;
    
    @Autowired
    private UserPointDAO userPointDAO;
    
    @Autowired
    private UserDAO userDAO;
    
    @Autowired
    private LevelDAO levelDAO;

	@Override
	public List<Map<String, Object>> getFollowerList(Map<String, Object> temp) {
//		System.out.println(searchFollower);
		List<Map<String,Object>> a = followDAO.getFollowerList(temp);
		for (Map<String, Object> map : a) {
//			System.out.println(map);
		}
		return a;
	}

	@Override
	public Long followerMainCnt(Long followeeId) {
		return followDAO.followerMainCnt(followeeId);
	}

	@Override
	public Long followerReplyCnt(Long followeeId) {
		return followDAO.followerReplyCnt(followeeId);
	}

	@Override
	public Long followerCnt(Long followeeId) {
		return followDAO.followerCnt(followeeId);
	}

	@Override
	public List<Map<String, Object>> getFollowingList(Map<String, Object> temp) {
		return followDAO.getFollowingList(temp);
	}
	
	@Override
	public Long followingMainCnt(Long followerId) {
		return followDAO.followingMainCnt(followerId);
	}

	@Override
	public Long followingReplyCnt(Long followerId) {
		return followDAO.followingReplyCnt(followerId);
	}

	@Override
	public Long followingCnt(Long followerId) {
		return followDAO.followingCnt(followerId);
	}

	
	@Override
	public void createFollow(FollowDTO followDTO) {
			followDAO.createFollow(followDTO);
			Long followerId = followDTO.getFollowerId();

			List<PointDTO> pointList = pointDAO.getPointList();
			PointDTO pointDTO = new PointDTO();
			
			for (PointDTO dto : pointList) {
				if (dto.getAction().equals("팔로우")) {
					pointDTO = dto;
//					System.out.println(pointDTO);
				}
			}
			Long pointId = pointDTO.getPointId();
//			System.out.println("pointId: " + pointId);
			
			
			UserPointDTO userPointDTO = UserPointDTO.of(followerId, pointId);
//			System.out.println(userPointDTO);
			
			userPointDAO.userPointSave(userPointDTO);
			
			Long point = userPointDAO.getUserTotalPoint(followerId);
//			System.out.println("Point: " + point);
			
			List<LevelDTO> levelList = levelDAO.getLevelList();
			Long level = (long) 1;
			
			for (LevelDTO levelDTO : levelList) {
				if (point >= levelDTO.getLevelValue()) {
					level = levelDTO.getLevelId();
				}
			}
			
			
//			System.out.println("Level: " + level);
			
			
			UserDTO userDetail = userDAO.getUserDetailById(followerId);
//			System.out.println("유저: " + userDetail);

			userDetail.setTotalPoint(point);
			userDetail.setLevelId(level);
//			System.out.println("변경후: " + userDetail);

			
			userDAO.updateUser(userDetail);
		
	}

	@Override
	public void deleteFollow(Long followeeId, Long followerId) {
		Map<String, Long> list = new HashMap<String, Long>();
		list.put("followeeId", followeeId);
		list.put("followerId", followerId);
		for (Map.Entry<String, Long> entry : list.entrySet()) {
//			System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
		}
		
		followDAO.deleteFollow(list);
	}
	@Override
	public Integer check(Long followeeId, Long followerId) {
		
		FollowDTO followDTO = new FollowDTO();
		followDTO.setFolloweeId(followeeId);
		followDTO.setFollowerId(followerId);
//		System.out.println("followerId: " + followerId);
//		System.out.println("followeeId: " + followeeId);
		
		return followDAO.check(followDTO);

	}



}
