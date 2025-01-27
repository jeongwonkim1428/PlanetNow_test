package com.application.planetnow.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPointDTO {
    private Long userPointId;
    private Long userId;
    private Long pointId;

    public static UserPointDTO of(Long userId, Long pointId){
       return UserPointDTO.builder()
                .userId(userId)
                .pointId(pointId)
                .build();
    }

}
