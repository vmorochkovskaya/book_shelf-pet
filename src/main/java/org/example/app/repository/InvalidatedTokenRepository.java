package org.example.app.repository;

import org.example.app.entity.InvalidatedToken;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InvalidatedTokenRepository extends CrudRepository<InvalidatedToken, String> {
    List<InvalidatedToken> findAll();
}