package com.application.planetnow.follow;

import lombok.Data;

@Data
public class FollowDTO {
    private Long followId;
    private Long followerId;    // 팔로우 하는
    private Long followeeId;    // 팔로우 당하는 사람

}
