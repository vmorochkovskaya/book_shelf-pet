package org.example.app.services;

import org.example.app.reposotories.ProjectRepository;
import org.example.app.entities.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements IAuthorService {
    private final ProjectRepository<Author> authorRepo;

    @Autowired
    public AuthorServiceImpl(ProjectRepository<Author> authorRepo) {
        this.authorRepo = authorRepo;
    }


    @Override
    public List<Author> getAllAuthors() {
        return authorRepo.retrieveAll();
    }

    @Override
    public void store(Author author) {
        authorRepo.store(author);
    }
}
