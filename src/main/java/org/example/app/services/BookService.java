package org.example.app.services;

import org.example.app.reposotories.ProjectRepository;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final ProjectRepository<Book> bookRepo;

    @Autowired
    public BookService(ProjectRepository<Book> bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.retreiveAll();
    }

    public void saveBook(Book book) {

        if(book.getAuthor() != "" || book.getSize() != null || book.getTitle() != ""){
            bookRepo.store(book);
        }
    }

    public boolean removeBookById(String bookIdToRemove) {
        return bookRepo.removeItemById(bookIdToRemove);
    }

    public void removeBookByAuthor(String authorToRemove){
            List<Book> allBooks = this.getAllBooks();
            allBooks.stream().filter((Book book) ->
                    book.getAuthor().toLowerCase().contains(authorToRemove.toLowerCase()))
            .forEach((Book book) -> bookRepo.removeItemById(book.getId()));
    }

    public void removeBookByTitle(String titleToRemove){
        List<Book> allBooks = this.getAllBooks();
        allBooks.stream().filter((Book book) ->
                book.getTitle().toLowerCase().contains(titleToRemove.toLowerCase()))
                .forEach((Book book) -> bookRepo.removeItemById(book.getId()));
    }

    public void removeBookBySize(Integer sizeToRemove){
        List<Book> allBooks = this.getAllBooks();
        allBooks.stream().filter((Book book) ->
                book.getSize() == sizeToRemove).forEach((Book book) -> bookRepo.removeItemById(book.getId()));
    }

    public List<Book> searchBookByAuthor(String authorToSearch){
        List<Book> allBooks = this.getAllBooks();
        return allBooks.stream().filter((Book book) ->
                book.getAuthor().toLowerCase().contains(authorToSearch.toLowerCase())).collect(Collectors.toList());
    }

    public List<Book> searchBookByTitle(String titleToSearch){
        List<Book> allBooks = this.getAllBooks();
        return allBooks.stream().filter((Book book) ->
                book.getTitle().toLowerCase().contains(titleToSearch.toLowerCase())).collect(Collectors.toList());
    }

    public List<Book> searchBookBySize(Integer sizeToSearch){
        List<Book> allBooks = this.getAllBooks();
        return allBooks.stream().filter((Book book) -> Objects.equals(book.getSize(), sizeToSearch)).collect(Collectors.toList());
    }



}
