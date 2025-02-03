package com.application.planetnow;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/home")
    public String main() {
        return "/home";
    }

}
