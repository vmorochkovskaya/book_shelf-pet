package org.example.app.entity.book.links;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book2rate")
public class Book2Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT NOT NULL AUTO_INCREMENT")
//  id связи
    private Integer id;

    @Column(name = "book_id", columnDefinition = "INT NOT NULL")
//  идентификатор книги
    private Integer bookId;

    @Column(name = "rate_id", columnDefinition = "INT NOT NULL")
    private Integer rateId;

    public Book2Rate(Integer bookId, Integer rateId) {
        this.bookId = bookId;
        this.rateId = rateId;
    }
}