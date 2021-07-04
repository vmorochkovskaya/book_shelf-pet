package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "/books")
public class BookShelfController {

    private Logger logger = Logger.getLogger(BookShelfController.class);
    private BookService bookService;

    @Autowired
    public BookShelfController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/shelf")
    public String books(Model model) {
        logger.info("got book shelf");
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(Book book) {
        bookService.saveBook(book);
        logger.info("current repository size: " + bookService.getAllBooks().size());
        return "redirect:/books/shelf";
    }

    @PostMapping("/remove")
    public String removeBook(@RequestParam(value = "bookIdToRemove") Integer bookIdToRemove) {
        bookService.removeBookById(bookIdToRemove);
        return "redirect:/books/shelf";
    }

    @PostMapping("/removeByAuthor")
    public String removeBookByAuthor(@RequestParam(value = "authorToRemove") String authorToRemove) {
        bookService.removeBookByAuthor(authorToRemove);
        return "redirect:/books/shelf";
    }

    @PostMapping("/removeByTitle")
    public String removeBookByTitle(@RequestParam(value = "titleToRemove") String titleToRemove) {
        bookService.removeBookByTitle(titleToRemove);
        return "redirect:/books/shelf";
    }

    @PostMapping("/removeBySize")
    public String removeBookByAuthor(@RequestParam(value = "sizeToRemove") Integer sizeToRemove) {
        bookService.removeBookBySize(sizeToRemove);
        return "redirect:/books/shelf";
    }

    @PostMapping("/searchByAuthor")
    public String searchBookByAuthor(Model model, @RequestParam(value = "authorToSearch") String authorToSearch) {
        List<Book> searchBooks = bookService.searchBookByAuthor(authorToSearch);
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", searchBooks);
        return "book_shelf";       }

    @PostMapping("/searchByTitle")
    public String searchBookByTitle(Model model, @RequestParam(value = "titleToSearch") String titleToSearch) {
        List<Book> searchBooks = bookService.searchBookByTitle(titleToSearch);
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", searchBooks);
        return "book_shelf";
    }

    @PostMapping("/searchBySize")
    public String searchBookBySize(Model model, @RequestParam(value = "sizeToSearch") Integer sizeToSearch) {
        List<Book> searchBooks = bookService.searchBookBySize(sizeToSearch);
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", searchBooks);
        return "book_shelf";
    }


}
