package org.example.web.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LoginFormDto {

    private String username;
    private String password;
}
