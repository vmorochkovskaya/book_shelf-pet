package org.example.app.entity.book;

import lombok.Data;
import org.example.app.entity.book.links.Book2Author;
import org.example.app.entity.genre.Genre;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToMany(mappedBy = "book")
    private List<Book2Author> book2Authors;
    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String title;

    @Column(columnDefinition = "INT NOT NULL")
    private Integer price;

    @Column(columnDefinition = "DATE NOT NULL")
    private Date pubDate;

    @Column(columnDefinition = "SMALLINT NOT NULL")
    private Integer isBestseller;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String slug;

    @Column(columnDefinition = "VARCHAR(255)")
    private String image;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String description;

    @Column(columnDefinition = "SMALLINT NOT NULL DEFAULT 0")
    private Integer discount;
}
