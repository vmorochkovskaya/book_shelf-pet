package org.example.web.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString
@RequiredArgsConstructor
public class LoginFormDto {

    private String username;
    private String password;
}
