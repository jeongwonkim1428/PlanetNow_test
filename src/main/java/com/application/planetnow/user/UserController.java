package com.application.planetnow.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/login")
    public String home(){
        return "/auth/login";
    }
    @GetMapping("/sign-up")
    public String signUp( ){
        return "/auth/sigin-up2";
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
