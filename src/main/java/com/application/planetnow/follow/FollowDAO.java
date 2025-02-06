package com.application.planetnow.follow;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FollowDAO {

	public List<Map<String, Object>> getFollowerList(Map<String, Object> temp);
	public Long followerMainCnt(Long followeeId);
	public Long followerReplyCnt(Long followeeId);
	public Long followerCnt(Long followeeId);

	public List<Map<String, Object>> getFollowingList(Long followerId0);
	public List<Map<String, Object>> getFollowingList(String searchFollowee);
	public Long followingMainCnt(Long followerId);
	public Long followingReplyCnt(Long followerId);
	public Long followingCnt(Long followerId);
	
	public void createFollow(FollowDTO followDTO);
	
	public void deleteFollow(Map<String, Long> list);
	public Long check(FollowDTO followDTO);

}
