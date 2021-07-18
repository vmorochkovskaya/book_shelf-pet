package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.app.services.IFileSystemStorageService;
import org.example.web.dto.Book;
import org.example.web.dto.BookIdToRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/books")
public class BookShelfController {

    private Logger logger = Logger.getLogger(BookShelfController.class);
    private BookService bookService;
    private IFileSystemStorageService fileSystemStorage;

    @Autowired
    public BookShelfController(BookService bookService, IFileSystemStorageService fileSystemStorage) {
        this.bookService = bookService;
        this.fileSystemStorage = fileSystemStorage;
    }

    @GetMapping("/shelf")
    public String books(Model model) {
        logger.info("got book shelf");
        model.addAttribute("book", new Book());
        model.addAttribute("bookIdToRemove", new BookIdToRemove());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(@Valid Book book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // Need to add the same book to attribute to get warning displayed
            model.addAttribute("book", book);
            model.addAttribute("bookList", bookService.getAllBooks());
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            return "book_shelf";
        }
        bookService.saveBook(book);
        logger.info("current repository size: " + bookService.getAllBooks().size());
        return "redirect:/books/shelf";
    }

    @PostMapping("/remove")
    public String removeBook(@Valid BookIdToRemove bookIdToRemove, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", new Book());
            model.addAttribute("bookIdToRemove", bookIdToRemove);
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        } else if (!bookService.removeBookById(bookIdToRemove.getId())) {
            bindingResult.addError(new ObjectError("globalError", "Id not found"));
            model.addAttribute("book", new Book());
            model.addAttribute("bookIdToRemove", bookIdToRemove);
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        }
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
        return "book_shelf";
    }

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

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        fileSystemStorage.saveFile(file);
        return "redirect:/books/shelf";
    }

    @GetMapping("/downloadFile")
    public String uploadFile(HttpServletRequest request,
                             HttpServletResponse response) {
        fileSystemStorage.loadFile("cool.jpg", response);
        return "redirect:/books/shelf";
    }


}
