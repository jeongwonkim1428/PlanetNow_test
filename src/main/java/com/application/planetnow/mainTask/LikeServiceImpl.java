package com.application.planetnow.mainTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {
	
	@Autowired
	private LikeDAO likeDAO;

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
