package com.application.planetnow.subTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sub-task")
public class SubTaskController {

    @Autowired
    SubTaskService subTaskService;

    @PostMapping("/change-status")
    @ResponseBody
    public String changeStatus (@RequestBody SubTaskDTO subTaskDTO) {
        System.out.println(subTaskDTO.getSubTaskId());
        System.out.println(subTaskDTO.getTaskStatusId());
        subTaskService.changeStatus(subTaskDTO);
        return "success";
    }


}
