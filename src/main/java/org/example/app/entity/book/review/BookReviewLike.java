package org.example.app.entity.book.review;

import lombok.*;
import org.example.app.entity.user.UserEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "book_review_like")
public class BookReviewLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TIMESTAMP NOT NULL")
//  дата и время, в которое поставлен лайк или дизлайк
    private LocalDateTime time;

    @Column(columnDefinition = "SMALLINT NOT NULL")
//  лайк (1) или дизлайк (-1)
    private Short value;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",
            referencedColumnName = "id")
//  идентификатор пользователя, поставившего лайк или дизлайк
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "review_id",
            columnDefinition = "INT NOT NULL")
//  идентификатор отзыва
    private BookReview bookReview;
}