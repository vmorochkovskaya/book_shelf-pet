package org.example.app.service.book;

import org.example.app.entity.book.Book;
import org.example.app.entity.book.Rate;
import org.example.app.entity.book.links.Book2Rate;
import org.example.app.repository.Book2RateRepository;
import org.example.app.repository.BookRepository;
import org.example.app.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BooksRatingAndPopularityService {

    private final BookRepository bookRepo;
    private final Book2RateRepository book2RateRepository;
    private final RateRepository rateRepository;

    @Autowired
    public BooksRatingAndPopularityService(BookRepository bookRepo, Book2RateRepository book2RateRepository, RateRepository rateRepository) {
        this.bookRepo = bookRepo;
        this.book2RateRepository = book2RateRepository;
        this.rateRepository = rateRepository;
    }

    public List<Book> getPopularBooksSortedByPopularityDesc(Integer offset, Integer limit) {
        List<Book> allBooksList = bookRepo.findAll();
        List<Book> allBooksListSorted = allBooksList.stream().sorted(new BookPopularityComparator()).collect(Collectors.toList());
        return allBooksListSorted.stream().skip((long) limit * offset).limit(limit).collect(Collectors.toList());
    }

    public void saveBookRate(String slug, String rating) {
        Integer bookId = bookRepo.findBookBySlug(slug).getId();
        Integer rateId = rateRepository.findRateByValue(Integer.parseInt(rating)).getId();
        book2RateRepository.save(new Book2Rate(bookId, rateId));
    }

    public int countBookRating(String slug) {
        List<Rate> bookGrades = rateRepository.findByBooks_slug(slug);
        return bookGrades.size() == 0 ? 0 : Math.round(bookGrades.stream().map(Rate::getValue).reduce(0, Integer::sum) / bookGrades.size());
    }

    class BookPopularityComparator implements Comparator<Book> {
        public int compare(Book a, Book b) {
            return (int) (getPopularity(b) - getPopularity(a));
        }
        private double getPopularity (Book book) {
           return book.getNumberUsersBoughtBook() + 0.7 * book.getNumberUsersBookInCard() + 0.4 * book.getNumberUsersBookPostponed();
        }
    }

}
