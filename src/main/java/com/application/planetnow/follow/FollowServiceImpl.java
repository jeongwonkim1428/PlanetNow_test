package com.application.planetnow.follow;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowServiceImpl implements FollowService {


    @Autowired
    private FollowDAO  followDAO;

	@Override
	public List<Map<String, Object>> getFollowerList(Map<String, Object> temp) {
//		System.out.println(searchFollower);
		List<Map<String,Object>> a = followDAO.getFollowerList(temp);
		for (Map<String, Object> map : a) {
			System.out.println(map);
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
	public List<Map<String, Object>> getFollowingList(Long followerId) {
		return followDAO.getFollowingList(followerId);
	}
	
	@Override
	public List<Map<String, Object>> getFollowingList(String searchFollowee) {
		return followDAO.getFollowingList(searchFollowee);
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
//		if (followDAO.checkList(list) == null) {
//			
//			return "create";
//			
//		}
//		else {
//			return "already";
//		}
		
	}

	@Override
	public void deleteFollow(Long followeeId, Long followerId) {
		Map<String, Long> list = new HashMap<String, Long>();
		list.put("followeeId", followeeId);
		list.put("followerId", followerId);
		for (Map.Entry<String, Long> entry : list.entrySet()) {
			System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
		}
		
		followDAO.deleteFollow(list);
	}
	@Override
	public Integer check(Long followeeId, Long followerId) {
		
		Integer isTrue = 0;
		
		FollowDTO followDTO = new FollowDTO();
		followDTO.setFolloweeId(followeeId);
		followDTO.setFollowerId(followerId);
		
		followDAO.check(followDTO);
		if (followDAO.check(followDTO) == null) {
			isTrue = 1;
		}
		else {
			isTrue = -1;
		}

		return isTrue;
	}



}
