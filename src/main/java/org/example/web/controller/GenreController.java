package org.example.web.controller;

import org.apache.log4j.Logger;
import org.example.app.entity.genre.Genre;
import org.example.app.service.IGenreService;
import org.example.app.service.book.BookService;
import org.example.app.service.user.BookstoreUserRegister;
import org.example.web.dto.BooksPageDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class GenreController {

    private Logger logger = Logger.getLogger(GenreController.class);
    private final IGenreService genreService;
    private final BookService bookService;
    private final BookstoreUserRegister userRegister;

    public GenreController(IGenreService genreService, BookService bookService, BookstoreUserRegister userRegister) {
        this.genreService = genreService;
        this.bookService = bookService;
        this.userRegister = userRegister;
    }

    @GetMapping("/genres")
    public String genres() {
        logger.info("genres");
        return "genres/index";
    }

    @GetMapping("/genres/slug")
    public String getBooksByAuthor(@RequestParam("id") Integer id, Model model) {
        Genre genre = genreService.getGenreById(id);
        model.addAttribute("genreName", genre.getName());
        model.addAttribute("genreId", id);
        model.addAttribute("genreBooks", genre.getBooks().stream().skip(0).limit(20).collect(Collectors.toList()));
        return "genres/slug";
    }

    @GetMapping("/books/genre/{id}")
    @ResponseBody
    public BooksPageDto getNextBooksByAuthor(@RequestParam("offset") Integer offset,
                                             @RequestParam("limit") Integer limit, @PathVariable(value = "id") Integer id) {
        return new BooksPageDto(bookService.getBooksByGenreId(id, offset, limit).getContent());
    }

    @ModelAttribute("genreList")
    public List<Genre> authorList() {
        return genreService.getAllGenres();
    }

    @ModelAttribute("curUsr")
    public Object isAuthenticated(HttpServletRequest request) {
        return this.userRegister.getCurrentUser(request);
    }
}
