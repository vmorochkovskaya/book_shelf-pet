package org.example.app.service.book;


import org.example.app.service.cookie.CookieType;
import org.example.app.service.cookie.ICookieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletResponse;


@Service
public class CartService {
 private final ICookieService cookieService;

    @Autowired
    public CartService(ICookieService cookieService) {
        this.cookieService = cookieService;
    }

    public void addCartCookie(String content, String slug, HttpServletResponse response, Model model) {
        cookieService.addCookie(CookieType.CART, content, slug, response, model);
    }
}
