package org.example.app.repository;

import org.example.app.entity.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    Page<Book> findBooksByPubDateBetween(LocalDate from, LocalDate to, Pageable p);

    Page<Book> findBooksByTagsId(Integer tagId, Pageable p);

    Page<Book> findBooksByAuthorsId(Integer tagId, Pageable p);

    Page<Book> findBooksByGenresId(Integer tagId, Pageable p);

    Book findBookBySlug(String slug);

    Book save(Book book);

    List<Book> findBooksBySlugIn(String[] slugs);

}
