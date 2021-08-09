package org.example.app.service;

import org.example.app.repository.ProjectRepository;
import org.example.app.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements IGenreService {
    private final ProjectRepository<Genre> genreRepo;

    @Autowired
    public GenreServiceImpl(ProjectRepository<Genre> genreRepo) {
        this.genreRepo = genreRepo;
    }


    @Override
    public List<Genre> getAllGenres() {
        return genreRepo.retrieveAll();
    }

    @Override
    public boolean store(Genre genre) {
        genreRepo.store(genre);
        return true;
    }
}
