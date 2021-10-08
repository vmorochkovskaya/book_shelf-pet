package org.example.app.service.book;

import org.example.app.repository.BookRepository;
import org.example.app.service.cookie.CookieType;
import org.example.app.service.cookie.ICookieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletResponse;

@Service
public class PostponedService {
    private final ICookieService cookieService;
    private final BookRepository bookRepository;

    @Autowired
    public PostponedService(ICookieService cookieService, BookRepository bookRepository) {
        this.cookieService = cookieService;
        this.bookRepository = bookRepository;
    }

    public void addCartCookie(String content, String slug, HttpServletResponse response, Model model) {
        cookieService.addBookToCookie(CookieType.POSTPONED, content, slug, response, model);
    }

    public void incrementNumberUsersBookPostponed(String cookieContent, String slug) {
        if (cookieService.isCookieContentEmpty(cookieContent) || !cookieContent.contains(slug)) {
            bookRepository.incrementNumberUsersBookPostponed(slug);
        }
    }
}
