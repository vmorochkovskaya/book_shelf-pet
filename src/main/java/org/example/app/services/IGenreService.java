package org.example.app.services;

import org.example.web.dto.Genre;

import java.util.List;

public interface IGenreService {
    List<Genre> getAllGenres();
    boolean store(Genre genre);
}
