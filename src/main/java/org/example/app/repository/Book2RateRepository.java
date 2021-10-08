package org.example.app.repository;

import org.example.app.entity.book.links.Book2Rate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Book2RateRepository extends JpaRepository<Book2Rate, Integer> {

    Book2Rate save(Book2Rate book2Rate);
}
