package org.example.web.dto;


import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

public class Book {
    private String id;
    @Size(max = 30, min = 3, message = "Author name is invalid")
    private String author;
    @Size(max = 30, min = 2, message = "Title is invalid")
    private String title;
    @Digits(fraction = 0, integer = 5)
    private Integer size;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", size=" + size +
                '}';
    }
}
