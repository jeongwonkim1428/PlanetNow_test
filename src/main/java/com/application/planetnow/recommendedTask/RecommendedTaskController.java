package com.application.planetnow.recommendedTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/recommended")
public class RecommendedTaskController {

    @Autowired
    RecommendedTaskService recommendedTaskService;

    @PostMapping("/create-recommended-task")
    @ResponseBody
    public String createRecommendedTask(@ModelAttribute RecommendedTaskDTO recommendedTaskDTO) {

        //세션 받아와서 userId 저장해야함
        recommendedTaskDTO.setUserId(1L);
        recommendedTaskService.createRecommendedTask(recommendedTaskDTO);

        String jsScript = "";
        jsScript += "<script>";
        jsScript += "alert('추천완료.');";
        jsScript += "location.href='/task/task-detail?mainTaskId=" + recommendedTaskDTO.getMainTaskId() + "';";
        jsScript += "</script>";

        return jsScript;
    }
}
