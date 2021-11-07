package org.example.app.service.user;

import io.jsonwebtoken.JwtException;
import org.example.app.entity.InvalidatedToken;
import org.example.app.entity.user.UserEntity;
import org.example.app.repository.UserRepository;
import org.example.app.security.BookstoreUserDetails;
import org.example.app.security.ContactConfirmationPayload;
import org.example.app.security.ContactConfirmationResponse;
import org.example.app.security.jwt.JWTUtil;
import org.example.app.service.token.IInvalidatedTokenService;
import org.example.web.dto.Oauth2UserDto;
import org.example.web.dto.RegisterFormDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class BookstoreUserRegister {
    private static final int USER_DEFAULT_BALANCE = 0;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final BookstoreUserDetailsService bookstoreUserDetailsService;
    private final JWTUtil jwtUtil;
    private final IInvalidatedTokenService invalidatedTokenService;

    @Autowired
    public BookstoreUserRegister(UserRepository userRepository, PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager,
                                 BookstoreUserDetailsService bookstoreUserDetailsService, JWTUtil jwtUtil, IInvalidatedTokenService invalidatedTokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.bookstoreUserDetailsService = bookstoreUserDetailsService;
        this.jwtUtil = jwtUtil;
        this.invalidatedTokenService = invalidatedTokenService;
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

    public ContactConfirmationResponse jwtLogin(ContactConfirmationPayload payload) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getContact(),
                payload.getCode()));
        BookstoreUserDetails userDetails =
                (BookstoreUserDetails) bookstoreUserDetailsService.loadUserByUsername(payload.getContact());
        String jwtToken = jwtUtil.generateToken(userDetails);
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult(jwtToken);
        return response;
    }

    public Object getCurrentUser() {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User) user;
            Oauth2UserDto oauth2UserDto = new Oauth2UserDto();
            oauth2UserDto.setName(oAuth2User.getAttribute("name"));
            oauth2UserDto.setEmail(oAuth2User.getAttribute("email"));
            oauth2UserDto.setPhone(null);
            return oauth2UserDto;
        } else if (user instanceof BookstoreUserDetails) {
            BookstoreUserDetails bookstoreUserDetails = (BookstoreUserDetails) user;
            return bookstoreUserDetails.getBookstoreUser();
        }
        return null;
    }

    public Object getCurrentUser(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = null;
        String username = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                    try {
                        username = jwtUtil.extractUsername(token);
                    } catch (JwtException e) {
                        return null;
                    }
                }
            }
        }
        if (username != null) {
            final String tokenToFindInRedis = token;
            BookstoreUserDetails userDetails =
                    (BookstoreUserDetails) bookstoreUserDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(token, userDetails) && this.invalidatedTokenService.getAllTokens().stream().noneMatch((InvalidatedToken x) ->
                    x.getId().equals(tokenToFindInRedis))) {
                return getCurrentUser();
            }
        } else {
            return getCurrentUser();
        }
        return null;
    }
}
