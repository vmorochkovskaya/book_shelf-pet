package org.example.web.controller;

import org.apache.log4j.Logger;
import org.example.app.service.LoginService;
import org.example.web.dto.LoginFormDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private final Logger logger = Logger.getLogger(LoginController.class);
    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/signin")
    public String login(Model model) {
        logger.info("GET /login returns signin.html");
        return "signin";
    }

    @PostMapping("/auth")
    public String authenticate(LoginFormDto loginFrom) {
        if (loginService.authenticate(loginFrom)) {
            logger.info("login OK redirect to book shelf");
            return "redirect:/index.html";
        } else {
            logger.info("login FAIL redirect back to login");
            return "redirect:/login";
        }
    }
}
