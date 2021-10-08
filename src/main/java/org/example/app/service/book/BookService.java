package org.example.app.service.book;

import org.example.app.entity.book.Rate;
import org.example.app.entity.book.review.BookReview;
import org.example.app.entity.enums.Rating;
import org.example.app.entity.user.UserEntity;
import org.example.app.repository.BookRepository;
import org.example.app.entity.book.Book;
import org.example.app.repository.BookReviewRepository;
import org.example.app.repository.RateRepository;
import org.example.app.repository.UserRepository;
import org.example.web.dto.BookReviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class BookService {

    private final BookRepository bookRepo;
    private final RateRepository rateRepo;
    private final UserRepository userRepo;
    private final BookReviewRepository bookReviewRepo;

    @Autowired
    public BookService(BookRepository bookRepo, RateRepository rateRepo, UserRepository userRepo, BookReviewRepository bookReviewRepository) {
        this.bookRepo = bookRepo;
        this.rateRepo = rateRepo;
        this.userRepo = userRepo;
        this.bookReviewRepo = bookReviewRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    public Page<Book> getPageOfRecommendedBooks(Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepo.findAll(nextPage);
    }

    public Page<Book> getPageOfRecentBooks(Integer offset, Integer limit) {
        return getPageOfRecommendedBooks(offset, limit);
    }

    public Page<Book> getPageOfRecentBooksSortedByPubBaseDesc(Integer offset, Integer limit, String from, String to) {
        Pageable nextPage = PageRequest.of(offset, limit, Sort.by("pubDate").descending());
        if (from != null && to != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate localDateFrom = LocalDate.parse(from, formatter);
            LocalDate localDateTo = LocalDate.parse(to, formatter);
            return bookRepo.findBooksByPubDateBetween(localDateFrom, localDateTo, nextPage);
        }
        return bookRepo.findAll(nextPage);
    }

    public Page<Book> getBooksByTagId(Integer tagId, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepo.findBooksByTagsId(tagId, nextPage);
    }

    public Page<Book> getBooksByAuthorId(Integer authorId, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepo.findBooksByAuthorsId(authorId, nextPage);
    }

    public Page<Book> getBooksByGenreId(Integer genreId, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepo.findBooksByGenresId(genreId, nextPage);
    }

    public Book getBookBySlug(String slug) {
        return bookRepo.findBookBySlug(slug);
    }

    public Book save(Book book) {
        return bookRepo.save(book);
    }

    public void updateBookWithReview(String slug, String text) {
        Book book = bookRepo.findBookBySlug(slug);
        UserEntity userEntity = userRepo.findUserById(3);
        BookReview bookReview = new BookReview(text, LocalDateTime.now(), userEntity, book);
        bookReviewRepo.save(bookReview);
    }

    public List<BookReviewDto> getBookReviews(String slug) {
        List<BookReview> bookReviewList = bookReviewRepo.findBookReviewsByBook_Slug(slug);
        List<BookReviewDto> bookReviewDtoList = new ArrayList<>();
        for (BookReview bookReview : bookReviewList) {
            String timeFormatted = bookReview.getTime().format(DateTimeFormatter.ofPattern("dd.MM.yyy HH:mm"));
            int likesCount = (int) bookReview.getBookReviewLikeList().stream().filter(like -> like.getValue() > 0).count();
            int dislikesCount = (int) bookReview.getBookReviewLikeList().stream().filter(like -> like.getValue() < 0).count();
            bookReviewDtoList.add(new BookReviewDto(bookReview.getText(), timeFormatted, bookReview.getUser(), bookReview.getBook(),
                    likesCount, dislikesCount, likesCount - dislikesCount));
        }

        return bookReviewDtoList;
    }

    public Map<Rating, Long> getCountOfUsersMarkedBookPerRate(String slug) {
        List<Rate> ratings = rateRepo.findRatesByBooksSlug(slug);
        Map<Rating, Long> ratesPerRatingValue = ratings.stream()
                .collect(groupingBy(Rate::getRating, Collectors.counting()));
        for (Rating rating : Rating.values()) {
            if (!ratesPerRatingValue.containsKey(rating)) {
                ratesPerRatingValue.put(rating, 0L);
            }
        }
        return ratesPerRatingValue;
    }

    public List<Book> getBooksBySlugIn(String[] slugs) {
        return bookRepo.findBooksBySlugIn(slugs);
    }
}
