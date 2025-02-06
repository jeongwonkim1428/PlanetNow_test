package com.application.planetnow.mainTask;
import org.springframework.stereotype.Service;


public interface LikeService {

	public boolean getLikeStatus(Long mainTaskId, Long userId);

	public void createHeart(LikeDTO likeDTO);

	public void deleteHeart(LikeDTO likeDTO);
	
	public int getLikeCnt(Long mainTaskId);


	
}
