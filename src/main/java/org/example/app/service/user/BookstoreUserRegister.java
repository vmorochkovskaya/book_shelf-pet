package org.example.app.service.user;

import org.example.app.entity.user.UserEntity;
import org.example.app.repository.UserRepository;
import org.example.app.security.BookstoreUserDetails;
import org.example.app.security.ContactConfirmationPayload;
import org.example.app.security.ContactConfirmationResponse;
import org.example.web.dto.RegisterFormDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class BookstoreUserRegister {
    private static final int USER_DEFAULT_BALANCE = 0;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final BookstoreUserDetailsService bookstoreUserDetailsService;

    @Autowired
    public BookstoreUserRegister(UserRepository userRepository, PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager,
                                 BookstoreUserDetailsService bookstoreUserDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.bookstoreUserDetailsService = bookstoreUserDetailsService;
    }

    public void registerNewUser(RegisterFormDto registerFormDto) {
        if (userRepository.findUserByEmail(registerFormDto.getEmail()) == null) {
            UserEntity user = new UserEntity();
            user.setBalance(USER_DEFAULT_BALANCE);
            user.setHash(UUID.randomUUID().toString());
            user.setRegTime(LocalDateTime.now());
            user.setName(registerFormDto.getName());
            user.setEmail(registerFormDto.getEmail());
            user.setPhone(registerFormDto.getPhone());
            user.setPassword(passwordEncoder.encode(registerFormDto.getPass()));
            userRepository.save(user);
        }
    }

    public ContactConfirmationResponse login(ContactConfirmationPayload payload) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getContact(),
                        payload.getCode()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        return response;
    }

    public Object getCurrentUser() {
        BookstoreUserDetails userDetails =
                (BookstoreUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getBookstoreUser();
    }
}
