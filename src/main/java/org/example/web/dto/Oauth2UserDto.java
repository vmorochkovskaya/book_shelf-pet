package org.example.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Oauth2UserDto {
    private String name;
    private String email;
    private String phone;
}
