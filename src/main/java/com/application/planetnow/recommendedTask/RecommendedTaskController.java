package com.application.planetnow.recommendedTask;

import com.application.planetnow.subTask.SubTaskDTO;
import com.application.planetnow.subTask.SubTaskService;
import com.application.planetnow.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/recommended")
public class RecommendedTaskController {

    @Autowired
    RecommendedTaskService recommendedTaskService;

    @Autowired
    SubTaskService subTaskService;

    @Autowired
    UserService userService;

    @PostMapping("/create-recommended-task")
    @ResponseBody
    public String createRecommendedTask(@ModelAttribute RecommendedTaskDTO recommendedTaskDTO,
                                        HttpServletRequest request) {

        //세션 받아와서 userId 저장해야함

        HttpSession session = request.getSession();
        System.out.println(session.getAttribute("email"));

        userService.getUserDetail((String)session.getAttribute("email")).getUserId();

        recommendedTaskDTO.setUserId(userService.getUserDetail((String)session.getAttribute("email")).getUserId());

        recommendedTaskService.createRecommendedTask(recommendedTaskDTO);

        String jsScript = "";
        jsScript += "<script>";
        jsScript += "alert('추천완료.');";
        jsScript += "location.href='/task/task-detail?mainTaskId=" + recommendedTaskDTO.getMainTaskId() + "';";
        jsScript += "</script>";

        return jsScript;
    }

    @PostMapping("/accept-recommended-task")
    @ResponseBody
    public String acceptRecommendedTask(@RequestBody RecommendedTaskDTO recommendedTaskDTO) {

        RecommendedTaskDTO recommendedTaskDTO1 = recommendedTaskService.getRecommendedTaskDTO(recommendedTaskDTO.getRecommendedTaskId());
        SubTaskDTO subTaskDTO = new SubTaskDTO();
        subTaskDTO.setTaskStatusId(1);
        subTaskDTO.setMainTaskId(recommendedTaskDTO1.getMainTaskId());
        subTaskDTO.setSubSubject(recommendedTaskDTO1.getSubSubject());
        subTaskService.createSubTask(subTaskDTO);

        recommendedTaskService.declineRecommendedTask(recommendedTaskDTO.getRecommendedTaskId());
        return "";
    }

    @PostMapping("/decline-recommended-task")
    @ResponseBody
    public String declineRecommendedTask(@RequestBody RecommendedTaskDTO recommendedTaskDTO) {
        recommendedTaskService.declineRecommendedTask(recommendedTaskDTO.getRecommendedTaskId());
        return "";
    }
}
