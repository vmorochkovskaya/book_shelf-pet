package org.example.web.controller.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.app.security.ContactConfirmationResponse;
import org.example.app.service.book.CartService;
import org.example.app.service.cookie.CookieType;
import org.example.app.service.cookie.ICookieService;
import org.example.app.service.user.BookstoreUserRegister;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookShelfCartControllerTest {

    private final MockMvc mockMvc;
    private final BookstoreUserRegister userRegister;

    @MockBean
    private CartService cartService;

    @MockBean
    private ICookieService cookieService;

    @Autowired
    public BookShelfCartControllerTest(MockMvc mockMvc, BookstoreUserRegister userRegister) {
        this.mockMvc = mockMvc;
        this.userRegister = userRegister;
    }

    @Test
    void addBookToCartTest() throws Exception {
        Map<String, String> bookMap = new HashMap<>();
        bookMap.put("booksIds", "1111");
        bookMap.put("status", "CART");
        ObjectMapper om = new ObjectMapper();

        ContactConfirmationResponse contactConfirmationResponse = new ContactConfirmationResponse();
        contactConfirmationResponse.setResult("true");

        this.mockMvc.perform(post("/books/changeBookStatus/{slug}", "1111")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(bookMap)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(om.writeValueAsString(contactConfirmationResponse))));

        verify(cartService, times(1)).addCartCookie(any(), any(String.class), any(HttpServletResponse.class), any(Model.class));
    }

    @Test
    void removeBookFromCartTest() throws Exception {
        Map<String, String> bookMap = new HashMap<>();
        bookMap.put("booksIds", "1111");
        bookMap.put("status", "CART");
        ObjectMapper om = new ObjectMapper();

        ContactConfirmationResponse contactConfirmationResponse = new ContactConfirmationResponse();
        contactConfirmationResponse.setResult("true");

        this.mockMvc.perform(post("/books/changeBookStatus/cart/remove/{slug}", "1111")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(bookMap)))
                .andExpect(status().isOk());
        verify(cookieService, times(1)).removeBookFromCookie(any(CookieType.class), any(), any(String.class), any(HttpServletResponse.class));
    }
}