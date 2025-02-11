package com.application.planetnow;

import com.application.planetnow.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    UserService userService;

    @GetMapping("/home")
    public String main(Model model) {

        model.addAttribute("userDTO", userService.getUserDetail("qwer1234@gmail.com"));

        return "/home";
    }

    @GetMapping("/layout")
    public String layout() {
        return "/layout";
    }
}
