package org.example.app.service.user;

import org.example.app.entity.user.UserEntity;
import org.example.app.repository.UserRepository;
import org.example.app.security.BookstoreUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BookstoreUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public BookstoreUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity bookstoreUser = userRepository.findUserByEmail(username);
        if (bookstoreUser != null) {
            return new BookstoreUserDetails(bookstoreUser);
        } else {
            System.out.println("UsernameNotFoundException occurred");
            throw new UsernameNotFoundException("user not found doh!");
        }
    }
}
