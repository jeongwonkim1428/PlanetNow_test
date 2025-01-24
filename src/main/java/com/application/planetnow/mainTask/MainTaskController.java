package com.application.planetnow.mainTask;

import org.springframework.beans.factory.annotation.Autowired;
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
    public String mainTaskList(Model model) {

        model.addAttribute("mainTaskListMap" ,  mainTaskService.getMainTaskList());

        return "/task/task-list";
    }

    @PostMapping("/searchMainTask")
    @ResponseBody
    public ModelAndView searchMainTask(@RequestParam("keyword") String keyword,
                                 Model model) {
        List<Map<String, Object>> mainTaskListMap = mainTaskService.getSearchMainTaskList(keyword);
        model.addAttribute("mainTaskListMap", mainTaskListMap);

        // Prepare the model and view
        ModelAndView modelAndView = new ModelAndView("fragments/task-rows");
        modelAndView.addObject("mainTaskListMap", mainTaskListMap);

        return modelAndView;
    }

}

