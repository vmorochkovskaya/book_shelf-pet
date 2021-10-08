package org.example.app.entity.book.review;

import lombok.*;
import org.example.app.entity.book.Book;
import org.example.app.entity.user.UserEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "book_review")
public class BookReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT NOT NULL AUTO_INCREMENT")
    private Integer id;

    @Column(columnDefinition = "TEXT NOT NULL")
//  текст отзыва
    private String text;

    @Column(columnDefinition = "TIMESTAMP NOT NULL")
//  время, когда оставлен отзыв
    private LocalDateTime time;

    @JoinColumn(name = "user_id",
            columnDefinition = "INT NOT NULL")
//  идентификатор пользователя, который написал данный отзыв
    @ManyToOne
    private UserEntity user;

    @JoinColumn(name = "book_id",
            columnDefinition = "INT NOT NULL")
//  идентификатор книги на которую ставится отзыв
    @ManyToOne
    private Book book;

    @OneToMany(mappedBy = "bookReview")
    private List<BookReviewLike> bookReviewLikeList;

    public BookReview(String text, LocalDateTime time, UserEntity user, Book book) {
        this.text = text;
        this.time = time;
        this.user = user;
        this.book = book;
    }
}