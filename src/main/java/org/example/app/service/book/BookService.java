package org.example.app.service.book;

import org.example.app.repository.BookRepository;
import org.example.app.entity.book.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepo;

    @Autowired
    public BookService(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    public Page<Book> getPageOfRecommendedBooks(Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepo.findAll(nextPage);
    }

    public Page<Book> getPageOfRecentBooks(Integer offset, Integer limit) {
        return getPageOfRecommendedBooks(offset, limit);
    }

    public Page<Book> getPageOfRecentBooksSortedByPubBaseDesc(Integer offset, Integer limit, String from, String to) {
        Pageable nextPage = PageRequest.of(offset, limit, Sort.by("pubDate").descending());
        if(from != null && to != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate localDateFrom = LocalDate.parse(from, formatter);
            LocalDate localDateTo = LocalDate.parse(to, formatter);
            return bookRepo.findBooksByPubDateBetween(localDateFrom, localDateTo, nextPage);
        }
        return bookRepo.findAll(nextPage);
    }

    public Page<Book> getPageOfRecentBooksSortedByPubBaseDesc(Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit, Sort.by("pubDate").descending());
        return bookRepo.findAll(nextPage);
    }

    public Page<Book> getPageOfPopularBooks(Integer offset, Integer limit) {
        return getPageOfRecommendedBooks(offset, limit);
    }
}
