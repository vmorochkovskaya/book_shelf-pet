package org.example.web.controller;

import org.apache.log4j.Logger;
import org.example.app.service.IAuthorService;
import org.example.app.entity.author.Author;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
public class AuthorController {
    private Logger logger = Logger.getLogger(AuthorController.class);
    private final IAuthorService authorService;

    public AuthorController(IAuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    public String authors() {
        logger.info("authors");
        return "authors/index";
    }

    @GetMapping("/authors/slug")
    public String authorsSlug() {
        logger.info("authors/slug");
        return "authors/slug";
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
}
