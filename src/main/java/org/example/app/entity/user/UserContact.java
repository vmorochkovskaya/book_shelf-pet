package org.example.app.entity.user;

import org.example.app.entity.enums.ContactType;

import javax.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "user_contact")
public class UserContact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT NOT NULL AUTO_INCREMENT")
    private Integer id;

    @Column(columnDefinition = "SMALLINT NOT NULL")
//  подтверждён ли контакт (0 или 1)
    private Short approved;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
//  код подтверждения
    private String code;

    @Column(name = "code_time",
            columnDefinition = "TIMESTAMP")
//  дата и время формирования кода подтверждения
    private LocalDateTime codeTime;

    @Column(name = "code_trails",
            columnDefinition = "INT")
//  количество попыток ввода кода подтверждения
    private Integer codeTrails;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
//  контакт (e-mail или телефон)
    private String contact;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    @Enumerated(EnumType.STRING)
//  тип контакта (телефон или e-mail)
    private ContactType type;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",
            referencedColumnName = "id")
//  идентификатор пользователя, к которому относится данный контакт
    private UserEntity user;
}