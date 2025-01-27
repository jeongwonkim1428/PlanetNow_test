package com.application.planetnow.mainTask;

import com.application.planetnow.subTask.SubTaskDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionIdChangedEvent;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/task")
public class MainTaskController {

    @Autowired
    MainTaskService mainTaskService;

    @GetMapping("/task-list")
    public String getMainTaskList(Model model) {

        model.addAttribute("mainTaskListMap" ,  mainTaskService.getMainTaskList());

        return "/task/task-list";
    }

    @PostMapping("/task-list")
    public String getMainTaskList(Model model,
                                  @RequestParam("keyword") String keyword) {

        model.addAttribute("mainTaskListMap" ,  mainTaskService.getMainTaskList(keyword));

        return "/task/task-list";
    }

    @GetMapping("/create-task")
    public String createMainTask(Model model,
                                 HttpServletRequest request) {

        //로그인 확인 필수. 없으면 로그인화면으로 리턴
        HttpSession session = request.getSession();

        model.addAttribute("categoryList", mainTaskService.getCategoryList());

        return "/task/create-task";
    }

    @PostMapping("/create-task")
    @ResponseBody
    public String createMainTask(@ModelAttribute MainTaskDTO mainTaskDTO) {

        System.out.println(mainTaskDTO);


        return "";
    }

}

