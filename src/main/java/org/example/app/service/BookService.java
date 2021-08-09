package org.example.app.service;

import org.example.app.repository.ProjectRepository;
import org.example.app.entity.Book;
import org.example.web.dto.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final ProjectRepository<Book> bookRepo;

    @Autowired
    public BookService(ProjectRepository<Book> bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<BookDto> getAllBooks() {
        return bookRepo.retrieveAll().stream().map((Book book) -> new BookDto(book.getAuthor().getName(), book.getTitle(), book.getPriceOld(), book.getPrice())).
                collect(Collectors.toList());
    }
}
