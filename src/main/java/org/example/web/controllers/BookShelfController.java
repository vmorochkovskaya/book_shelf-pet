package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.app.services.IFileSystemStorageService;
import org.example.web.dto.*;
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
        model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
        model.addAttribute("bookAuthorToSearch", new BookAuthorToSearch());
        model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
        model.addAttribute("bookTitleToSearch", new BookTitleToSearch());
        model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
        model.addAttribute("bookSizeToSearch", new BookSizeToSearch());
        model.addAttribute("bookList", bookService.getAllBooks());
        model.addAttribute("fileList", fileSystemStorage.getAllFiles());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(@Valid Book book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // Need to add the same book to attribute to get warning displayed
            model.addAttribute("book", book);
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
            model.addAttribute("bookAuthorToSearch", new BookAuthorToSearch());
            model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
            model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
            model.addAttribute("bookList", bookService.getAllBooks());
            model.addAttribute("bookTitleToSearch", new BookTitleToSearch());
            model.addAttribute("bookSizeToSearch", new BookSizeToSearch());
            model.addAttribute("fileList", fileSystemStorage.getAllFiles());
            return "book_shelf";
        }
        bookService.saveBook(book);
        logger.info("current repository size: " + bookService.getAllBooks().size());
        return "redirect:/books/shelf";
    }

    @PostMapping("/remove")
    public String removeBook(@Valid BookIdToRemove bookIdToRemove, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return getString(bookIdToRemove, model);
        } else if (!bookService.removeBookById(bookIdToRemove.getId())) {
            bindingResult.addError(new ObjectError("globalError", "Id not found"));
            return getString(bookIdToRemove, model);
        }
        return "redirect:/books/shelf";
    }

    private String getString(@Valid BookIdToRemove bookIdToRemove, Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("bookIdToRemove", bookIdToRemove);
        model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
        model.addAttribute("bookAuthorToSearch", new BookAuthorToSearch());
        model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
        model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
        model.addAttribute("bookTitleToSearch", new BookTitleToSearch());
        model.addAttribute("bookSizeToSearch", new BookSizeToSearch());
        model.addAttribute("bookList", bookService.getAllBooks());
        model.addAttribute("fileList", fileSystemStorage.getAllFiles());
        return "book_shelf";
    }

    private String setSearchModel(Model model, List searchResults) {
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", searchResults);
        model.addAttribute("bookIdToRemove", new BookIdToRemove());
        model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
        model.addAttribute("bookAuthorToSearch", new BookAuthorToSearch());
        model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
        model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
        model.addAttribute("bookTitleToSearch", new BookTitleToSearch());
        model.addAttribute("bookSizeToSearch", new BookSizeToSearch());
        model.addAttribute("fileList", fileSystemStorage.getAllFiles());
        return "book_shelf";
    }

    @PostMapping("/removeByAuthor")
    public String removeBookByAuthor(@Valid BookAuthorToRemove bookAuthorToRemove, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            bookService.removeBookByAuthor(bookAuthorToRemove.getAuthor());
            return "redirect:/books/shelf";
        } else {
            model.addAttribute("book", new Book());
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookAuthorToRemove", bookAuthorToRemove);
            model.addAttribute("bookAuthorToSearch", new BookAuthorToSearch());
            model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
            model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
            model.addAttribute("bookTitleToSearch", new BookTitleToSearch());
            model.addAttribute("bookSizeToSearch", new BookSizeToSearch());
            model.addAttribute("bookList", bookService.getAllBooks());
            model.addAttribute("fileList", fileSystemStorage.getAllFiles());
            return "book_shelf";
        }
    }

    @PostMapping("/removeByTitle")
    public String removeBookByTitle(@Valid BookTitleToRemove bookTitleToRemove, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            bookService.removeBookByTitle(bookTitleToRemove.getTitle());
            return "redirect:/books/shelf";
        } else {
            model.addAttribute("book", new Book());
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
            model.addAttribute("bookAuthorToSearch", new BookAuthorToSearch());
            model.addAttribute("bookTitleToRemove", bookTitleToRemove);
            model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
            model.addAttribute("bookTitleToSearch", new BookTitleToSearch());
            model.addAttribute("bookSizeToSearch", new BookSizeToSearch());
            model.addAttribute("bookList", bookService.getAllBooks());
            model.addAttribute("fileList", fileSystemStorage.getAllFiles());
            return "book_shelf";
        }
    }

    @PostMapping("/removeBySize")
    public String removeBookByAuthor(@Valid BookSizeToRemove bookSizeToRemove, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            bookService.removeBookBySize(bookSizeToRemove.getSize());
            return "redirect:/books/shelf";
        } else {
            model.addAttribute("book", new Book());
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
            model.addAttribute("bookAuthorToSearch", new BookAuthorToSearch());
            model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
            model.addAttribute("bookSizeToRemove", bookSizeToRemove);
            model.addAttribute("bookTitleToSearch", new BookTitleToSearch());
            model.addAttribute("bookSizeToSearch", new BookSizeToSearch());
            model.addAttribute("bookList", bookService.getAllBooks());
            model.addAttribute("fileList", fileSystemStorage.getAllFiles());
            return "book_shelf";
        }
    }

    @PostMapping("/searchByAuthor")
    public String searchBookByAuthor(Model model, @Valid BookAuthorToSearch bookAuthorToSearch, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            List<Book> searchBooks = bookService.searchBookByAuthor(bookAuthorToSearch.getAuthor());
            return setSearchModel(model, searchBooks);
        } else {
            model.addAttribute("book", new Book());
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
            model.addAttribute("bookAuthorToSearch", bookAuthorToSearch);
            model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
            model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
            model.addAttribute("bookTitleToSearch", new BookTitleToSearch());
            model.addAttribute("bookSizeToSearch", new BookSizeToSearch());
            model.addAttribute("bookList", bookService.getAllBooks());
            model.addAttribute("fileList", fileSystemStorage.getAllFiles());
            return "book_shelf";
        }
    }

    @PostMapping("/searchByTitle")
    public String searchBookByTitle(Model model, @Valid BookTitleToSearch bookTitleToSearch, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            List<Book> searchBooks = bookService.searchBookByTitle(bookTitleToSearch.getTitle());
            return setSearchModel(model, searchBooks);
        } else {
            model.addAttribute("book", new Book());
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
            model.addAttribute("bookAuthorToSearch", new BookAuthorToSearch());
            model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
            model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
            model.addAttribute("bookTitleToSearch", bookTitleToSearch);
            model.addAttribute("bookSizeToSearch", new BookSizeToSearch());
            model.addAttribute("bookList", bookService.getAllBooks());
            model.addAttribute("fileList", fileSystemStorage.getAllFiles());
            return "book_shelf";
        }
    }

    @PostMapping("/searchBySize")
    public String searchBookBySize(Model model, @Valid BookSizeToSearch bookSizeToSearch, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            List<Book> searchBooks = bookService.searchBookBySize(bookSizeToSearch.getSize());
            return setSearchModel(model, searchBooks);
        } else {
            model.addAttribute("book", new Book());
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
            model.addAttribute("bookAuthorToSearch", new BookAuthorToSearch());
            model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
            model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
            model.addAttribute("bookTitleToSearch", new BookTitleToSearch());
            model.addAttribute("bookSizeToSearch", bookSizeToSearch);
            model.addAttribute("bookList", bookService.getAllBooks());
            model.addAttribute("fileList", fileSystemStorage.getAllFiles());
            return "book_shelf";
        }
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        fileSystemStorage.saveFile(file);
        return "redirect:/books/shelf";
    }

    @GetMapping("/downloadFile")
    public String uploadFile(@RequestParam(value = "fileName") String fileName, HttpServletRequest request,
                             HttpServletResponse response) {
        fileSystemStorage.loadFile(fileName, response);
        return "redirect:/books/shelf";
    }


}
