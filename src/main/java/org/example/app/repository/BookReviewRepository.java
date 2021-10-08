package org.example.app.repository;

import org.example.app.entity.book.review.BookReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookReviewRepository extends JpaRepository<BookReview, Integer> {

    List<BookReview> findBookReviewsByBook_Slug(String slug);
}