package com.application.planetnow.mainTask;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikeDAO {

	public LikeDTO getLikeStatus(LikeDTO like);

	public void createHeart(LikeDTO likeDTO);

	public void deleteHeart(LikeDTO likeDTO);

	public int getLikeCnt(Long mainTaskId);
	

}
