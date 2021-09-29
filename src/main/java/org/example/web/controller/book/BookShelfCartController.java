package org.example.web.controller.book;

import org.example.app.entity.book.Book;
import org.example.app.entity.enums.Book2UserTypeEnum;
import org.example.app.service.book.BookService;
import org.example.app.service.book.CartService;
import org.example.app.service.book.PostponedService;
import org.example.app.service.cookie.ICookieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller
@RequestMapping("/books")
public class BookShelfCartController {

    private final BookService bookService;
    private final ICookieService cookieService;
    private final CartService cartService;
    private final PostponedService postponedService;

    @Autowired
    public BookShelfCartController(BookService bookService, ICookieService cookieService, CartService cartService,
                                   PostponedService postponedService) {
        this.bookService = bookService;
        this.cookieService = cookieService;
        this.cartService = cartService;
        this.postponedService = postponedService;
    }

    @ModelAttribute(name = "bookCart")
    public List<Book> bookCart() {
        return new ArrayList<>();
    }

    @ModelAttribute(name = "booksPostponed")
    public List<Book> booksPostponed() {
        return new ArrayList<>();
    }

    @PostMapping("/changeBookStatus/{slug}")
    public String saveNewBookImage(@PathVariable("slug") String slug, @RequestParam("status") Book2UserTypeEnum status,
            @CookieValue(name = "cartContents", required = false) String cartContents,
                                   @CookieValue(name = "postponedContents", required = false) String postponedContents,
                                   HttpServletResponse response, Model model) {
        switch(status){
            case CART:
                cartService.addCartCookie(cartContents, slug, response, model);
                return "redirect:/books/" + slug;
            case KEPT:
                postponedService.addCartCookie(postponedContents, slug, response, model);
                return "redirect:/books/" + slug;
        }

        return "redirect:/books/" + slug;
    }

    @GetMapping("/cart")
    public String handleCartRequest(@CookieValue(value = "cartContents", required = false) String cartContents,
                                    Model model) {
        boolean isCookieContentEmpty = cookieService.isCookieContentEmpty(cartContents);
        model.addAttribute("isCartEmpty", isCookieContentEmpty);
        if (!isCookieContentEmpty) {
            String[] cookieSlugs = cookieService.getBooksSlugsFromCookie(cartContents);
            List<Book> booksFromCookieSlugs = bookService.getBooksBySlugIn(cookieSlugs);
            model.addAttribute("bookCart", booksFromCookieSlugs);
        }
        return "cart";
    }

    @PostMapping("/changeBookStatus/cart/remove/{slug}")
    public String handleRemoveBookFromCartRequest(@PathVariable("slug") String slug, @CookieValue(name =
            "cartContents", required = false) String cartContents, HttpServletResponse response, Model model) {

        if (cartContents != null && !cartContents.equals("")) {
            ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(cartContents.split("/")));
            cookieBooks.remove(slug);
            Cookie cookie = new Cookie("cartContents", String.join("/", cookieBooks));
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        } else {
            model.addAttribute("isCartEmpty", true);
        }

        return "redirect:/books/cart";
    }

    @GetMapping("/postponed")
    public String handlePostponedRequest(@CookieValue(value = "postponedContents", required = false) String postponedContents,
                                         Model model) {
        boolean isCookieContentEmpty = cookieService.isCookieContentEmpty(postponedContents);
        model.addAttribute("isPostponedEmpty", isCookieContentEmpty);
        if (!isCookieContentEmpty) {
            String[] cookieSlugs = cookieService.getBooksSlugsFromCookie(postponedContents);
            List<Book> booksFromCookieSlugs = bookService.getBooksBySlugIn(cookieSlugs);
            model.addAttribute("booksPostponed", booksFromCookieSlugs);
        }
        return "postponed";
    }
}
