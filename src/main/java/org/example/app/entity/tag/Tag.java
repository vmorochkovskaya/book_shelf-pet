package org.example.app.entity.tag;

import lombok.*;
import org.example.app.entity.book.Book;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
//  наименование тэга
    private String name;

    @ManyToMany(mappedBy = "tags")
//  список книг относящихся к данному тэгу
    private Set<Book> books;
}