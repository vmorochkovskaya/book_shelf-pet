package org.example.app.entity.book.file;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "book_file")
public class BookFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String hash;

    @Column(columnDefinition = "INT NOT NULL")
    private String typeId;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String path;
}
