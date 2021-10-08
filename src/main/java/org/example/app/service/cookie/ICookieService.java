package org.example.app.service.cookie;

import org.springframework.ui.Model;

import javax.servlet.http.HttpServletResponse;

public interface ICookieService {

    String[] getBooksSlugsFromCookie(String cookieContents);

    void addBookToCookie(CookieType cookieType, String content, String slugs, HttpServletResponse response, Model model);

    void removeBookFromCookie(CookieType cookieType, String content, String slug, HttpServletResponse response);

    boolean isCookieContentEmpty(String cookieContents);
}
