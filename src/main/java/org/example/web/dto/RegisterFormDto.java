package org.example.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegisterFormDto {

    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private String username;
    private String password;
}
