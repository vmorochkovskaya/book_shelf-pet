package org.example.app.service.book;

import org.example.app.entity.book.Book;
import org.example.app.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksRatingAndPopularityService {

    private final BookRepository bookRepo;

    @Autowired
    public BooksRatingAndPopularityService(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

}
