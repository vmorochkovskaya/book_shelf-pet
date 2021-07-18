package org.example.web.dto;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class BookIdToRemove {
    @Size(max = 30, min = 3, message = "Id is invalid")
    private String id;

    public String getId(){
        return id;
    }

    public void setId(String id){
       this.id = id;
    }
}
