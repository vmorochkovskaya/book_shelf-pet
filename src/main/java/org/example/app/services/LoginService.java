package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.app.reposotories.BookRepository;
import org.example.app.reposotories.ProjectRepository;
import org.example.web.dto.LoginForm;
import org.example.web.dto.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Repository
    public static class UserRepository implements ProjectRepository<RegisterForm>{
        private final Logger logger = Logger.getLogger(BookRepository.class);
        private final List<RegisterForm> repo = new ArrayList<>();

        @Override
        public List<RegisterForm> retreiveAll() {
           return new ArrayList<>(repo);
        }

        @Override
        public void store(RegisterForm user) {
            logger.info("store new user: " + user);
            repo.add(user);
        }

//        @Override
//        public boolean removeItemById(String userIdToRemove) {
//            for (RegisterForm user : retreiveAll()) {
//                if (user.getId().equals(userIdToRemove)) {
//                    logger.info("remove user completed: " + user);
//                    return repo.remove(user);
//                }
//            }
//            return false;
//        }
    }
}
