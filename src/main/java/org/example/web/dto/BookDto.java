package org.example.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.app.entity.author.Author;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private List<Author> authors;
    private String title;
    private Integer discount;
    private Integer price;
    private Integer rating;
    private String image;
    private String slug;

    public BookDto(List<Author> authors, String title, Integer discount, Integer price, String image, String slug) {
        this.authors = authors;
        this.title = title;
        this.discount = discount;
        this.price = price;
        this.image = image;
        this.slug = slug;
    }
}
