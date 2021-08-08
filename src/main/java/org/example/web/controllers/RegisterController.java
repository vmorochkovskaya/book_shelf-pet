package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.LoginService;
import org.example.web.dto.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/registration")
public class RegisterController {
    private final Logger logger = Logger.getLogger(RegisterController.class);
    private LoginService loginService;

    @Autowired
    public RegisterController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping
    public String register(Model model){
        logger.info("GET /registration returns register_page.html");
        model.addAttribute("registrationForm", new RegisterForm());
        return "registration_page";
    }

    @PostMapping("/addUser")
    public String addUser(RegisterForm registerForm){
        loginService.addUser(registerForm);
        logger.info("POST /registration/addUser registers new user");
        return "redirect:/login";
    }

}
