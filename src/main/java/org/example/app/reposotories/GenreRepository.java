package org.example.app.reposotories;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.example.web.dto.Genre;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class GenreRepository implements ProjectRepository<Genre> {
    private final Logger logger = Logger.getLogger(BookRepository.class);
    private final List<Genre> repo = new ArrayList<>();

    @Override
    public List<Genre> retreiveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(Genre genre) {
        genre.setId(String.valueOf(genre.hashCode()));
        logger.info("store new book: " + genre);
        repo.add(genre);
    }

//    @Override
//    public boolean removeItemById(String bookIdToRemove) {
//        return false;
//    }
}
