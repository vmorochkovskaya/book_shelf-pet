package org.example.app.repository;

import org.example.app.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AuthorRepository implements ProjectRepository<Author> {
    private static final String SELECT_ALL_AUTHORS_QUERY = "select * from authors";
    private static final String INSERT_AUTHOR_QUERY = "insert into authors (name) values ('%1$s')";
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Author> retrieveAll() {
        List<Author> authorList = jdbcTemplate.query(SELECT_ALL_AUTHORS_QUERY, (ResultSet rs, int rowNum) -> {
            Author author = new Author();
            author.setName(rs.getString("name"));
            return author;
        });
        return new ArrayList(authorList);
    }

    @Override
    public void store(Author author) {
        jdbcTemplate.execute(String.format(INSERT_AUTHOR_QUERY, author.getName()));
    }
}