package com.application.planetnow.reply;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.planetnow.user.UserDTO;
import com.application.planetnow.user.UserService;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @Autowired
    private UserService userService;

    @GetMapping("/createReply")
    public String createReply(Model model , @RequestParam("mainTaskId") Long mainTaskId) {

        model.addAttribute("mainTaskId", mainTaskId);

        return "task/task-detail";
    }


    @PostMapping("/createReply")
    @ResponseBody
    public String createReply(@ModelAttribute ReplyDTO replyDTO,
                              HttpServletRequest request) {

        System.out.println(replyDTO.getMainTaskId());
        // 세션에서 이메일 가져오기
        HttpSession session = request.getSession();


        // 세션에서 가져온 이메일을 email 변수에 저장
        String email = (String) session.getAttribute("email");

        // 이메일로 사용자 정보를 조회하고 UserDTO에 저장
        UserDTO userDTO = userService.getUserDetail(email);

        // userDTO가 null 이면 오류 메시지 반환
        if (userDTO == null) {
            return "<script>alert('로그인 시 댓글 등록이 가능합니다..'); location.href='/auth/login.html';</script>";
        }

        // 댓글 작성자의 ID 저장
        replyDTO.setUserId(userDTO.getUserId());

        // 댓글을 DB에 저장
        replyService.createReply(replyDTO);

        // 등록 완료 메시지 반환
        return "<script>alert('댓글이 등록되었습니다.'); "
                + "location.href='/task/task-detail?mainTaskId=" + replyDTO.getMainTaskId() + "';</script>";
    }


    @PostMapping("/updateReply")
    @ResponseBody
    public boolean updateReply(@RequestBody ReplyDTO replyDTO) {
        replyService.updateReply(replyDTO);
        return true;
    }

    @GetMapping("/deleteReply")
    @ResponseBody
    public String deleteReply(@RequestParam("replyId") Long replyId) {

        replyService.deleteReply(replyId);

        String jsScript = """
				<script>
					alert('삭제되었습니다.');
					history.go(-1)
				</script>
				""";
        return jsScript;
    }

}
