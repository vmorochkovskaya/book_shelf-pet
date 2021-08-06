package org.example.web.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

public class Genre {
    private String id;
    @Size(max = 30, min = 3, message = "Genre is invalid")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
