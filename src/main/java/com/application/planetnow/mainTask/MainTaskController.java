package com.application.planetnow.mainTask;

import com.application.planetnow.subTask.SubTaskDTO;
import com.application.planetnow.subTask.SubTaskService;
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

    @Autowired
    SubTaskService subTaskService;

    @GetMapping("/task-list")
    public String getMainTaskList(Model model) {

        model.addAttribute("mainTaskListMap" ,  mainTaskService.getMainTaskList());
        model.addAttribute("categoryList", mainTaskService.getCategoryList());

        return "/task/task-list";
    }

    @PostMapping("/task-list")
    public String getMainTaskList(Model model,
                                  @RequestParam("keyword") String keyword,
                                  @RequestParam("categoryId") Long categoryId) {

        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("mainTaskListMap" ,  mainTaskService.getMainTaskList(keyword, categoryId));
        model.addAttribute("categoryList", mainTaskService.getCategoryList());

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

        mainTaskDTO.setTaskStatusId(1);

        //유저 아이디 받아야함 세션 (추가예정)
        mainTaskDTO.setUserId(1L);

        mainTaskService.createMainTask(mainTaskDTO);

        // 생성된 mainTaskId 불러오기
        Long mainTaskId = mainTaskDTO.getMainTaskId();
        System.out.println(mainTaskId);

        // Extract the subTaskDtoList from MainTaskDTO
        List<SubTaskDTO> subTaskDtoList = mainTaskDTO.getSubTaskDtoList();
        if (subTaskDtoList != null && !subTaskDtoList.isEmpty()) {
            for (SubTaskDTO subTaskDTO : subTaskDtoList) {
                subTaskDTO.setMainTaskId(mainTaskId);
                subTaskDTO.setTaskStatusId(1);
                subTaskService.createSubTask(subTaskDTO);
                // 물어보자 여기서 for 돌리는 게 좋은지 아니면 sql 쿼리로 하는 게 좋은지 성능면에서
            }
        }
        String response = """
				<script>
					alert('게시글이 등록 되었습니다.');
					location.href = '/task/task-list';
				</script>""";
        return response;
    }


    @GetMapping("/task-detail")
    public String getMainTaskDetail(@RequestParam("mainTaskId") Long mainTaskId, Model model) {

        model.addAttribute("mainTaskDetail", mainTaskService.getMainTaskDetail(mainTaskId));
        model.addAttribute("subTaskList", subTaskService.getSubTaskList(mainTaskId));

        return "/task/task-detail";
    }

}

