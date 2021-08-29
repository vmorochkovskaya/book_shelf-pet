package org.example.app.entity.genre;

import lombok.Data;
import lombok.ToString;
import org.example.app.entity.book.Book;

import javax.persistence.*;
import java.util.List;

@Data@ToString
@Entity
@Table(name="genres")
public class Genre {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @OneToMany(mappedBy="genre")
    private List<Book> books;
}
