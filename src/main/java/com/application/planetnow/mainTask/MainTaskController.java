package com.application.planetnow.mainTask;

import com.application.planetnow.recommendedTask.RecommendedTaskService;
import com.application.planetnow.subTask.SubTaskDTO;
import com.application.planetnow.subTask.SubTaskService;
import com.application.planetnow.user.UserService;
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

    @Autowired
    RecommendedTaskService recommendedTaskService;

    @Autowired
    UserService userService;

    @GetMapping("/task-list")
    public String getMainTaskList(Model model,
                                  @RequestParam(value = "keyword", required = false) String keyword,
                                  @RequestParam(value = "categoryId", required = false) Long categoryId) {


        if (keyword == null && categoryId == null) {
            model.addAttribute("mainTaskListMap", mainTaskService.getMainTaskList());
        } else {
            model.addAttribute("selectedCategoryId", categoryId);
            model.addAttribute("mainTaskListMap", mainTaskService.getMainTaskList(keyword, categoryId));
        }

        model.addAttribute("categoryList", mainTaskService.getCategoryList());

        return "/task/task-list";
    }

    @GetMapping("/create-task")
    public String createMainTask(Model model,
                                 HttpServletRequest request) {

        //로그인 확인 필수. 없으면 로그인화면으로 리턴
        HttpSession session = request.getSession();
        if (session.getAttribute("email") == null) {
            return "redirect:/user/login";
        }


        model.addAttribute("categoryList", mainTaskService.getCategoryList());

        return "/task/create-task";
    }

    @PostMapping("/create-task")
    @ResponseBody
    public String createMainTask(@ModelAttribute MainTaskDTO mainTaskDTO,
                                 HttpServletRequest request) {
        HttpSession session = request.getSession();

        mainTaskDTO.setTaskStatusId(1);

        System.out.println(session.getAttribute("email"));

        userService.getUserDetail((String)session.getAttribute("email")).getUserId();

        mainTaskDTO.setUserId(userService.getUserDetail((String)session.getAttribute("email")).getUserId());


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
                //여기서 for 돌리는 게 좋은지 아니면 sql 쿼리로 하는 게 좋은지 성능면에서
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
    public String getMainTaskDetail(@RequestParam("mainTaskId") Long mainTaskId, Model model,
                                    HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("email") == null) {
            return "redirect:/user/login";
        }


        model.addAttribute("mainTaskDetail", mainTaskService.getMainTaskDetail(mainTaskId));
        model.addAttribute("subTaskList", subTaskService.getSubTaskList(mainTaskId));
        model.addAttribute("recommendedTaskList", recommendedTaskService.getRecommendedTaskList(mainTaskId));
        return "/task/task-detail";
    }

    @GetMapping("/task-update")
    public String updateMainTask(Model model,
                                 @RequestParam("mainTaskId") Long mainTaskId) {
        model.addAttribute("mainTaskDTO", mainTaskService.getMainTaskDTO(mainTaskId));
        model.addAttribute("categoryList", mainTaskService.getCategoryList());

        return "/task/task-update";
    }

    @PostMapping("/task-update")
    @ResponseBody
    public String updateMainTask(@ModelAttribute MainTaskDTO mainTaskDTO) {

        mainTaskService.updateMainTask(mainTaskDTO);

        // Extract the subTaskDtoList from MainTaskDTO
        List<SubTaskDTO> subTaskDtoList = mainTaskDTO.getSubTaskDtoList();
        if (subTaskDtoList != null && !subTaskDtoList.isEmpty()) {
            for (SubTaskDTO subTaskDTO : subTaskDtoList) {
                subTaskDTO.setMainTaskId(mainTaskDTO.getMainTaskId());
                subTaskDTO.setTaskStatusId(1);
                subTaskService.createSubTask(subTaskDTO);
                //여기서 for 돌리는 게 좋은지 아니면 sql 쿼리로 하는 게 좋은지 성능면에서
            }
        }

        String jsScript = "";
        jsScript += "<script>";
        jsScript += "alert('수정되었습니다.');";
        jsScript += "location.href='/task/task-detail?mainTaskId=" + mainTaskDTO.getMainTaskId() + "';";
        jsScript += "</script>";

        return jsScript;
    }

    @GetMapping("/task-delete")
    @ResponseBody
    public String deleteMainTask(@RequestParam("mainTaskId") Long mainTaskId,
                                 HttpServletRequest request) {

        mainTaskService.getMainTaskDTO(mainTaskId).getUserId();
        HttpSession session = request.getSession();

        String response = "";

        if (!session.getAttribute("userId").equals(mainTaskService.getMainTaskDTO(mainTaskId).getUserId())) {
            response = """
				<script>
					alert('권한이 없습니다');
					history.go(-1);
				</script>""";
        }
        else {
            mainTaskService.deleteMainTask(mainTaskId);
            response = """
				<script>
					alert('삭제가 완료되었습니다');
					location.href = '/task/task-list';
				</script>""";
        }

        return response;
    }

    @GetMapping("/my-profile/task-delete")
    @ResponseBody
    public String deleteMainTaskFromMyProfile(@RequestParam("mainTaskId") Long mainTaskId,
                                 HttpServletRequest request) {

        mainTaskService.getMainTaskDTO(mainTaskId).getUserId();
        HttpSession session = request.getSession();

        String response = "";

        if (!session.getAttribute("userId").equals(mainTaskService.getMainTaskDTO(mainTaskId).getUserId())) {
            response = """
				<script>
					alert('권한이 없습니다');
					history.go(-1);
				</script>""";
        }
        else {
            mainTaskService.deleteMainTask(mainTaskId);
            response = """
				<script>
					alert('삭제가 완료되었습니다');
					history.go(-1);
				</script>""";
        }
        return response;
    }

}

