package org.example.app.service.cookie;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletResponse;

@Service
public interface ICookieService {

    String[] getBooksSlugsFromCookie(String cookieContents);

    void addCookie(CookieType cookieType, String content, String slug, HttpServletResponse response, Model model);

    boolean isCookieContentEmpty(String cookieContents);
}
