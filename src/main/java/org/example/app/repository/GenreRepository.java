package org.example.app.repository;

import org.example.app.entity.genre.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
    Genre findGenreById(Integer id);
}
