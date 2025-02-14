package com.application.planetnow;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.application.planetnow.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.planetnow.mainTask.MainTaskDTO;
import com.application.planetnow.mainTask.MainTaskService;
import com.application.planetnow.user.UserDTO;
import com.application.planetnow.user.UserService;

@Controller
public class MainController {

	
	@Autowired
	private MainTaskService mainTaskService;
	
    @Autowired
    UserService userService;


    @GetMapping("/layout")
    public String layout() {
        return "/layout";
    }
    
    @GetMapping("/home")
    public String home(Model model ) { 
    	
    	// 조회수 top3
    	List<MainTaskDTO> topViewList = mainTaskService.getTopViewCnt();
    	// 좋아요 top3
    	List<MainTaskDTO> topLikeList = mainTaskService.getTopLikeCnt();
    	// 댓글 top3
    	List<MainTaskDTO> topReplyList = mainTaskService.getTopReplyCnt();
    	
    	model.addAttribute("topViewList", topViewList);
    	model.addAttribute("topLikeList", topLikeList);
    	model.addAttribute("topReplyList", topReplyList);
    	
    	// 베스트 유저 top1
    	List<MainTaskDTO> bestUser = mainTaskService.getBestUserCnt(); 
    	
    	// 베스트 유저 top2-5
    	List<MainTaskDTO> bestUserList = mainTaskService.getBestCnt(); 
    	
    	model.addAttribute("bestUser", bestUser);
    	model.addAttribute("bestUserList" , bestUserList);
    	return "/home";
    }

	@GetMapping("/chat")
	public String chat(Model model, HttpServletRequest request) {
		model.addAttribute("userId", request.getSession().getAttribute("userId"));
		model.addAttribute("nickname", userService.getUserDetail((String) request.getSession().getAttribute("email")).getNickname());
		return "/chat/chat";
	}
    
}
