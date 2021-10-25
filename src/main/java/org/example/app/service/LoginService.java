package org.example.app.service;

import org.apache.log4j.Logger;
import org.example.app.repository.BookRepository;
import org.example.app.repository.ProjectRepository;
import org.example.web.dto.LoginFormDto;
import org.example.web.dto.RegisterFormDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginService {

    private final Logger logger = Logger.getLogger(LoginService.class);
    private final ProjectRepository<RegisterFormDto> userRepo;

    @Autowired
    public LoginService(ProjectRepository<RegisterFormDto> bookRepo) {
        this.userRepo = bookRepo;
    }

    public boolean authenticate(LoginFormDto loginFrom) {
//        logger.info("try auth with user-form: " + loginFrom);
//        for(RegisterFormDto user : userRepo.retrieveAll()){
//            if(loginFrom.getUsername().equals(user.getUsername()) && loginFrom.getPassword().equals(user.getPassword())){
//                return true;
//            }
//        }
        return false;
    }

    public void addUser(RegisterFormDto registerForm) {
        userRepo.store(registerForm);
    }

    @Repository
    public static class UserRepository implements ProjectRepository<RegisterFormDto>{
        private final Logger logger = Logger.getLogger(BookRepository.class);
        private final List<RegisterFormDto> repo = new ArrayList<>();

        @Override
        public List<RegisterFormDto> retrieveAll() {
           return new ArrayList<>(repo);
        }

        @Override
        public void store(RegisterFormDto user) {
            logger.info("store new user: " + user);
            repo.add(user);
        }
    }
}
