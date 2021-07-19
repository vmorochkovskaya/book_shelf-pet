package org.example.web.dto.base;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

public class BookSize {
    @Digits(fraction = 0, integer = 5)
    private Integer size;

    public Integer getSize(){
        return size;
    }

    public void setSize(Integer size){
        this.size = size;
    }
}
