package org.example.app.service.cookie;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
public enum CookieType {
    CART("cartContents"),
    POSTPONED("postponedContents");

    private final String value;

    public String getValue(){
        return value;
    }
}
