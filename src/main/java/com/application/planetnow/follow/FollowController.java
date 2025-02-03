package com.application.planetnow.follow;



import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/follow")
public class FollowController {

    @Value("${file.repo.path}")
    private String fileRepoPath;

    @Autowired
    private FollowService followService;

    @GetMapping("/follower-list")
    public String followerList(Model model) {
        
    	List<Map<String, Object>> followerList = followService.getFollowerList();
        
        for (Map<String, Object> follower : followerList) {
            Long followeeId = (Long) follower.get("userId");
            follower.put("mainTaskCount", followService.followerMainCnt(followeeId));
            follower.put("replyCount", followService.followerReplyCnt(followeeId));
            follower.put("followerCount", followService.followerCnt(followeeId));
        }

        model.addAttribute("followerListMap", followerList);

        return "follow/follower-list";
        
    }
    
    @GetMapping("/following-list")
    public String followingList(Model model) {
    	
    	List<Map<String, Object>> followingList = followService.getFollowingList();
        
        for (Map<String, Object> following : followingList) {
            Long followerId = (Long) following.get("userId");
            following.put("mainTaskCount", followService.followingMainCnt(followerId));
            following.put("replyCount", followService.followingReplyCnt(followerId));
            following.put("followingCount", followService.followingCnt(followerId));
        }

        model.addAttribute("followingListMap", followingList);
    	
    	return "follow/following-list";
    	
    }
    
    @PostMapping("/createFollow")
    @ResponseBody
    public void createFollow(@RequestBody FollowDTO followDTO) {
    	followService.createFollow(followDTO);
    }
    
    @PostMapping("/deleteFollow")
    @ResponseBody
    public void deleteFollow(@RequestBody FollowDTO followDTO) {
    	followService.deleteFollow(followDTO);
    }
    
}
