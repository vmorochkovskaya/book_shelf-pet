package org.example.app.entity.book.links;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "book2user")
public class Book2User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//  уникальный идентификатор связи
    private Integer id;

    @Column(name = "book_id", columnDefinition = "INT NOT NULL")
//  идентификатор книги
    private Integer bookId;

    @Column(columnDefinition = "TIMESTAMP NOT NULL")
//  дата и время возникновения привязки
    private LocalDateTime time;

    @Column(name = "type_id", columnDefinition = "INT NOT NULL")
//  тип привязки книги к пользователю
    private Integer typeId;

    @Column(name = "user_id", columnDefinition = "INT NOT NULL")
//  идентификатор пользователя
    private Integer userId;
}