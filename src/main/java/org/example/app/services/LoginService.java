package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.example.web.dto.LoginForm;
import org.example.web.dto.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private Logger logger = Logger.getLogger(LoginService.class);
    private final ProjectRepository<RegisterForm> userRepo;

    @Autowired
    public LoginService(ProjectRepository<RegisterForm> bookRepo) {
        this.userRepo = bookRepo;
    }

    public boolean authenticate(LoginForm loginFrom) {
        logger.info("try auth with user-form: " + loginFrom);
        for(RegisterForm user : userRepo.retreiveAll()){
            if(loginFrom.getUsername().equals(user.getUsername()) && loginFrom.getPassword().equals(user.getPassword())){
                return true;
            }
        }
        return false;
    }

    public void addUser(RegisterForm registerForm) {
        userRepo.store(registerForm);
    }
}
