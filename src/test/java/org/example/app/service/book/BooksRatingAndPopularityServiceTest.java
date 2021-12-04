package org.example.app.service.book;

import org.example.app.entity.book.Rate;
import org.example.app.repository.RateRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class BooksRatingAndPopularityServiceTest {

    private final BooksRatingAndPopularityService ratingAndPopularityService;

    @MockBean
    private RateRepository rateRepositoryMock;

    @Autowired
    BooksRatingAndPopularityServiceTest(BooksRatingAndPopularityService ratingAndPopularityService) {
        this.ratingAndPopularityService = ratingAndPopularityService;
    }

    @Test
    void countBookRatingZero() {
        Mockito.when(rateRepositoryMock.findByBooks_slug("string")).thenReturn(new ArrayList<Rate>());
        int rating = ratingAndPopularityService.countBookRating("string");
        assertEquals(0, rating);
    }

    @Test
    void countBookRatingNotZeroAllRatesInteger() {
        Rate rate = new Rate(1);
        Rate rate2 = new Rate(2);
        Rate rate3 = new Rate(3);
        Rate rate4 = new Rate(4);
        Rate rate5 = new Rate(5);
        List<Rate> rates = Arrays.asList(rate, rate2, rate3, rate4, rate5);
        Mockito.when(rateRepositoryMock.findByBooks_slug(any())).thenReturn(rates);
        int rating = ratingAndPopularityService.countBookRating(any());
        assertEquals(3, rating);
    }

    @Test
    void countBookRatingNotZeroAllRatesBounding() {
        Rate rate = new Rate(1);
        Rate rate2 = new Rate(2);
        Rate rate3 = new Rate(3);
        Rate rate4 = new Rate(4);
        List<Rate> rates = Arrays.asList(rate, rate2, rate3, rate4);
        Mockito.when(rateRepositoryMock.findByBooks_slug(any())).thenReturn(rates);
        int rating = ratingAndPopularityService.countBookRating(any());
        assertEquals(2, rating);
    }
}