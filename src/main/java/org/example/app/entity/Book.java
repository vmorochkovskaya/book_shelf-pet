package org.example.app.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Book {
    private int id;
    private Author author;
    private Genre genre;
    private String title;
    private String priceOld;
    private String price;
}
