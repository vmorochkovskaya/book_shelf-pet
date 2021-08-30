package org.example.app.entity.other;

import javax.persistence.*;
import lombok.*;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "faq")
public class Faq {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "sort_index", columnDefinition = "INT NOT NULL  DEFAULT 0")
//  порядковый номер вопроса в списке вопросов на странице “Помощь”
    private Integer sortIndex;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
//  вопрос
    private String question;

    @Column(columnDefinition = "TEXT NOT NULL")
//  ответ в формате HTML
    private String answer;
}