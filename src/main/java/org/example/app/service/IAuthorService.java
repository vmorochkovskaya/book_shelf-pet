package org.example.app.service;

import org.example.app.entity.Author;

import java.util.List;

public interface IAuthorService {
    List<Author> getAllAuthors();
    void store(Author genre);
}

