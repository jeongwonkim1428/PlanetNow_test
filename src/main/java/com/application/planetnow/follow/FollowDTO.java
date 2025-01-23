package com.application.planetnow.follow;

import lombok.Data;

@Data
public class FollowDTO {
    private Long followId;
    private Long followerId;
    private Long followeeId;

}
