package org.example.web.dto;

import lombok.Data;
import org.example.app.entity.book.Book;

import java.util.List;

@Data
public class BooksPageDto {

    private Integer count;
    private List<Book> books;

    public BooksPageDto(List<Book> books) {
        this.books = books;
        this.count = books.size();
    }
}
