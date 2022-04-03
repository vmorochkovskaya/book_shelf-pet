package org.example.app.service.user;

import org.example.app.entity.user.UserEntity;
import org.example.app.repository.UserRepository;
import org.example.app.security.BookstoreUserDetails;
import org.example.app.security.ContactConfirmationPayload;
import org.example.app.security.ContactConfirmationResponse;
import org.example.app.security.jwt.JWTUtil;
import org.example.web.dto.RegisterFormDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;

@SpringBootTest
class BookstoreUserRegisterTest {

    private final BookstoreUserRegister userRegister;
    private final PasswordEncoder passwordEncoder;
    private RegisterFormDto registrationForm;


    @MockBean
    private UserRepository bookstoreUserRepositoryMock;
    @MockBean
    private BookstoreUserDetailsService bookstoreUserDetailsServiceMock;
    @MockBean
    private JWTUtil jwtUtilMock;
    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    public BookstoreUserRegisterTest(BookstoreUserRegister userRegister, PasswordEncoder passwordEncoder) {
        this.userRegister = userRegister;
        this.passwordEncoder = passwordEncoder;
    }

    @BeforeEach
    void setUp() {
        registrationForm = new RegisterFormDto();
        registrationForm.setEmail("test@mail.org");
        registrationForm.setName("Tester");
        registrationForm.setPass("iddqd");
        registrationForm.setPhone("9031232323");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void registerNewUserMethodsOrder() {
        InOrder inOrder = Mockito.inOrder(bookstoreUserRepositoryMock);
        userRegister.registerNewUser(registrationForm);

        inOrder.verify(bookstoreUserRepositoryMock).findUserByEmail(registrationForm.getEmail());
        inOrder.verify(bookstoreUserRepositoryMock).save(any());
    }

    @Test
    void registerNewUserMethodsReturnValue() {
        Mockito.when(bookstoreUserRepositoryMock.findUserByEmail(registrationForm.getEmail()))
                .thenReturn(new UserEntity());

        UserEntity user = userRegister.registerNewUser(registrationForm);
        assertNull(user);
    }

    @Test
    void registerNewUserStoredValues() {
        UserEntity user = userRegister.registerNewUser(registrationForm);
        assertNotNull(user);
        assertTrue(passwordEncoder.matches(registrationForm.getPass(), user.getPassword()));
        assertEquals(user.getPhone(), registrationForm.getPhone());
        assertEquals(user.getName(), registrationForm.getName());
        assertEquals(user.getEmail(), registrationForm.getEmail());
    }

    @Test
    void jwtLogin() {
        Mockito.when(bookstoreUserDetailsServiceMock.loadUserByUsername(any()))
                .thenReturn(new BookstoreUserDetails(new UserEntity()));
        Mockito.when(jwtUtilMock.generateToken(any()))
                .thenReturn("");
        ContactConfirmationResponse confirmationResponse = userRegister.jwtLogin(new ContactConfirmationPayload());
        assertNotNull(confirmationResponse.getResult());
    }

    @Test
    void jwtLoginGenerateToken() {
        Mockito.when(bookstoreUserDetailsServiceMock.loadUserByUsername(any()))
                .thenReturn(new BookstoreUserDetails(new UserEntity()));
        userRegister.jwtLogin(new ContactConfirmationPayload());
        Mockito.verify(jwtUtilMock, Mockito.times(1)).generateToken(any(BookstoreUserDetails.class));
    }

    @Test()
    void jwtLoginFail() {
        Mockito.when(bookstoreUserDetailsServiceMock.loadUserByUsername(any()))
                .thenThrow(UsernameNotFoundException.class);
        assertThrows(UsernameNotFoundException.class, () -> userRegister.jwtLogin(new ContactConfirmationPayload()));
        Mockito.verify(jwtUtilMock, never()).generateToken(any());
    }

}