package org.example.app.repository;

import org.example.app.entity.author.Author;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Author findAuthorsById(Integer id);

    List<Author> findAuthorsByBooksSlugIn(String[] slugs);
}