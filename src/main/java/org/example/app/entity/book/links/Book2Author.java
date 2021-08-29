package org.example.app.entity.book.links;

import lombok.Data;
import org.example.app.entity.author.Author;
import org.example.app.entity.book.Book;

import javax.persistence.*;

@Data
@Entity
@Table(name = "book2author")
public class Book2Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @Column(columnDefinition = "INT NOT NULL  DEFAULT 0")
    private int sortIndex;
}
