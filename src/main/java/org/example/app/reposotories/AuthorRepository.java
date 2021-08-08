package org.example.app.reposotories;

import org.apache.log4j.Logger;
import org.example.app.entities.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AuthorRepository implements ProjectRepository<Author> {
    private static final String SELECT_ALL_AUTHORS_QUERY = "select * from authors";
    private final Logger logger = Logger.getLogger(AuthorRepository.class);
    private final List<Author> repo = new ArrayList<>();
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
        author.setId(String.valueOf(author.hashCode()));
        logger.info("store new author: " + author);
        repo.add(author);
    }
}