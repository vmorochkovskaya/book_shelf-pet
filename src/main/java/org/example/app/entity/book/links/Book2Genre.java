package org.example.app.entity.book.links;

import javax.persistence.*;

@Entity
@Table(name = "book2genre")
public class Book2Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "INT NOT NULL")
    private int bookId;

    @Column(columnDefinition = "INT NOT NULL")
    private int genreId;
}
