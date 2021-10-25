package org.example.app.service.cookie;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;

@Service
public class CookieService implements ICookieService {
    private final Logger logger = Logger.getLogger(CookieService.class);
    private final String cookiePath = "/";

    @Override
    public String[] getBooksSlugsFromCookie(String cookieContents) {
        String parsedCookieContents = cookieContents.startsWith("/") ? cookieContents.substring(1) : cookieContents;
        parsedCookieContents = parsedCookieContents.endsWith("/") ? parsedCookieContents.substring(0, parsedCookieContents.length() - 1)
                : parsedCookieContents;
        return parsedCookieContents.split("/");
    }

    @Override
    public void addBookToCookie(CookieType cookieType, String content, String slugs, HttpServletResponse response, Model model) {
        String[] slugsValues = slugs.split(", ");
        if (isCookieContentEmpty(content)) {
            String valuesToAdd = String.join("/", slugsValues);
            logger.info(String.format("Set new cookie - name: %1$s, value: %2$s", cookieType.getValue(), valuesToAdd));
            Cookie cookieValue = new Cookie(cookieType.getValue(), valuesToAdd);
            cookieValue.setPath(cookiePath);
            response.addCookie(cookieValue);
            model.addAttribute("isCartEmpty", false);
        } else {
            StringJoiner stringJoiner = new StringJoiner("/");
            stringJoiner.add(content);
            for (String slugValue : slugsValues) {
                if (!content.contains(slugValue)) {
                    logger.info(String.format("Add cookie - name: %1$s, value: %2$s", cookieType.getValue(), slugValue));
                    stringJoiner.add(slugValue);
                    model.addAttribute("isCartEmpty", false);
                }
            }
            Cookie cookie = new Cookie(cookieType.getValue(), stringJoiner.toString());
            cookie.setPath(cookiePath);
            response.addCookie(cookie);
        }
    }

    @Override
    public void removeBookFromCookie(CookieType cookieType, String content, String slug, HttpServletResponse response) {
        if (!isCookieContentEmpty(content)) {
            logger.info(String.format("Remove book from cookie - name: %1$s, value: %2$s", cookieType.getValue(), slug));
            ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(content.split("/")));
            cookieBooks.remove(slug);
            Cookie cookie = new Cookie(cookieType.getValue(), String.join("/", cookieBooks));
            cookie.setPath(cookiePath);
            response.addCookie(cookie);
        }
    }

    @Override
    public boolean isCookieContentEmpty(String cookieContents) {
        return cookieContents == null || cookieContents.isEmpty();
    }
}
