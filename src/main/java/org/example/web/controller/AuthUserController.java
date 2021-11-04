package org.example.web.controller;

import org.example.app.entity.InvalidatedToken;
import org.example.app.security.ContactConfirmationPayload;
import org.example.app.security.ContactConfirmationResponse;
import org.example.app.service.token.IInvalidatedTokenService;
import org.example.app.service.user.BookstoreUserRegister;
import org.example.web.dto.RegisterFormDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@Controller
public class AuthUserController {

    private final BookstoreUserRegister userRegister;
    private final IInvalidatedTokenService invalidatedTokenService;

    @Autowired
    public AuthUserController(BookstoreUserRegister userRegister, IInvalidatedTokenService invalidatedTokenService) {
        this.userRegister = userRegister;
        this.invalidatedTokenService = invalidatedTokenService;
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
    public ContactConfirmationResponse handleLogin(@RequestBody ContactConfirmationPayload payload, HttpServletResponse httpServletResponse) {
        ContactConfirmationResponse loginResponse = userRegister.jwtLogin(payload);
        Cookie cookie = new Cookie("token", loginResponse.getResult());
        httpServletResponse.addCookie(cookie);
        return loginResponse;
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

    @GetMapping("/log-out")
    public String handleLogout(HttpServletRequest request) {
        for (Cookie cookie : request.getCookies()) {
            cookie.setMaxAge(0);
        }
        Optional<Cookie> token = Arrays.stream(request.getCookies()).filter((Cookie value) ->
                value.getName().equals("token")).findFirst();
        token.ifPresent(cookie -> invalidatedTokenService.addToBlackList(new InvalidatedToken(cookie.getValue())));
        return "signin";
    }
}
