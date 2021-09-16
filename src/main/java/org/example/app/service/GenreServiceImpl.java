package org.example.app.service;

import org.example.app.repository.GenreRepository;
import org.example.app.entity.genre.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements IGenreService {
    private final GenreRepository genreRepo;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepo) {
        this.genreRepo = genreRepo;
    }


    @Override
    public List<Genre> getAllGenres() {
        return genreRepo.findAll();
    }

    @Override
    public void store(Genre genre) {
       genreRepo.save(genre);
    }

    @Override
    public Genre getGenreById(Integer id) {
        return genreRepo.findGenreById(id);
    }
}
