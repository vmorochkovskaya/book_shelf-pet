package org.example.web.controller;

import org.apache.log4j.Logger;
import org.example.app.service.BookService;
import org.example.web.dto.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class BookShelfController {

    private Logger logger = Logger.getLogger(BookShelfController.class);
    private final BookService bookService;

    @Autowired
    public BookShelfController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/index.html")
    public String books(Model model) {
        logger.info("got book shelf");
        model.addAttribute("book", new BookDto());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "index";
    }

}
