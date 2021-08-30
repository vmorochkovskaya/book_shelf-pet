package org.example.app.entity.payments;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "balance_transaction")
public class BalanceTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "book_id",
            columnDefinition = "INT NOT NULL")
//  книга, за покупку которой произошло списание, или NULL
    private Integer bookId;

    @Column(columnDefinition = "TEXT NOT NULL")
//  описание транзакции: если зачисление, то откуда, если списание, то на что
    private String description;

    @Column(columnDefinition = "TIMESTAMP NOT NULL")
//  дата и время транзакции
    private LocalDateTime time;

    @Column(name = "user_id",
            columnDefinition = "INT NOT NULL")
//  идентификатор пользователя
    private Integer userId;

    @Column(columnDefinition = "INT NOT NULL  DEFAULT 0")
//  размер транзакции (положительный — зачисление, отрицательный — списание)
    private Integer value;
}