package org.example.app.service;

import org.example.app.entity.genre.Genre;

import java.util.List;

public interface IGenreService {
    List<Genre> getAllGenres();
    void store(Genre genre);
}
