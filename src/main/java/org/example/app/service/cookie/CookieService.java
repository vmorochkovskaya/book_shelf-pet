package org.example.app.service.cookie;

import org.springframework.ui.Model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.StringJoiner;

public class CookieService implements ICookieService {

    @Override
    public String[] getBooksSlugsFromCookie(String cookieContents) {
        String parsedCookieContents = cookieContents.startsWith("/") ? cookieContents.substring(1) : cookieContents;
        parsedCookieContents = parsedCookieContents.endsWith("/") ? parsedCookieContents.substring(0, parsedCookieContents.length() - 1)
                : parsedCookieContents;
        return parsedCookieContents.split("/");
    }

    @Override
    public void addCookie(CookieType cookieType, String content, String slug, HttpServletResponse response, Model model) {
        if (isCookieContentEmpty(content)) {
            Cookie cookieValue = new Cookie(cookieType.getValue(), slug);
            cookieValue.setPath("/books");
            response.addCookie(cookieValue);
            model.addAttribute("isCartEmpty", false);
        } else if (!content.contains(slug)) {
            StringJoiner stringJoiner = new StringJoiner("/");
            stringJoiner.add(content).add(slug);
            Cookie cookie = new Cookie(cookieType.getValue(), stringJoiner.toString());
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        }
    }

    @Override
    public boolean isCookieContentEmpty(String cookieContents) {
        return cookieContents == null || cookieContents.isEmpty();
    }
}
