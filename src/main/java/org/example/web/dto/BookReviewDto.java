package org.example.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.app.entity.book.Book;
import org.example.app.entity.user.UserEntity;

@Getter
@AllArgsConstructor
public class BookReviewDto {
    private String text;
    private String time;
    private UserEntity user;
    private Book book;
    private Integer likesCount;
    private Integer dislikesCount;
    private Integer rating;
}
