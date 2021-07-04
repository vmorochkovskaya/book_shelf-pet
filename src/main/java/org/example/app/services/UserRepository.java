package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.RegisterForm;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository implements ProjectRepository<RegisterForm>{
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

    @Override
    public boolean removeItemById(Integer userIdToRemove) {
        for (RegisterForm user : retreiveAll()) {
            if (user.getId().equals(userIdToRemove)) {
                logger.info("remove user completed: " + user);
                return repo.remove(user);
            }
        }
        return false;
    }
}
