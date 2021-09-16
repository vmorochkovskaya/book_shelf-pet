package org.example.app.service;

import org.example.app.repository.AuthorRepository;
import org.example.app.entity.author.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements IAuthorService {
    private final AuthorRepository authorRepo;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepo) {
        this.authorRepo = authorRepo;
    }


    @Override
    public List<Author> getAllAuthors() {
        return authorRepo.findAll();
    }

    public Map<String, List<Author>> getAuthorsMap() {
        return getAllAuthors().stream().collect(Collectors.groupingBy((Author a) -> a.getName().substring(0, 1)
        ));
    }

    @Override
    public Author getAuthorById(Integer id) {
        return authorRepo.findAuthorsById(id);
    }

    @Override
    public void store(Author author) {
        authorRepo.save(author);
    }
}
