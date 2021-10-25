package org.example.app.entity.user;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import lombok.*;
import org.example.app.entity.book.Book;
import org.example.app.entity.book.review.BookReviewLike;
import org.example.app.entity.book.review.Message;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "user_entity")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//  идентификатор пользователя
    private Integer id;

    @Column(columnDefinition = "INT NOT NULL DEFAULT 0")
//  баланс личного счёта, по умолчанию 0
    private Integer balance;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
//  хэш пользователя, используемый для внешней идентификации пользователя с целью скрытия его ID
    private String hash;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
//  имя пользователя
    private String name;

    @Column(name = "reg_time", columnDefinition = "TIMESTAMP NOT NULL")
//  дата и время регистрации
    private LocalDateTime regTime;

    @Column(name = "password", columnDefinition = "VARCHAR(255) NOT NULL")
    private String password;

    @Column(name = "email", columnDefinition = "VARCHAR(255) NOT NULL")
    private String email;

    @Column(name = "phone", columnDefinition = "VARCHAR(255) NOT NULL")
    private String phone;

    @ManyToMany(mappedBy = "users")
//  список книг имеющих связь с данным пользователем
    private Set<Book> books;

    @ManyToMany(mappedBy = "usersDownloadedBooks")
//  список книг скаченных пользователем
    private Set<Book> downloadedBooks;

    @ManyToMany(mappedBy = "usersBalanceTransactions")
//  список книг имеющих транзакции с пользователем
    private Set<Book> booksBalanceTransactions;

    @ManyToMany(mappedBy = "usersBookReviews")
//  список книг о которых пользователь оставил отзывы
    private List<Book> booksReviews;

    @ToString.Exclude
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
//  контакты пользователя
    private UserContact userContact;

    @OneToMany(mappedBy = "user")
//  Список отзывов на которые оставлены лайк или дизлайк
    private Set<BookReviewLike>
            bookReviewLike;

    @OneToMany(mappedBy = "user")
//  Список сообщений отправленных пользователем
    private Set<Message> message;
}