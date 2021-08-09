package org.example.app.entity;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Size;

@Data@ToString
public class Genre {
    private String id;
    @Size(max = 30, min = 3, message = "Genre is invalid")
    private String name;
}
