package org.example.app.repository;

import org.example.app.entity.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface BookRepository extends JpaRepository<Book, Integer> {

    Page<Book> findBooksByPubDateBetween(LocalDate from, LocalDate to, Pageable p);

    @Query(value = "SELECT (number_users_bought_book + 0.7 * number_users_book_in_card + 0.4 * number_users_book_postponed) as p ORDER BY p DESC)", nativeQuery = true)
    Page<Book> findBooksByPopularityDesc(Pageable p);

}
