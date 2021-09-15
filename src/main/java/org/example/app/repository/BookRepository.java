package org.example.app.repository;

import org.example.app.entity.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface BookRepository extends JpaRepository<Book, Integer> {

    Page<Book> findBooksByPubDateBetween(LocalDate from, LocalDate to, Pageable p);

}
