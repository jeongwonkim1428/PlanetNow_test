package com.application.planetnow.follow;

import java.util.List;
import java.util.Map;

public interface FollowService {

	public List<Map<String, Object>> getFollowerList();
	public Long followerMainCnt(Long followeeId);
	public Long followerReplyCnt(Long followeeId);
	public Long followerCnt(Long followeeId);

	public List<Map<String, Object>> getFollowingList();
	public Long followingMainCnt(Long followerId);
	public Long followingReplyCnt(Long followerId);
	public Long followingCnt(Long followerId);
	
	public void createFollow(FollowDTO followDTO);
	public void deleteFollow(FollowDTO followDTO);
}
