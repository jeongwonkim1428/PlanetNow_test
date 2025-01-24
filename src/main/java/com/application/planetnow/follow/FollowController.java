package com.application.planetnow.follow;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/follow")
public class FollowController {

    @GetMapping("/follower-list")
    public String followerList() {
        return "/user/user-list";
    }
}
