package org.example.app.entity.author;

import lombok.*;
import org.example.app.entity.book.Book;

import javax.persistence.*;
import java.util.Set;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT NOT NULL AUTO_INCREMENT")
//  id автора
    private Integer id;

    @Column(columnDefinition = "TEXT")
//  описание (биография, характеристика)
    private String description;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
//  имя и фамилия автора
    private String name;

    @Column(columnDefinition = "VARCHAR(255)")
//  ссылка на изображение с фотографией автора
    private String photo;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
//  мнемонический идентификатор автора, который будет отображаться в ссылке на его страницу
    private String slug;

    @ManyToMany(mappedBy = "authors")
//  список книг данного автора
    private Set<Book> books;
}

