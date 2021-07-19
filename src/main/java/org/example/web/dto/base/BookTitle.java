package org.example.web.dto.base;

import javax.validation.constraints.Size;

public class BookTitle {
    @Size(max = 30, min = 2, message = "Title is invalid")
    private String title;

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }
}
