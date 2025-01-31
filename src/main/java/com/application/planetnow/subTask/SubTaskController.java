package com.application.planetnow.subTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/update-sub-task")
    @ResponseBody
    public String updateSubTask(@ModelAttribute SubTaskDTO subTaskDTO) {
        System.out.println(subTaskDTO.getSubTaskId());
        System.out.println(subTaskDTO.getSubSubject());
        System.out.println(subTaskDTO.getMainTaskId());

        subTaskService.updateSubTask(subTaskDTO);

        String jsScript = "";
            jsScript += "<script>";
            jsScript += "alert('수정되었습니다.');";
            jsScript += "location.href='/task/task-detail?mainTaskId=" + subTaskDTO.getMainTaskId() + "';";
            jsScript += "</script>";

        return jsScript;
    }


}
