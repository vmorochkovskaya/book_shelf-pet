package org.example.app.entity;

import lombok.*;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
public class Author {
    private String id;
    @Size(max = 30, min = 3, message = "Author is invalid")
    @NonNull
    private String name;
}
