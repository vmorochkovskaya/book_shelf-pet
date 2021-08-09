package org.example.app.repository;

import org.example.app.entity.Author;
import org.example.app.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository implements ProjectRepository<Book> {
    private static final String SELECT_BOOK_DATA_QUERY = "select title, priceOld, price, authors.name as author from books\n" +
            "inner join authors on books.idAuthor = authors.id";
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> retrieveAll() {
        List<Book> bookList = jdbcTemplate.query(SELECT_BOOK_DATA_QUERY, (ResultSet rs, int rowNum) -> {
            Book book = new Book();
            book.setTitle(rs.getString("title"));
            book.setPriceOld(rs.getString("priceOld"));
            book.setPrice(rs.getString("price"));
            book.setAuthor(new Author(rs.getString("author")));
            return book;
        });
        return new ArrayList(bookList);
    }

    @Override
    public void store(Book book) {
    // TODO implement
    }
}
