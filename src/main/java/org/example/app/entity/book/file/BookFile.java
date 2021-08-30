package org.example.app.entity.book.file;

import lombok.*;

import javax.persistence.*;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "book_file")
public class BookFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT NOT NULL AUTO_INCREMENT")
//  id скачанного файла книги
    private Integer id;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
//  случайный хэш, предназначенный для идентификации файла при скачивании
    private String hash;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
//  путь к файлу
    private String path;

    @Column(name = "book_file_type_id", columnDefinition = "INT NOT NULL")
//  тип файла
    private Integer typeId;
}