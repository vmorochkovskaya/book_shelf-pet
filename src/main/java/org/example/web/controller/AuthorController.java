package org.example.web.controller;

import org.apache.log4j.Logger;
import org.example.app.service.IAuthorService;
import org.example.app.entity.author.Author;
import org.example.app.service.book.BookService;
import org.example.app.service.user.BookstoreUserRegister;
import org.example.web.dto.BooksPageDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class AuthorController {
    private Logger logger = Logger.getLogger(AuthorController.class);
    private final IAuthorService authorService;
    private final BookService bookService;
    private final BookstoreUserRegister userRegister;

    public AuthorController(IAuthorService authorService, BookService bookService, BookstoreUserRegister userRegister) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.userRegister = userRegister;
    }

    @GetMapping("/authors")
    public String authors() {
        logger.info("authors");
        return "authors/index";
    }

    @ModelAttribute("authorsMap")
    public Map<String, List<Author>> authorList() {
        return authorService.getAuthorsMap();
    }


    @PostMapping("/save")
    public String save(@Valid Author author, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("author", author);
            model.addAttribute("authorList", authorService.getAllAuthors());
            return "authors/index";
        }
        authorService.store(author);
        logger.info("current repository size: " + authorService.getAllAuthors().size());
        return "redirect:/authors";
    }

    @GetMapping("/authors/slug")
    public String getBooksByAuthor(@RequestParam("id") Integer id, Model model) {
        Author author = authorService.getAuthorById(id);
        model.addAttribute("authorName", author.getName());
        model.addAttribute("authorId", id);
        model.addAttribute("authorBooks", author.getBooks().stream().skip(0).limit(6).collect(Collectors.toList()));
        return "authors/slug";
    }

    @GetMapping("/books/author/{id}")
    @ResponseBody
    public BooksPageDto getNextBooksByAuthor(@RequestParam("offset") Integer offset,
                                             @RequestParam("limit") Integer limit, @PathVariable(value = "id") Integer id) {
        return new BooksPageDto(bookService.getBooksByAuthorId(id, offset, limit).getContent());
    }

    @ModelAttribute("curUsr")
    public Object isAuthenticated(HttpServletRequest request) {
        return this.userRegister.getCurrentUser(request);
    }
}
