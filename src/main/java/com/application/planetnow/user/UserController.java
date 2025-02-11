package com.application.planetnow.user;


import com.application.planetnow.follow.FollowService;
import com.application.planetnow.mainTask.LikeService;
import com.application.planetnow.mainTask.MainTaskService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final MainTaskService mainTaskService;
    private final FollowService followService;
    private final LikeService likeService;
    @Value("${file.repo.path}")
    private String fileRepo;
    @GetMapping("/home")
    public String home(){
        return "home";
    }
    @GetMapping("/login")
    public String login(HttpServletRequest request){
        UserDTO userDTO = userService.getUserFromSession(request);
        if (userDTO != null) {
            return "redirect:/home";
        }
        return "/auth/login";
    }
    @PostMapping("/login")
    @ResponseBody
    public boolean login(@RequestBody UserDTO userDTO, HttpServletRequest request){

        boolean is = userService.loginResult(userDTO);
        if (is == true){
            HttpSession session = request.getSession();
            session.setAttribute("email",userDTO.getEmail());
            session.setAttribute("userId", userService.getUserDetail(userDTO.getEmail()).getUserId());
            session.setAttribute("nickname", userService.getUserDetail(userDTO.getEmail()).getNickname());
            return true;
        }else {
            return false;
        }


    }
    @GetMapping("/sign-up")
    public String signUp(HttpServletRequest request ){
        UserDTO userDTO = userService.getUserFromSession(request);
        if (userDTO != null) {
            return "redirect:/home";
        }
        return "/auth/sign-up";
    }
    @PostMapping("/sign-up")
    @ResponseBody
    public String signUp(@RequestParam("myProfile")MultipartFile myProfile,@ModelAttribute UserDTO userDTO) throws IOException {
        userService.signUp(myProfile,userDTO);
        String jsScript = """
            <script>
               alert('회원가입 되었습니다.');
               location.href = '/user/login';
            </script>""";
        return jsScript;
    }
    @PostMapping("/valid-email")
    @ResponseBody
    public boolean validEmail(@RequestParam("email") String email){
        return userService.validEmailResult(email);
    }
    @PostMapping("/valid-nickname")
    @ResponseBody
    public boolean validNickname(@RequestParam("nickname") String nickname){
        return userService.validNicknameResult(nickname);
    }
    @GetMapping("/search")
    public String searchUser(){
        return "/user/user-list";
    }
    @GetMapping("/name")
    @ResponseBody
    public List<Map<String, Object>> searchName(@RequestParam("search") String search) {
        return userService.searchUser(search);
    }

    @GetMapping("/user-detail")
    public String userDetail(@RequestParam("userId")Long userId,Model model ){
        UserDTO userDTO = userService.getUserDetailById(userId);
        log.info("유저 정보: " + userDTO);
        model.addAttribute("userDTO",userDTO);
        Integer size = 5;

        List<Map<String, Object>> mainTaskList = mainTaskService.getMainTaskListById(size, 1, userDTO.getUserId());
        for (Map<String, Object> mainTask : mainTaskList) {
            Long mainTaskId = (Long) mainTask.get("mainTaskId");
            mainTask.put("likeCnt", likeService.getLikeCnt(mainTaskId));
        }
        model.addAttribute("nOfPages", mainTaskService.getTotalOfMainTaskByUserId(userDTO.getUserId()));
        model.addAttribute("mainTaskListMap", mainTaskList);
        model.addAttribute("followerCount", followService.followerCnt(userDTO.getUserId()));
        model.addAttribute("followingCount", followService.followingCnt(userDTO.getUserId()));
        model.addAttribute("progress", userService.getProgress(userDTO.getUserId()));

        return "/user/user-detail";
    }


    @PostMapping("/user-detail-nextpage")
    @ResponseBody
    public List<Map<String, Object>> taskListNextPage(@RequestParam("userId")Long userId,
                                                      @RequestParam("page") Integer page) {

        Integer size = 5;
        System.out.println(page);

        List<Map<String, Object>> mainTaskList = mainTaskService.getMainTaskListById(size, page, userId);
        for (Map<String, Object> mainTask : mainTaskList) {
            Long mainTaskId = (Long) mainTask.get("mainTaskId");
            mainTask.put("likeCnt", likeService.getLikeCnt(mainTaskId));
        }
        System.out.println(mainTaskList);

        return mainTaskList;
    }







    @GetMapping("/profile")
    public String myProfile(HttpServletRequest request, Model model){
        UserDTO userDTO = userService.getUserFromSession(request);
        if (userDTO == null) {
            return "/user/login";
        }
        model.addAttribute("userDTO", userDTO);

        Integer size = 5;

        List<Map<String, Object>> mainTaskList = mainTaskService.getMainTaskListById(size, 1, userDTO.getUserId());
            for (Map<String, Object> mainTask : mainTaskList) {
                Long mainTaskId = (Long) mainTask.get("mainTaskId");
                mainTask.put("likeCnt", likeService.getLikeCnt(mainTaskId));
            }


        model.addAttribute("nOfPages", mainTaskService.getTotalOfMainTaskByUserId(userDTO.getUserId()));

        model.addAttribute("mainTaskListMap", mainTaskList);
        model.addAttribute("followerCount", followService.followerCnt(userDTO.getUserId()));
        model.addAttribute("followingCount", followService.followingCnt(userDTO.getUserId()));

        model.addAttribute("progress", userService.getProgress(userDTO.getUserId()));

        return "/mypage/profile";
    }


    @PostMapping("/profile-nextpage")
    @ResponseBody
    public List<Map<String, Object>> profileNextPage(@RequestParam("page") Integer page, Model model,
                                                     HttpServletRequest request) {
        UserDTO userDTO = userService.getUserFromSession(request);
        Integer size = 5;
        System.out.println(page);

        List<Map<String, Object>> mainTaskList = mainTaskService.getMainTaskListById(size, page, userDTO.getUserId());
        for (Map<String, Object> mainTask : mainTaskList) {
            Long mainTaskId = (Long) mainTask.get("mainTaskId");
            mainTask.put("likeCnt", likeService.getLikeCnt(mainTaskId));
        }
        System.out.println(mainTaskList);

        return mainTaskList;
    }







    @GetMapping("/profile-update")
    public String profileUpdate(HttpServletRequest request, Model model){
        UserDTO userDTO = userService.getUserFromSession(request);
        if (userDTO == null) {
            return "redirect:/auth/login";
        }
        model.addAttribute("userDTO", userDTO);
        return "/mypage/profile-update";
    }
    @PostMapping("/profile-update")
    @ResponseBody
    public String profileUpdate(@RequestParam("myProfile")MultipartFile myProfile,@ModelAttribute UserDTO userDTO) throws IOException {
        log.info("유저 정보 : " + userDTO);
        userService.updateUserResult(myProfile,userDTO);
        String jsScript = """
            <script>
               alert('수정 완료되었습니다.');
               location.href = '/user/profile';
            </script>""";
        return jsScript;
    }


    @GetMapping("/profile-remove")
    public String profileRemove(HttpServletRequest request, Model model){
        UserDTO userDTO = userService.getUserFromSession(request);
        if (userDTO == null) {
            return "/auth/login";
        }
        model.addAttribute("userDTO", userDTO);
        return "/mypage/profile-remove";
    }


    @PostMapping("/profile-remove")
    @ResponseBody
    public boolean profileRemove(@RequestBody UserDTO userDTO, HttpServletRequest request){
        log.info("email : "+ userDTO.getEmail());


        boolean isUserDel = userService.userRemoveResult(userDTO);
        if (isUserDel){
            HttpSession session= request.getSession();
            session.invalidate();
            return true;
        }else {
            return false;
        }
    }


    @GetMapping("/thumbnails")
    @ResponseBody
    public Resource thumbnails(@RequestParam("fileName") String fileName) throws MalformedURLException {
        return new UrlResource("file:" + fileRepo + fileName);
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/user/login";
    }

}



