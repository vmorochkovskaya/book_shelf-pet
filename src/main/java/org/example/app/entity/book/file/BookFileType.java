package org.example.app.entity.book.file;

import lombok.*;
import org.example.app.entity.enums.BookFileTypeEnum;

import javax.persistence.*;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "book_file_type")
public class BookFileType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    @Enumerated(EnumType.STRING)
    private BookFileTypeEnum name;
}