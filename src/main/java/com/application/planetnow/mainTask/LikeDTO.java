package com.application.planetnow.mainTask;

import lombok.Data;

@Data
public class LikeDTO {
	
	private Long likeId;
	private Long userId;
	private Long mainTaskId;
}
