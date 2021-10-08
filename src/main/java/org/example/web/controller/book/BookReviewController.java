package org.example.web.controller.book;

import org.example.app.entity.enums.Book2UserTypeEnum;
import org.example.app.service.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
public class BookReviewController {
    private final BookService bookService;

    @Autowired
    public BookReviewController(BookService bookService) {
        this.bookService = bookService;
    }


}
