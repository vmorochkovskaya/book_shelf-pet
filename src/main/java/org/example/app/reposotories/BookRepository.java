package org.example.app.reposotories;

import org.apache.log4j.Logger;
import org.example.app.entities.Author;
import org.example.app.entities.Book;
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
    private final Logger logger = Logger.getLogger(BookRepository.class);
    private final List<Book> repo = new ArrayList<>();
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
        book.setId(book.hashCode());
        logger.info("store new book: " + book);
        repo.add(book);
    }
}
