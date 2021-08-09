package org.example.app.service;

import org.example.app.entity.Genre;

import java.util.List;

public interface IGenreService {
    List<Genre> getAllGenres();
    boolean store(Genre genre);
}
