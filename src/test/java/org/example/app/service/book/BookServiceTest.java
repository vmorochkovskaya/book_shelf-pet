package org.example.app.service.book;

import org.example.app.entity.author.Author;
import org.example.app.entity.book.Book;
import org.example.app.entity.book.Rate;
import org.example.app.entity.book.review.BookReview;
import org.example.app.entity.book.review.BookReviewLike;
import org.example.app.entity.genre.Genre;
import org.example.app.entity.tag.Tag;
import org.example.app.repository.*;
import org.example.app.service.cookie.CookieType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.servlet.http.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class BookServiceTest {
    private final BookService bookService;
    private final BooksRatingAndPopularityService booksRatingAndPopularityService;

    @MockBean
    HttpServletRequest httpServletRequest;
    @MockBean
    BookRepository bookRepository;
    @MockBean
    RateRepository rateRepository;
    @MockBean
    AuthorRepository authorRepository;
    @MockBean
    TagRepository tagRepository;
    @MockBean
    GenreRepository genreRepository;

    @Autowired
    BookServiceTest(BookService bookService, BooksRatingAndPopularityService booksRatingAndPopularityService) {
        this.bookService = bookService;
        this.booksRatingAndPopularityService = booksRatingAndPopularityService;
    }

    @Test
    void countBookReviewRatingPositive() {
        BookReview bookReview = new BookReview();
        BookReviewLike like = new BookReviewLike();
        like.setValue((short) 1);
        BookReviewLike like2 = new BookReviewLike();
        like2.setValue((short) 1);
        BookReviewLike like3 = new BookReviewLike();
        like3.setValue((short) -1);
        List<BookReviewLike> bookReviewLikeList = Arrays.asList(like, like2, like3);
        bookReview.setBookReviewLikeList(bookReviewLikeList);
        int rating = bookService.countBookReviewRating(bookReview);
        assertEquals(1, rating);
    }

    @Test
    void countBookReviewRatingNegative() {
        BookReview bookReview = new BookReview();
        BookReviewLike like = new BookReviewLike();
        like.setValue((short) 1);
        BookReviewLike like2 = new BookReviewLike();
        like2.setValue((short) -1);
        BookReviewLike like3 = new BookReviewLike();
        like3.setValue((short) -1);
        List<BookReviewLike> bookReviewLikeList = Arrays.asList(like, like2, like3);
        bookReview.setBookReviewLikeList(bookReviewLikeList);
        int rating = bookService.countBookReviewRating(bookReview);
        assertEquals(-1, rating);
    }

    @Test
    void countBookReviewRatingZero() {
        BookReview bookReview = new BookReview();
        List<BookReviewLike> bookReviewLikeList = new ArrayList<>();
        bookReview.setBookReviewLikeList(bookReviewLikeList);
        int rating = bookService.countBookReviewRating(bookReview);
        assertEquals(0, rating);
    }

    @Test
    void getPageOfRecommendedBooksNoCart() {
        int offset = 0;
        int limit = 6;
        Book book1 = new Book();
        book1.setSlug("111");
        book1.setPubDate(LocalDate.parse("2021-11-10"));
        book1.setRates(Arrays.asList(new Rate(5), new Rate(5), new Rate(1)));
        Book book2 = new Book();
        book2.setSlug("222");
        book2.setPubDate(LocalDate.parse("2021-11-12"));
        book2.setRates(Arrays.asList(new Rate(5), new Rate(5), new Rate(5)));
        Book book3 = new Book();
        book3.setSlug("333");
        book3.setPubDate(LocalDate.parse("2021-11-09"));
        book3.setRates(Collections.singletonList(new Rate(5)));
        Book book4 = new Book();
        book4.setSlug("444");
        book4.setPubDate(LocalDate.parse("2020-11-10"));
        book4.setRates(Arrays.asList(new Rate(5), new Rate(5)));
        Mockito.when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2, book3, book4));

        for (Book book : Arrays.asList(book1, book2, book3, book4)) {
            Mockito.when(rateRepository.findByBooks_slug(book.getSlug())).thenReturn(book.getRates());
        }
        List<Book> recommendedBooks = bookService.getPageOfRecommendedBooks(offset, limit, httpServletRequest.getCookies());
        assertFalse(recommendedBooks.isEmpty());
        assertFalse(recommendedBooks.contains(book1));
        assertFalse(recommendedBooks.contains(book4));
        assertTrue(recommendedBooks.contains(book3));
        assertTrue(recommendedBooks.contains(book2));
    }

    @Test
    void getPageOfRecommendedBooksInCart() {
        int offset = 0;
        int limit = 6;
        /// book1 /////
        Book book1 = new Book();
        book1.setSlug("111");
        book1.setPubDate(LocalDate.parse("2021-11-10"));
        Author author1 = new Author();
        author1.setName("Harry Potter");
        Author author12 = new Author();
        author12.setName("Agatha Kristi");
        book1.setAuthors(Arrays.asList(author12, author1));
        Genre genre1 = new Genre();
        genre1.setName("Fantasy");
        Genre genre12 = new Genre();
        genre12.setName("Documentary");
        book1.setGenres(Arrays.asList(genre1, genre12));
        Tag tag1 = new Tag();
        tag1.setName("tag1");
        Tag tag12 = new Tag();
        tag12.setName("tag12");
        book1.setTags(Arrays.asList(tag1, tag12));
        //////

        /// book2 /////
        Book book2 = new Book();
        book2.setSlug("222");
        book2.setPubDate(LocalDate.parse("2021-11-08"));
        Author author21 = new Author();
        author21.setName("George Orwell");
        Author author22 = new Author();
        author22.setName("Joanne Rowling");
        book2.setAuthors(Arrays.asList(author22, author21));
        Genre genre21 = new Genre();
        genre21.setName("Novel");
        Genre genre22 = new Genre();
        genre22.setName("crime");
        book2.setGenres(Arrays.asList(genre21, genre22));
        Tag tag21 = new Tag();
        tag21.setName("tag21");
        Tag tag22 = new Tag();
        tag22.setName("tag22");
        book2.setTags(Arrays.asList(tag21, tag22));
        //////

        /// book recommended by author
        Book bookRecommAuthor1 = new Book();
        bookRecommAuthor1.setSlug("1112");
        bookRecommAuthor1.setPubDate(LocalDate.parse("2021-11-10"));
        Author authorRecommAuthor1 = new Author();
        authorRecommAuthor1.setName("Harry Potter");
        Author authorRecommAuthor12 = new Author();
        authorRecommAuthor12.setName("Some other author");
        bookRecommAuthor1.setAuthors(Arrays.asList(authorRecommAuthor1, authorRecommAuthor12));
        bookRecommAuthor1.setGenres(Collections.singletonList(new Genre()));
        bookRecommAuthor1.setTags(Collections.singletonList(new Tag()));
        //////

        /// book recommended2 by author
        Book bookRecommAuthor2 = new Book();
        bookRecommAuthor2.setSlug("11123");
        bookRecommAuthor2.setPubDate(LocalDate.parse("2021-11-10"));
        Author authorRecommAuthor21 = new Author();
        authorRecommAuthor21.setName("Joanne Rowling");
        Author authorRecommAuthor22 = new Author();
        authorRecommAuthor12.setName("Some other author2");
        bookRecommAuthor2.setAuthors(Arrays.asList(authorRecommAuthor22, authorRecommAuthor21));
        bookRecommAuthor2.setGenres(Arrays.asList(new Genre()));
        bookRecommAuthor2.setTags(Arrays.asList(new Tag()));
        //////

        /// book recommended by genre
        Book bookRecommGenre1 = new Book();
        bookRecommGenre1.setSlug("11124");
        bookRecommGenre1.setPubDate(LocalDate.parse("2021-11-10"));
        Genre genreRecommGenre1 = new Genre();
        genreRecommGenre1.setName("Documentary");
        bookRecommGenre1.setGenres(Collections.singletonList(genreRecommGenre1));
        bookRecommGenre1.setAuthors(Collections.singletonList(new Author()));
        bookRecommGenre1.setTags(Collections.singletonList(new Tag()));
        //////

        /// book recommended by tag
        Book bookRecommTag1 = new Book();
        bookRecommTag1.setSlug("11125");
        bookRecommTag1.setPubDate(LocalDate.parse("2021-11-09"));
        Tag tagRecommTag1 = new Tag();
        tagRecommTag1.setName("tag12");
        bookRecommTag1.setTags(Collections.singletonList(tagRecommTag1));
        bookRecommTag1.setGenres(Collections.singletonList(new Genre()));
        bookRecommTag1.setAuthors(Collections.singletonList(new Author()));
        //////

        /// book simple /////
        Book bookSimple = new Book();
        bookSimple.setSlug("12345");
        bookSimple.setPubDate(LocalDate.parse("2021-09-10"));
        ///
        Cookie cookieCart = new Cookie(CookieType.CART.getValue(), "111");
        Cookie cookiePostponed = new Cookie(CookieType.POSTPONED.getValue(), "222");
        Cookie[] cookies = new Cookie[2];
        cookies[0] = cookieCart;
        cookies[1] = cookiePostponed;

        Mockito.when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2, bookRecommAuthor1,
                bookRecommGenre1, bookRecommAuthor2, bookRecommTag1, bookSimple));
        List<Author> listOngoingStubbing = new ArrayList<>();
        listOngoingStubbing.add(author1);
        listOngoingStubbing.add(author12);
        listOngoingStubbing.add(author21);
        listOngoingStubbing.add(author22);
        Mockito.when(authorRepository.findAuthorsByBooksSlugIn(new String[]{"111", "222"})).thenReturn(listOngoingStubbing);
        Mockito.when(genreRepository.findGenresByBooksSlugIn(new String[]{"111", "222"})).thenReturn(Arrays.asList(genre1, genre12, genre21, genre22));
        Mockito.when(tagRepository.findTagsByBooksSlugIn(new String[]{"111", "222"})).thenReturn(Arrays.asList(tag1, tag12, tag21, tag22));
        List<Book> recommendedBooks = bookService.getPageOfRecommendedBooks(offset, limit, cookies);

        assertEquals(6, recommendedBooks.size());
        assertTrue(recommendedBooks.contains(book1));
        assertTrue(recommendedBooks.contains(book2));
        assertTrue(recommendedBooks.contains(bookRecommAuthor1));
        assertTrue(recommendedBooks.contains(bookRecommTag1));
        assertTrue(recommendedBooks.contains(bookRecommGenre1));
        assertTrue(recommendedBooks.contains(bookRecommAuthor2));
    }
}