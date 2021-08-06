package org.example.app.services;

import org.example.web.dto.Author;

import java.util.List;

public interface IAuthorService {
    List<Author> getAllAuthors();
    boolean store(Author genre);
}

