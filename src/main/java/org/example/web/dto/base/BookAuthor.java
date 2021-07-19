package org.example.web.dto.base;

import javax.validation.constraints.Size;

public class BookAuthor {
    @Size(max = 30, min = 3, message = "Author is invalid")
    private String author;

    public String getAuthor(){
        return author;
    }

    public void setAuthor(String author){
        this.author = author;
    }
}
