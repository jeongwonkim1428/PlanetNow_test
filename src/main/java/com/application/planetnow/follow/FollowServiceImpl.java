package com.application.planetnow.follow;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowServiceImpl implements FollowService {


    @Autowired
    private FollowDAO  followDAO;

	@Override
	public List<Map<String, Object>> getFollowerList() {
		return followDAO.getFollowerList();
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
	public List<Map<String, Object>> getFollowingList() {
		return followDAO.getFollowingList();
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
	}

	@Override
	public void deleteFollow(FollowDTO followDTO) {
		followDAO.deleteFollow(followDTO);
	}

}
