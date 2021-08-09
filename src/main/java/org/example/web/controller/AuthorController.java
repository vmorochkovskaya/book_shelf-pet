package org.example.web.controller;

import org.apache.log4j.Logger;
import org.example.app.service.IAuthorService;
import org.example.app.entity.Author;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/authors")
public class AuthorController {
    private Logger logger = Logger.getLogger(AuthorController.class);
    private final IAuthorService authorService;

    public AuthorController(IAuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/index.html")
    public String genres(Model model) {
        logger.info("authors");
        model.addAttribute("author", new Author());
        model.addAttribute("authorList", authorService.getAllAuthors());
        return "authors/index";
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
        return "redirect:/authors/index.html";
    }
}
