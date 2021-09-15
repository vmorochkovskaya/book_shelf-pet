package org.example.app.service.book;

import org.example.app.entity.book.Book;
import org.example.app.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BooksRatingAndPopularityService {

    private final BookRepository bookRepo;

    @Autowired
    public BooksRatingAndPopularityService(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }


    public List<Book> getPopularBooksSortedByPopularityDesc(Integer offset, Integer limit) {
        List<Book> allBooksList = bookRepo.findAll();
        List<Book> allBooksListSorted = allBooksList.stream().sorted(new BookPopularityComparator()).collect(Collectors.toList());
        return allBooksListSorted.stream().skip(limit * offset).limit(limit).collect(Collectors.toList());
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
