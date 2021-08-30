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
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TIMESTAMP NOT NULL")
//  дата и время отправки сообщения
    private LocalDateTime time;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",
            referencedColumnName = "id")
//  если пользователь был авторизован
    private UserEntity user;

    @Column(columnDefinition = "VARCHAR(255)")
//  электронная почта пользователя, если он не был авторизован
    private String email;

    @Column(columnDefinition = "VARCHAR(255)")
//  имя пользователя, если он не был авторизован
    private String name;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
//  тема сообщения
    private String subject;

    @Column(columnDefinition = "TEXT NOT NULL")
//  текст сообщения
    private String text;
}