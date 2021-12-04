package org.example.app.service.book;

import org.example.app.entity.author.Author;
import org.example.app.entity.book.Rate;
import org.example.app.entity.book.review.BookReview;
import org.example.app.entity.enums.Rating;
import org.example.app.entity.genre.Genre;
import org.example.app.entity.tag.Tag;
import org.example.app.entity.user.UserEntity;
import org.example.app.repository.*;
import org.example.app.entity.book.Book;
import org.example.app.service.cookie.CookieType;
import org.example.app.service.cookie.ICookieService;
import org.example.web.dto.BookReviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final TagRepository tagsRepository;
    private final BookReviewRepository bookReviewRepo;
    private final BooksRatingAndPopularityService booksRatingAndPopularityService;
    private final ICookieService cookieService;

    @Autowired
    public BookService(BookRepository bookRepo, RateRepository rateRepo, UserRepository userRepo, AuthorRepository authorRepository, GenreRepository genreRepository, TagRepository tagsRepository, BookReviewRepository bookReviewRepository, BooksRatingAndPopularityService booksRatingAndPopularityService, ICookieService cookieService) {
        this.bookRepo = bookRepo;
        this.rateRepo = rateRepo;
        this.userRepo = userRepo;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.tagsRepository = tagsRepository;
        this.bookReviewRepo = bookReviewRepository;
        this.booksRatingAndPopularityService = booksRatingAndPopularityService;
        this.cookieService = cookieService;
    }

    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    public List<Book> getPageOfRecommendedBooks(Integer offset, Integer limit, Cookie[] cookies) {
        return this.getRecommendedBooksAccordingPreviousSelections(cookies).stream().skip((long) limit * offset).limit(limit).collect(Collectors.toList());
    }

    private List<Book> getRecommendedBooksAccordingPreviousSelections(Cookie[] cookies) {
        boolean isInCartNotPresent = true;
        if (cookies != null) {
            isInCartNotPresent = Arrays.stream(cookies).noneMatch(cookie -> cookie.getName().equals(CookieType.CART.getValue()) && !cookie.getValue().isEmpty() ||
                    cookie.getName().equals(CookieType.POSTPONED.getValue()) && !cookie.getValue().isEmpty());

        }
        System.out.println(isInCartNotPresent);
        List<Book> recentBooksByPubDate = getRecentBooksByPubDate();
        if (isInCartNotPresent) {
            return findRecommendedBooksNoCart(recentBooksByPubDate);
        } else {
            return findRecommendedBooksInCart(cookies, recentBooksByPubDate);
        }
    }

    private List<Book> findRecommendedBooksNoCart(List<Book> recentBooks) {
        List<Book> recommendedBooksRates = new ArrayList<>();
        for (Book book : recentBooks) {
            if (booksRatingAndPopularityService.countBookRating(book.getSlug()) == 5) {
                recommendedBooksRates.add(book);
            }
        }
        return recommendedBooksRates;
    }

    private List<Book> findRecommendedBooksInCart(Cookie[] cookies, List<Book> recentBooks) {
        System.out.println("rewirwpierp");
        List<Cookie> cartCookies = Arrays.stream(cookies).filter(c -> c.getName().equals(CookieType.CART.getValue()) ||
                c.getName().equals(CookieType.POSTPONED.getValue())).collect(Collectors.toList());
        List<String> slugs = new ArrayList<>();
        for (Cookie cookie : cartCookies) {
            System.out.println("trtert");
            slugs.addAll(Arrays.asList(cookieService.getBooksSlugsFromCookie(cookie.getValue())));
        }

        List<Author> cartBooksAuthors = authorRepository.findAuthorsByBooksSlugIn(slugs.toArray(new String[0]));
        System.out.println(cartBooksAuthors.get(0));
        List<Genre> cartBooksGenres = genreRepository.findGenresByBooksSlugIn(slugs.toArray(new String[0]));
        List<Tag> cartBooksTags = tagsRepository.findTagsByBooksSlugIn(slugs.toArray(new String[0]));
        Set<Book> booksWithCartBooksAuthors = recentBooks.stream().filter(b -> b.getAuthors().stream().anyMatch(cartBooksAuthors::contains)).collect(Collectors.toSet());
        Set<Book> booksWithCartBooksGenres = recentBooks.stream().filter(b -> b.getGenres().stream().anyMatch(cartBooksGenres::contains)).collect(Collectors.toSet());
        Set<Book> booksWithCartBooksTags = recentBooks.stream().filter(b -> b.getTags().stream().anyMatch(cartBooksTags::contains)).collect(Collectors.toSet());

        Set<Book> recommendedBooks = new HashSet<>();
        recommendedBooks.addAll(booksWithCartBooksAuthors);
        recommendedBooks.addAll(booksWithCartBooksGenres);
        recommendedBooks.addAll(booksWithCartBooksTags);
        return new ArrayList(recommendedBooks);
    }

    private List<Book> getRecentBooksByPubDate() {
        return bookRepo.findAll().stream().filter((Book b) -> b.getPubDate().plusDays(30).compareTo(LocalDate.now()) > 0)
                .collect(Collectors.toList());
    }

    public Page<Book> getPageOfRecentBooks(Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit, Sort.by("pubDate").descending());
        return bookRepo.findAll(nextPage);
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
        List<BookReview> bookReviewList = bookReviewRepo.findBookReviewsByBookSlug(slug);
        List<BookReviewDto> bookReviewDtoList = new ArrayList<>();
        for (BookReview bookReview : bookReviewList) {
            String timeFormatted = bookReview.getTime().format(DateTimeFormatter.ofPattern("dd.MM.yyy HH:mm"));
            int likesCount = (int) bookReview.getBookReviewLikeList().stream().filter(like -> like.getValue() > 0).count();
            int dislikesCount = (int) bookReview.getBookReviewLikeList().stream().filter(like -> like.getValue() < 0).count();
            bookReviewDtoList.add(new BookReviewDto(bookReview.getText(), timeFormatted, bookReview.getUser(), bookReview.getBook(),
                    countBookReviewLikes(bookReview), countBookReviewDislikes(bookReview), this.countBookReviewRating(bookReview)));
        }

        return bookReviewDtoList;
    }

    public int countBookReviewRating(BookReview bookReview) {
        return countBookReviewLikes(bookReview) - countBookReviewDislikes(bookReview);
    }

    public int countBookReviewLikes(BookReview bookReview) {
        return (int) bookReview.getBookReviewLikeList().stream().filter(like -> like.getValue() > 0).count();
    }

    public int countBookReviewDislikes(BookReview bookReview) {
        return (int) bookReview.getBookReviewLikeList().stream().filter(like -> like.getValue() < 0).count();
    }

    public Map<Rating, Long> getCountOfUsersMarkedBookPerRate(String slug) {
        List<Rate> ratings = rateRepo.findByBooks_slug(slug);
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
