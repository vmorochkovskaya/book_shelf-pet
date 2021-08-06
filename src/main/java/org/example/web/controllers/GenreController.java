package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.IGenreService;
import org.example.web.dto.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/genres")
public class GenreController {

    private Logger logger = Logger.getLogger(GenreController.class);
    private IGenreService genreService;

    public GenreController(IGenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/index.html")
    public String genres(Model model) {
        logger.info("genres");
        model.addAttribute("genre", new Genre());
        model.addAttribute("genreList", genreService.getAllGenres());
        return "genres/index";
    }
}
