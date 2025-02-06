package com.application.planetnow.follow;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.application.planetnow.user.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/follow")
public class FollowController {

    @Value("${file.repo.path}")
    private String fileRepoPath;

    @Autowired
    private FollowService followService;
    
    @Autowired
    private UserService userService; 

    @GetMapping("/follower-list")
    public String followerList(Model model, @RequestParam("userId") Long userId,
    						   @RequestParam(value = "searchFollower", required = false) String searchFollower) {
        
    	List<Map<String, Object>> followerList = new ArrayList<>();
    	
    	// 맵생성 
    	Map<String, Object> temp = new HashMap<String, Object>();
    	
    	if (searchFollower == null || searchFollower.isEmpty()) {
    		// 아디만
    		temp.put("followeeId", userId);
    	}
    	else {
    		//아디, 검색
    		temp.put("followeeId", userId);
    		temp.put("searchFollower", searchFollower);
    	}
    	// 무조건 맵으로 전송
    	followerList = followService.getFollowerList(temp);
        
        for (Map<String, Object> follower : followerList) {
            // Long followeeId = (Long) follower.get("userId");
        	follower.put("mainTaskCount", followService.followerMainCnt((Long)follower.get("followerId")));
            follower.put("replyCount", followService.followerReplyCnt((Long)follower.get("followerId")));
            follower.put("followerCount", followService.followerCnt((Long)follower.get("followerId")));
//            System.out.println(follower.get("followerId"));
            follower.put("check", followService.check(userId, (Long)follower.get("followerId")));
            
//        	System.out.println(follower);
        }
//        System.out.println(searchFollower);
//        System.out.println("userId: " + userId);
        model.addAttribute("userId", userId);
        model.addAttribute("followerListMap", followerList);

        return "follow/follower-list";
        
    }
    
    @GetMapping("/following-list")
    public String followingList(Model model, HttpServletRequest request) {
    	
    	HttpSession session = request.getSession();
    	Long followerId = userService.getUserDetail((String) session.getAttribute("email")).getUserId();
    	List<Map<String, Object>> followingList = followService.getFollowingList(followerId);
        
        for (Map<String, Object> following : followingList) {
//            Long followerId = (Long) following.get("userId");
            following.put("mainTaskCount", followService.followingMainCnt(followerId));
            following.put("replyCount", followService.followingReplyCnt(followerId));
            following.put("followingCount", followService.followingCnt(followerId));
        }
        
        model.addAttribute("followingListMap", followingList);
    	
    	return "follow/following-list";
    	
    }
    
    @PostMapping("/following-list")
    public String getFollowingList(Model model,
    							   @RequestParam("searchFollowee") String searchFollowee) {
    	model.addAttribute("followingMap", followService.getFollowingList(searchFollowee));
    	return "/follow/following-list";
    }
    
    @PostMapping("/createFollow")
    @ResponseBody
    public String createFollower(@RequestParam("followeeId") Long followeeId, 
    						   @RequestParam("followerId") Long followerId) {
//    	Long followerId = userService.getUserDetail(followerEmail).getUserId();
//    	System.out.println(followeeId);
//    	System.out.println(followerId);
    	FollowDTO followDTO = new FollowDTO();
    	followDTO.setFolloweeId(followeeId);
    	followDTO.setFollowerId(followerId);
    	followService.createFollow(followDTO);
    	System.out.println(followDTO);
//    	System.out.println(result);
    	
    	return "";
    }
    
    @PostMapping("/deleteFollow")
    @ResponseBody
    public String deleteFollower(@RequestParam("followeeId") Long followeeId, 
			   				  @RequestParam("followerEmail") String followerEmail) {
    	Long followerId = userService.getUserDetail(followerEmail).getUserId();
    	followService.deleteFollow(followeeId, followerId);
    	System.out.println(followeeId);
    	System.out.println(followerId);
    	return "";
    }
//    
////    @PostMapping("/createFollowing")
////    @ResponseBody
////    public void createFollowing(@RequestBody FollowDTO followDTO) {
////    	followService.createFollowing(followDTO);
////    }
////    
//    @PostMapping("/deleteFollowing")
//    @ResponseBody
//    public String deleteFollowing(@RequestParam("followeeId") Long followeeId, 
//				  				@RequestParam("followerEmail") String followerEmail) {
//    	Long followerId = userService.getUserDetail(followerEmail).getUserId();
//    	followService.deleteFollowing(followeeId, followerId);
//    	System.out.println(followerId);
//    	return "";
//    }
    
}
