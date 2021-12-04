package org.example.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.app.security.ContactConfirmationPayload;
import org.example.app.security.ContactConfirmationResponse;
import org.example.app.service.user.BookstoreUserRegister;
import org.example.web.dto.RegisterFormDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthUserControllerTest {

    private final MockMvc mockMvc;
    private final BookstoreUserRegister userRegister;

    private RegisterFormDto registrationForm;

    @MockBean
    private BookstoreUserRegister userRegisterMock;

    @MockBean
    private ContactConfirmationResponse contactConfirmationResponseMock;

    @Autowired
    public AuthUserControllerTest(MockMvc mockMvc, BookstoreUserRegister userRegister) {
        this.mockMvc = mockMvc;
        this.userRegister = userRegister;
    }

    @Test
    void handleUserRegistrationTest() throws Exception {
        registrationForm = new RegisterFormDto();
        registrationForm.setEmail("test@mail.org");
        registrationForm.setName("Tester");
        registrationForm.setPass("iddqd");
        registrationForm.setPhone("9031232323");
        ObjectMapper om = new ObjectMapper();

        mockMvc.perform(post("/reg")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(registrationForm)))
                .andDo(print())
                .andExpect(status().isOk());
        ;
        verify(userRegisterMock, times(1)).registerNewUser(registrationForm);
    }

    @Test
    void loginByEmailTest() throws Exception {
        ContactConfirmationPayload payload = new ContactConfirmationPayload();
        payload.setContact("test@mail.org");
        payload.setCode("1234567");

        ContactConfirmationResponse confirmationResponse = new ContactConfirmationResponse();
        confirmationResponse.setResult("true");

        ObjectMapper om = new ObjectMapper();

        Mockito.when(userRegisterMock.jwtLogin(payload)).thenReturn(confirmationResponse);
        Mockito.when(contactConfirmationResponseMock.getResult()).thenReturn(confirmationResponse.getResult());

        this.mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(payload)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(om.writeValueAsString(confirmationResponse))));
    }

    @Test
    void logoutTest() throws Exception {
        this.mockMvc.perform(get("/log-out"))
                .andExpect(redirectedUrl("/signin"));
    }
}