package org.example.app.services;

import org.example.app.entities.Author;

import java.util.List;

public interface IAuthorService {
    List<Author> getAllAuthors();
    void store(Author genre);
}

