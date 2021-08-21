package org.example.web.controller;

import org.apache.log4j.Logger;
import org.example.app.entity.Genre;
import org.example.app.service.IGenreService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;


@Controller
public class GenreController {

    private Logger logger = Logger.getLogger(GenreController.class);
    private final IGenreService genreService;

    public GenreController(IGenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/genres")
    public String genres() {
        logger.info("genres");
        return "genres/index";
    }

    @GetMapping("/genres/slug")
    public String genresSlug() {
        logger.info("genres/slug");
        return "genres/slug";
    }

    @ModelAttribute("genreList")
    public List<Genre> authorList() {
        return genreService.getAllGenres();
    }
}
