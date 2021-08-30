package org.example.app.entity.other;

import lombok.*;

import javax.persistence.*;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "document")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "sort_index", columnDefinition = "INT NOT NULL  DEFAULT 0")
//  порядковый номер документа (для вывода на странице списка документов)
    private Integer sortIndex;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
//  мнемонический код документа, отображаемый в ссылке на страницу документа
    private String slug;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
//  наименование документа
    private String title;

    @Column(columnDefinition = "TEXT NOT NULL")
//  текст документа в формате HTML
    private String text;
}