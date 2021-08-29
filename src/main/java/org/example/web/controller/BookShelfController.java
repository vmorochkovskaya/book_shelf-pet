package org.example.web.controller;

import org.apache.log4j.Logger;
import org.example.app.entity.book.Book;
import org.example.app.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class BookShelfController {

    private Logger logger = Logger.getLogger(BookShelfController.class);
    private final BookService bookService;

    @Autowired
    public BookShelfController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String books() {
        logger.info("got book shelf");
        return "index";
    }

    @GetMapping("/books/recent")
    public String booksRecent() {
        logger.info("got book shelf");
        return "books/recent";
    }

    @GetMapping("/books/author")
    public String booksOfAuthor() {
        return "books/author";
    }

    @GetMapping("/books/popular")
    public String booksPopular(){
        return "books/popular";
    }

    @GetMapping("/postponed")
    public String postponed(){
        return "postponed";
    }

    @GetMapping("/search")
    public String search(){
        return "search/index";
    }

    @ModelAttribute("bookList")
    public List<Book> bookList(){
        return bookService.getAllBooks();
    }
}
