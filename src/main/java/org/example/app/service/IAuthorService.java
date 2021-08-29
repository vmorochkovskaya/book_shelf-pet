package org.example.app.service;

import org.example.app.entity.author.Author;

import java.util.List;
import java.util.Map;

public interface IAuthorService {
    List<Author> getAllAuthors();
    void store(Author genre);
    Map<String,List<Author>> getAuthorsMap();
}

