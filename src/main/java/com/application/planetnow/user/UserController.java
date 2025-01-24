package com.application.planetnow.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    @GetMapping("/home")
    public String home(){
        return "home";
    }
    @GetMapping("/login")
    public String login(){
        return "/auth/login";
    }
    @PostMapping("/login")
    @ResponseBody
    public boolean login(@RequestBody UserDTO userDTO, HttpServletRequest request){
        log.info("email: "+userDTO.getEmail());
        log.info("password: "+userDTO.getPassword());
        boolean is = userService.loginResult(userDTO);
        if (is == true){
            HttpSession session = request.getSession();
            session.setAttribute("email",userDTO.getEmail());
            return true;
        }else {
            return false;
        }

    }
    @GetMapping("/sign-up")
    public String signUp( ){
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
}
