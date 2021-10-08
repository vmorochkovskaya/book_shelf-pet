package org.example.app.repository;

import org.example.app.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findUserById(Integer id);

}