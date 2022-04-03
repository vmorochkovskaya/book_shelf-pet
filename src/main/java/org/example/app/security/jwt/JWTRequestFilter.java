package org.example.app.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import org.apache.log4j.Logger;
import org.example.app.entity.InvalidatedToken;
import org.example.app.repository.InvalidatedTokenRepository;
import org.example.app.security.BookstoreUserDetails;
import org.example.app.service.user.BookstoreUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {
    private final Logger logger = Logger.getLogger(JWTRequestFilter.class);
    private final BookstoreUserDetailsService bookstoreUserDetailsService;
    private final JWTUtil jwtUtil;
    private final InvalidatedTokenRepository invalidatedTokenRepository;


    public JWTRequestFilter(BookstoreUserDetailsService bookstoreUserDetailsService, JWTUtil jwtUtil, InvalidatedTokenRepository invalidatedTokenRepository) {
        this.bookstoreUserDetailsService = bookstoreUserDetailsService;
        this.jwtUtil = jwtUtil;
        this.invalidatedTokenRepository = invalidatedTokenRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        String username = null;
        Cookie[] cookies = httpServletRequest.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                    try {
                        username = jwtUtil.extractUsername(token);
                    } catch (ExpiredJwtException e) {
                        logger.warn("token has expired");
                    }
                }
            }
/**
 * user is not authenticated yet
 */
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                final String tokenToFindInRedis = token;
                BookstoreUserDetails userDetails =
                        (BookstoreUserDetails) bookstoreUserDetailsService.loadUserByUsername(username);
                if (jwtUtil.validateToken(token, userDetails) && this.invalidatedTokenRepository.findAll().stream().noneMatch((InvalidatedToken x) ->
                        x.getId().equals(tokenToFindInRedis))) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
