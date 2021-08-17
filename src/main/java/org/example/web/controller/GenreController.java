package org.example.web.controller;

import org.apache.log4j.Logger;
import org.example.app.entity.Genre;
import org.example.app.service.IGenreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class GenreController {

    private Logger logger = Logger.getLogger(GenreController.class);
    private final IGenreService genreService;

    public GenreController(IGenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/genres")
    public String genres(Model model) {
        logger.info("genres");
//        model.addAttribute("genre", new Genre());
//        model.addAttribute("genreList", genreService.getAllGenres());
        return "genres/index";
    }
}
