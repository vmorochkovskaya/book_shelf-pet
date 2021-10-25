package org.example.web.controller;

import org.example.app.entity.author.Author;
import org.example.app.security.ContactConfirmationPayload;
import org.example.app.security.ContactConfirmationResponse;
import org.example.app.service.user.BookstoreUserRegister;
import org.example.web.dto.RegisterFormDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class AuthUserController {

    private final BookstoreUserRegister userRegister;

    @Autowired
    public AuthUserController(BookstoreUserRegister userRegister) {
        this.userRegister = userRegister;
    }

    @GetMapping("/signin")
    public String handleSignIn() {
        return "signin";
    }

    @GetMapping("/signup")
    public String handleSignUp(Model model) {
        model.addAttribute("regForm", new RegisterFormDto());
        return "signup";
    }

    @PostMapping("/requestContactConfirmation")
    @ResponseBody
    public ContactConfirmationResponse handleRequestContactConfirmation() {
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        return response;
    }

    @PostMapping("/approveContact")
    @ResponseBody
    public ContactConfirmationResponse handleApproveContact() {
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        return response;
    }

    @PostMapping("/reg")
    public String handleUserRegistration(RegisterFormDto registrationForm, Model model) {
        userRegister.registerNewUser(registrationForm);
        model.addAttribute("regOk", true);
        return "signin";
    }

    @PostMapping(value = "/login")
    @ResponseBody
    public ContactConfirmationResponse handleLogin(@RequestBody ContactConfirmationPayload payload) {
        return userRegister.login(payload);
    }

    @GetMapping("/my")
    public String handleMy(Model model) {
        model.addAttribute("curUsr", userRegister.getCurrentUser());
        return "my";
    }

    @GetMapping("/profile")
    public String handleProfile(Model model) {
        model.addAttribute("curUsr", userRegister.getCurrentUser());
        return "profile";
    }

    @GetMapping("/logout")
    public String handleLogout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        SecurityContextHolder.clearContext();
        if (session != null) {
            session.invalidate();
        }
        for (Cookie cookie : request.getCookies()) {
            cookie.setMaxAge(0);
        }
        return "redirect:/";
    }
}
