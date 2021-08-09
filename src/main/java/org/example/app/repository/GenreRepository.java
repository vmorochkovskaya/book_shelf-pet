package org.example.app.repository;

import org.example.app.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GenreRepository implements ProjectRepository<Genre> {
    private static final String SELECT_ALL_GENRES_QUERY = "select * from genres";
    private static final String INSERT_GENRE_QUERY = "insert into genres (name) values ('%1$s')";
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> retrieveAll() {
        List<Genre> genreList = jdbcTemplate.query(SELECT_ALL_GENRES_QUERY, (ResultSet rs, int rowNum) -> {
            Genre genre = new Genre();
            genre.setName(rs.getString("name"));
            return genre;
        });
        return new ArrayList(genreList);
    }

    @Override
    public void store(Genre genre) {
        jdbcTemplate.execute(String.format(INSERT_GENRE_QUERY, genre.getName()));
    }
}
